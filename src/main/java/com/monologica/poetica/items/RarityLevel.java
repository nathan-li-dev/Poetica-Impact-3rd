package com.monologica.poetica.items;

import com.monologica.poetica.utils.WeightedItem;
import org.bukkit.ChatColor;

/**
 * Represents a level of rarity or tier of rarity. Custom ones may be instantiated for certain needs.
 */
public class RarityLevel implements WeightedItem {

    int id;
    String name;
    ChatColor primaryColour;
    ChatColor secondaryColour;
    int weight;

    public RarityLevel(int id, int weight, String name, ChatColor primary, ChatColor secondary) {
        this.id = id;
        this.name = name;
        this.primaryColour = primary;
        this.secondaryColour = secondary;
        this.weight = weight;
    }

    public ChatColor getPrimaryColour() {
        return primaryColour;
    }

    public ChatColor getSecondaryColour() {
        return secondaryColour;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }
}
