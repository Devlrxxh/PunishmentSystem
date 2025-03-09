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

    public Profile create(UUID uuid) {
        profiles.put(uuid, new Profile(uuid));
        return profiles.get(uuid);
    }

    public void remove(UUID uuid) {
        profiles.remove(uuid);
    }

    public Profile get(UUID uuid) {
        return profiles.get(uuid);
    }
}
