package me.nopox.hcf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import lombok.Getter;
import me.nopox.hcf.listeners.StatsListener;
import me.nopox.hcf.storage.MongoHandler;
import me.nopox.hcf.storage.profiles.ProfileHandler;
import me.nopox.hcf.storage.profiles.ProfileListener;
import me.nopox.hcf.storage.teams.TeamHandler;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class HCF extends JavaPlugin {

    @Getter private static HCF instance;

    private MongoHandler mongoHandler;
    private ProfileHandler profileHandler;
    private TeamHandler teamHandler;

    private final Gson GSON = new GsonBuilder()
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .serializeNulls().setPrettyPrinting().create();

    @Override
    public void onEnable() {
        instance = this;


        mongoHandler = new MongoHandler();

        profileHandler = new ProfileHandler();

        teamHandler = new TeamHandler();

        getServer().getPluginManager().registerEvents(new ProfileListener(), this);
        getServer().getPluginManager().registerEvents(new StatsListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
