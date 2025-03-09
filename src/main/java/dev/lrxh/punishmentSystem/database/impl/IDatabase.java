package dev.lrxh.punishmentSystem.database.impl;

import dev.lrxh.punishmentSystem.configs.impl.SettingsLocale;

import java.util.List;
import java.util.UUID;

public interface IDatabase {
    String uri = SettingsLocale.URI.getString();
    String database = SettingsLocale.DATABASE.getString();

    IDatabase load();

    DataDocument getUserData(UUID playerUUID);

    void replace(UUID playerUUID, DataDocument newDocument);

    void replace(String playerUUID, DataDocument newDocument);

    List<DataDocument> getAll();
}
