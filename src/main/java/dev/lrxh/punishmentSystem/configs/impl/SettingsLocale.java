package dev.lrxh.punishmentSystem.configs.impl;

import dev.lrxh.punishmentSystem.configs.ConfigService;
import dev.lrxh.punishmentSystem.configs.impl.handler.DataType;
import dev.lrxh.punishmentSystem.configs.impl.handler.IDataAccessor;
import dev.lrxh.punishmentSystem.configs.impl.handler.Replacement;
import dev.lrxh.punishmentSystem.utils.ClickableUtils;
import dev.lrxh.punishmentSystem.utils.ConfigFile;
import dev.lrxh.punishmentSystem.utils.PlayerUtils;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum SettingsLocale implements IDataAccessor {
    NEVER_JOINED("NEVER_JOINED", DataType.STRING_LIST, "&c<player> isn't online"),
    HISTORY_MENU_TITLE("HISTORY_MENU.TITLE", DataType.STRING, "&7<player> History"),
    HISTORY_MENU_SIZE("HISTORY_MENU.SIZE", DataType.INT, "27"),
    HISTORY_MENU_MUTE_ITEM_NAME("HISTORY_MENU.MUTE_NAME.NAME", DataType.STRING, "&c<name>"),
    HISTORY_MENU_MUTE_ITEM_LORE("HISTORY_MENU.MUTE_ITEM.LORE", DataType.STRING_LIST,
            "&fIssuer: &c<issuer>",
            "&fDuration: &c<duration>",
            "&fIssued on: &c<issuedOn>"),
    HISTORY_MENU_KICK_NAME("HISTORY_MENU.KICK_ITEM.NAME", DataType.STRING, "&e<name>"),
    HISTORY_MENU_KICK_LORE("HISTORY_MENU.KICK_ITEM.LORE", DataType.STRING_LIST,
            "&fIssuer: &c<issuer>",
            "&fIssued on: &c<issuedOn>"),
    HISTORY_MENU_BAN_ITEM_NAME("HISTORY_MENU.BAN_ITEM.NAME", DataType.STRING, "&c<name>"),
    HISTORY_MENU_BAN_ITEM_LORE("HISTORY_MENU.BAN_ITEM.LORE", DataType.STRING_LIST,
            "&fIssuer: &c<issuer>",
            "&fDuration: &c<duration>",
            "&fIssued on: &c<issuedOn>"),
    BAN_MESSAGE("BAN_MESSAGE", DataType.STRING_LIST,
            "&fIssuer: &c<issuer>",
            "&fDuration: &c<duration>",
            "&fIssued on: &c<issuedOn>"),
    KICK_MESSAGE("KICK.MESSAGE", DataType.STRING, "&cYou have been kicked by <issuer>"),
    MUTE_MESSAGE("MUTE_MESSAGE", DataType.STRING_LIST, "&cYou are muted and won't be able to talk!"),
    IP_BAN_MESSAGE("IP_BAN_MESSAGE", DataType.STRING, "&cYour IP is banned from this server."),
    DATABASE_TYPE("DATABASE.TYPE", "Database Type. MONGO, MYSQL, SQLITE", DataType.STRING, "SQLITE"),
    URI("DATABASE.URI", "Connection URI.", DataType.STRING, "NONE"),
    DATABASE("DATABASE.DATABASE_NAME", "Database Name", DataType.STRING, "punishments");

    private final String path;
    private final String comment;
    private final List<String> defaultValue = new ArrayList<>();
    private final DataType dataType;

    SettingsLocale(String path, @Nullable String comment, DataType dataType, String... defaultValue) {
        this.path = path;
        this.comment = comment;
        this.defaultValue.addAll(Arrays.asList(defaultValue));
        this.dataType = dataType;
    }

    SettingsLocale(String path, DataType dataType, String... defaultValue) {
        this.path = path;
        this.comment = null;
        this.defaultValue.addAll(Arrays.asList(defaultValue));
        this.dataType = dataType;
    }

    @Override
    public String getHeader() {
        return "";
    }

    @Override
    public ConfigFile getConfigFile() {
        return ConfigService.get().getSettingsConfig();
    }

    public void send(Player player, Replacement... replacements) {
        if (dataType.equals(DataType.STRING_LIST)) {
            for (String message : getStringList()) {
                if (message.equals("NONE")) continue;
                PlayerUtils.sendMessage(player, ClickableUtils.returnMessage(message, replacements));
            }
        } else if (dataType.equals(DataType.STRING)) {
            if (getString().equals("NONE")) return;
            PlayerUtils.sendMessage(player, ClickableUtils.returnMessage(getString(), replacements));
        }
    }
}