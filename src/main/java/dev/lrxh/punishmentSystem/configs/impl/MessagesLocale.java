package dev.lrxh.punishmentSystem.configs.impl;

import dev.lrxh.punishmentSystem.configs.ConfigService;
import dev.lrxh.punishmentSystem.configs.impl.handler.DataType;
import dev.lrxh.punishmentSystem.configs.impl.handler.IDataAccessor;
import dev.lrxh.punishmentSystem.configs.impl.handler.Replacement;
import dev.lrxh.punishmentSystem.utils.ClickableUtils;
import dev.lrxh.punishmentSystem.utils.ConfigFile;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import dev.lrxh.punishmentSystem.utils.PlayerUtils;

@Getter
public enum MessagesLocale implements IDataAccessor {
    NEVER_JOINED("NEVER_JOINED", DataType.STRING_LIST, "&c<player> isn't online");

    private final String path;
    private final String comment;
    private final List<String> defaultValue = new ArrayList<>();
    private final DataType dataType;

    MessagesLocale(String path, @Nullable String comment, DataType dataType, String... defaultValue) {
        this.path = path;
        this.comment = comment;
        this.defaultValue.addAll(Arrays.asList(defaultValue));
        this.dataType = dataType;
    }

    MessagesLocale(String path, DataType dataType, String... defaultValue) {
        this.path = path;
        this.comment = null;
        this.defaultValue.addAll(Arrays.asList(defaultValue));
        this.dataType = dataType;
    }

    @Override
    public ConfigFile getConfigFile() {
        return ConfigService.get().getMessagesConfig();
    }

    @Override
    public String getHeader() {
        return "Replace with NONE to disable";
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