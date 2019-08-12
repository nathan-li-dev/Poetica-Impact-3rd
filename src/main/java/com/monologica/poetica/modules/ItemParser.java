package com.monologica.poetica.modules;

import com.monologica.poetica.PoeticaController;
import com.monologica.poetica.items.RarityLevel;
import com.monologica.poetica.items.equipment.EquipmentArchetype;
import com.monologica.poetica.items.equipment.EquipmentInstance;
import com.monologica.poetica.items.equipment.StattedItem;
import com.monologica.poetica.items.mods.EquipmentMod;
import com.monologica.poetica.stats.AppliedItemStat;
import com.monologica.poetica.stats.ItemStat;
import com.monologica.poetica.stats.StatID;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parses ItemStacks into EquipmentInstances by reading their NBT data
 */
public class ItemParser {

    private RarityManager rarityManager;
    private ItemStatManager itemStatManager;
    private ModManager modManager;
    private ArchetypeManager archetypeManager;

    public ItemParser(PoeticaController controller) {
        this.archetypeManager = controller.getArchetypeManager();
        this.itemStatManager = controller.getItemStatManager();
        this.modManager = controller.getModManager();
        this.rarityManager = controller.getEquipmentRarities();
    }

    public boolean isPoeticaItem(ItemStack item) {
        NBTItem nbti = new NBTItem(item);
        return nbti.hasKey("poetica");
    }

    /**
     * Does a short parse to get only the stats of an item
     *
     * @param item The item to parse
     * @return an object which contains the AppliedStats of an item
     */
    public StattedItem quickParse(ItemStack item) {
        NBTItem nbti = new NBTItem(item);
        NBTCompound pluginFolder    = nbti.addCompound("poetica");
        NBTCompound mainStatFolder  = pluginFolder.addCompound("mainStats");
        NBTCompound bonusStatFolder = pluginFolder.addCompound("bonusStats");

        StattedItem parsed = new StattedItem();
        if (!isPoeticaItem(item)) {
            return parsed;
        }

        // Load main stats
        for(String key: mainStatFolder.getKeys()) {
            StatID id = StatID.valueOf(key);
            int value = mainStatFolder.getInteger(key);
            ItemStat stat = itemStatManager.get(id);
            parsed.add(new AppliedItemStat(stat, value));
        }

        // Load bonus stats
        for(String key: bonusStatFolder.getKeys()) {
            StatID id = StatID.valueOf(key);
            int value = bonusStatFolder.getInteger(key);
            ItemStat stat = itemStatManager.get(id);
            parsed.add(new AppliedItemStat(stat, value));
        }

        return parsed;
    }

    public EquipmentInstance parse(ItemStack item) {
        if(!isPoeticaItem(item)) {
            return null;
        }

        long start = System.nanoTime();

        NBTItem nbti = new NBTItem(item);
        NBTCompound pluginFolder    = nbti.addCompound("poetica");
        NBTCompound mainStatFolder  = pluginFolder.addCompound("mainStats");
        NBTCompound bonusStatFolder = pluginFolder.addCompound("bonusStats");
        NBTCompound modFolder       = pluginFolder.addCompound("mods");

        RarityLevel rarity;
        EquipmentArchetype type;
        int itemLevel;
        int upgradeLevel;
        ChatColor primary;
        ChatColor secondary;
        List<AppliedItemStat> intrinsicStats;
        List<AppliedItemStat> bonusStats;
        List<EquipmentMod> mods;
        List<String> loreLines = null;
        String name;

        // Load basic information
        name = item.getItemMeta().getDisplayName();
        itemLevel = pluginFolder.getInteger("level");
        upgradeLevel = pluginFolder.getInteger("upgradeLevel");
        type = archetypeManager.get(pluginFolder.getString("type"));
        rarity = rarityManager.get(pluginFolder.getInteger("rarity"));
        primary = ChatColor.valueOf(pluginFolder.getString("primary"));
        secondary = ChatColor.valueOf(pluginFolder.getString("secondary"));

        // Load main stats
        intrinsicStats = new ArrayList<AppliedItemStat>();
        bonusStats = new ArrayList<AppliedItemStat>();
        for(String key: mainStatFolder.getKeys()) {
            StatID id = StatID.valueOf(key);
            int value = mainStatFolder.getInteger(key);
            ItemStat stat = itemStatManager.get(id);
            intrinsicStats.add(new AppliedItemStat(stat, value));
        }

        // Load bonus stats
        for(String key: bonusStatFolder.getKeys()) {
            StatID id = StatID.valueOf(key);
            int value = bonusStatFolder.getInteger(key);
            ItemStat stat = itemStatManager.get(id);
            bonusStats.add(new AppliedItemStat(stat, value));
        }

        // Load mods
        mods = new ArrayList<EquipmentMod>();
        for(String key: modFolder.getKeys()) {
            int value = modFolder.getInteger(key);
            for(int i = 0; i < value; i++) {
                mods.add(modManager.get(key));
            }
        }

        // Try to load lore
        if (modFolder.hasKey("lore")) {
            String lore = modFolder.getString("lore");
            loreLines = Arrays.asList(lore.split("||"));
        }

        long end = System.nanoTime();
        long elapsed = end - start;
        System.out.println("Parsing item took " + elapsed + " nano seconds");

        return new EquipmentInstance(type, rarity, name, upgradeLevel, itemLevel, primary, secondary, intrinsicStats, bonusStats, mods, loreLines);

    }
}
