package me.cazmik.number1.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BlockChangeState {
    private static BlockChangeState instance;
    private boolean changeblocks = false;
    private Material currentBlock = Material.NETHERITE_BLOCK;

    private BlockChangeState() {}

    public static BlockChangeState getInstance() {
        if (instance == null) {
            instance = new BlockChangeState();
        }
        return instance;
    }

    public boolean isChangeBlocks() {
        return changeblocks;
    }

    public void toggleChangeBlocks() {
        changeblocks = !changeblocks;
    }

    public Material getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(Material block, Player player) {
        this.currentBlock = block;
        player.sendMessage("Changed current block to " + block.toString().toLowerCase().replace("_"," ") + ".");
    }
}
