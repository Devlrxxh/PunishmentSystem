package dev.lrxh.punishmentSystem.profile;

import dev.lrxh.punishmentSystem.configs.impl.SettingsLocale;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ProfileListener implements Listener {
    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        Profile profile = ProfileService.get().create(event.getUniqueId());
        if (profile.disallowJoin()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, profile.banMessage());
            ProfileService.get().remove(event.getUniqueId());
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        ProfileService.get().remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onMessage(AsyncChatEvent event) {
        if (ProfileService.get().get(event.getPlayer().getUniqueId()).disallowTalk()) {
            event.setCancelled(true);

            SettingsLocale.MUTE_MESSAGE.send(event.getPlayer());

            event.getPlayer().sendMessage("You cant talk ");
        }
    }
}
