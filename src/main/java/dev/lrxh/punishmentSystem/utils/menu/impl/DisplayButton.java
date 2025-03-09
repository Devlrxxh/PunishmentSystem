package dev.lrxh.punishmentSystem.utils.menu.impl;

import dev.lrxh.punishmentSystem.utils.ItemBuilder;
import dev.lrxh.punishmentSystem.utils.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DisplayButton extends Button {
    private final ItemStack itemStack;
    private final String name;
    private final Consumer<?> action;
    private final List<String> lore;

    public DisplayButton(int slot, Material itemStack, String name) {
        super(slot, false);
        this.itemStack = new ItemStack(itemStack);
        this.name = name;
        this.action = null;
        this.lore = new ArrayList<>();
    }

    public DisplayButton(int slot, ItemStack itemStack, String name, List<String> lore) {
        super(slot, false);
        this.itemStack = new ItemStack(itemStack);
        this.name = name;
        this.action = null;
        this.lore = lore;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        if (name != null) return new ItemBuilder(itemStack).name(name).lore(lore).build();
        return new ItemBuilder(itemStack).lore(lore).build();
    }

    @Override
    public void onClick(ClickType type, Player player) {
        if (action == null) return;

        action.accept(null);
    }
}
