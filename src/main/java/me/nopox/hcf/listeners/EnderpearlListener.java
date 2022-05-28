package me.nopox.hcf.listeners;

import me.nopox.hcf.utils.Cooldown;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class EnderpearlListener implements Listener {

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof EnderPearl)) return;

        Player player = (Player) event.getEntity().getShooter();

        Cooldown cooldown = new Cooldown(player, "enderpearl");

        if (cooldown.isOnCooldown()) {
            event.setCancelled(true);
            return;
        }

        Cooldown newCd = new Cooldown(player, );
    }
}
