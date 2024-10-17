package me.cazmik.number1.commands;

import me.cazmik.number1.Number1;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FeedCommand implements CommandExecutor, TabCompleter {

    private final Number1 plugin;

    public FeedCommand(Number1 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { // checks if sender is not a player
            if (args.length <= 0){
                plugin.getLogger().info("Provide arguments or run this command in game.");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null){
                plugin.feedPlayer(target);
                plugin.getLogger().info(target.getName() + "'s appetite has been sated.");
            } else {
                plugin.getLogger().info("Unknown player.");
            }
            return true;
        }

        Player player = (Player) sender;

        double cost = 10.d;
        if(!Number1.economy.has(player, cost)) {
            player.sendMessage("Insufficient funds. $10 needed to feed");
            return true;
        }

        if (args.length <= 0) { // if no argument is given
            plugin.feedPlayer(player);
            Number1.economy.withdrawPlayer(player, cost);
            player.sendMessage(ChatColor.RED +"-$10");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target != null && player.hasPermission("number1.feed.other")) { // if argument given is an online player & sender has perms
            plugin.feedPlayer(target);
            player.sendMessage(target.getName() + "'s appetite has been sated." );
        } else if (!(player.hasPermission("number.feed.other"))) { // if no permissions
            player.sendMessage("You do not have permission to feed others.");
        } else { // if argument is not a player
            player.sendMessage("Unknown player.");
        }
        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 1) { //feed <playername>
            Player player = (Player) sender;
            List<String> playerNames = new ArrayList<>();
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(player.canSee(p)) playerNames.add(p.getName());
            }
            List<String> outputPlayers = new ArrayList<>();
            StringUtil.copyPartialMatches(args[0], playerNames, outputPlayers);
            return outputPlayers;
        }
        return List.of();
    }
}
