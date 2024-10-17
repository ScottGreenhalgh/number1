package me.cazmik.number1.commands;

import me.cazmik.number1.Number1;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {

    private final Number1 plugin;

    public VanishCommand(Number1 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { //if not a player
            plugin.getLogger().info("Run this command in game.");
            return true;
        }
        Player player = (Player) sender;
        if (!(player.hasPermission("number1.vanish"))) { //if sender has permission
            player.sendMessage("You don't have permission to run this command.");
            return true;
        }
        if (plugin.invisList.contains(player)) { //if array contains invis player
            for (Player people : Bukkit.getOnlinePlayers()){
                people.showPlayer(plugin, player);
            }
            plugin.invisList.remove(player); // remove player from invis list
            player.sendMessage("You've become visible.");
        } else  { // if array doesn't contain invis player
            for (Player people : Bukkit.getOnlinePlayers()){
                people.hidePlayer(plugin, player); //hides invis player for all online players
            }
            plugin.invisList.add(player); //add player to invis list
            player.sendMessage("You've become invisible.");
        }
        return true;
    }
}
