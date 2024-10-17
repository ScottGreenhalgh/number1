package me.cazmik.number1.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpawnerBreakEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    protected Player breaker;
    protected Block spawner;

    public SpawnerBreakEvent(Player breaker, Block spawner) {
        this.breaker = breaker;
        this.spawner = spawner;
    }

    public Block getSpawner() {
        return spawner;
    }

    public Player getBreaker() {
        return breaker;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
