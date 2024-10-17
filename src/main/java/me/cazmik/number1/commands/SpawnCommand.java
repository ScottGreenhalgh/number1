package me.cazmik.number1.commands;

import me.cazmik.number1.Number1;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class SpawnCommand implements CommandExecutor {

    private final Number1 plugin;

    public SpawnCommand(Number1 plugin) {
        this.plugin = plugin;
    }

    private final HashMap<UUID, Long> cooldown = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player player)) { // if sender is not a player
            plugin.getLogger().info("Run this command in game.");
            return true;
        }

        long spawnCooldown = plugin.getConfig().getLong("spawn_cooldown", 10_000); // grabs a cooldown from configuration, if not given defaults to 10k milliseconds

        UUID playerId = player.getUniqueId();
        Location location = plugin.getConfig().getLocation("spawn"); // grabs the spawn location from the configuration

        long currentTime = System.currentTimeMillis(); // gets the system time
        if (cooldown.containsKey(playerId) && currentTime - cooldown.get(playerId) < spawnCooldown) {
            long millisRemaining = (spawnCooldown - (currentTime - cooldown.get(playerId)));
            int secondRemaining = (int) (millisRemaining % 1000 == 0 ? millisRemaining / 1000 : millisRemaining / 1000 + 1); // calculates time remaining before running again, rounded up to nearest second
            player.sendMessage("You can't run this command again for another " + secondRemaining + " seconds.");
            return true;
        }

        cooldown.put(playerId, currentTime);
        if (location == null) { // if spawn configuration is empty
            player.sendMessage("No spawn point set. Use /setspawn to set it.");
            return true;
        }

        player.teleport(location);
        player.sendMessage("Teleported to spawn.");
        return true;
    }
}

/*    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player player) {
            Location location = plugin.getConfig().getLocation("spawn");
            if (!cooldown.containsKey(player.getUniqueId())) {
                cooldown.put(player.getUniqueId(), System.currentTimeMillis());

                if (location != null) {
                    player.teleport(location);
                    player.sendMessage("Teleported to spawn.");
                }
                else {
                    player.sendMessage("No spawn point set. Use /setspawn to set it.");
                }
            }
            else {
                long timeElapsed = System.currentTimeMillis() - cooldown.get(player.getUniqueId());
                if (timeElapsed >= 10000) {
                    cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                    if (location != null) {
                        player.teleport(location);
                        player.sendMessage("Teleported to spawn.");
                    }
                    else {
                        player.sendMessage("No spawn point set. Use /setspawn to set it.");
                    }
                }
                else {
                    player.sendMessage("You can't run this command again for another " + (10000 - timeElapsed) + " milliseconds.");
                }
            }
        }
        else {
            plugin.getLogger().info("Run this command in game.");
        }
        return true;
    }
} */