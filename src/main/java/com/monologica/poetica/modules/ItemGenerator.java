package com.monologica.poetica.modules;

import com.monologica.poetica.items.PoeticaItem;
import com.monologica.poetica.items.RarityLevel;
import com.monologica.poetica.items.equipment.*;
import com.monologica.poetica.items.mods.EquipmentMod;
import com.monologica.poetica.stats.AppliedItemStat;
import com.monologica.poetica.stats.ItemStat;
import com.monologica.poetica.stats.PercentStat;
import com.monologica.poetica.utils.WeightedSelector;

import java.util.*;

/**
 * Module which handles the generation of Poetica items
 */
public class ItemGenerator {
    private float BONUS_RATIO = 0.1f;

    private WeightedSelector<RarityLevel> equipmentRaritySelector;
    private WeightedSelector<EquipmentArchetype> archetypeSelector;
    private WeightedSelector<CustomItem> customItemSelector;
    private WeightedSelector<ItemStat> statSelector;
    private WeightedSelector<EquipmentMod> modSelector;

    private List<String> prefixes;
    private List<String> suffixes;
    private Random rand;

    /**
     * Initializes the ItemGenerator
     */
    public ItemGenerator(RarityManager rarityManager, ArchetypeManager archetypeManager, CustomItemManager customItemManager,
            ModManager modManager, ItemStatManager itemStatManager, List<String> prefixes, List<String> suffixes) {

        this.equipmentRaritySelector = new WeightedSelector<RarityLevel>(rarityManager.getStorage());
        this.archetypeSelector = new WeightedSelector<EquipmentArchetype>(archetypeManager.getStorage());
        this.customItemSelector = new WeightedSelector<CustomItem>(customItemManager.getStorage());
        this.modSelector = new WeightedSelector<EquipmentMod>(modManager.getStorage());
        this.statSelector = new WeightedSelector<ItemStat>(itemStatManager.getStorage());
        this.prefixes = prefixes;
        this.suffixes = suffixes;
        this.rand = new Random();


        System.out.println("Created item generator.");
    }

    public PoeticaItem nextItem(int itemLevel) {
        int actualItemLevel = rand.nextInt(itemLevel + 1);
        return nextEquipment(actualItemLevel);
    }

    /**
     * Generates a random PoeticaItem, type and rarity will be randomized
     *
     * @param itemLevel the item level of the item
     * @return a random PoeticaItem
     */
    public EquipmentInstance nextEquipment(int itemLevel) {
        return nextEquipment(itemLevel, nextArchetype(), nextRarity());
    }

    /**
     * Generates a random PoeticaItem, rarity will be randomized
     *
     * @param itemLevel the item level of the item
     * @param type      the archetype of the item
     * @return a random PoeticaItem
     */
    public EquipmentInstance nextEquipment(int itemLevel, EquipmentArchetype type) {
        return nextEquipment(itemLevel, type, nextRarity());
    }

    /**
     * Generates a random PoeticaItem, type will be randomized
     *
     * @param itemLevel the item level of the item
     * @param rarity      the archetype of the item
     * @return a random PoeticaItem
     */
    public EquipmentInstance nextEquipment(int itemLevel, RarityLevel rarity) {
        return nextEquipment(itemLevel, nextArchetype(), rarity);
    }

    /**
     * Generates a random PoeticaItem, only the stats will be randomized
     *
     * @param itemLevel the item level of the item
     * @param type      the archetype of the item
     * @param rarity    the rarity of the item
     * @return
     */
    public EquipmentInstance nextEquipment(int itemLevel, EquipmentArchetype type, RarityLevel rarity) {
        String name = nextName();
        int numBonus;

        HashMap<ItemStat, Float> requiredStats = type.getIntrinsicStats();
        List<AppliedItemStat> mains = new ArrayList<AppliedItemStat>();
        List<AppliedItemStat> bonus = new ArrayList<AppliedItemStat>();

        // Generate main stats
        for(ItemStat stat: requiredStats.keySet()) {
            int statLevel = generateMainStat(stat, type, itemLevel);
            AppliedItemStat appliedStat = new AppliedItemStat(stat, statLevel);
            mains.add(appliedStat);
        }

        // Generate bonus stats
        numBonus = randomInt(0, 3);
        for (int i = 0; i < numBonus; i++) {
            ItemStat randomStat = nextStat();
            int bonusLevel = generateBonusStat(randomStat, itemLevel);
            AppliedItemStat appliedStat = new AppliedItemStat(randomStat, bonusLevel);
            bonus.add(appliedStat);
        }

        return new EquipmentInstance(type, rarity, name, itemLevel, mains, bonus);
    }

    /**
     * Generates a random custom item of a given level
     *
     * @return an EquipmentInstance built off a random CustomItem
     */
    public EquipmentInstance nextCustom(int itemLevel) {
        return nextCustom(itemLevel, customItemSelector.next());
    }

