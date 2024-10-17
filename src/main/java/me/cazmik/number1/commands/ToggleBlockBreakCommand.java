package me.cazmik.number1.commands;

import me.cazmik.number1.Number1;
import me.cazmik.number1.utils.BlockBreakState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleBlockBreakCommand implements CommandExecutor {

    private final Number1 plugin;

    public ToggleBlockBreakCommand(Number1 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { // if sender is not a player
            plugin.getLogger().info("Run this command in game.");
            return true;
        }

        BlockBreakState.getInstance().toggleAllowBlockBreak(); // toggles bedrock block breaking
        boolean currentState = BlockBreakState.getInstance().isAllowBlockBreak(); // checks if this is true or false

        sender.sendMessage("Bedrock block breaking set to: " + (currentState ? "true" : "false"));
        return true;
    }
}
