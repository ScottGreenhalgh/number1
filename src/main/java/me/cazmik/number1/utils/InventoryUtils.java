package me.cazmik.number1.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class InventoryUtils {

    public static ItemStack[] trimContents(ItemStack[] contents, int size) {
        ItemStack[] trimmedContents = new ItemStack[size];
        System.arraycopy(contents, 0, trimmedContents, 0, Math.min(contents.length, size));
        return trimmedContents;
    }
    // creates inventory items with name, material and lore
    public static ItemStack createItem(String name, Material mat, List<String> lore) {
        ItemStack item = new ItemStack(mat, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }
}
