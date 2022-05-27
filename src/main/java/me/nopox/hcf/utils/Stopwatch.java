package me.nopox.hcf.utils;

import org.bukkit.Bukkit;

import java.util.logging.Level;

/**
 * @author Nopox
 */
public class Stopwatch {

    private final long current = System.currentTimeMillis();


    /**
     * This builds the stopwatch and prints it out to console.
     *
     * @param s What gets printed in console
     */
    public void build(String s) {
        Bukkit.getLogger().log(Level.INFO, "Fetched " + s + " in " + (System.currentTimeMillis() - current) + "ms");
    }
}
