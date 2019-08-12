package com.monologica.poetica.items.equipment;

import com.monologica.poetica.items.RarityLevel;
import com.monologica.poetica.items.mods.EquipmentMod;
import com.monologica.poetica.stats.ItemStat;
import com.monologica.poetica.utils.WeightedItem;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;

import java.util.List;
import java.util.Map;

/**
 * Represents a prototype of a custom item
 */
public class CustomItem extends EquipmentItem implements WeightedItem {

    public static RarityLevel CUSTOM_RARITY = new RarityLevel(-1, -1, "Unique", ChatColor.WHITE, ChatColor.WHITE);

    // ItemStat : ratio
    private Map<ItemStat, Float> intrinsicStats;
    private Map<ItemStat, Float> bonusStats;
    private Map<Enchantment, Integer> enchants;
    private List<String> lore;
    int weight;

    public CustomItem(EquipmentArchetype type, String name, ChatColor primaryColour,
                      ChatColor secondaryColour, List<EquipmentMod> mods, Map<ItemStat, Float> intrinsicStats,
                      Map<ItemStat, Float> bonusStats, Map<Enchantment, Integer> enchants, int weight, List<String> lore) {
        super(type, CUSTOM_RARITY, name, primaryColour, secondaryColour, mods);
        this.intrinsicStats = intrinsicStats;
        this.bonusStats = bonusStats;
        this.enchants = enchants;
        this.weight = weight;
        this.lore = lore;
    }

    public Map<Enchantment, Integer> getEnchants() {
        return enchants;
    }

    public Map<ItemStat, Float> getBonusStats() {
        return bonusStats;
    }

    public Map<ItemStat, Float> getIntrinsicStats() {
        return intrinsicStats;
    }

    public int getWeight() {
        return weight;
    }

    public List<String> getLore() {
        return lore;
    }

    @Override
    public String toString() {
        String s = "Custom Item: " + name;
        s += "\n\tType: " + type.getName();
        s += "\n\tPrimary: " + primaryColour.toString();
        s += "\n\tSecondary: " + secondaryColour.toString();
        s += "\n\tMain stats";
        for (ItemStat stat: intrinsicStats.keySet()) {
            s += "\n\t\t" + stat.getName() + ": " + intrinsicStats.get(stat);
        }
        s += "\n\tBonus stats";
        for (ItemStat stat: bonusStats.keySet()) {
            s += "\n\t\t" + stat.getName() + ": " + bonusStats.get(stat);
        }
        s += "\n\tEnchantments";
        for (Enchantment enchantment: enchants.keySet()) {
            s += "\n\t\t" + enchantment.getKey() + ": " + enchants.get(enchantment);
        }

        return s;
    }
}
