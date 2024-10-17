package me.cazmik.number1.listeners;

import me.cazmik.number1.Number1;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class PlayerMenuListener implements Listener {

    private final Number1 plugin;

    public PlayerMenuListener(Number1 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;
        if (event.isRightClick()) return;
        if (plugin.getCommandInventory() == null) return; //if inventory isn't loaded
        if (event.getClickedInventory() .equals(plugin.getCommandInventory())) { //if clicked inventory is custom inventory
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            // what each item will do when clicked
            if (event.getSlot() == 0) {
                double cost = 10.d;
                if(!Number1.economy.has(player, cost)) {
                    player.sendMessage("Insufficient funds. $10 needed to feed");
                    return;
                }
                Number1.economy.withdrawPlayer(player, cost);
                player.sendMessage(ChatColor.RED +"-$10");
                plugin.feedPlayer((Player) player);
                player.closeInventory();
            }
            if (event.getSlot() == 4) {
                plugin.getServer().dispatchCommand(player, "spawn");
                player.closeInventory();
            }
            if (event.getSlot() == 6) {
                plugin.getServer().dispatchCommand(player, "togglebedrock");
                player.closeInventory();
            }
            if (event.getSlot() == 2) {
                plugin.getServer().dispatchCommand(player, "astand");
                player.closeInventory();
            }
            if (event.getSlot() == 8) {
                player.sendMessage("Menu closed");
                player.closeInventory();
            }
        }
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory() .equals(plugin.getCommandInventory())) {
            plugin.removeInventory(); //unload inventory on close
        }
    }
}
