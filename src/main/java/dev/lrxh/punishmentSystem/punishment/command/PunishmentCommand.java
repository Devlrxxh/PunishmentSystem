package dev.lrxh.punishmentSystem.punishment.command;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Flag;
import com.jonahseguin.drink.annotation.Sender;
import dev.lrxh.punishmentSystem.configs.impl.MessagesLocale;
import dev.lrxh.punishmentSystem.configs.impl.handler.Replacement;
import dev.lrxh.punishmentSystem.database.DatabaseService;
import dev.lrxh.punishmentSystem.database.impl.DataDocument;
import dev.lrxh.punishmentSystem.profile.Profile;
import dev.lrxh.punishmentSystem.profile.ProfileService;
import dev.lrxh.punishmentSystem.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunishmentCommand {

    @Command(name = "ban", desc = "", usage = "<player> <duration> [-p: perm] [-i: ip]")
    public void ban(@Sender Player player, String playerName, String duration, @Flag('p') boolean perm, @Flag('i') boolean ip) {

    }

    @Command(name = "kick", desc = "", usage = "<target>")
    public void kick(@Sender Player player, Player target) {
        ProfileService.get().get(target.getUniqueId()).kick(player.getUniqueId());
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
                MessagesLocale.NEVER_JOINED.send(player, new Replacement("<player>", targetName));
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
            if (commandSender instanceof Player player) {
                ProfileService.get().get(target.getUniqueId()).mute(player.getUniqueId(), duration, perm);
            } else {
                ProfileService.get().get(target.getUniqueId()).mute(null, duration, perm);
            }
            return;
        }

        DataDocument dataDocument = DatabaseService.get().getDatabase().getUserData(target.getUniqueId());

        if (dataDocument == null) {
            if (commandSender instanceof Player player)
                MessagesLocale.NEVER_JOINED.send(player, new Replacement("<player>", targetName));
            return;
        }

        Profile profile = Profile.deserialize(dataDocument);

        profile.mute(commandSender instanceof Player player ? player.getUniqueId() : null, duration, perm);

        profile.save();
    }
}
