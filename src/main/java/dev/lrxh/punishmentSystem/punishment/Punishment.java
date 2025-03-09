package dev.lrxh.punishmentSystem.punishment;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Punishment {
    @SerializedName("type")
    private final PunishmentType type;
    @SerializedName("issuer")
    private final UUID issuer;
    @SerializedName("duration")
    private final long duration;
    @SerializedName("issuedAt")
    private final long issuedAt;
    @SerializedName("unDone")
    private boolean unDone;
    @SerializedName("perm")
    private final boolean perm;

    public Punishment(PunishmentType type, UUID issuer, long duration, boolean perm) {
        this.type = type;
        this.issuer = issuer;
        this.duration = duration;
        this.issuedAt = System.currentTimeMillis();
        this.unDone = false;
        this.perm = perm;
    }

    public void setUndone() {
        this.unDone = true;
    }

    public boolean isOngoing() {
        if (unDone) {
            return false;
        }

        if (perm) {
            return true;
        }

        return (System.currentTimeMillis() - issuedAt) < duration;
    }
}