    /**
     * Generates a custom item of a given level
     *
     * @param  item the CustomItem which will be used
     * @return an EquipmentInstance built off a given CustomItem
     */
    public EquipmentInstance nextCustom(int itemLevel, CustomItem item) {
        int numBonus;

        List<AppliedItemStat> mainStats = new ArrayList<AppliedItemStat>();
        List<AppliedItemStat> bonusStats = new ArrayList<AppliedItemStat>();

        Map<ItemStat, Float> mainRatios = item.getIntrinsicStats();
        Map<ItemStat, Float> bonusRatios = item.getBonusStats();

        // Generate main stats
        for(ItemStat s: mainRatios.keySet()) {
            int amount = generateMainStat(s, mainRatios.get(s), itemLevel);
            mainStats.add(new AppliedItemStat(s, amount));
        }

        // Generate intrinsic bonus stats
        for(ItemStat s: bonusRatios.keySet()) {
            int amount = generateBonusStat(s, bonusRatios.get(s), itemLevel);
            bonusStats.add(new AppliedItemStat(s, amount));
        }

        // Generate random bonus stats
        numBonus = randomInt(0, 2);
        for (int i = 0; i < numBonus; i++) {
            ItemStat randomStat = nextStat();
            int bonusLevel = generateBonusStat(randomStat, itemLevel);
            AppliedItemStat appliedStat = new AppliedItemStat(randomStat, bonusLevel);
            bonusStats.add(appliedStat);
        }

        return new EquipmentInstance(item.getType(), item.getRarity(), item.getName(), 0, itemLevel,
                item.getPrimaryColour(), item.getSecondaryColour(), mainStats, bonusStats, item.getMods(), item.getLore());
    }

    /**
     * Generates a random EquipmentMod
     * @return an EquipmentMod
     */
    public EquipmentMod nextMod() {
        return modSelector.next();
    }

    /**
     * Picks a random ItemStat
     * @return an ItemStat
     */
   public ItemStat nextStat() {
        return statSelector.next();
   }

    /**
     * Picks a random EquipmentArchetype
     * @return an EquipmentArchetype
     */
   public EquipmentArchetype nextArchetype() {
        return archetypeSelector.next();
   }

    /**
     * Picks a random RarityLevel
     * @return a RarityLevel
     */
   public RarityLevel nextRarity() {
        return equipmentRaritySelector.next();
   }

    /**
     * Picks a random name
     * @return a two-word string composed of a prefix and suffix
     */
    public String nextName() {
        return nextPrefix() + " " + nextSuffix();
    }

    /**
     * Picks a random prefix
     * @return a prefix word, usually an adjective or adverb
     */
    public String nextPrefix() {
        return prefixes.get(rand.nextInt(prefixes.size()));
    }

    /**
     * Picks a random suffix
     * @return a suffix word, usually a noun or verb
     */
    public String nextSuffix() {
        return suffixes.get(rand.nextInt(suffixes.size()));
    }

    /**
     * Generates a main stat for an item, provided there is a customized stat multiplier
     * PercentStats will be given a value from 1 to 10%
     *
     * @param stat      The stat whose value will be generated
     * @param ratio     The ratio to multiply the stat by
     * @param itemLevel The item level of the item to generate
     * @return an integer
     */
    public int generateMainStat(ItemStat stat, float ratio, int itemLevel) {
        if (stat instanceof PercentStat) {
            return randomInt(1, 11);
        }

        float crude = itemLevel * ratio;
        int rounded = Math.round(crude);

        // Pick a number that's rounded +- 3
        int upperBound = rounded + 3;
        int lowerBound = rounded - 3;
        int randomized = randomInt(lowerBound, upperBound + 1);

        return Math.max(1, randomized);
    }

    /**
     * Generates a main stat for an item, using a ratio from an archetype
     * PercentStats will be given a value from 1 to 10%
     *
     * @param stat      The stat whose value will be generated
     * @param archetype The archetype whose ratio for the stat will be probed
     * @param itemLevel The item level of the item to generate
     * @return an integer
     */
    public int generateMainStat(ItemStat stat, EquipmentArchetype archetype, int itemLevel) {
        return generateMainStat(stat, archetype.getRatio(stat), itemLevel);
    }

    /**
     * Generates a bonus stat for an item, using a custom ratio
     * PercentStats will be given a value from 1 to 10%
     *
     * @param ratio     The ratio to multiply the bonus stat by
     * @param itemLevel The item level of the item to generate
     * @return an integer
     */
    public int generateBonusStat(ItemStat stat, float ratio, int itemLevel) {
        if (stat instanceof PercentStat) {
            return randomInt(1, 11);
        }

        float crude = itemLevel * ratio;
        int rounded = Math.round(crude);

        // Pick a number that's rounded +- 3
        int upperBound = rounded + 3;
        int lowerBound = rounded - 3;
        int randomized = randomInt(lowerBound, upperBound + 1);

        return Math.max(1, randomized);
    }

    /**
     * Generates a bonus stat for an item
     *
     * @param itemLevel The item level of the item to generate
     * @return an integer
     */
    public int generateBonusStat(ItemStat stat, int itemLevel) {
        return generateBonusStat(stat, BONUS_RATIO, itemLevel);
    }

    /**
     * Returns a random integer between two bounds
     *
     * @param lowerBound lower bound, inclusive
     * @param upperBound upper bound, exclusive
     * @return a random integer between lowerBound and upperBound
     */
    private int randomInt(int lowerBound, int upperBound) {
         return rand.nextInt(upperBound - lowerBound) + lowerBound;
    }

}
