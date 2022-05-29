package me.nopox.hcf.objects;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.nopox.hcf.HCF;
import me.nopox.hcf.utils.Stopwatch;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

/**
 * The profile of a player.
 *
 * @author Nopox
 */
@Data
@AllArgsConstructor
public class Profile {
    String id;
    private UUID teamId;
    private int kills, deaths, lives, killstreak;
    private double balance;
    private long playtime, pvpTimer;

    /**
     * Syncs the profile from the cache with the database,
     * make sure to call this after you have updated the profile.
     */
    @Deprecated
    public void saveToMongo() {
        Document current = Document.parse(HCF.getInstance().getGSON().toJson(this));

        Stopwatch stopwatch = new Stopwatch();

        HCF.getInstance().getMongoHandler().getProfiles().replaceOne(Filters.eq("_id", id), current, new ReplaceOptions().upsert(true));

        Bukkit.getLogger().log(Level.INFO, "[Profiles] Saving profile to mongoDB for " + Bukkit.getOfflinePlayer(UUID.fromString(id)).getName() + " took " + stopwatch.getTime() + "ms");

        HCF.getInstance().getProfileHandler().setLastLatency(stopwatch.getTime());

    }

    /**
     * Updates the profile in the cache
     */
    public void saveToCache() {
        Stopwatch stopwatch = new Stopwatch();

        HCF.getInstance().getProfileHandler().getCachedProfiles().put(id, this);

        Bukkit.getLogger().log(Level.INFO, "[Profiles] Saving profile to cache for " + Bukkit.getOfflinePlayer(UUID.fromString(id)).getName() + " took " + stopwatch.getTime() + "ms");
    }


    /**
     * Gets a player's team from MongoDB.
     *
     * @return CompletableFuture of Team
     */
    public CompletableFuture<Team> getTeam() {
        return HCF.getInstance().getTeamHandler().getTeam(teamId);
    }

    /**
     * Call this whenever a player dies.
     */
    public void dispatchDeath() {
        balance = 0;
        deaths++;
        killstreak = 0;

        saveToCache();
    }

    /**
     * Call this whenever a player kills another player.
     */
    public void dispatchKill(Player victim) {
        kills++;
        killstreak++;

        HCF.getInstance().getProfileHandler().getProfile(victim.getUniqueId().toString()).thenAccept(victimProfile -> {
            balance += victimProfile.getBalance();
        });

        saveToCache();
    }

    /**
     * Call this whenever a player joins a team
     */
    public void joinTeam(UUID teamId) {
        this.teamId = teamId;
        saveToCache();
    }



}
