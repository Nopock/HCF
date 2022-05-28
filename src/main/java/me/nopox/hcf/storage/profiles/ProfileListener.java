package me.nopox.hcf.storage.profiles;

import me.nopox.hcf.HCF;
import me.nopox.hcf.objects.Profile;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.logging.Level;

/**
 * @author Nopox
 */
public class ProfileListener implements Listener {


    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent player) {
        System.out.println("Player " + player.getName() + " is trying to join.");
        HCF plugin = HCF.getInstance();

        plugin.getProfileHandler().getProfile(player.getUniqueId()).thenAccept(p -> {
            System.out.println("Profile found for " + player.getUniqueId() + ": " + player.getName());
            if (p.getId() == null) {
                Profile profile = new Profile(player.getUniqueId(), null, player.getName(), 0, 0, 0, 0, 0, 0L);
                profile.save();

                System.out.println("Profile created for " + player.getUniqueId() + ": " + player.getName());

                return;
            }

            p.setUsername(player.getName());
            p.save();
        });

    }
}
