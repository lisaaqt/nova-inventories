package dev.lisaa.novainventories.size;

/**
 * @author Lisa Kapahnke
 * @project nova-inventories
 * @created 21.02.2026 - 19:14
 * <p>
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/
public enum InventorySize {

    ZERO(0),
    ONE_ROW(9),
    TWO_ROWS(18),
    THREE_ROWS(27),
    FOUR_ROWS(36),
    FIVE_ROWS(45),
    SIX_ROWS(54);

    private final int size;

    InventorySize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getRows() {
        return size / 9;
    }

    public static InventorySize ofRows(int rows) {
        return switch (rows) {
            case 0 -> ZERO;
            case 1 -> ONE_ROW;
            case 2 -> TWO_ROWS;
            case 3 -> THREE_ROWS;
            case 4 -> FOUR_ROWS;
            case 5 -> FIVE_ROWS;
            case 6 -> SIX_ROWS;
            default -> throw new IllegalArgumentException("Invalid row count: " + rows);
        };
    }
}
