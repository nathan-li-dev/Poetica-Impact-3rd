package com.monologica.poetica.modules;

import com.monologica.poetica.items.equipment.EquipmentType;
import com.monologica.poetica.stats.ItemStat;
import com.monologica.poetica.stats.PercentStat;
import com.monologica.poetica.stats.StatID;

public class ItemStatManager extends IDStorageManager<StatID, ItemStat> {

    public ItemStatManager() {
        storage.put(StatID.MELEE_DAMAGE,
                new ItemStat("Melee Damage", StatID.MELEE_DAMAGE, EquipmentType.WEAPON, 100));
        storage.put(StatID.RANGED_DAMAGE,
                new ItemStat("Ranged Damage", StatID.MELEE_DAMAGE, EquipmentType.WEAPON, 100));
        storage.put(StatID.CRITICAL_DAMAGE,
                new ItemStat("Critical Damage", StatID.MELEE_DAMAGE, EquipmentType.WEAPON, 100));
        storage.put(StatID.ACCURACY,
                new ItemStat("Accuracy", StatID.MELEE_DAMAGE, EquipmentType.WEAPON, 100));
        storage.put(StatID.EVASION,
                new ItemStat("Evasion", StatID.MELEE_DAMAGE, EquipmentType.ARMOUR, 100));
        storage.put(StatID.DEFENCE,
                new ItemStat("Defence", StatID.MELEE_DAMAGE, EquipmentType.ARMOUR, 100));

        storage.put(StatID.CRITICAL_CHANCE,
                new PercentStat("% Critical Chance", StatID.MELEE_DAMAGE, EquipmentType.WEAPON, 100));

    }
}
