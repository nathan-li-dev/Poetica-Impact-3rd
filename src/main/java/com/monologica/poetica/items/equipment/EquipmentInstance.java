package com.monologica.poetica.items.equipment;

import com.monologica.poetica.items.PoeticaItem;
import com.monologica.poetica.items.RarityLevel;
import com.monologica.poetica.items.mods.EquipmentMod;
import com.monologica.poetica.stats.AppliedItemStat;
import com.monologica.poetica.stats.ItemStat;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a solid instance of a piece of Poetica equipment. That is, it has fields that only belong to an actual
 * instance of equipment that exists.
 *
 * @version 0.1.1
 * @author Nathan Li
 */
public class EquipmentInstance extends EquipmentItem implements PoeticaItem {

    public static int MAX_UPGRADE_LEVEL = 10;

    protected int upgradeLevel;
    protected int itemLevel;
    protected List<AppliedItemStat> intrinsicStats;
    protected List<AppliedItemStat> bonusStats;
    protected List<String> flavourText;

    /**
     * Full EquipmentInstance constructor. Used to parse an existing EquipmentInstance
     *
     * @param type            The EquipmentArchetype the item belongs to
     * @param rarity          The rarity of the equipment
     * @param name            The name of the equipment
     * @param upgradeLevel    The upgrade level of the equipment
     * @param itemLevel       The item level the equipment was generated at
     * @param primaryColour   The primary style colour of the equipment
     * @param secondaryColour The secondary style colour of the equipment
     * @param intrinsicStats  A list of stats on the item which can be upgraded
     * @param bonusStats      A list of bonus stats on the item
     * @param mods
     */
    public EquipmentInstance(EquipmentArchetype type, RarityLevel rarity, String name, int upgradeLevel, int itemLevel,
                             ChatColor primaryColour, ChatColor secondaryColour, List<AppliedItemStat> intrinsicStats,
                             List<AppliedItemStat> bonusStats, List<EquipmentMod> mods, List<String> lore) {

        this(type, rarity, name, upgradeLevel, itemLevel, primaryColour, secondaryColour, intrinsicStats, bonusStats, mods);
        this.flavourText = lore;
    }

    /**
     * Full EquipmentInstance constructor. Used to parse an existing EquipmentInstance
     *
     * @param type            The EquipmentArchetype the item belongs to
     * @param rarity          The rarity of the equipment
     * @param name            The name of the equipment
     * @param upgradeLevel    The upgrade level of the equipment
     * @param itemLevel       The item level the equipment was generated at
     * @param primaryColour   The primary style colour of the equipment
     * @param secondaryColour The secondary style colour of the equipment
     * @param intrinsicStats  A list of stats on the item which can be upgraded
     * @param bonusStats      A list of bonus stats on the item
     * @param mods
     */
    public EquipmentInstance(EquipmentArchetype type, RarityLevel rarity, String name, int upgradeLevel, int itemLevel,
                             ChatColor primaryColour, ChatColor secondaryColour, List<AppliedItemStat> intrinsicStats,
                             List<AppliedItemStat> bonusStats, List<EquipmentMod> mods) {

        super(type, rarity, name, primaryColour, secondaryColour, mods);
        this.upgradeLevel = upgradeLevel;
        this.itemLevel = itemLevel;
        this.intrinsicStats = intrinsicStats;
        this.bonusStats = bonusStats;
        this.flavourText = null;
    }

    /**
     * EquipmentInstance generator with minimal informational requirements
     *
     * @param type            The EquipmentArchetype the item belongs to
     * @param rarity          The rarity of the equipment
     * @param name            The name of the equipment
     * @param itemLevel       The item level the equipment was generated at
     * @param intrinsicStats  A list of stats on the item which can be upgraded
     * @param bonusStats      A list of bonus stats on the item
     */
    public EquipmentInstance(EquipmentArchetype type, RarityLevel rarity, String name, int itemLevel,
                             List<AppliedItemStat> intrinsicStats, List<AppliedItemStat> bonusStats) {
        super(type, rarity, name, rarity.getPrimaryColour(), rarity.getSecondaryColour());
        this.upgradeLevel = 0;
        this.itemLevel = itemLevel;
        this.intrinsicStats = intrinsicStats;
        this.bonusStats = bonusStats;
        this.flavourText = null;
    }

    /**
     * Upgrades an  item
     */
    public boolean upgradeItem() {
        if (upgradeLevel >= MAX_UPGRADE_LEVEL) {
            return false;
        }
        return false;
    }

