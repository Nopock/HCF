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
    private UUID id, teamId;
    private String username;
    private int kills, deaths, lives, killstreak;
    private double balance;
    private long playtime;


    /**
     * Syncs the profile with the database,
     * make sure to call this after you have updated the profile.
     */
    public void save() {
        Document current = Document.parse(HCF.getInstance().getGSON().toJson(this));

        Stopwatch stopwatch = new Stopwatch();

        HCF.getInstance().getMongoHandler().getProfiles().replaceOne(Filters.eq("_id", id), current, new ReplaceOptions().upsert(true));

        Bukkit.getLogger().log(Level.INFO, "[Profiles] Saving profile for " + username + " took " + stopwatch.getTime() + "ms");


    }


    /**
     * Gets a player's team from MongoDB.
     *
     * @return CompletableFuture of Team
     */
    public CompletableFuture<Team> getTeam() {
        return HCF.getInstance().getTeamHandler().getTeam(teamId);
    }
}
