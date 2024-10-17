package me.cazmik.number1.commands;

import me.cazmik.number1.Number1;
import me.cazmik.number1.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class PlayerMenuCommand implements CommandExecutor, InventoryHolder {

    private final Number1 plugin;
    private Inventory inv;

    public PlayerMenuCommand(Number1 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { // if sender is not player
            plugin.getLogger().info("Run this command in game.");
            return true;
        }

        inv = Bukkit.createInventory(null, 9, "Selection Screen"); //creates a chest inventory of 9 slots
        // create a set of items
        ItemStack feed = InventoryUtils.createItem(ChatColor.GREEN + "Feed", Material.BREAD, Collections.singletonList(ChatColor.DARK_GREEN + "Click to sate appetite."));
        ItemStack spawn = InventoryUtils.createItem(ChatColor.AQUA + "Spawn", Material.GRASS_BLOCK, Collections.singletonList(ChatColor.BLUE + "Click to teleport to spawn."));
        ItemStack bedrock = InventoryUtils.createItem(ChatColor.GRAY + "Bedrock", Material.BEDROCK, Collections.singletonList(ChatColor.DARK_GRAY + "Toggle bedrock break."));

        ItemStack armorStand = InventoryUtils.createItem(ChatColor.LIGHT_PURPLE + "Create Armor Stand", Material.ARMOR_STAND, Collections.singletonList(ChatColor.DARK_PURPLE + "Create a new armor stand."));
        ItemStack close = InventoryUtils.createItem(ChatColor.RED + "Close", Material.BARRIER, Collections.singletonList(ChatColor.DARK_RED + "Click to close"));

        // assign items to slots
        inv.setItem(0, feed);
        inv.setItem(4, spawn);
        inv.setItem(6, bedrock);

        inv.setItem(2,armorStand);
        inv.setItem(8,close);
        //opens the custom inventory
        Player player = (Player) sender;
        player.openInventory(inv);
        player.sendMessage("Opened Menu");

        return true;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
    public void removeInventory() {
        inv = null;
    }
}
        //create a set of custom clickable buttons
        /*
        ItemStack feed = new ItemStack(Material.BREAD);
        ItemStack spawn = new ItemStack(Material.GRASS_BLOCK);
        ItemStack bedrock = new ItemStack(Material.BEDROCK);

        ItemMeta feedMeta = feed.getItemMeta();
        feedMeta.setDisplayName(ChatColor.GREEN + "Feed");
        feedMeta.setLore(List.of(ChatColor.DARK_GREEN + "Click to sate appetite."));
        feed.setItemMeta(feedMeta);

        ItemMeta spawnMeta = spawn.getItemMeta();
        spawnMeta.setDisplayName(ChatColor.AQUA + "Spawn");
        spawnMeta.setDisplayName(ChatColor.BLUE + "Click to teleport to spawn.");
        spawn.setItemMeta(spawnMeta);

        ItemMeta bedrockMeta = bedrock.getItemMeta();
        bedrockMeta.setDisplayName(ChatColor.DARK_GRAY + "Bedrock");
        bedrockMeta.setDisplayName(ChatColor.BLACK + "Toggle bedrock break.");

        //assign items to custom chest slots
        menu.setItem(0,feed);
        menu.setItem(4,spawn);
        menu.setItem(8,bedrock);

        player.openInventory(menu);

         */

