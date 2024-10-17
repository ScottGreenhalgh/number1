package me.cazmik.number1.listeners;

import me.cazmik.number1.events.SpawnerBreakEvent;
import me.cazmik.number1.utils.BlockBreakState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        event.getPlayer().sendBlockChange(event.getBlock().getLocation(), event.getBlock().getBlockData());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        if (event.getPlayer().isSneaking()) {
            event.setCancelled(true); // cancels block break when sneaking
        }

        if(BlockBreakState.getInstance().isAllowBlockBreak()) {
            for (ItemStack item : event.getBlock().getDrops(event.getPlayer().getEquipment().getItemInMainHand())) { //checks if the tool in hand can normally provide drops
                event.getPlayer().getWorld().dropItem(event.getBlock().getLocation(), item); //allows the usual items to drop
            }
            event.getBlock().setType(Material.BEDROCK);
            event.setCancelled(true); // cancels the block break and sets the location to bedrock
        }

        /*if (event.getBlock().getType().equals(Material.SPAWNER) && event.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH) && event.getPlayer().hasPermission("number1.silk")) {
            Bukkit.getPluginManager().callEvent(new SpawnerBreakEvent(event.getPlayer(), event.getBlock())); //calls custom event if block is spawner, tool has silk enchant and player has permission
        } else if (event.getBlock().getType().equals(Material.SPAWNER) && event.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH) && !(event.getPlayer().hasPermission("number1.silk"))) {
            event.getPlayer().sendMessage("You cannot receive spawners from silk touch."); // if no permission is given
        }*/

        if(event.getBlock().getType().equals(Material.SPAWNER)) {
            if(event.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
                if (event.getPlayer().hasPermission("number1.silk"))
                        Bukkit.getPluginManager().callEvent(new SpawnerBreakEvent(event.getPlayer(), event.getBlock()));
                else    event.getPlayer().sendMessage("You cannot receive spawners from silk touch.");
            }
        }
    }
}
