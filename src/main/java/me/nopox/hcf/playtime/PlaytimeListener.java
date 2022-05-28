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

    private final HashMap<UUID, Long> joined = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        joined.put(player.getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        long playtime = System.currentTimeMillis() - joined.get(player.getUniqueId());

        HCF.getInstance().getProfileHandler().getProfile(player.getUniqueId().toString()).thenAccept(profile -> {
            profile.setPlaytime(profile.getPlaytime() + playtime);
            profile.saveToMongo();
        });

        joined.remove(player.getUniqueId());
    }
}
