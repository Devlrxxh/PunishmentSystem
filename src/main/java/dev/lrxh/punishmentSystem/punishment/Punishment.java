package dev.lrxh.punishmentSystem.punishment;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class Punishment {
    private final PunishmentType punishmentType;
    private final UUID issuer;
    private final long duration;
}
