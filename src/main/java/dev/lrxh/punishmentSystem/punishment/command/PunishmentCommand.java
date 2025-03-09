package dev.lrxh.punishmentSystem.punishment.command;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import dev.lrxh.punishmentSystem.profile.Profile;
import dev.lrxh.punishmentSystem.profile.ProfileService;
import dev.lrxh.punishmentSystem.utils.CC;
import org.bukkit.entity.Player;

public class PunishmentCommand {

    @Command(name = "ban", desc = "", usage = "<player> <duration> [-p: perm] [-ip: ip]")
    public void ban(@Sender Player player, String playerName, String duration, boolean perm, boolean ip) {

    }

    @Command(name = "kick", desc = "", usage = "<player>")
    public void kick(@Sender Player player, String playerName) {

    }

    @Command(name = "mute", desc = "", usage = "<target> <duration> [-p: perm]")
    public void mute(@Sender Player player, Player target, String duration, boolean perm) {
        if (target == null) {
            player.sendMessage(CC.color("&cPlayer isn't online"));
            return;
        }
        ProfileService.get().get(target.getUniqueId()).mute(player.getUniqueId(), duration, perm);
    }

    @Command(name = "unmute", desc = "", usage = "<player>")
    public void unmute(@Sender Player player, Player target) {
        if (target == null) {
            return;
        }

        ProfileService.get().get(target.getUniqueId()).unMute();
    }
}
