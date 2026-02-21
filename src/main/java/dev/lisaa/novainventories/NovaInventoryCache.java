package dev.lisaa.novainventories;

import org.bukkit.entity.Player;

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
public class NovaInventoryCache {

    private final Map<UUID, Map<Class<? extends NovaInventory>, NovaInventory>> cache = new HashMap<>();

    public <T extends NovaInventory> T get(Player player,
                                           Class<T> clazz) {

        return (T) cache
                .computeIfAbsent(player.getUniqueId(), u -> new HashMap<>())
                .computeIfAbsent(clazz, c -> {
                    try {
                        return clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public void clear(Player player) {
        cache.remove(player.getUniqueId());
    }

    public void remove(Player player, Class<? extends NovaInventory> clazz) {
        Map<Class<? extends NovaInventory>, NovaInventory> map =
                cache.get(player.getUniqueId());

        if (map != null) {
            map.remove(clazz);
        }
    }
}
