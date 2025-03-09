package dev.lrxh.punishmentSystem.profile;

import dev.lrxh.punishmentSystem.Main;
import dev.lrxh.punishmentSystem.configs.impl.SettingsLocale;
import dev.lrxh.punishmentSystem.database.DatabaseService;
import dev.lrxh.punishmentSystem.database.impl.DataDocument;
import dev.lrxh.punishmentSystem.punishment.Punishment;
import dev.lrxh.punishmentSystem.punishment.PunishmentType;
import dev.lrxh.punishmentSystem.utils.CC;
import dev.lrxh.punishmentSystem.utils.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class Profile {
    private final UUID uuid;
    @Getter
    private final List<Punishment> punishments;

    public Profile(UUID uuid) {
        this.uuid = uuid;
        this.punishments = new ArrayList<>();
    }

    private static Punishment deserialize(String serialized) {
        return Main.instance.getGson().fromJson(serialized, Punishment.class);
    }

    private static List<Punishment> deserializePunishments(List<String> punishments) {
        if (punishments == null || punishments.isEmpty()) {
            return new ArrayList<>();
        }

        List<Punishment> deserialized = new ArrayList<>();
        for (String serialized : punishments) {
            deserialized.add(deserialize(serialized));
        }
        return deserialized;
    }

    public static Profile deserialize(DataDocument dataDocument) {
        return new Profile(
                UUID.fromString(dataDocument.getString("uuid")),
                deserializePunishments(dataDocument.getList("punishments")));

    }

    public void save() {
        DatabaseService.get().getDatabase().replace(uuid, serialize());
    }

    public boolean disallowJoin() {
        for (Punishment punishment : punishments) {
            if (punishment.getType().isBan() && punishment.isOngoing()) {
                return true;
            }
        }

        return false;
    }

    public Component banMessage() {
        Component component = Component.text("");

        for (Punishment punishment : punishments) {
            if (punishment.getType().isBan() && punishment.isOngoing()) {
                for (String line : SettingsLocale.BAN_MESSAGE.getStringList()) {
                    line = line.replaceAll("<issuer>", punishment.getIssuer());
                    line = line.replaceAll("<issuedOn>", punishment.getIssuedOnString());
                    line = line.replaceAll("<duration>", TimeUtil.unparse(punishment.getDuration()));

                    component = component.append(Component.text(CC.color(line)));
                    component = component.appendNewline();
                }
            }
        }

        return component;
    }

    public boolean disallowTalk() {
        for (Punishment punishment : punishments) {
            if (punishment.getType().isMute() && punishment.isOngoing()) {
                return true;
            }
        }

        return false;
    }

    public void mute(UUID issuer, String duration, boolean perm) {
        if (perm) {
            addPunishment(new Punishment(PunishmentType.MUTE, issuer, TimeUtil.parse(duration), true));
        } else {
            addPunishment(new Punishment(PunishmentType.TEMP_MUTE, issuer, TimeUtil.parse(duration), false));
        }
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void kick(UUID issuer, String issuerName) {
        addPunishment(new Punishment(PunishmentType.KICK, issuer, 1, false));
        Player player = getPlayer();
        if (player == null) return;
        player.kick(Component.text(SettingsLocale.KICK_MESSAGE.getString().replace("<issuer>", issuerName)));
    }

    public void unMute() {
        for (Punishment punishment : punishments) {
            if (punishment.getType().isMute()) punishment.setUndone();
        }
    }

    public void unBan() {
        for (Punishment punishment : punishments) {
            if (punishment.getType().isBan()) punishment.setUndone();
        }
    }

    public void ban(UUID issuer, boolean ip, boolean perm, String duration) {
        PunishmentType punishmentType = null;

        if (ip) {
            punishmentType = PunishmentType.IP_BAN;
        }

        if (perm) {
            punishmentType = PunishmentType.BAN;
        }

        if (punishmentType == null) {
            punishmentType = PunishmentType.TEMP_BAN;
        }

        addPunishment(new Punishment(punishmentType, issuer, TimeUtil.parse(duration), perm));

        Player player = getPlayer();
        if (player == null) return;

        player.kick(banMessage());
    }

    private List<String> serializePunishments() {
        if (punishments.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<String> serialized = new ArrayList<>();
        for (Punishment punishment : punishments) {
            serialized.add(serialize(punishment));
        }
        return serialized;
    }

    private String serialize(Punishment punishment) {
        return Main.instance.getGson().toJson(punishment);
    }

    private DataDocument serialize() {
        Document document = new Document();

        document.put("punishments", serializePunishments());
        document.put("uuid", uuid.toString());

        return new DataDocument(document);
    }

    public void addPunishment(Punishment punishment) {
        if (punishments.size() >= 7) {
            punishments.remove(0);
        }
        punishments.add(punishment);
    }
}
