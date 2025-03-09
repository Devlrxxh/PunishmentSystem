package dev.lrxh.punishmentSystem.punishment.command;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import org.bukkit.entity.Player;

public class PunishmentCommand {

    @Command(name = "ban", desc = "", usage = "<player> <duration> [-p: perm] [-ip: ip]")
    public void ban(@Sender Player player, String playerName, String duration, boolean perm, boolean ip) {

    }

    @Command(name = "kick", desc = "", usage = "<player>")
    public void kick(@Sender Player player, String playerName) {

    }

    @Command(name = "mute", desc = "", usage = "<player> <duration> [-p: perm]")
    public void mute(@Sender Player player, String playerName, String duration, boolean perm) {

    }
}
