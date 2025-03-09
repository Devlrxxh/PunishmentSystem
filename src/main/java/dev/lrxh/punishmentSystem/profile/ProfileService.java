package dev.lrxh.punishmentSystem.profile;

import org.bukkit.entity.Player;

import java.util.IdentityHashMap;
import java.util.UUID;

public class ProfileService {
    private static ProfileService instance;
    private final IdentityHashMap<UUID, Profile> profiles = new IdentityHashMap<>();

    public static ProfileService get() {
        if (instance == null) instance = new ProfileService();

        return instance;
    }

    public void create(Player player) {
        profiles.put(player.getUniqueId(), new Profile(player.getUniqueId()));
    }
}
