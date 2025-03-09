package dev.lrxh.punishmentSystem.profile;

import dev.lrxh.punishmentSystem.punishment.Punishment;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ProfileListener implements Listener {
    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        Profile profile = ProfileService.get().create(event.getUniqueId());
        if (profile.disallowJoin()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Component.text("Banned"));
            ProfileService.get().remove(event.getUniqueId());
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        ProfileService.get().remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        if (ProfileService.get().get(event.getPlayer().getUniqueId()).disallowTalk()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("You cant talk ");
        }
    }
}
