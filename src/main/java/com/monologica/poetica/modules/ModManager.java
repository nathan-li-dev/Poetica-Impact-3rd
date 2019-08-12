package com.monologica.poetica.modules;

import com.monologica.poetica.items.mods.EquipmentMod;

public class ModManager extends IDStorageManager<String, EquipmentMod> {

    public ModManager() {
        // EquipmentMod testMod = new EnchantmentMod("test", "test", 0, EquipmentType.NONE, Enchantment.ARROW_DAMAGE, 4);

        storage.put(EquipmentMod.EMPTY_MOD.getFriendlyId(),EquipmentMod.EMPTY_MOD);
        //storage.put(testMod.getFriendlyId(), testMod);
    }
}
