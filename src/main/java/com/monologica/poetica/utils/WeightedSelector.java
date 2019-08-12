package com.monologica.poetica.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Performs weighted random selection
 */
public class WeightedSelector<T extends WeightedItem> {
    private List<T> elements;
    private Random rand;

    private int totalWeight;

    /**
     * Creates a WeightedSelector instance
     *
     * @param elements the WeightedElements to select from
     */
    public WeightedSelector(final List<T> elements) {
        this.rand = new Random();
        this.elements = elements;

        if (elements.size() == 0) {
            throw new IllegalArgumentException("Selector cannot select an item from an empty list");
        }

        for(WeightedItem element: elements) {
            totalWeight += element.getWeight();
        }
    }

    /**
     * Creates a WeightedSelector instance
     *
     * @param elements the WeightedElements to select from
     */
    public WeightedSelector(final T[] elements) {
        this(Arrays.asList(elements));
    }

    /**
     * Picks a random WeightedElement
     *
     * @return a random WeightedElement from the elements list
     */
    public T next() {
        int selection = rand.nextInt(totalWeight + 1);

        for(int i = 0; i < elements.size(); i++) {
            int weight = elements.get(i).getWeight();
            if (selection < elements.get(i).getWeight()) {
                return elements.get(i);
            }
            selection -= weight;
        }

        // Fallback
        return elements.get(0);
    }
}
