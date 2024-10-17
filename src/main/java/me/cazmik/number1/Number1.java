package me.cazmik.number1;

import me.cazmik.number1.commands.*;
import me.cazmik.number1.listeners.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Number1 extends JavaPlugin {

    private PlayerMenuCommand pmc = null;
    public static Economy economy;
    public ArrayList<Player> invisList = new ArrayList<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        if(!setupEconomy()) {
            getLogger().severe("No economy plugin found. plugin will not start.");
            setEnabled(false);
            return;
        }

        registerEvents(
                new PlayerCraftListener(),
                new PlayerMoveListener(),
                new BlockBreakListener(),
                new PlayerJoinListener(this),
                new InventoryClickListener(this),
                new InventoryCloseListener(this),
                new PlayerMenuListener(this),
                new CustomListeners()
        );

        pmc = new PlayerMenuCommand(this);

        registerCommand("toggleblock", new ToggleBlocksCommand(this));
        registerCommand("togglebedrock", new ToggleBlockBreakCommand(this));
        registerCommand("setspawn", new SetSpawnCommand(this));
        registerCommand("spawn", new SpawnCommand(this));
        registerCommand("feed", new FeedCommand(this));
        registerCommand("inv", new InventoryViewerCommand(this));
        registerCommand("menu", pmc);
        registerCommand("astand", new ArmorStandCommand(this));
        registerCommand("hologram", new HologramCommand(this));
        registerCommand("sign", new SignCommand(this));
        registerCommand("rtp", new RandomTPCommand(this));
        registerCommand("vanish", new VanishCommand(this));

    }

    private boolean setupEconomy() {
        if(getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> registeredServiceProvider = getServer().getServicesManager().getRegistration(Economy.class);

        if(registeredServiceProvider == null) return false;
        economy = registeredServiceProvider.getProvider();

        return true;
    }

    public Inventory getCommandInventory() { return pmc.getInventory(); }
    public void removeInventory() { pmc.removeInventory(); }

    public void feedPlayer(Player player) {
        player.setFoodLevel(20);
        player.sendMessage("Appetite sated.");
    }

    private void registerEvents(Listener... listeners) {
        for(Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private void registerCommand(String command, CommandExecutor executor) {
        getCommand(command).setExecutor(executor);
    }
}
