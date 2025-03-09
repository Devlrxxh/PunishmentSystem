package dev.lrxh.punishmentSystem.profile;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ProfileListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ProfileService.get().create(event.getPlayer());
    }
}
