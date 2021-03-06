package me.nopox.hcf.storage.profiles;

import me.nopox.hcf.HCF;
import me.nopox.hcf.objects.Profile;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.logging.Level;

/**
 * @author Nopox
 */
public class ProfileListener implements Listener {


    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent player) {
        HCF plugin = HCF.getInstance();

        long pvpTimerLength = plugin.getMapHandler().isSotw() ? 0L : 30 * 1000 * 60;


        plugin.getProfileHandler().getProfile(player.getUniqueId().toString()).thenAccept(p -> {
            System.out.println("Profile found for " + player.getUniqueId() + ": " + player.getName());
            if (p == null) {
                plugin.getProfileHandler().create(player.getUniqueId().toString(), pvpTimerLength);
            }
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
        if (!plugin.getProfileHandler().getCachedProfiles().containsKey(player.getUniqueId().toString())) {
            plugin.getProfileHandler().getProfile(player.getUniqueId().toString()).thenAccept(p -> {
                plugin.getProfileHandler().getCachedProfiles().put(player.getUniqueId().toString(), p);
            });
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        HCF.getInstance().getProfileHandler().getProfile(event.getPlayer().getUniqueId().toString()).thenAccept(Profile::saveToMongo);
    }
}