    /**
     * Gets the ItemStack representation of the EquipmentInstance
     *
     * @return an ItemStack representation of the current state of the object
     */
    public ItemStack toItem() {
        ItemStack    item            = new ItemStack(type.getMaterial());
        ItemMeta     itemMeta        = item.getItemMeta();
        List<String> lore            = generateLore();

        if (itemMeta == null) {
            return null;
        }

        // Apply the name & lore
        itemMeta.setDisplayName(primaryColour + name);
        itemMeta.setLore(lore);

        // Apply the item stats, if they modify the item in any way
        for (AppliedItemStat stat: intrinsicStats) {
            stat.applyToItem(item);
        }
        for (AppliedItemStat stat: bonusStats) {
            stat.applyToItem(item);
        }

        // Apply all mods
        for (EquipmentMod mod: mods) {
            mod.apply(item);
        }

        // Apply the changes
        item.setItemMeta(itemMeta);
        item = applyToNBT(item);

        // Return the item
        return item;
    }

    /**
     * Generates the lore display of the item. Used internally to get the ItemStack form of a Poetica item
     *
     * @return A List of coloured Strings with the item's lore
     */
    private List<String> generateLore() {
        String       statString;
        List<String> lore = new ArrayList<String>();

        lore.add(String.format("%s%s%s", ChatColor.DARK_GRAY, ChatColor.ITALIC, rarity.getName()));
        lore.add(String.format("%s%s%s", ChatColor.GRAY, ChatColor.ITALIC, type.getName()));
        lore.add(ChatColor.DARK_GRAY + "▂▂▂▂▂▂▂▂▂▂▂▂▂");
        lore.add(ChatColor.DARK_GRAY + "▔▔▔▔▔▔▔▔▔▔▔▔▔");
        for(AppliedItemStat stat: intrinsicStats) {
            statString = String.format("%s%s %s", primaryColour, ItemStat.INTRINSIC_ICON, stat.getDisplayString());
            lore.add(statString);
        }
        for(AppliedItemStat stat: bonusStats) {
            statString = String.format("%s%s %s", secondaryColour, ItemStat.BONUS_ICON, stat.getDisplayString());
            lore.add(statString);
        }
        if (mods != null && mods.size() > 0) {
            lore.add("");
            for (EquipmentMod mod : mods) {
                lore.add(mod.getDisplayString(primaryColour));
            }
        }

        if (flavourText != null && flavourText.size() > 0) {
            lore.add("");
            for(String line: flavourText) {
                lore.add(line);
            }
        }
        return lore;
    }

    /**
     * Stores the item data in the NBT of the item, clearing out old data. Used internally to get the ItemStack form of
     * a Poetica item
     *
     * @param  item the ItemStack to apply the NBT data to
     * @return the modified ItemStack
     */
    private ItemStack applyToNBT(ItemStack item) {
        NBTItem nbti = new NBTItem(item);
        //nbti.removeKey("poetica");

        // Save new data
        String key;
        String lore = "";
        NBTCompound pluginFolder    = nbti.addCompound("poetica");
        NBTCompound mainStatFolder  = pluginFolder.addCompound("mainStats");
        NBTCompound bonusStatFolder = pluginFolder.addCompound("bonusStats");
        NBTCompound modFolder       = pluginFolder.addCompound("mods");

        pluginFolder.setInteger("rarity", rarity.getId());
        pluginFolder.setInteger("level", itemLevel);
        pluginFolder.setInteger("upgradeLevel", upgradeLevel);
        pluginFolder.setString("primary", primaryColour.toString());
        pluginFolder.setString("secondary", secondaryColour.toString());
        pluginFolder.setString("type", type.getFriendlyID());

        // Stats are ID : value
        for (AppliedItemStat stat: intrinsicStats) {
            key = String.valueOf(stat.getId());
            mainStatFolder.setInteger(key, stat.getAmount());
        }

        // Stats are ID : value
        for (AppliedItemStat stat: bonusStats) {
            key = String.valueOf(stat.getId());
            bonusStatFolder.setInteger(key, stat.getAmount());
        }

        // Mods are ID : amount applied
        for (EquipmentMod mod: mods) {
            String idString = String.valueOf(mod.getFriendlyId());
            int currentAmount = modFolder.getInteger(idString);
            modFolder.setInteger(idString, currentAmount + 1);
        }

        // Lore is stored lore: LORE where LORE is the lore separated by ||
        if (flavourText != null) {
            for (String line : flavourText) {
                lore += "||" + line;
            }
        }
        modFolder.setString("lore", lore);

        return nbti.getItem();
    }

    public List<AppliedItemStat> getIntrinsicStats() {
        return intrinsicStats;
    }

    public List<AppliedItemStat> getBonusStats() {
        return bonusStats;
    }

    @Override
    public String toString() {
        String s = name;
        s += "\nPrimary: " + primaryColour;
        s += "\nSecondary: " + secondaryColour;
        for(String line : generateLore()) {
            s += "\n" + ChatColor.stripColor(line);
        }
        return s;
    }
}
