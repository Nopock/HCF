package me.nopox.hcf.map;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * This class handles stuff such as Warzone, EOTW,
 * SOTW and more.
 *
 * @author Nopox & HCTeams (Some math is from them)
 */
@Getter
public class MapHandler {

    @Getter private boolean eotw = false;
    @Getter private boolean sotw = false;
    @Getter private long remainingSotw = Long.MAX_VALUE;
    @Getter private int teamSize = 5;
    @Getter private int allies = 0;

    @Getter private int claimStartsAt = 500;
    @Getter private int breakBlocksAt = 300;


    /**
     * @param location The location we are checking
     *
     * @return True if the location is Warzone
     */
    public boolean isWarzone(Location location) {
        if (location.getWorld().getEnvironment() != World.Environment.NORMAL) return false;

        return (Math.abs(location.getBlockX()) <= breakBlocksAt && Math.abs(location.getBlockZ()) <= breakBlocksAt) || ((Math.abs(location.getBlockX()) > claimStartsAt || Math.abs(location.getBlockZ()) > claimStartsAt));
    }




}
