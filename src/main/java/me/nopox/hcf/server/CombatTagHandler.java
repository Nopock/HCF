package me.nopox.hcf.server;

import lombok.Getter;
import me.nopox.hcf.utils.CC;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CombatTagHandler {

    @Getter private static Map<UUID, Long> spawnTags;

    public CombatTagHandler(){
        spawnTags = new HashMap<>();
    }

    public static void tag(Player player){

        if (!isSpawnTagged(player)){
            player.sendMessage(CC.translate("&cYou have been combat tagged for &e30 &cseconds."));
            return;
        }

        int secondsTaggedFor = (int) ((spawnTags.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000L);
        int newSeconds = Math.min(secondsTaggedFor + 30, 30);

        spawnTags.put(player.getUniqueId(), System.currentTimeMillis() + (newSeconds * 1000L));
    }

    public static boolean isSpawnTagged(Player player){
        return spawnTags.containsKey(player.getUniqueId()) && spawnTags.get(player.getUniqueId()) > System.currentTimeMillis();
    }

}
