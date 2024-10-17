package me.cazmik.number1.commands;

import me.cazmik.number1.Number1;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    private final Number1 plugin;

    public SetSpawnCommand(Number1 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player player) { // if sender is a player
            Location location = player.getLocation();
         /*
            plugin.getConfig().set("spawn.x", location.getX());
            plugin.getConfig().set("spawn.y", location.getY());
            plugin.getConfig().set("spawn.z", location.getZ());
            plugin.getConfig().set("spawn.worldName", location.getWorld().getName());
        */
            plugin.getConfig().set("spawn", location); // sets the location of the player in the configuration
            plugin.saveConfig();
            player.sendMessage("Spawn location set.");
        }
        else { // if sender is not a player
            plugin.getLogger().info("Run this command in game.");
        }

        return true;
    }
}
