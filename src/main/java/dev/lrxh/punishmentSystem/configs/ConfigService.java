package dev.lrxh.punishmentSystem.configs;


import dev.lrxh.punishmentSystem.configs.impl.MessagesLocale;
import dev.lrxh.punishmentSystem.configs.impl.SettingsLocale;
import dev.lrxh.punishmentSystem.utils.ConfigFile;
import lombok.Getter;

@Getter
public class ConfigService {
    private static ConfigService instance;
    private ConfigFile settingsConfig;
    private ConfigFile messagesConfig;

    public static ConfigService get() {
        if (instance == null) instance = new ConfigService();

        return instance;
    }

    public void load() {
        settingsConfig = new ConfigFile("settings");
        messagesConfig = new ConfigFile("messages");

        SettingsLocale.URI.load();
        MessagesLocale.NEVER_JOINED.load();
    }
}
