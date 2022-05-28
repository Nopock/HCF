package me.nopox.hcf.listeners;

import me.nopox.hcf.utils.CC;
import me.nopox.hcf.utils.Cooldown;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class EnderpearlListener implements Listener {

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        if (!(event.getEntity() instanceof EnderPearl)) return;

        Player player = (Player) event.getEntity().getShooter();



        if (Cooldown.isOnCooldown("enderpearl", player)) {
            event.setCancelled(true);
            return;
        }

        Cooldown.addCooldown("enderpearl", player, 16);

        player.sendMessage(CC.translate("&cYou are now on enderpearl cooldown for &e16 seconds&c."));
    }
}
