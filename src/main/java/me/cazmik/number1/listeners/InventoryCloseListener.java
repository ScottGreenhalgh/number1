package me.cazmik.number1.listeners;

import me.cazmik.number1.Number1;
import me.cazmik.number1.utils.InventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class InventoryCloseListener implements Listener {

    private final Number1 plugin;

    public InventoryCloseListener(Number1 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClose (InventoryCloseEvent event) {
        Inventory closedInventory = event.getInventory();
        for (Inventory inv : InventoryManager.getInstance().getViewedInventory().values()) { //loops the viewed inventory and contents
            if (inv .equals(closedInventory)) { // if a viewed inventory is closed
                if (inv.getViewers().size() > 1) return; // checks for any viewers
                if (inv.getHolder() instanceof Player holder) {
                    InventoryManager.getInstance().removeInventory(holder.getUniqueId()); //removes the inventory from the map
                    plugin.getLogger().info("Removed inventory for " + holder.getName() + " from the map.");
                }
                return;
            }
        }
    }
}
