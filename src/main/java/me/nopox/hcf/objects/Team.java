package me.nopox.hcf.objects;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.nopox.hcf.HCF;
import me.nopox.hcf.utils.Stopwatch;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Represents a team in the game.
 *
 * @author Nopox
 */
@Data
@AllArgsConstructor
public class Team {
    private UUID id, ally;
    private String name, announcement;
    private Location HQ;
    private double balance, DTR;
    private UUID leader;
    private Set<UUID> coleaders, captains, members, invites;
    private int points, kills, deaths, captures, diamondsMined;
    private List<Claim> claims;

    /**
     * Syncs a team with the database,
     * make sure to call this after you have updated a team.
     */
    @Deprecated
    public void saveToMongo() {
        Document current = Document.parse(HCF.getInstance().getGSON().toJson(this));

        Stopwatch stopwatch = new Stopwatch();

        HCF.getInstance().getMongoHandler().getTeams().replaceOne(Filters.eq("_id", id), current, new ReplaceOptions().upsert(true));

        Bukkit.getLogger().log(Level.INFO, "[Teams] Saving team for " + id + " took " + stopwatch.getTime() + "ms");

        HCF.getInstance().getTeamHandler().setLastLatency(stopwatch.getTime());
    }

    /**
     * Updates the profile in the cache
     */
    public void saveToCache() {
        Stopwatch stopwatch = new Stopwatch();

        HCF.getInstance().getTeamHandler().getCachedTeams().put(id.toString(), this);

        Bukkit.getLogger().log(Level.INFO, "[Teams] Saving team to cache for " + name + " took " + stopwatch.getTime() + "ms");
    }


    /**7
     * This checks if a team is raidable (0 Dtr or less)
     *
     * @return true if raidable
     */
    public boolean isRaidable() {
        return DTR <= 0;
    }

    /**
     * This deletes the team.
     */
    public void delete() {
        HCF.getInstance().getMongoHandler().getTeams().deleteOne(Filters.eq("_id", id));
    }



}
