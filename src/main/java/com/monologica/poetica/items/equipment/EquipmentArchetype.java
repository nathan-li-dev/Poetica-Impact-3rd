package com.monologica.poetica.items.equipment;

import com.monologica.poetica.stats.ItemStat;
import com.monologica.poetica.utils.WeightedItem;
import org.bukkit.Material;

import java.util.HashMap;

/**
 * Represents a "type" of item, such as a longsword, scythe, etc.
 */
public class EquipmentArchetype implements WeightedItem
{
    /* Stat -> Ratio - Main stats that will be on every randomly generated instance of this archetype. For example, a
       level 100 item will have its stat multiplied by this ratio
     */
    private HashMap<ItemStat, Float> intrinsicStats;
    // What kind of item it is
    private EquipmentType type;
    // Display name
    private String name;
    // ID
    private String friendlyID;
    // The material the archetype will spawn with
    private Material material;
    int weight;

    public EquipmentArchetype(HashMap<ItemStat, Float> stats, EquipmentType type, String name, String friendlyID,
                              Material m, int weight) {
        this.weight = weight;
        this.intrinsicStats = stats;
        this.type = type;
        this.name = name;
        this.material = m;
        this.friendlyID = friendlyID;
    }

    public String getName() {
        return name;
    }

    public EquipmentType getType() {
        return type;
    }

    public Material getMaterial() {
        return material;
    }

    public String getFriendlyID() {
        return friendlyID;
    }

    public int getWeight() {
        return weight;
    }

    public HashMap<ItemStat, Float> getIntrinsicStats() {
        return intrinsicStats;
    }

    public float getRatio(ItemStat s) {
        if (intrinsicStats.containsKey(s)) {
            return intrinsicStats.get(s);
        }
        return 0;
    }

    @Override
    public String toString() {
        String s = "Equipment Archetype: " + name + "\n";
        s += "\tType: " + type + "\n";
        s += "\tMaterial: " + material + "\n";
        s += "\tWeight: " + weight + "\n";
        s += "\tStats:";
        for(ItemStat stat: intrinsicStats.keySet()) {
            s += "\n\t\t" + stat.getName() + ": " + intrinsicStats.get(stat);
        }

        return s;

    }
}
