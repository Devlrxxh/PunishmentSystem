package dev.lrxh.punishmentSystem;

import com.jonahseguin.drink.CommandService;
import com.jonahseguin.drink.Drink;
import dev.lrxh.punishmentSystem.punishment.command.PunishmentCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        loadCMD();
    }

    private void loadCMD() {
        CommandService drink = Drink.get(this);
        drink.register(new PunishmentCommand(), "punishment");
        drink.registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
