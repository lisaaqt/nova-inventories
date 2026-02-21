package dev.lisaa.novainventories;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Lisa Kapahnke
 * @project nova-inventories
 * @created 21.02.2026 - 18:12
 * <p>
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/
public class NovaInventoryManager {

    private final Plugin plugin;
    private final NovaInventoryCache cache;

    private final Map<UUID, NovaInventory> openInventories = new HashMap<>();

    public NovaInventoryManager(Plugin plugin) {
        this.plugin = plugin;
        this.cache = new NovaInventoryCache();

        plugin.getServer().getPluginManager()
                .registerEvents(new NovaInventoryListener(this), plugin);
    }

    public NovaInventoryCache getCache() {
        return cache;
    }

    public void markOpen(UUID uuid, NovaInventory inventory) {
        openInventories.put(uuid, inventory);
    }

    public void markClosed(UUID uuid) {
        openInventories.remove(uuid);
    }

    public NovaInventory getOpen(UUID uuid) {
        return openInventories.get(uuid);
    }

    public <T extends NovaInventory> void open(Player player, Class<T> clazz) {

        T inventory = cache.get(player, clazz);

        if (inventory.manager == null) {
            inventory.initialize(this);
            inventory.create(player);
        }

        inventory.open(player);
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
