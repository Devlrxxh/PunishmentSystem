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

    public Punishment(PunishmentType type, UUID issuer, long duration) {
        this.type = type;
        this.issuer = issuer;
        this.duration = duration;
        this.issuedAt = System.currentTimeMillis();
        this.unDone = false;
    }

    public void setUndone() {
        this.unDone = true;
    }

    public boolean isOngoing() {
        if (unDone) {
            return false;
        }
        return (System.currentTimeMillis() - issuedAt) < duration;
    }
}
