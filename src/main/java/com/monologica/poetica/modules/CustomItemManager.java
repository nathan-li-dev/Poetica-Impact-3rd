package com.monologica.poetica.modules;

import com.monologica.poetica.items.equipment.CustomItem;
import com.monologica.poetica.items.equipment.EquipmentArchetype;
import com.monologica.poetica.items.mods.EquipmentMod;
import com.monologica.poetica.stats.ItemStat;
import com.monologica.poetica.stats.StatID;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores a collection of custom items in a friendlyID:CustomItem map
 */
public class CustomItemManager extends IDStorageManager <String, CustomItem> {
    public CustomItemManager(YamlConfiguration yml, ItemStatManager stats, ArchetypeManager archetypes, ModManager mods) {

        EquipmentArchetype type;
        int weight;
        String name;
        ChatColor primary;
        ChatColor secondary;
        Map<ItemStat, Float> intrinsicStats;
        Map<ItemStat, Float> bonusStats;
        Map<Enchantment, Integer> enchants;
        List<EquipmentMod> itemMods;
        List<String> lore;

        for(String friendlyId: yml.getConfigurationSection("").getKeys(false)) {
            ConfigurationSection itemSec = yml.getConfigurationSection(friendlyId);

            weight    = itemSec.getInt("weight");
            name      = itemSec.getString( "name");
            type      = archetypes.get(itemSec.getString( "type"));
            primary   = ChatColor.valueOf(itemSec.getString( "primary-colour"));
            secondary = ChatColor.valueOf(itemSec.getString("secondary-colour"));
            lore = itemSec.getStringList("lore");

            intrinsicStats = new HashMap<ItemStat, Float>();
            bonusStats = new HashMap<ItemStat, Float>();
            enchants = new HashMap<Enchantment, Integer>();
            itemMods = new ArrayList<EquipmentMod>();

            ConfigurationSection mainStatsSec = itemSec.getConfigurationSection("main-stats");
            for (String statName: mainStatsSec.getKeys(false)){
                StatID statId   = StatID.valueOf(statName);
                ItemStat stat   = stats.get(statId);
                if (stat != null) {
                    intrinsicStats.put(stat, (float) mainStatsSec.getDouble(statName));
                } else {
                    System.out.println("Error when parsing main stats: Unable to identify stat named " + statName);
                }
            }

            ConfigurationSection bonusStatsSec = itemSec.getConfigurationSection("bonus-stats");
            if (bonusStatsSec != null) {
                for (String statName : bonusStatsSec.getKeys(false)) {
                    StatID statId = StatID.valueOf(statName);
                    ItemStat stat = stats.get(statId);
                    if (stat != null) {
                        bonusStats.put(stat, (float) bonusStatsSec.getDouble(statName));
                    } else {
                        System.out.println("Error when parsing main stats: Unable to identify stat named " + statName);
                    }
                }
            }

            ConfigurationSection enchantSec = itemSec.getConfigurationSection("enchantments");
            if (enchantSec != null) {
                for (String enchName : enchantSec.getKeys(false)) {
                    Enchantment ench = Enchantment.getByKey(NamespacedKey.minecraft(enchName));
                    int value = enchantSec.getInt(enchName);
                    enchants.put(ench, value);
                }
            }

            ConfigurationSection modSec = itemSec.getConfigurationSection("mods");
            // itemMods.add(EquipmentMod.EMPTY_MOD);
            if (modSec != null) {
                for (String modIdString : enchantSec.getKeys(false)) { ;
                    int amount = modSec.getInt(modIdString);
                    EquipmentMod mod = mods.get(modIdString);
                    for(int i = 0; i < amount; i++) {
                        itemMods.add(mod);
                    }
                }
            }

            CustomItem c = new CustomItem(type, name, primary, secondary, itemMods, intrinsicStats, bonusStats, enchants, weight, lore);

            System.out.println(c);

            storage.put(friendlyId, c);

        }
    }
}
