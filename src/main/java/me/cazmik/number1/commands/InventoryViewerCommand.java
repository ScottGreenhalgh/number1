package me.cazmik.number1.commands;

import me.cazmik.number1.Number1;
import me.cazmik.number1.utils.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryViewerCommand implements CommandExecutor{

    private final Number1 plugin;

    public InventoryViewerCommand(Number1 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) { //if sender is not a player
            plugin.getLogger().info("Run this command in game.");
            return true;
        }
        if (args.length <= 0) { // if no argument is given
            sender.sendMessage("No target given.");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]); //targeted online player
        if (target != null) { // if argument is an online player
            int size = target.getInventory().getSize();
            int newSize = Math.min(((size + 4 + 8) / 9) * 9, 54); // rounds up inventory to the nearest multiple of 9, max 54
            Inventory viewedInventory = Bukkit.createInventory(target, newSize, target.displayName()); //creates a custom chest window
            ItemStack[] items = target.getInventory().getContents(); // gets inventory contents as an itemstack
            viewedInventory.setContents(items); // passes the itemstack to the custom chest
            plugin.getLogger().info("Adding inventory for " + target.getName() + " to the map.");
            InventoryManager.viewedInventory.put(target.getUniqueId(), viewedInventory); // adds the uuid and itemstack to the hashmap
            plugin.getLogger().info("Added inventory for " + target.getName() + " to the map. Map size: " + InventoryManager.viewedInventory.size());
            player.openInventory(viewedInventory); // opens the custom chest window for the command sender
        } else { // if argument is not a player
            sender.sendMessage("Target player not found.");
        }
        return true;
    }
}
