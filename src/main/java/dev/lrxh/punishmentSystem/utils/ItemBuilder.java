package dev.lrxh.punishmentSystem.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemBuilder {
    private final ItemStack item;

    public ItemBuilder(Material material) {
        item = new ItemStack(Objects.requireNonNullElse(material, Material.AIR));
    }

    public ItemBuilder(String material) {
        item = new ItemStack(Objects.requireNonNullElse(Material.valueOf(material), Material.AIR));
    }

    public ItemBuilder(ItemStack itemStack) {
        if (itemStack != null) {
            item = new ItemStack(itemStack);
        } else {
            item = new ItemStack(Material.AIR);
        }
    }

    public ItemBuilder name(String name) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(CC.color(name));
            item.setItemMeta(meta);
        }
        return this;
    }

    private void clearFlags() {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.addItemFlags(ItemFlag.values());
            item.setItemMeta(meta);
        }
        resetAmount();
    }

    public ItemBuilder makeUnbreakable() {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setUnbreakable(true);
            item.setItemMeta(meta);
        }
        return this;
    }

    public void resetAmount() {
        item.setAmount(1);
    }

    public ItemBuilder lore(List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            List<String> toSet = new ArrayList<>();
            for (String string : lore) {
                toSet.add(CC.color(string));
            }
            meta.setLore(toSet);
            item.setItemMeta(meta);
        }
        return this;
    }


    public ItemBuilder amount(int amount) {
        item.setAmount(amount <= 0 ? 1 : Math.min(amount, 64));
        return this;
    }

    public ItemStack build() {
        clearFlags();
        return item;
    }
}