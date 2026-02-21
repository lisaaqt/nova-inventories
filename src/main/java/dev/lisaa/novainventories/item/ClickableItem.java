package dev.lisaa.novainventories.item;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * @author Lisa Kapahnke
 * @project nova-inventories
 * @created 21.02.2026 - 18:12
 * <p>
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/
public record ClickableItem(
        ItemStack item,
        Consumer<InventoryClickEvent> action
) {

    public static ClickableItem of(ItemStack item, Consumer<InventoryClickEvent> action) {
        return new ClickableItem(item, action);
    }
}
