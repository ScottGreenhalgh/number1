package me.cazmik.number1.commands;

import me.cazmik.number1.Number1;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SignCommand implements CommandExecutor {

    private final Number1 plugin;

    public SignCommand(Number1 plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().info("Run this command in game.");
            return true;
        }
        Player player = (Player) sender;

        if (args.length < 2){
            player.sendMessage("Please provide a line and text");
            player.sendMessage("e.g. /sign 3 test");
        } else if (args.length >= 2) {
            player.getWorld().getBlockAt(player.getLocation()).setType(Material.OAK_SIGN);

            Sign sign = (Sign) player.getWorld().getBlockAt(player.getLocation()).getState();

            int line = Integer.parseInt(args[0]) - 1;
            StringBuilder words = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                words.append(args[i]).append(" ");
            }

            sign.setLine(line, words.toString());
            sign.update();
        }
        return true;
    }
}
