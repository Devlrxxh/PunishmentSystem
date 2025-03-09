package dev.lrxh.punishmentSystem.punishment;

import lombok.Getter;

@Getter
public enum PunishmentType {
    BAN("Permanent Ban"),
    TEMP_BAN("Temporary Ban"),
    MUTE("Permanent mute"),
    TEMP_MUTE("Temporary Mute"),
    IP_BAN("IP Ban"),
    KICK("Kick");

    private final String name;

    PunishmentType(String name) {
        this.name = name;
    }

    public boolean isBan() {
        switch (this) {
            case BAN, IP_BAN, TEMP_BAN -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public boolean isMute() {
        switch (this) {
            case MUTE, TEMP_MUTE -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public boolean isKick() {
        return this == PunishmentType.KICK;
    }
}
