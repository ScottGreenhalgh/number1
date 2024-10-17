package me.cazmik.number1.listeners;

import me.cazmik.number1.utils.BlockChangeState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void playerMove(PlayerMoveEvent event) {
        if (!BlockChangeState.getInstance().isChangeBlocks()) return;
        Player player = event.getPlayer();
        Location local = player.getLocation();
        local.add(0, -1, 0); //sets the location below the players feet
        if (local.getBlock().isSolid()) { // checks if the block below is solid
            Material currentBlock = BlockChangeState.getInstance().getCurrentBlock(); //grabs current active block
         //   player.getWorld().setBlockData(local, currentBlock.createBlockData()); //change the blocks in the world for everyone
            player.sendBlockChange(local, currentBlock.createBlockData()); // creates blocks only visible to that player
        }
    }
}
