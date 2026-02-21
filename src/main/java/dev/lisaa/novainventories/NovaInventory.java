package dev.lisaa.novainventories;

import dev.lisaa.novainventories.item.ClickableItem;
import dev.lisaa.novainventories.size.InventorySize;
import dev.lisaa.novainventories.slot.Slot;
import dev.lisaa.novainventories.slot.SlotSelection;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lisa Kapahnke
 * @project nova-inventories
 * @created 21.02.2026 - 18:12
 * <p>
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/
public abstract class NovaInventory {

    protected NovaInventoryManager manager;

    protected Inventory inventory;
    protected final Map<Integer, ClickableItem> items = new HashMap<>();

    protected InventorySize size = InventorySize.THREE_ROWS;
    protected InventoryType type = InventoryType.CHEST;
    protected Component title;

    /* ================= INITIALIZATION ================= */

    public void initialize(NovaInventoryManager manager) {
        this.manager = manager;
        configure();
    }

    protected abstract void configure();
    protected abstract void render(Player player);

    /* ================= CREATION ================= */

    void create(Player player) {

        if (type == InventoryType.CHEST) {

            inventory = Bukkit.createInventory(
                    player,
                    size.getSize(),
                    title
            );

        } else {

            inventory = Bukkit.createInventory(
                    player,
                    type,
                    title
            );

            // For non-chest types adjust internal size dynamically
            this.size = InventorySize.ofRows(
                    inventory.getSize() / 9
            );
        }

        render(player);
    }

    /* ================= OPEN ================= */

    public void open(Player player) {
        player.openInventory(inventory);
        manager.markOpen(player.getUniqueId(), this);
    }

    public void reopen(Player player) {
        items.clear();
        inventory.clear();
        render(player);
        open(player);
    }

    /* ================= CLICK HANDLING ================= */

    public void handleClick(InventoryClickEvent event) {

        int rawSlot = event.getRawSlot();

        if (rawSlot < 0 || rawSlot >= size.getSize())
            return;

        event.setCancelled(true);

        ClickableItem clickable = items.get(rawSlot);
        if (clickable != null) {
            clickable.action().accept(event);
        }
    }

    public void setItem(Slot slot, ClickableItem item) {

        int index = slot.toIndex();

        if (isOutside(index))
            return;

        items.put(index, item);
        inventory.setItem(index, item.item());
    }

    public void setItems(SlotSelection selection, ClickableItem item) {

        selection.getSlots().forEach(slot ->
                setItem(slot, item)
        );
    }

    public void fill(SlotSelection selection, ItemStack stack) {

        selection.getSlots().forEach(slot -> {

            int index = slot.toIndex();

            if (isOutside(index))
                return;

            if (!items.containsKey(index)) {
                inventory.setItem(index, stack);
            }
        });
    }

    private boolean isOutside(int index) {
        return index < 0 || index >= size.getSize();
    }

    public Inventory getInventory() {
        return inventory;
    }
}
