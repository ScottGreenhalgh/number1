package me.cazmik.number1.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Random;

public class TeleportUtils {

/*    public static HashSet<Material> badBlocks = new HashSet<>();

    static{
        badBlocks.add(Material.LAVA);
        badBlocks.add(Material.CACTUS);
        badBlocks.add(Material.FIRE);
    }
*/
    public static Location generateLocation(Player player){

        Random random = new Random(System.currentTimeMillis()); //time value input for unique result each time
        boolean safeLocation = false;
        int y = 150;
        Location randomLocation = null;
        int maxTries = 50;

        while(!safeLocation) { //generates up to 50 alternative locations if not safe
            if(maxTries == 0) break;
            int x = random.nextInt(10_000)-5000; //random between -5k and 5k
            int z = random.nextInt(10_000)-5000;


            randomLocation = new Location(player.getWorld(), x, y, z);
            y = randomLocation.getWorld().getHighestBlockYAt(randomLocation);
            randomLocation.setY(y); //sets the y value to the highest block at that location

            safeLocation = isSafeLocation(randomLocation);
            maxTries--;
        }

        return randomLocation.add(0, 1, 0); //ensures boots on ground
    }

    public static boolean isSafeLocation(Location location){

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        Block block = location.getWorld().getBlockAt(x, y, z);
        Block feet = location.getWorld().getBlockAt(x, y + 1, z);
        Block head = location.getWorld().getBlockAt(x, y + 2, z);

        return feet.getType().equals(Material.AIR) && head.getType().equals(Material.AIR) && block.isSolid(); //checks the floor is solid and 2 blocks of air are above

    }
}
