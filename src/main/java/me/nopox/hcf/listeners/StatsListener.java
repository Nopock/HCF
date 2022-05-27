package me.nopox.hcf.listeners;

import me.nopox.hcf.HCF;
import me.nopox.hcf.storage.profiles.ProfileHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class StatsListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player dead = event.getEntity();
        Player killer = dead.getKiller();

        ProfileHandler profileHandler = HCF.getInstance().getProfileHandler();

        profileHandler.getProfile(dead.getUniqueId()).thenAccept(profile -> {
            profile.setDeaths(profile.getDeaths() + 1);
            profile.setKillstreak(0);
            profile.save();
        });

        if (killer != null) {
            profileHandler.getProfile(killer.getUniqueId()).thenAccept(profile -> {
                profile.setKills(profile.getKills() + 1);
                profile.setKillstreak(profile.getKillstreak() + 1);
                profile.save();
            });
        }

    }
}
