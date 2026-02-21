package dev.lisaa.novainventories;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Lisa Kapahnke
 * @project nova-inventories
 * @created 21.02.2026 - 18:24
 * <p>
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/
public class NovaInventoryListener implements Listener {

    private final NovaInventoryManager manager;

    public NovaInventoryListener(NovaInventoryManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player player))
            return;

        NovaInventory inventory =
                manager.getOpen(player.getUniqueId());

        if (inventory == null)
            return;

        if (!event.getView().getTopInventory().equals(inventory.getInventory()))
            return;

        inventory.handleClick(event);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {

        if (event.getPlayer() instanceof Player player) {
            manager.markClosed(player.getUniqueId());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        manager.markClosed(event.getPlayer().getUniqueId());
        manager.getCache().clear(event.getPlayer());
    }
}
