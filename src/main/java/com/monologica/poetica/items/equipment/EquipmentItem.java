package com.monologica.poetica.items.equipment;

import com.monologica.poetica.items.RarityLevel;
import com.monologica.poetica.items.mods.EquipmentMod;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a piece of Poetica equipment which does not have an instance in the game world. That is, a "prototype" of
 * an equipment item
 */
public abstract class EquipmentItem {
    protected EquipmentArchetype type;
    protected RarityLevel rarity;
    protected String name;
    protected ChatColor primaryColour;
    protected ChatColor secondaryColour;
    protected List<EquipmentMod> mods;

    public EquipmentItem(EquipmentArchetype type, RarityLevel rarity, String name, ChatColor primaryColour, ChatColor secondaryColour, List<EquipmentMod> mods) {
        this(type, rarity, name, primaryColour, secondaryColour);
        this.mods = mods;
    }

    public EquipmentItem(EquipmentArchetype type, RarityLevel rarity, String name, ChatColor primaryColour, ChatColor secondaryColour) {
        this.type = type;
        this.rarity = rarity;
        this.name = name;
        this.primaryColour = primaryColour;
        this.secondaryColour = secondaryColour;
        List<EquipmentMod> modList = new ArrayList<EquipmentMod>();
        // modList.add(EquipmentMod.EMPTY_MOD);
        this.mods = modList;
    }

    public boolean addMod(EquipmentMod newMod) {
        for (int i = 0; i < mods.size(); i++) {
            if (!mods.get(i).equals(EquipmentMod.EMPTY_MOD)) {
                continue;
            }
            mods.set(i, newMod);
            return true;
        }
        return false;
    }

    public EquipmentMod removeMod(int index) {
        // If the mod can't be removed, return null
        if ((index >= mods.size()) || (!mods.get(index).isRemovable())) {
            return null;
        }

        EquipmentMod old = mods.get(index);
        mods.set(index, EquipmentMod.EMPTY_MOD);
        return old;
    }

    public EquipmentArchetype getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public RarityLevel getRarity() {
        return rarity;
    }

    public ChatColor getSecondaryColour() {
        return secondaryColour;
    }

    public ChatColor getPrimaryColour() {
        return primaryColour;
    }

    public List<EquipmentMod> getMods() {
        return mods;
    }

}
