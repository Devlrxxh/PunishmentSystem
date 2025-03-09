package dev.lrxh.punishmentSystem.utils;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class CC {
    private final Pattern COLOR_PATTERN = Pattern.compile("&[0-9a-fA-Fk-oK-OrR]");
    private final Pattern HEX_PATTERN = Pattern.compile("&#([a-fA-F0-9]{6})");

    public String color(String message) {
        final char colorChar = ChatColor.COLOR_CHAR;

        final Matcher matcher = HEX_PATTERN.matcher(message);
        final StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

        while (matcher.find()) {
            final String group = matcher.group(1);

            matcher.appendReplacement(buffer, colorChar + "x"
                    + colorChar + group.charAt(0) + colorChar + group.charAt(1)
                    + colorChar + group.charAt(2) + colorChar + group.charAt(3)
                    + colorChar + group.charAt(4) + colorChar + group.charAt(5));
        }

        String r = matcher.appendTail(buffer).toString();

        return ChatColor.translateAlternateColorCodes('&', r);
    }
}