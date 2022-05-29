package me.nopox.hcf;

import co.aikar.commands.PaperCommandManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import lombok.Getter;
import me.nopox.hcf.commands.LatencyCommand;
import me.nopox.hcf.commands.team.FCreateCommand;
import me.nopox.hcf.listeners.EnderpearlListener;
import me.nopox.hcf.listeners.StatsListener;
import me.nopox.hcf.map.MapHandler;
import me.nopox.hcf.objects.Profile;
import me.nopox.hcf.storage.MongoHandler;
import me.nopox.hcf.storage.profiles.ProfileHandler;
import me.nopox.hcf.storage.profiles.ProfileListener;
import me.nopox.hcf.storage.teams.TeamHandler;
import me.nopox.hcf.utils.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

@Getter
public final class HCF extends JavaPlugin {

    private final HashMap<String, Long> playerCache = new HashMap<>();

    @Getter private static HCF instance;

    private MongoHandler mongoHandler;
    private ProfileHandler profileHandler;
    private TeamHandler teamHandler;
    private MapHandler mapHandler;

    private final Gson GSON = new GsonBuilder()
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .serializeNulls().setPrettyPrinting().create();

    @Override
    public void onEnable() {
        instance = this;


        mongoHandler = new MongoHandler();

        profileHandler = new ProfileHandler();

        teamHandler = new TeamHandler();

        mapHandler = new MapHandler();

        getServer().getPluginManager().registerEvents(new ProfileListener(), this);
        getServer().getPluginManager().registerEvents(new StatsListener(), this);
        getServer().getPluginManager().registerEvents(new EnderpearlListener(), this);

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new LatencyCommand());
        manager.registerCommand(new FCreateCommand());

        Cooldown.createCooldown("enderpearl");

        getTeamHandler().addAllToCache();


        // Syncs all profiles with mongo every 2 minutes
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            getTeamHandler().saveAllToMongo();
        }, 0, 20 * 60);



    }

    @Override
    public void onDisable() {
        for (Player player : getServer().getOnlinePlayers()) {
            getProfileHandler().getProfile(player.getUniqueId().toString()).thenAccept(Profile::saveToMongo);
        }

        getTeamHandler().saveAllToMongo();


    }
}
