package dev.lrxh.punishmentSystem.configs;


import dev.lrxh.punishmentSystem.configs.impl.SettingsLocale;
import dev.lrxh.punishmentSystem.utils.ConfigFile;
import lombok.Getter;

@Getter
public class ConfigService {
    private static ConfigService instance;
    private ConfigFile settingsConfig;

    public static ConfigService get() {
        if (instance == null) instance = new ConfigService();

        return instance;
    }

    public void load() {
        settingsConfig = new ConfigFile("settings");

        SettingsLocale.URI.load();
    }
}
