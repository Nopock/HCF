package me.nopox.hcf.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Cooldown {
    private Player player;
    private String cooldown;

    private final HashMap<String, HashMap<UUID, Long>> cooldowns = new HashMap<>();

    /**
     * Call this only when you are registering a new cooldown.
     */
    public Cooldown() {

    }

    /**
     * Call this whenever you put a player on cooldown.
     *
     * @param player The player to put on cooldown.
     * @param length The length of the cooldown.
     * @param cooldown The name of the cooldown.
     */
    public Cooldown(Player player, long length, String cooldown) {
        cooldowns.get(cooldown).put(player.getUniqueId(), System.currentTimeMillis() + length);
        this.player = player;
        this.cooldown = cooldown;
    }

    /**
     * Call this whenever you want to check if a player is on cooldown.
     *
     * @param player The player to check.
     * @param cooldown The name of the cooldown.
     */
    public Cooldown(Player player, String cooldown) {
        this.player = player;
        this.cooldown = cooldown;
    }

    /**
     * Call this to create a new cooldown.
     *
     * Cooldown:
     *
     * Cooldown cooldown = new Cooldown();
     * 
     * cooldown.registerCooldown("cooldown";
     *
     *
     * @param cooldown The name of the cooldown.
     */
    public void registerCooldown(String cooldown) {
        cooldowns.put(cooldown, new HashMap<>());
    }

    /**
     * When you call this make sure to access it using
     * {@link Cooldown#Cooldown(Player, String)}.
     *
     * @return True if a player is on cooldown.
     */
    public boolean isOnCooldown() {
        if (!(cooldowns.get(cooldown).get(player.getUniqueId()) > System.currentTimeMillis())) {
            removeCooldown();
            return false;
        }

        return cooldowns.get(cooldown).containsKey(player.getUniqueId());
    }

    /**
     * This removes the cooldown from the player.
     */
    public void removeCooldown() {
        if (!isOnCooldown()) {
            throw new IllegalStateException("Player is not on cooldown!");
        }
        cooldowns.get(cooldown).remove(player.getUniqueId());
    }

    /**
     * @return The remaining time of the cooldown but formatted. (ex. "5m")
     */
    public String getFormattedCooldown() {
        if (!isOnCooldown()) {
            throw new IllegalStateException("Player is not on cooldown!");
        }

        return TimeUtils.formatLongIntoDetailedString((cooldowns.get(cooldown).get(player.getUniqueId()) - System.currentTimeMillis()) / 1000);
    }

}
