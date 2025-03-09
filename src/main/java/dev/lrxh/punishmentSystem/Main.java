package dev.lrxh.punishmentSystem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jonahseguin.drink.CommandService;
import com.jonahseguin.drink.Drink;
import dev.lrxh.punishmentSystem.configs.ConfigService;
import dev.lrxh.punishmentSystem.database.DatabaseService;
import dev.lrxh.punishmentSystem.profile.ProfileListener;
import dev.lrxh.punishmentSystem.punishment.command.PunishmentCommand;
import dev.lrxh.punishmentSystem.utils.menu.MenuListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Main extends JavaPlugin {
    public static Main instance;
    @Getter
    private Gson gson;

    @Override
    public void onEnable() {
        instance = this;
        gson = new GsonBuilder().setPrettyPrinting().create();

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
        List.of(
                new ProfileListener(),
                new MenuListener()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
