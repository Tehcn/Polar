package me.tehcn.polar;

import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_16_R3.generator.InternalChunkGenerator;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Polar extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r[&bPolar&r] Plugin loaded and running version " + getDescription().getVersion()));
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("polar")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "This command is not configured to run from the console");
                return true;
            }
            Player player = (Player) sender;
            if(args.length<1 || args[0].equalsIgnoreCase("test")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&r[&bPolar&r] The plugin is working correctly!"));
                return true;
            }
            String cmd = args[0];
            if(cmd.equalsIgnoreCase("help")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&r[&bPolar&r] /polar <test|help|version>"));
                return true;
            }
            if(cmd.equalsIgnoreCase("version")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&r[&bPolar&r] Running version " + getDescription().getVersion()));
            }
            return true;
        }
        if(label.equalsIgnoreCase("item")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "This command is not configured to run from the console");
                return true;
            }
                Player player = (Player) sender;
                if(args.length<1) {
                    return false;
                }
                String itemToGive = args[0];
                if(itemToGive.equalsIgnoreCase("death")) {
                    Map<Enchantment, Integer> enchants = new HashMap<>();
                    enchants.put(Enchantment.DAMAGE_ALL, 10);
                    enchants.put(Enchantment.FIRE_ASPECT, 10);
                    PolarItem item = new PolarItem(Material.NETHERITE_SWORD, "Death", enchants);
                    player.getInventory().addItem(item.getItem());
                }
                else {
                    if(args.length<3) {
                        return false;
                    }
                    if(args[2] == null || args[3] == null) {
                        Material mat = Material.matchMaterial(args[1]);
                        Map<Enchantment, Integer> enchants = new HashMap<>();
                        PolarItem item = new PolarItem(mat, args[0], enchants);
                        player.getInventory().addItem(item.getItem());
                        return true;
                    }
                    Material mat = Material.matchMaterial(args[1]);
                    Map<Enchantment, Integer> enchants = new HashMap<>();
                    enchants.put(Enchantment.getByKey(NamespacedKey.minecraft(args[2])), Integer.parseInt(args[3]));
                    PolarItem item = new PolarItem(mat, args[0], enchants);
                    player.getInventory().addItem(item.getItem());
                    return true;
                }
        }
        return true;
    }
}