package dev.lrxh.punishmentSystem.punishment.command;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Flag;
import com.jonahseguin.drink.annotation.Sender;
import dev.lrxh.punishmentSystem.database.DatabaseService;
import dev.lrxh.punishmentSystem.database.impl.DataDocument;
import dev.lrxh.punishmentSystem.profile.Profile;
import dev.lrxh.punishmentSystem.profile.ProfileService;
import dev.lrxh.punishmentSystem.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.xml.crypto.Data;

public class PunishmentCommand {

    @Command(name = "ban", desc = "", usage = "<player> <duration> [-p: perm] [-ip: ip]")
    public void ban(@Sender Player player, String playerName, String duration, boolean perm, boolean ip) {

    }

    @Command(name = "kick", desc = "", usage = "<target>")
    public void kick(@Sender Player player, Player target) {
        ProfileService.get().get(target.getUniqueId()).kick(player.getUniqueId());
    }

    @Command(name = "mute", desc = "", usage = "<target> <duration> [-p: perm]")
    public void mute(@Sender Player player, Player target, String duration, @Flag('p') boolean perm) {
        ProfileService.get().get(target.getUniqueId()).mute(player.getUniqueId(), duration, perm);
    }

    @Command(name = "unmute", desc = "", usage = "<target>")
    public void unmute(@Sender Player player, Player target) {
        ProfileService.get().get(target.getUniqueId()).unMute();
    }

    @Command(name = "unmute", desc = "", usage = "<targetName>")
    public void unmute(@Sender Player player, String targetName) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);

        DataDocument dataDocument = DatabaseService.get().getDatabase().getUserData(target.getUniqueId());

        if (dataDocument == null) {
            player.sendMessage(CC.color("Player never joined server"));
            return;
        }

        Profile profile = Profile.deserialize(dataDocument);

        profile.unMute();

        profile.save();
    }

    @Command(name = "mute", desc = "", usage = "<targetName> <duration> [-p: perm]")
    public void mute(@Sender Player player, String targetName, String duration, @Flag('p') boolean perm) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);

        DataDocument dataDocument = DatabaseService.get().getDatabase().getUserData(target.getUniqueId());

        if (dataDocument == null) {
            player.sendMessage(CC.color("Player never joined server"));
            return;
        }

        Profile profile = Profile.deserialize(dataDocument);

        profile.mute(player.getUniqueId(), duration, perm);

        profile.save();
    }
}
