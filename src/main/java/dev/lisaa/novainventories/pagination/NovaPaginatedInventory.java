package dev.lisaa.novainventories.pagination;

import dev.lisaa.novainventories.NovaInventory;
import dev.lisaa.novainventories.item.ClickableItem;
import dev.lisaa.novainventories.slot.Slot;
import dev.lisaa.novainventories.slot.SlotSelection;
import org.bukkit.entity.Player;

/**
 * @author Lisa Kapahnke
 * @project nova-inventories
 * @created 21.02.2026 - 18:32
 * <p>
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/
public abstract class NovaPaginatedInventory<T> extends NovaInventory {

    protected NovaPagination<T> pagination;

    protected abstract SlotSelection paginationArea();
    protected abstract ClickableItem nextButton(Player player);
    protected abstract ClickableItem previousButton(Player player);
    protected abstract void provideItems(Player player);
    protected abstract ClickableItem renderItem(Player player, T value);

    @Override
    protected void render(Player player) {

        if (pagination == null) {
            pagination = new NovaPagination<>(
                    paginationArea(),
                    this::renderItem
            );
        }

        provideItems(player);
        pagination.render(player, this);
        setupNavigation(player);
    }

    private void setupNavigation(Player player) {

        int lastRow = size.getRows();

        // NEXT
        if (pagination.getPage() < pagination.getMaxPage()) {
            setItem(
                    new Slot(lastRow, 9),
                    ClickableItem.of(
                            nextButton(player).item(),
                            e -> {
                                pagination.nextPage();
                                reopen(player);
                            }
                    )
            );
        }

        // PREVIOUS
        if (pagination.getPage() > 0) {
            setItem(
                    new Slot(lastRow, 1),
                    ClickableItem.of(
                            previousButton(player).item(),
                            e -> {
                                pagination.previousPage();
                                reopen(player);
                            }
                    )
            );
        }
    }
}
