package dev.lrxh.punishmentSystem.utils;

import dev.lrxh.punishmentSystem.Main;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
public class ConfigFile {
    private final File file;
    private final YamlConfiguration configuration;

    public ConfigFile(String name) {
        File dataFolder = new File(Main.instance.getDataFolder().getParentFile(), "Neptune");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        this.file = new File(dataFolder, name + ".yml");

        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (!created) {
                    throw new IOException("File was not created.");
                }
            } catch (IOException e) {
                Main.instance.getLogger().severe(e.getMessage());
            }
        }

        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            Main.instance.getLogger().severe(e.getMessage());
        }
    }
}