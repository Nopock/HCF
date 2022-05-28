package me.nopox.hcf.playtime;

import me.nopox.hcf.HCF;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlaytimeListener implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        HCF.getInstance().getPlayerCache().put(player.getUniqueId().toString(), System.currentTimeMillis());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        long playtime = System.currentTimeMillis() - HCF.getInstance().getPlayerCache().get(player.getUniqueId().toString());

        HCF.getInstance().getProfileHandler().getProfile(player.getUniqueId().toString()).thenAccept(profile -> {
            profile.setPlaytime(profile.getPlaytime() + playtime);
            profile.saveToMongo();
        });

        HCF.getInstance().getPlayerCache().remove(player.getUniqueId().toString());
    }
}
