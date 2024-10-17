package me.cazmik.number1.listeners;

import me.cazmik.number1.events.SpawnerBreakEvent;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class CustomListeners implements Listener {

    @EventHandler
    public void onSpawnerBreak (SpawnerBreakEvent event) {

        CreatureSpawner creatureSpawner = (CreatureSpawner) event.getSpawner().getState();
        ItemStack spawnerGiven = new ItemStack(Material.SPAWNER);

        if (creatureSpawner.getSpawnedType() == null) {
            event.getBreaker().sendMessage("Spawners that don't spawn mobs cannot be gathered");
            return;
        }

        BlockStateMeta creatureSpawnerMeta = (BlockStateMeta) spawnerGiven.getItemMeta();
        CreatureSpawner creatureSpawnerState = (CreatureSpawner) creatureSpawnerMeta.getBlockState();

        creatureSpawnerState.setSpawnedType(creatureSpawner.getSpawnedType());
        creatureSpawnerMeta.setBlockState(creatureSpawnerState);
        creatureSpawnerMeta.setItemName(creatureSpawner.getSpawnedType().getName() + " Spawner");
        spawnerGiven.setItemMeta(creatureSpawnerMeta);

        event.getBreaker().sendMessage("You have received a spawner from silk touch");
        event.getBreaker().getInventory().addItem(spawnerGiven);
    }
}
