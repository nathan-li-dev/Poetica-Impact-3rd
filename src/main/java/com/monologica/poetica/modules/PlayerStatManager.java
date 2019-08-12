package com.monologica.poetica.modules;

import com.monologica.poetica.items.equipment.StattedItem;
import com.monologica.poetica.stats.ItemStat;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper module which handles measuring of stat bonuses on players
 */
public class PlayerStatManager {

    private ItemParser parser;

    public PlayerStatManager(ItemParser parser) {
        this.parser = parser;
    }

    /**
     * Get the stats of a player
     * @param  p the player to check the item stats of
     * @return a HashMap of stats:value
     */
    public HashMap<ItemStat, Integer> getStats(Player p) {
        List<StattedItem> items = parseGearOf(p);
        HashMap<ItemStat, Integer> playerStats = new HashMap<ItemStat, Integer>();

        // Loop through all parsed items
        for (StattedItem item: items) {

            // Loop through all stats on the parsed item
            Map<ItemStat, Integer> statsOfItem = item.getStats();
            for (ItemStat stat: statsOfItem.keySet()) {
                if (playerStats.containsKey(stat)) {
                    playerStats.put(stat, playerStats.get(stat) + statsOfItem.get(stat));
                } else {
                    playerStats.put(stat, statsOfItem.get(stat));
                }
            }
        }

        return playerStats;
    }

    /**
     * Parses the gear of a player
     * @param  p the player whose gear is to be parsed
     * @return A list of parsed gear items
     */
    private List<StattedItem> parseGearOf(Player p) {
        PlayerInventory inv = p.getInventory();
        List<StattedItem> items = new ArrayList<StattedItem>();

        ItemStack[] equippedItems = new ItemStack[6];
        equippedItems[0] = inv.getBoots();
        equippedItems[1] = inv.getLeggings();
        equippedItems[2] = inv.getChestplate();
        equippedItems[3] = inv.getHelmet();
        equippedItems[4] = inv.getItemInMainHand();
        equippedItems[5] = inv.getItemInOffHand();

        for (ItemStack equipment: equippedItems) {
            StattedItem parsed = parser.quickParse(equipment);
            if (parsed != null) {
                items.add(parsed);
            }
        }

        return items;
    }
}
