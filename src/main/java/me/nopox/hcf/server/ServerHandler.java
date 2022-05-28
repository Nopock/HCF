package me.nopox.hcf.server;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ServerHandler {


    public boolean isWarzone(Location loc) {
        if (loc.getWorld().getEnvironment() != World.Environment.NORMAL) {
            return (false);
        }

        int WARZONE_RADIUS2 = 400;
        int WARZONE_BORDER2 = 1000;


        return (Math.abs(loc.getBlockX()) <= WARZONE_RADIUS2 && Math.abs(loc.getBlockZ()) <= WARZONE_RADIUS2) || ((Math.abs(loc.getBlockX()) > WARZONE_BORDER2 || Math.abs(loc.getBlockZ()) > WARZONE_BORDER2));
    }



}
