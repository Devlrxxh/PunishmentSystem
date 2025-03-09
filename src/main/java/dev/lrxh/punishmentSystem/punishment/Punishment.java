package dev.lrxh.punishmentSystem.punishment;

import com.google.gson.annotations.SerializedName;
import dev.lrxh.punishmentSystem.utils.DateUtils;
import dev.lrxh.punishmentSystem.utils.TimeUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Getter
public class Punishment {
    @SerializedName("type")
    private final PunishmentType type;
    @SerializedName("issuer")
    private final UUID issuer;
    @SerializedName("duration")
    private final long duration;
    @SerializedName("issuedOn")
    private final long issuedOn;
    @SerializedName("perm")
    private final boolean perm;
    @SerializedName("issuedOnString")
    private final String issuedOnString;
    @SerializedName("unDone")
    private boolean unDone;

    public Punishment(PunishmentType type, UUID issuer, long duration, boolean perm) {
        this.type = type;
        this.issuer = issuer;
        this.duration = duration;
        this.issuedOn = System.currentTimeMillis();
        this.unDone = false;
        this.perm = perm;
        this.issuedOnString = DateUtils.getDate();
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

        return (System.currentTimeMillis() - issuedOn) < duration;
    }

    public ItemStack getIcon() {
        Material material = null;

        if (getType().isBan()) {
            material = Material.BARRIER;
        }

        if (getType().isMute()) {
            material = Material.YELLOW_DYE;
        }

        if (getType().isKick()) {
            material = Material.RED_DYE;
        }

        return new ItemStack(material);
    }

    public String getIssuer() {
        if (issuer == null) {
            return "Console";
        }

        return Bukkit.getOfflinePlayer(issuer).getName();
    }

    public String getDuration() {
        if (perm) {
            return "Permanent";
        }

        return TimeUtil.unparse(duration);
    }
}
