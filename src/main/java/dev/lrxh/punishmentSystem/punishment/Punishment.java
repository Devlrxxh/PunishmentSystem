package dev.lrxh.punishmentSystem.punishment;

import lombok.Getter;
import java.util.UUID;

@Getter
public class Punishment {
    private final PunishmentType type;
    private final UUID issuer;
    private final long duration;
    private final long issuedAt;
    private boolean undone;

    public Punishment(PunishmentType type, UUID issuer, long duration) {
        this.type = type;
        this.issuer = issuer;
        this.duration = duration;
        this.issuedAt = System.currentTimeMillis();
        this.undone = false;
    }

    public void setUndone() {
        this.undone = true;
    }

    public boolean isOngoing() {
        if (undone) {
            return false;
        }
        return (System.currentTimeMillis() - issuedAt) < duration;
    }
}
