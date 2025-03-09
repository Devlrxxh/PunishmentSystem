package dev.lrxh.punishmentSystem.profile;

import dev.lrxh.punishmentSystem.database.DatabaseService;
import dev.lrxh.punishmentSystem.database.impl.DataDocument;

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
        DataDocument dataDocument = DatabaseService.get().getDatabase().getUserData(uuid);
        if (dataDocument == null) {
            Profile profile = new Profile(uuid);
            profile.save();
            profiles.put(uuid, profile);
            return profile;
        }

        profiles.put(uuid, Profile.deserialize(dataDocument));
        return profiles.get(uuid);
    }

    public void remove(UUID uuid) {
        profiles.get(uuid).save();
        profiles.remove(uuid);
    }

    public Profile get(UUID uuid) {
        return profiles.get(uuid);
    }
}
