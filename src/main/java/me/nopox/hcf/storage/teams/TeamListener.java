package me.nopox.hcf.storage.teams;

import me.nopox.hcf.HCF;
import me.nopox.hcf.objects.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TeamListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        HCF plugin = HCF.getInstance();


        Player player = event.getPlayer();

        plugin.getProfileHandler().getProfile(player.getUniqueId().toString()).thenAccept(profile -> {
            if (profile.getTeam() == null) return;

            if (!plugin.getTeamHandler().getCachedTeams().containsKey(profile.getTeamId().toString())) {
                plugin.getTeamHandler().getTeam(profile.getTeamId().toString()).thenAccept(team -> {
                    plugin.getTeamHandler().getCachedTeams().put(profile.getTeamId().toString(), team);
                });
            }
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        HCF plugin = HCF.getInstance();

        Player player = event.getPlayer();

        plugin.getProfileHandler().getProfile(player.getUniqueId().toString()).thenAccept(profile -> {
            if (profile.getTeam() == null) return;

            plugin.getTeamHandler().getTeam(profile.getTeamId().toString()).thenAccept(Team::saveToMongo);
        });
    }
}
