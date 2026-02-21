package dev.lisaa.novainventories.pagination;

import dev.lisaa.novainventories.NovaInventory;
import dev.lisaa.novainventories.item.ClickableItem;
import dev.lisaa.novainventories.slot.Slot;
import dev.lisaa.novainventories.slot.SlotSelection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * @author Lisa Kapahnke
 * @project nova-inventories
 * @created 21.02.2026 - 18:31
 * <p>
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/
public class NovaPagination<T> {

    private final List<Slot> slots;
    private final BiFunction<Player, T, ClickableItem> renderer;

    private final List<T> items = new ArrayList<>();
    private int page = 0;

    public NovaPagination(SlotSelection selection,
                          BiFunction<Player, T, ClickableItem> renderer) {
        this.slots = new ArrayList<>(selection.getSlots());
        this.renderer = renderer;
    }

    public void setItems(List<T> newItems) {

        items.clear();
        items.addAll(newItems);

        // clamp page instead of resetting
        if (page > getMaxPage()) {
            page = getMaxPage();
        }
    }

    public void nextPage() {
        if ((page + 1) * slots.size() < items.size()) {
            page++;
        }
    }

    public void previousPage() {
        if (page > 0) {
            page--;
        }
    }

    public int getPage() {
        return page;
    }

    public int getMaxPage() {
        if (items.isEmpty()) return 0;
        return (items.size() - 1) / slots.size();
    }

    public void render(Player player, NovaInventory inventory) {

        int start = page * slots.size();
        int end = Math.min(start + slots.size(), items.size());

        for (int i = 0; i < slots.size(); i++) {

            int dataIndex = start + i;

            if (dataIndex >= end)
                break;

            T value = items.get(dataIndex);
            ClickableItem clickable = renderer.apply(player, value);

            inventory.setItem(slots.get(i), clickable);
        }
    }
}
