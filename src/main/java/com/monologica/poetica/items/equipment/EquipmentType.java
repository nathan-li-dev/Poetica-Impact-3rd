package com.monologica.poetica.items.equipment;

import org.bukkit.Material;

/**
 * Represents an "equippable slot type"
 */
public enum EquipmentType {
    NONE, WEAPON, ARMOUR, TOOL;

    public boolean contains(Material m) {
        switch (this) {
            case NONE:
                return true;
            // TODO Fix checks
            case WEAPON:
                return true;
            case ARMOUR:
                return true;
            case TOOL:
                return true;
            default:
                return false;
        }
    }

    public String displayName() {
        return this.name().substring(0,1).toUpperCase() + this.name().substring(1).toLowerCase();
    }
}
