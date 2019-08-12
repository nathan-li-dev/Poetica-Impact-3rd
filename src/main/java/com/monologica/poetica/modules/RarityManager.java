package com.monologica.poetica.modules;

import com.monologica.poetica.items.RarityLevel;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import static com.monologica.poetica.items.equipment.CustomItem.CUSTOM_RARITY;

/**
 * Module which handles the storage and obtaining of equipment rarities
 */
public class RarityManager extends IDStorageManager<Integer, RarityLevel>{

    public RarityManager(YamlConfiguration yml) {

        RarityLevel common    = new RarityLevel(0, 10, "Common",    ChatColor.WHITE,       ChatColor.GRAY);
        RarityLevel uncommon  = new RarityLevel(1, 10, "Uncommon",  ChatColor.DARK_GREEN,  ChatColor.GREEN);
        RarityLevel rare      = new RarityLevel(2, 5,  "Rare",      ChatColor.DARK_AQUA,   ChatColor.AQUA);
        RarityLevel legendary = new RarityLevel(3, 1,  "Legendary", ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE);

        storage.put(CUSTOM_RARITY.getId(), CUSTOM_RARITY);
        storage.put(common.getId(), common);
        storage.put(uncommon.getId(), uncommon);
        storage.put(rare.getId(), rare);
        storage.put(legendary.getId(), legendary);
    }
}
