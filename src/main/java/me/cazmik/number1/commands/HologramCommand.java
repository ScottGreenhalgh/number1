package me.cazmik.number1.commands;

import me.cazmik.number1.Number1;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HologramCommand implements CommandExecutor, TabCompleter {

    private final Number1 plugin;

    public HologramCommand(Number1 plugin) {
        this.plugin = plugin;
        // stores the armorstand UUID to config
        String holo1String = plugin.getConfig().getString("hologram1_UUID", "").replaceAll("_", "-");
        if (!holo1String.isEmpty()) holo1 = UUID.fromString(holo1String);

        String holo2String = plugin.getConfig().getString("hologram2_UUID", "").replaceAll("_", "-");
        if (!holo2String.isEmpty()) holo2 = UUID.fromString(holo2String);
    }
    private UUID holo1;
    private UUID holo2;
    private ArmorStand hologram;
    private ArmorStand hologram2;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // if UUID is in config set respective hologram to entity
        if (holo1 != null) {
            hologram = (ArmorStand) Bukkit.getEntity(holo1);
        }
        if (holo2 != null) {
            hologram2 = (ArmorStand) Bukkit.getEntity(holo2);
        }
        // if sender is not a player
        if (!(sender instanceof Player)) {
            plugin.getLogger().info("Run this command in game.");
            return true;
        }
        // if no arguments are provided
        if (args.length <= 0) {
            sender.sendMessage("Please provide arguments.");
            return true;
        }
        // if arguments are provided
        Player player = (Player) sender;
        if (args.length > 0) {
            // switch containing the first argument
            switch (args[0].toLowerCase()) {
                case "line1":
                    if (holo1 == null) {
                        //creates an armorstand at sender location with given parameters
                        hologram = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, 1, 0), EntityType.ARMOR_STAND);
                        hologram.setInvisible(true);
                        hologram.setInvulnerable(true);
                        hologram.setCollidable(false);
                        hologram.setCanPickupItems(false);
                        hologram.setGravity(false);
                        hologram.setSmall(true);
                        hologram.setMarker(true);
                        hologram.setCustomNameVisible(true);
                        hologram.setCustomName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("hologram1_text")));
                        holo1 = hologram.getUniqueId();
                        //stores the armorstand entity UUID to config
                        plugin.getConfig().set("hologram1_UUID", holo1.toString().replaceAll("-","_"));
                        plugin.saveConfig();
                        player.sendMessage("Created Line 1.");

                    } else {
                        //if armorstand entity already exists under hologram
                        player.sendMessage("Line 1 already exists, use /hologram remove");
                    }
                    break;
                case "line2":
                    if (holo2 == null) {
                        //creates an armorstand at sender location with given parameters
                        hologram2 = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, (1 - 0.25), 0), EntityType.ARMOR_STAND);
                        hologram2.setInvisible(true);
                        hologram2.setInvulnerable(true);
                        hologram2.setCollidable(false);
                        hologram2.setCanPickupItems(false);
                        hologram2.setGravity(false);
                        hologram2.setSmall(true);
                        hologram2.setMarker(true);
                        hologram2.setCustomNameVisible(true);
                        hologram2.setCustomName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("hologram2_text")));
                        holo2 = hologram2.getUniqueId();
                        //stores the armostand entity UUID to config
                        plugin.getConfig().set("hologram2_UUID", holo2.toString().replaceAll("-","_"));
                        plugin.saveConfig();
                        player.sendMessage("Created Line 2.");

                    } else {
                        //if armorstand entity already exists under hologram2
                        player.sendMessage("Line 2 already exists, use /hologram remove");
                    }
                    break;
                case "remove":
                    if (args.length == 1) {
                        player.sendMessage("Provide line to remove.");
                    } else {
                        //switch containing second argument of args[0].equals("remove")
                        switch (args[1].toLowerCase()) {
                            case "line1":
                                if (holo1 != null) {
                                    //removes hologram and sets UUID in config to null
                                    hologram.remove();
                                    holo1 = null;
                                    plugin.getConfig().set("hologram1_UUID", null);
                                    plugin.saveConfig();
                                    player.sendMessage("Line 1 removed.");
                                } else {
                                    player.sendMessage("Nothing to remove. Create with /hologram line1");
                                }
                                break;
                            case "line2":
                                if (holo2 != null) {
                                    //removes hologram2 and sets UUID in config to null
                                    hologram2.remove();
                                    holo2 = null;
                                    plugin.getConfig().set("hologram2_UUID", null);
                                    plugin.saveConfig();
                                    player.sendMessage("Line 2 removed.");
                                } else {
                                    player.sendMessage("Nothing to remove. Create with /hologram line2");
                                }
                                break;
                        }
                    }
                    break;
                case "reload":
                    // reloads configuration and reassigns hologram text based on updated config
                    plugin.reloadConfig();
                    if (hologram != null) hologram.setCustomName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("hologram1_text")));
                    if (hologram2 != null) hologram2.setCustomName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("hologram2_text")));
                    player.sendMessage("Config reloaded");
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            //if command is hologram
            if (cmd.getName().equalsIgnoreCase("hologram")) {
                //if argument length is 1 provide given arraylist
                if (args.length == 1) {
                    return StringUtil.copyPartialMatches(args[0], List.of("line1", "line2", "remove", "reload"), new ArrayList<>());
                }
                //if argument length is 2 provide given arraylist
                if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
                    return StringUtil.copyPartialMatches(args[1], List.of("line1", "line2"), new ArrayList<>());
                }
            }
        }
        return List.of();
    }
}
