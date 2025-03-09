package dev.lrxh.punishmentSystem.database;

import dev.lrxh.punishmentSystem.Main;
import dev.lrxh.punishmentSystem.configs.impl.SettingsLocale;
import dev.lrxh.punishmentSystem.database.impl.DatabaseType;
import dev.lrxh.punishmentSystem.database.impl.IDatabase;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class DatabaseService {
    private static DatabaseService instance;
    private IDatabase database = null;

    public DatabaseService() {
        String uri = SettingsLocale.URI.getString();
        if (uri != null && (uri.isEmpty() || uri.equals("NONE")) &&
                !SettingsLocale.DATABASE_TYPE.getString().equalsIgnoreCase("SQLITE")) {
            Main.instance.getLogger().severe("URI is missing or empty");
            Bukkit.getPluginManager().disablePlugin(Main.instance);
        }

        try {
            this.database = DatabaseType.valueOf(SettingsLocale.DATABASE_TYPE.getString()).getIDatabase().load();
        } catch (RuntimeException e) {
            Main.instance.getLogger().severe("Unknown database type in settings.yml");
        }
    }

    public static DatabaseService get() {
        if (instance == null) instance = new DatabaseService();

        return instance;
    }
}
