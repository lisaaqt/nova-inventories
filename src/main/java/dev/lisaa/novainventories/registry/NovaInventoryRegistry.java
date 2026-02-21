package dev.lisaa.novainventories.registry;

import dev.lisaa.novainventories.NovaInventory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lisa Kapahnke
 * @project nova-inventories
 * @created 21.02.2026 - 18:22
 * <p>
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/
public class NovaInventoryRegistry {

    private final Map<String, Class<? extends NovaInventory>> inventories = new HashMap<>();

    public void register(String id, Class<? extends NovaInventory> clazz) {
        inventories.put(id.toLowerCase(), clazz);
    }

    public Class<? extends NovaInventory> get(String id) {
        return inventories.get(id.toLowerCase());
    }
}
