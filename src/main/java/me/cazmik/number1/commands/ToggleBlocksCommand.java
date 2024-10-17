package me.cazmik.number1.commands;

import me.cazmik.number1.Number1;
import me.cazmik.number1.utils.BlockChangeState;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class ToggleBlocksCommand implements CommandExecutor, TabCompleter {


    private final HashMap<String, Material> BLOCKS = new HashMap<String, Material>();

    public ToggleBlocksCommand(Number1 instanceAccess) {
        List<String> materials = instanceAccess.getConfig().getStringList("blocks"); // gathers a list of blocks from configuration
        for (String material : materials) {
            if (Material.matchMaterial(material) != null) {
                BLOCKS.put(material.toLowerCase().split("_")[0],Material.matchMaterial(material));
                //adds to the hashmap, grabs the suffix of the block in configuration in lowercase as key and the block as material.
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (args.length <= 0) { // if no arguments given
            BlockChangeState.getInstance().toggleChangeBlocks(); // toggles change state
            sender.sendMessage("Block change state set to: " + (BlockChangeState.getInstance().isChangeBlocks() ? "true" : "false"));
            return true;
        }
        try {
            Material type = BLOCKS.get(args[0].toLowerCase()); //if block is suffix in configuration
            BlockChangeState.getInstance().setCurrentBlock(type, player);
        } catch (Exception e) { // if given block is not in configuration
            sender.sendMessage("Unknown block type.");
        }
        return true;
    }

    /*    if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "netherite":
                    BlockChangeState.getInstance().setCurrentBlock(Material.NETHERITE_BLOCK, player);
                    break;
                case "emerald":
                    BlockChangeState.getInstance().setCurrentBlock(Material.EMERALD_BLOCK, player);
                    break;
                case "diamond":
                    BlockChangeState.getInstance().setCurrentBlock(Material.DIAMOND_BLOCK, player);
                    break;
                case "gold":
                    BlockChangeState.getInstance().setCurrentBlock(Material.GOLD_BLOCK, player);
                    break;
                case "iron":
                    BlockChangeState.getInstance().setCurrentBlock(Material.IRON_BLOCK, player);
                    break;
                default:
                    sender.sendMessage("Unknown block type. Use: netherite, emerald, diamond, gold, or iron.");
                    break;
            }
        } else {
            BlockChangeState.getInstance().toggleChangeBlocks();
            sender.sendMessage("Block change state set to: " + (BlockChangeState.getInstance().isChangeBlocks() ? "true" : "false"));
        }
        return true; */

    @Override
    public @Nullable List<String> onTabComplete( CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (cmd.getName().equalsIgnoreCase("toggleblock")) {
                if (args.length == 1) {

                    return BLOCKS.keySet().stream().toList(); // tab completes with the hashmap keys
                }
            }
        }
        return List.of(); // prevents tab complete of player names
    }
}
