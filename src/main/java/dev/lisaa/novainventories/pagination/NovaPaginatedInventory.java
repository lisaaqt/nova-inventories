package dev.lisaa.novainventories.pagination;

import dev.lisaa.novainventories.NovaInventory;
import dev.lisaa.novainventories.item.ClickableItem;
import dev.lisaa.novainventories.slot.Slot;
import dev.lisaa.novainventories.slot.SlotSelection;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.function.BiFunction;

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

    /* ================= PAGE FORMAT CONFIG ================= */

    protected final BiFunction<Integer, Integer, Component> pageFormat =
            (current, max) ->
                    Component.text(" §7(" + current + "/" + max + ")");

    protected final boolean showPageIndicator = true;

    /* ================= ABSTRACT API ================= */

    protected abstract SlotSelection paginationArea();

    protected abstract ClickableItem nextButton(Player player);

    protected abstract ClickableItem previousButton(Player player);

    protected abstract void provideItems(Player player);

    protected abstract ClickableItem renderItem(Player player, T value);

    /* ================= RENDER ================= */

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

    /* ================= TITLE ================= */

    @Override
    protected Component getCurrentTitle() {

        if (!showPageIndicator || pagination == null)
            return title;

        int current = pagination.getPage() + 1;
        int max = pagination.getMaxPage() + 1;

        if (max <= 1)
            return title;

        return title.append(pageFormat.apply(current, max));
    }

    /* ================= NAVIGATION ================= */

    private void setupNavigation(Player player) {

        int lastRow = size.getRows();

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

    /* ================= PUBLIC HELPERS ================= */

    public int getCurrentPage() {
        return pagination == null ? 1 : pagination.getPage() + 1;
    }

    public int getMaxPage() {
        return pagination == null ? 1 : pagination.getMaxPage() + 1;
    }
}