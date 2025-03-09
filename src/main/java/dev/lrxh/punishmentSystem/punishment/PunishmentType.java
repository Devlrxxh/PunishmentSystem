package dev.lrxh.punishmentSystem.punishment;

public enum PunishmentType {
    BAN,
    TEMP_BAN,
    MUTE,
    TEMP_MUTE,
    IP_BAN,
    KICK;

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
}
