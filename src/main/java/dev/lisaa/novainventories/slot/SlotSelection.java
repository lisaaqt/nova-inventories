package dev.lisaa.novainventories.slot;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Lisa Kapahnke
 * @project nova-inventories
 * @created 21.02.2026 - 18:28
 * <p>
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/
public class SlotSelection {

    private final Set<Slot> slots = new HashSet<>();

    private SlotSelection() {}

    /* ---------- FACTORY METHODS ---------- */

    public static SlotSelection single(int row, int column) {
        return new SlotSelection().add(new Slot(row, column));
    }

    public static SlotSelection of(Slot slot) {
        return new SlotSelection().add(slot);
    }

    public static SlotSelection row(int row) {
        SlotSelection selection = new SlotSelection();
        for (int col = 1; col <= 9; col++) {
            selection.add(new Slot(row, col));
        }
        return selection;
    }

    public static SlotSelection rows(int fromRow, int toRow) {
        SlotSelection selection = new SlotSelection();
        for (int row = fromRow; row <= toRow; row++) {
            for (int col = 1; col <= 9; col++) {
                selection.add(new Slot(row, col));
            }
        }
        return selection;
    }

    public static SlotSelection column(int column, int totalRows) {
        SlotSelection selection = new SlotSelection();
        for (int row = 1; row <= totalRows; row++) {
            selection.add(new Slot(row, column));
        }
        return selection;
    }

    public static SlotSelection rectangle(int fromRow, int fromCol,
                                          int toRow, int toCol) {
        SlotSelection selection = new SlotSelection();
        for (int row = fromRow; row <= toRow; row++) {
            for (int col = fromCol; col <= toCol; col++) {
                selection.add(new Slot(row, col));
            }
        }
        return selection;
    }

    /* ---------- INSTANCE METHODS ---------- */

    public SlotSelection add(Slot slot) {
        this.slots.add(slot);
        return this;
    }

    public SlotSelection add(int row, int column) {
        return add(new Slot(row, column));
    }

    public Set<Slot> getSlots() {
        return slots;
    }
}