package com.monologica.poetica.stats;

import com.monologica.poetica.items.equipment.EquipmentType;

public class PercentStat extends ItemStat {
    public PercentStat(String name, StatID id, EquipmentType applicableType, int weight) {
        super(name, id, applicableType, weight);
    }
}
