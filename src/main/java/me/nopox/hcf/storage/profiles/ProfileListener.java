package me.nopox.hcf.storage.profiles;

import me.nopox.hcf.HCF;
import me.nopox.hcf.objects.Profile;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author Nopox
 */
public class ProfileListener implements Listener {

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent player) {
        HCF plugin = HCF.getInstance();
        plugin.getProfileHandler().getProfile(player.getUniqueId()).thenAccept(p -> {
                    if (p == null) {
                        Profile profile = new Profile(player.getUniqueId(), null, player.getName(), 0, 0, 0, 0, 2.00);
                        profile.save();
                    }
        });


        plugin.getProfileHandler().getProfile(player.getUniqueId()).thenAccept(profile ->  {
            profile.setUsername(player.getName());
            profile.save();
        });
    }
}
