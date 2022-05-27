package me.nopox.hcf.listeners.prevention;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;

public class PotionPreventer implements Listener {

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        for (LivingEntity e : event.getAffectedEntities()) {

        }
    }
}
