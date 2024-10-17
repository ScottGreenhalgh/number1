package me.cazmik.number1.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerCraftListener implements Listener {

    @EventHandler
    public void onItemCrafting(PrepareItemCraftEvent event) {
        if (event.getRecipe() == null) return; //if no item is being crafted
        ItemMeta meta = event.getInventory().getResult().getItemMeta();
        List<String> lore = new ArrayList<>();
        if (meta.hasLore()) { // if item has lore
            lore = meta.getLore(); // grabs lore
        }
        lore.add(ChatColor.AQUA+"Made by "+event.getView().getPlayer().getName());
        meta.setLore(lore); // sets additional lore
        event.getInventory().getResult().setItemMeta(meta); //assigns to item
    }
}
