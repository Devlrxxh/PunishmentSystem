package dev.lrxh.punishmentSystem.profile;

import dev.lrxh.punishmentSystem.punishment.Punishment;
import dev.lrxh.punishmentSystem.punishment.PunishmentType;
import dev.lrxh.punishmentSystem.utils.TimeUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Profile {
    private final UUID uuid;
    @Getter
    private final List<Punishment> punishments;

    public Profile(UUID uuid) {
        this.uuid = uuid;
        this.punishments = new ArrayList<>();

        load();
    }

    private void load() {

    }

    public boolean disallowJoin() {
        for (Punishment punishment : punishments) {
            if (punishment.getType().isBan() && punishment.isOngoing()) {
                return true;
            }
        }

        return false;
    }

    public boolean disallowTalk() {
        for (Punishment punishment : punishments) {
            if (punishment.getType().isMute() && punishment.isOngoing()) {
                return true;
            }
        }

        return false;
    }

    public void mute(UUID issuer, String duration, boolean perm) {
        if (perm) {
            punishments.add(new Punishment(PunishmentType.MUTE, issuer, TimeUtil.parse(duration)));
        } else {
            punishments.add(new Punishment(PunishmentType.TEMP_MUTE, issuer, TimeUtil.parse(duration)));
        }
    }

    public void unMute() {
        for (Punishment punishment : punishments) {
            if (punishment.getType().isMute()) punishment.setUndone();
        }
    }
}
