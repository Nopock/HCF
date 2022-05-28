package me.nopox.hcf.listeners;

import me.nopox.hcf.HCF;
import me.nopox.hcf.objects.Profile;
import me.nopox.hcf.storage.profiles.ProfileHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class StatsListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        ProfileHandler profileHandler = HCF.getInstance().getProfileHandler();

        profileHandler.getProfile(victim.getUniqueId().toString()).thenAccept(Profile::dispatchDeath);

        if (killer != null) {
            profileHandler.getProfile(killer.getUniqueId().toString()).thenAccept(profile -> {
                profile.dispatchKill(victim);
            });
        }

    }
}
