package dev.lrxh.punishmentSystem;

import com.jonahseguin.drink.CommandService;
import com.jonahseguin.drink.Drink;
import dev.lrxh.punishmentSystem.configs.ConfigService;
import dev.lrxh.punishmentSystem.database.DatabaseService;
import dev.lrxh.punishmentSystem.profile.ProfileListener;
import dev.lrxh.punishmentSystem.punishment.command.PunishmentCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class Main extends JavaPlugin {
    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        loadCMD();
        loadConfigs();
        loadListeners();

        DatabaseService.get();
    }

    private void loadCMD() {
        CommandService drink = Drink.get(this);
        drink.register(new PunishmentCommand(), "punishment");
        drink.registerCommands();
    }

    private void loadConfigs() {
        ConfigService.get().load();
    }

    private void loadListeners() {
        Arrays.asList(
                new ProfileListener()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
