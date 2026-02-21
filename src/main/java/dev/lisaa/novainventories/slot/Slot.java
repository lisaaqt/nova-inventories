package dev.lisaa.novainventories.slot;

/**
 * @author Lisa Kapahnke
 * @project nova-inventories
 * @created 21.02.2026 - 18:12
 * <p>
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/
public record Slot(int row, int column) {

    public Slot {
        if (row < 1)
            throw new IllegalArgumentException("Row must be >= 1");
        if (column < 1 || column > 9)
            throw new IllegalArgumentException("Column must be between 1-9");
    }

    public static Slot of(int row, int column) {
        return new Slot(row, column);
    }

    public int toIndex() {
        return (row - 1) * 9 + (column - 1);
    }

    public Slot offset(int rowOffset, int colOffset) {
        return new Slot(row + rowOffset, column + colOffset);
    }
}
