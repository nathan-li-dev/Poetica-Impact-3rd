package com.monologica.poetica.items.mods;

import com.monologica.poetica.items.equipment.EquipmentType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantmentMod extends EquipmentMod {

    private Enchantment enchantment;
    private int level;

    public EnchantmentMod(String name, String friendlyId, int weight, EquipmentType type, Enchantment enchantment, int level) {
        super(name, friendlyId, weight,true ,type);
        this.enchantment = enchantment;
        this.level = level;
    }

    @Override
    public boolean apply(ItemStack item) {
        if(type.contains(item.getType())) {
            return false;
        }

        ItemMeta im = item.getItemMeta();
        if (im.hasEnchant(enchantment)) {
            int newLevel = level + im.getEnchantLevel(enchantment);
            im.addEnchant(enchantment, newLevel, true);
        } else {
            im.addEnchant(enchantment, level, true);
        }
        item.setItemMeta(im);
        return true;
    }

    @Override
    public boolean remove(ItemStack item) {
        if (!removable) {
            return false;
        }

        super.remove(item);
        ItemMeta im = item.getItemMeta();
        if (im.hasEnchant(enchantment)) {
            int theoreticalLevel = im.getEnchantLevel(enchantment) - level;
            if (theoreticalLevel > 0) {
                im.addEnchant(enchantment, theoreticalLevel, true);
            } else {
                im.removeEnchant(enchantment);
            }
        }
        item.setItemMeta(im);
        return true;
    }

}

