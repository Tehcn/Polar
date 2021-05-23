package me.tehcn.polar;

import me.tehcn.polar.enchantments.PolarEnchants;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Polar extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&bPolar&r]&f Plugin loaded and running version " + getDescription().getVersion()));
        PolarEnchants.register();
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&bPolar&r]&f Plugin unloaded!"));
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&bPolar&r]&f Thanks for using Polar!"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String noConsoleMessage = ChatColor.RED + "This command is not configured to run from the console";
        if(label.equalsIgnoreCase("polar")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(noConsoleMessage);
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
                sender.sendMessage(noConsoleMessage);
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
                    return true;
                }
                else if (itemToGive.equalsIgnoreCase("kbstick")) {
                    Map<Enchantment, Integer> enchants = new HashMap<>();
                    enchants.put(Enchantment.KNOCKBACK, 10);
                    PolarItem item = new PolarItem(Material.STICK, "Knockback Stick", enchants);
                    player.getInventory().addItem(item.getItem());
                    return true;
                }
                else if (itemToGive.equalsIgnoreCase("boomstick")) {
                    Map<Enchantment, Integer> enchants = new HashMap<>();
                    enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                    PolarItem item = new PolarItem(Material.BLAZE_ROD, "Boom Stick", null);
                    ItemStack boomstick = item.getItem();
                    ItemMeta meta = boomstick.getItemMeta();
                    ArrayList<String> lore = new ArrayList<String>();
                    lore.add(ChatColor.translateAlternateColorCodes('&', "&6Item Ability: Boom &l&eRIGHT CLICK&r"));
                    lore.add(ChatColor.translateAlternateColorCodes('&', "&7Shoots a fireball where ever you look"));
                    meta.setLore(lore);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
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
        if(label.equalsIgnoreCase("heal")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(noConsoleMessage);
                return true;
            }
            if(args.length>1) {
                Player target = getPlayerByName(args[0]);
                target.setHealth(20);
                return true;
            }
            Player player = (Player) sender;
            player.setHealth(20);
            return true;
        }
        if(label.equalsIgnoreCase("feed")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(noConsoleMessage);
                return true;
            }
            if(args.length>1) {
                Player target = getPlayerByName(args[0]);
                target.setFoodLevel(20);
                return true;
            }
            Player player = (Player) sender;
            player.setFoodLevel(20);

            return true;
        }
//        if(label.equalsIgnoreCase("kit")) {
//            if(!(sender instanceof Player)) {
//                sender.sendMessage(ChatColor.RED + "This command is not configured to run from the console");
//                return true;
//            }
//            if((args.length<1)) {
//                return false;
//            }
//            String kit = args[0];
//            Player player = (Player) sender;
//            if(kit.equalsIgnoreCase("basic")) {
//                List<PolarItem> items = null;
//                Map<Enchantment, Integer> enchants = null;
//                enchants.put(Enchantment.DAMAGE_ALL, 5);
//                PolarItem sword = new PolarItem(Material.IRON_SWORD, "sword", enchants);
//                items.add(sword);
//                PolarKit basicKit = new PolarKit("basic", items);
//                player.getInventory().addItem(Arrays.stream(basicKit.getKitItems()).iterator().next());
//                return true;
//            }
//            else {
//                player.sendMessage(ChatColor.RED + args[0] + " is not a kit!");
//            }
//            return true;
//        }
        if (label.equalsIgnoreCase("telekinesis")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(noConsoleMessage);
                return true;
            }
            Player player = (Player) sender;
            ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
            item.addUnsafeEnchantment(PolarEnchants.TELEKINESIS, 1);

            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList<String>();
            lore.add(ChatColor.GRAY + "Telekinesis");
            meta.setLore(lore);
            item.setItemMeta(meta);

            player.getInventory().addItem(item);
            return true;
        }
        if (label.equalsIgnoreCase("isop")) {
            if(!(sender instanceof Player)) {
                if (args.length<1) {
                    return false;
                }
                Player target = getPlayerByName(args[0]);
                boolean isOpped = target.isOp();
                if(isOpped) {
                    sender.sendMessage(target.getDisplayName() + " is an operator");
                }
                else {
                    sender.sendMessage(target.getDisplayName() + " is not an operator");
                }
                return true;
            }
            if(args.length>1) {
                Player target = getPlayerByName(args[0]);
                Player player = (Player) sender;
                boolean isOpped = target.isOp();
                if(isOpped) {
                    player.sendMessage(target.getDisplayName() + " is an operator");
                }
                else {
                    player.sendMessage(target.getDisplayName() + " is not an operator");
                }
                return true;
            }
            Player player = (Player) sender;
            boolean isOpped = player.isOp();
            if(isOpped) {
                player.sendMessage(player.getDisplayName() + " is an operator");
            }
            else {
                player.sendMessage(player.getDisplayName() + " is not an operator");
            }

            return true;
        }
        if (label.equalsIgnoreCase("fly")) {
            if (!(sender instanceof Player)) {
                if (!(args.length<1)) {
                    sender.sendMessage(noConsoleMessage);
                    return true;
                }
                Player target = getPlayerByName(args[0]);
                if (target.getAllowFlight()) {
                    target.sendMessage(ChatColor.AQUA + "Fly mode disabled");
                    target.setAllowFlight(false);
                    return true;
                }
                target.sendMessage(ChatColor.AQUA + "Fly mode enabled");
                target.setAllowFlight(true);
                return true;
            }
            if (!(args.length<1)) {
                Player target = getPlayerByName(args[0]);
                if (target.getAllowFlight()) {
                    target.sendMessage(ChatColor.AQUA + "Fly mode disabled");
                    target.setAllowFlight(false);
                    return true;
                }
                target.sendMessage(ChatColor.AQUA + "Fly mode enabled");
                target.setAllowFlight(true);
                return true;
            }
            Player player = (Player) sender;
            if (player.getAllowFlight()) {
                player.sendMessage(ChatColor.AQUA + "Fly mode disabled");
                player.setAllowFlight(false);
                return true;
            }
            player.sendMessage(ChatColor.AQUA + "Fly mode enabled");
            player.setAllowFlight(true);
            return true;
        }
        if (label.equalsIgnoreCase("ci")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(noConsoleMessage);
                return true;
            }
            if(args.length>1) {
                Player target = getPlayerByName(args[0]);
                target.getInventory().clear();
                return true;
            }
            Player player = (Player) sender;
            player.getInventory().clear();
        }
        return true;
    }

    public Player getPlayerByName(String name) {
        Collection<Player> onlinePlayers = (Collection<Player>) getServer().getOnlinePlayers();
        for (int i = 0; i < onlinePlayers.size(); i++) {
            Player currentPlayer = (Player) onlinePlayers.toArray()[i];
            if (currentPlayer.getDisplayName().equalsIgnoreCase(name)) {
                return currentPlayer;
            }
        }
        return null;
    }
    public void PolarMessage(String text, Player target) {
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&r[&bPolar&r]" + text));
    }

    // Event Handlers

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand() == null) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(PolarEnchants.TELEKINESIS)) return;
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE || event.getPlayer().getGameMode() == GameMode.SPECTATOR) return;
        if (event.getPlayer().getInventory().firstEmpty() == -1) return;
        if (event.getBlock().getState() instanceof Container) return;

        event.setDropItems(false);
        Player player = event.getPlayer();
        Block block = event.getBlock();

        Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());
        if (drops.isEmpty()) return;
        player.getInventory().addItem(drops.iterator().next());
    }
}
