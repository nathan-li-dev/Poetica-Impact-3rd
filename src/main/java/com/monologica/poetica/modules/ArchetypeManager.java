package com.monologica.poetica.modules;

import com.monologica.poetica.items.equipment.EquipmentArchetype;
import com.monologica.poetica.items.equipment.EquipmentType;
import com.monologica.poetica.stats.ItemStat;
import com.monologica.poetica.stats.StatID;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;

/**
 * Responsible for handling the storage of existing item archetypes. Fed a config file to read from.
 */
public class ArchetypeManager extends IDStorageManager<String, EquipmentArchetype> {

    /**
     * Initializes the archetypes based on values in a yml file
     * @param yml
     * @param stats
     */
    public ArchetypeManager(YamlConfiguration yml, ItemStatManager stats) {
        ConfigurationSection typeSection = yml.getConfigurationSection("");
        for(String type: typeSection.getKeys(false)) {
            EquipmentType slot     = EquipmentType.valueOf(typeSection.getString(type + ".type"));
            Material      material = Material.valueOf(typeSection.getString(type + ".material"));
            String        name     = typeSection.getString(type + ".name");
            int           weight   = typeSection.getInt(type + ".weight");

            HashMap<ItemStat, Float> mainStats = new HashMap<ItemStat, Float>();
            for (String statName: yml.getConfigurationSection(type + ".main-stats").getKeys(false)){
                StatID   statId = StatID.valueOf(statName);
                ItemStat stat   = stats.get(statId);
                if (stat != null) {
                    mainStats.put(stat, (float) yml.getDouble(type + ".main-stats." + statName));
                } else {
                    System.out.println("Unable to identify stat named " + statName);
                }
            }

            EquipmentArchetype archetype = new EquipmentArchetype(mainStats, slot, name, type, material, weight);
            System.out.println(archetype);
            storage.put(archetype.getFriendlyID(), archetype);
        }
    }

}
