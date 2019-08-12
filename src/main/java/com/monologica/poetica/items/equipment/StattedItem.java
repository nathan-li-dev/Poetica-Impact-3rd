package com.monologica.poetica.items.equipment;

import com.monologica.poetica.stats.AppliedItemStat;
import com.monologica.poetica.stats.ItemStat;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an item with ItemStats. Can be used in a minimal parse to read only the stats of an item
 */
public class StattedItem {
    protected Map<ItemStat, Integer> stats;

    public StattedItem(Map<ItemStat, Integer> stats) {
        this.stats = stats;
    }

    public StattedItem() {
        this.stats = new HashMap<ItemStat, Integer>();
    }

    public void add(AppliedItemStat appliedStat) {
        ItemStat stat = appliedStat.getStat();

        if (stats.containsKey(stat)) {
            stats.put(stat, stats.get(stat) + appliedStat.getAmount());
        }  else {
            stats.put(stat, appliedStat.getAmount());
        }
    }

    public Map<ItemStat, Integer> getStats() {
        return stats;
    }
}
