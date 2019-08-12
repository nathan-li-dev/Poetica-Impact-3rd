package com.monologica.poetica.utils;

/**
 * Represents an item with a weight associated. Used for king of the hill randomization
 */
public interface WeightedItem {

    /**
     * Gets the weight of the item
     *
     * @return the item's weight
     */
    public int getWeight();
}
