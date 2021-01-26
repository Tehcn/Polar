package me.tehcn.polar;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Map;

public class PolarItem {


    private final ItemStack item;

    PolarItem(Material mat, String name, Map<Enchantment, Integer> enchantments) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        item.addUnsafeEnchantments(enchantments);
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }
}
