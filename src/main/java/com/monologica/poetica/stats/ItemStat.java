package com.monologica.poetica.stats;

import com.monologica.poetica.items.equipment.EquipmentType;
import com.monologica.poetica.utils.WeightedItem;

/**
 * Represents a Poetica item stat (i.e Melee Damage +2) which is/can be applied to an item
 *
 * @version 0.1.1
 * @author Nathan Li
 */
public class ItemStat implements WeightedItem {

    public static char INTRINSIC_ICON = '✦';
    public static char BONUS_ICON     = '✧';

    protected String name;
    protected EquipmentType applicableType;
    protected StatID id;
    protected int weight;

    public ItemStat(String name, StatID id, EquipmentType applicableType, int weight) {
        this.name = name;
        this.applicableType = applicableType;
        this.id = id;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public StatID getId() {
        return id;
    }

    public EquipmentType getApplicableType() {
        return applicableType;
    }

    public int getWeight() {
        return weight;
    }

}
