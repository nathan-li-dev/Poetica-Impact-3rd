package com.monologica.poetica.items.mods;

import com.monologica.poetica.items.PoeticaItem;
import com.monologica.poetica.items.equipment.EquipmentType;
import com.monologica.poetica.utils.WeightedItem;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class EquipmentMod implements PoeticaItem, WeightedItem {
    public static char MOD_ICON = '❖';

    protected String name;
    protected int weight;
    protected String friendlyId;
    protected EquipmentType type;
    protected boolean removable;

    public static EquipmentMod EMPTY_MOD = new EquipmentMod("Empty Mod Slot", "empty", 0, false, EquipmentType.NONE);

    /**
     * Creates an EquipmentMod
     *
     * @param name       The name that will be displayed on an item's lore
     * @param friendlyId The name that should be used within the code to refer to this mod
     * @param weight     How often the mod will be found
     * @param removable  If the mod can be removed from an item
     * @param type       What kind of items the mod can be applied to
     */
    public EquipmentMod(String name, String friendlyId, int weight, boolean removable, EquipmentType type) {
        this.name = name;
        this.friendlyId = friendlyId;
        this.weight = weight;
        this.type = type;
        this.removable = removable;
    }

    /**
     * Gets the EquipmentMod as an ItemStack
     * @return
     */
    public ItemStack toItem() {
        return null;
    }

    /**
     * Applies the EquipmentMod to an ItemStack in a way not covered by EquipmentItem.toItem().
     *
     * @param item The item to apply the mod to
     * @return True if the mod has been applied, otherwise false
     */
    public boolean apply(ItemStack item) {
        return type.contains(item.getType());
    }

    /**
     * Removes the EquipmentMod from an ItemStack
     *
     * @param item The item to remove the mod from
     * @return True if the mod has been removed, otherwise false
     */
    public boolean remove(ItemStack item) {
        return removable;
    }

    /**
     * Gets how a Mod will be displayed on an item lore, including the icon.
     * @return A coloured string that will be added to an item's lore
     */
    public String getDisplayString(ChatColor iconColour) {
        String displayString;
        ChatColor usedColor = friendlyId.equals(EMPTY_MOD.friendlyId) ? ChatColor.DARK_GRAY : iconColour;
        displayString = String.format("%s%s %s%s", usedColor, MOD_ICON, ChatColor.GRAY, name);

        return displayString;
    }

    /**
     * The string representation of an EquipmentMod. Used for debugging.
     * @return the string representation of an EquipmentMo
     */
    @Override
    public String toString() {
        String s = "Equipment mod: " + name;
        s += "\n\tWeight: " + weight;
        s += "\n\tType: " + type;
        s += "\n\tRemovable: " + removable;

        return s;
    }

    public String getFriendlyId() {
        return friendlyId;
    }

    public String getName() {
        return name;
    }

    public EquipmentType getType() {
        return type;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isRemovable() {
        return removable;
    }
}
