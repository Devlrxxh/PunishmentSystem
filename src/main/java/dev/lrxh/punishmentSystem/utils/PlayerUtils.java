package dev.lrxh.punishmentSystem.utils;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;

import java.util.List;

@UtilityClass
public class PlayerUtils {
    public void sendMessage(Player player, List<Object> content) {
        TextComponent.Builder builder = Component.text();

        for (Object obj : content) {
            if (obj instanceof String message) {
                builder.append(Component.text(message));
            } else if (obj instanceof TextComponent) {
                builder.append((TextComponent) obj);
            }
        }

        sendMessage(player, builder);
    }


    public void sendMessage(Player player, Object message) {
        if (player == null) return;

        if (message instanceof String) {
            player.sendMessage((String) message);
        } else if (message instanceof Component) {
            player.sendMessage((Component) message);
        } else if (message instanceof TextComponent.Builder) {
            player.sendMessage((TextComponent.Builder) message);
        }
    }
}
