package me.cazmik.number1.listeners;

import me.cazmik.number1.Number1;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerJoinListener implements Listener {

    private final Number1 plugin;

    public PlayerJoinListener(Number1 instanceAccess) {
        this.plugin = instanceAccess;
    }

    @EventHandler
    public void  onPlayerJoin(PlayerJoinEvent event) {

        plugin.getLogger().info(event.getPlayer().getName());
        String message = this.plugin.getConfig().getString("message");
        if (message != null) {
            event.getPlayer().sendMessage(message);
        }

        Player player = event.getPlayer(); //get instance of joined player

        for (int i = 0; i < plugin.invisList.size(); i++) { //loops all players on the invis list
            player.hidePlayer(plugin, plugin.invisList.get(i));//make invis players invisible to joined player
        }

        if (!event.getPlayer().hasPlayedBefore()) { // if player ever joined
            Location location = plugin.getConfig().getLocation("spawn");
            if (location != null) { // checks for spawn in config
                player.teleport(location);
            }
        }
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {

        Location location = plugin.getConfig().getLocation("spawn");
        if (location != null) { // checks for spawn in config
            event.setRespawnLocation(location); // sends to spawn
        }
    }
}
