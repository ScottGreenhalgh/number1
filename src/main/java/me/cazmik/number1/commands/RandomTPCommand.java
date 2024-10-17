package me.cazmik.number1.commands;

import me.cazmik.number1.Number1;
import me.cazmik.number1.utils.TeleportUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RandomTPCommand implements CommandExecutor {

    private final Number1 plugin;

    public RandomTPCommand(Number1 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { // checks if sender is not a player
            plugin.getLogger().info("Run this command in game.");
            return true;
        }
        Player player = (Player) sender;

        player.teleport(TeleportUtils.generateLocation(player)); //player tp
        player.sendMessage("Teleported to a random location.");

        return true;
    }
}
