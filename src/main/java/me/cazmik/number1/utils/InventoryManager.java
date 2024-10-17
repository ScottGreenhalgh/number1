package me.cazmik.number1.utils;

import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryManager {

    public static InventoryManager instance;
    public static HashMap<UUID, Inventory> viewedInventory;

    static {
        viewedInventory = new HashMap<>();
    }

    public static synchronized InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public Map<UUID, Inventory> getViewedInventory() {
        return viewedInventory;
    }

    public void addInventory(UUID playerId, Inventory inventory) {
        viewedInventory.put(playerId, inventory);
    }

    public void removeInventory(UUID playerId) {
        viewedInventory.remove(playerId);
    }
}

