package com.monologica.poetica.stats;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class AppliedItemStat {

    private static int    DISPLAY_SPACES = 3;
    private static String DISPLAY_SPACER = "...";

    private ItemStat stat;
    private int amount;

    public AppliedItemStat(ItemStat stat, int amount) {
        this.stat = stat;
        this.amount = amount;
    }


    /**
     * Stores the stat's data to the ItemStack's NBT. Does not add its display information.
     *
     * @param i the ItemStack to apply to
     */
    public void applyToItem(ItemStack i) {
    }

    /**
     * Gets how the stat would be displayed on an item's lore
     */
    public String getDisplayString() {
        String displayString;
        String spacer = "";
        int spacesToFill = DISPLAY_SPACES;

        // Build up the black spacer
        if (amount < 0)    spacesToFill--;
        if (amount >= 10)  spacesToFill--;
        if (amount >= 100) spacesToFill--;

        if (spacesToFill > 0) {
            spacer += ChatColor.BLACK;
            for (int i = 0; i < spacesToFill; i++) {
                spacer += DISPLAY_SPACER;
            }
        }

        // Build the rest of the string
        displayString = String.format("%s%d %s| %s%s", ChatColor.WHITE, amount, ChatColor.DARK_GRAY, ChatColor.GRAY, stat.getName());

        return spacer + displayString;
    }

    /**
     * Returns the level value specified by the object
     *
     * @return
     */
    public int getAmount() {
        return amount;
    }

    public String getName() {
        return stat.getName();
    }

    public StatID getId() {
        return stat.getId();
    }

    public ItemStat getStat() {
        return stat;
    }
}
