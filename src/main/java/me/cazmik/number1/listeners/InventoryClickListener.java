package me.cazmik.number1.listeners;

import me.cazmik.number1.Number1;
import me.cazmik.number1.utils.InventoryManager;
import me.cazmik.number1.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {

    private final Number1 plugin;

    public InventoryClickListener(Number1 plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onInventoryClick (InventoryClickEvent event) {
        if (InventoryManager.viewedInventory .isEmpty()) { //if hashmap is empty
            return;
        }

        for (Inventory inv : InventoryManager.getInstance().getViewedInventory().values()) { //loops the viewed inventory and contents
            Player player = (Player) event.getWhoClicked();
            Player holder = (Player) inv.getHolder();

            if (holder.equals(player)) { //if the inventory holder is the player
                Bukkit.getScheduler().runTaskLater(plugin, ()-> {
                    InventoryManager.getInstance().getViewedInventory().get(player.getUniqueId()).setContents(player.getInventory().getContents()); //updates map when holder modifies inventory
                    for (HumanEntity p : inv.getViewers()) {
                        ((Player) p).updateInventory();
                    }}, 1); // delays the inventory update post click
            }
            else { // if they are the viewer
                if (inv.equals(event.getClickedInventory())) {
                    holder.getInventory().setContents(InventoryUtils.trimContents(inv.getContents(), holder.getInventory().getSize())); //updates the contents of the custom chest, accounting for divisible by 9 element
                    event.setCancelled(true);
                    return;
                }
            }
            return;
        }
    }
}
