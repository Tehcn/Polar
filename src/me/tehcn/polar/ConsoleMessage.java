package me.tehcn.polar;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ConsoleMessage extends JavaPlugin {
    public void send(String m) {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', m));
    }
}
