package dev.lrxh.punishmentSystem.punishment.command;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Flag;
import com.jonahseguin.drink.annotation.Sender;
import dev.lrxh.punishmentSystem.configs.impl.SettingsLocale;
import dev.lrxh.punishmentSystem.configs.impl.handler.Replacement;
import dev.lrxh.punishmentSystem.database.DatabaseService;
import dev.lrxh.punishmentSystem.database.impl.DataDocument;
import dev.lrxh.punishmentSystem.profile.Profile;
import dev.lrxh.punishmentSystem.profile.ProfileService;
import dev.lrxh.punishmentSystem.punishment.menu.PunishmentHistoryMenu;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunishmentCommand {

    @Command(name = "ban", desc = "", usage = "<player> <duration> [-p: perm]")
    public void ban(@Sender CommandSender commandSender, String targetName, String duration, @Flag('p') boolean perm) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);

        if (target.isOnline()) {
            ProfileService.get().get(target.getUniqueId()).ban(commandSender instanceof Player player ? player.getUniqueId() : null, perm, duration);
            return;
        }

        DataDocument dataDocument = DatabaseService.get().getDatabase().getUserData(target.getUniqueId());

        if (dataDocument == null) {
            if (commandSender instanceof Player player)
                SettingsLocale.NEVER_JOINED.send(player, new Replacement("<player>", targetName));
            return;
        }

        Profile profile = Profile.deserialize(dataDocument);

        profile.ban(commandSender instanceof Player player ? player.getUniqueId() : null, perm, duration);
    }

    @Command(name = "unban", desc = "", usage = "<targetName>")
    public void unban(@Sender CommandSender commandSender, String targetName) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);

        if (target.isOnline()) {
            ProfileService.get().get(target.getUniqueId()).unBan();
            return;
        }

        DataDocument dataDocument = DatabaseService.get().getDatabase().getUserData(target.getUniqueId());

        if (dataDocument == null) {
            if (commandSender instanceof Player player)
                SettingsLocale.NEVER_JOINED.send(player, new Replacement("<player>", targetName));
            return;
        }

        Profile profile = Profile.deserialize(dataDocument);

        profile.unBan();

        profile.save();
    }

    @Command(name = "history", desc = "", usage = "<player>")
    public void history(@Sender Player player, String targetName) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);

        if (target.isOnline()) {
            new PunishmentHistoryMenu(ProfileService.get().get(target.getUniqueId()), targetName).open(player);
            return;
        }

        DataDocument dataDocument = DatabaseService.get().getDatabase().getUserData(target.getUniqueId());

        if (dataDocument == null) {
            SettingsLocale.NEVER_JOINED.send(player, new Replacement("<player>", targetName));
            return;
        }

        Profile profile = Profile.deserialize(dataDocument);

        new PunishmentHistoryMenu(profile, targetName).open(player);
    }

    @Command(name = "kick", desc = "", usage = "<target>")
    public void kick(@Sender CommandSender commandSender, Player target) {
        ProfileService.get().get(target.getUniqueId()).kick(commandSender instanceof Player player ? player.getUniqueId() : null, commandSender instanceof Player player ? player.getName() : "Console");
    }

    @Command(name = "unmute", desc = "", usage = "<targetName>")
    public void unmute(@Sender CommandSender commandSender, String targetName) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);

        if (target.isOnline()) {
            ProfileService.get().get(target.getUniqueId()).unMute();
            return;
        }

        DataDocument dataDocument = DatabaseService.get().getDatabase().getUserData(target.getUniqueId());

        if (dataDocument == null) {
            if (commandSender instanceof Player player)
                SettingsLocale.NEVER_JOINED.send(player, new Replacement("<player>", targetName));
            return;
        }

        Profile profile = Profile.deserialize(dataDocument);

        profile.unMute();

        profile.save();
    }

    @Command(name = "mute", desc = "", usage = "<targetName> <duration> [-p: perm]")
    public void mute(@Sender CommandSender commandSender, String targetName, String duration, @Flag('p') boolean perm) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);

        if (target.isOnline()) {
            ProfileService.get().get(target.getUniqueId()).mute(commandSender instanceof Player player ? player.getUniqueId() : null, duration, perm);
            return;
        }

        DataDocument dataDocument = DatabaseService.get().getDatabase().getUserData(target.getUniqueId());

        if (dataDocument == null) {
            if (commandSender instanceof Player player)
                SettingsLocale.NEVER_JOINED.send(player, new Replacement("<player>", targetName));
            return;
        }

        Profile profile = Profile.deserialize(dataDocument);

        profile.mute(commandSender instanceof Player player ? player.getUniqueId() : null, duration, perm);

        profile.save();
    }
}
