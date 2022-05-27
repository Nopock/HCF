package me.nopox.hcf.utils;

import org.bukkit.ChatColor;

/**
 * @author Nopox
 */
public class CC {


    /**
     * @param s The string you want to colorize
     * @return The colored string
     */
    public static String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
