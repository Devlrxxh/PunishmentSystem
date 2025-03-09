package dev.lrxh.punishmentSystem.punishment.command;

import com.jonahseguin.drink.annotation.Command;
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

    @Command(name = "mute", desc = "", usage = "<target> <duration>")
    public void mute(@Sender Player player, Player target, String duration) {
        ProfileService.get().get(target.getUniqueId()).mute(player.getUniqueId(), duration, false);
    }

    @Command(name = "mute", desc = "", usage = "<target>")
    public void mute(@Sender Player player, Player target) {
        ProfileService.get().get(target.getUniqueId()).mute(player.getUniqueId(), "1s", true);
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

    @Command(name = "mute", desc = "", usage = "<targetName> <duration>")
    public void mute(@Sender Player player, String targetName, String duration) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);

        DataDocument dataDocument = DatabaseService.get().getDatabase().getUserData(target.getUniqueId());

        if (dataDocument == null) {
            player.sendMessage(CC.color("Player never joined server"));
            return;
        }

        Profile profile = Profile.deserialize(dataDocument);

        profile.mute(player.getUniqueId(), duration, false);

        profile.save();
    }

    @Command(name = "mute", desc = "", usage = "<targetName>")
    public void mute(@Sender Player player, String targetName) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);

        DataDocument dataDocument = DatabaseService.get().getDatabase().getUserData(target.getUniqueId());

        if (dataDocument == null) {
            player.sendMessage(CC.color("Player never joined server"));
            return;
        }

        Profile profile = Profile.deserialize(dataDocument);

        profile.mute(player.getUniqueId(), "1s", true);

        profile.save();
    }
}
