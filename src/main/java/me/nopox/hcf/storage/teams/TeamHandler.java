package me.nopox.hcf.storage.teams;

import com.mongodb.client.model.Filters;
import lombok.Getter;
import me.nopox.hcf.HCF;
import me.nopox.hcf.objects.Profile;
import me.nopox.hcf.objects.Team;
import me.nopox.hcf.utils.Stopwatch;
import org.bson.Document;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Getter
/**
 * This class will return the Team
 * object as a ComepletableFuture<Team>.
 * Using a CompletableFuture allows us to
 * parse the Team object async.
 *
 * @author Nopox
 */
public class TeamHandler {

    private long lastLatency;

    private final HCF plugin = HCF.getInstance();

    public HashMap<String, Team> cachedTeams = new HashMap<>();

    /**
     * This returns a Team from MongoDB
     * It searches using the {@link #getTeamWithFields(String, Object)}
     * which returns a CompletableFuture
     *
     * @param name The Name that we are looking for inside MongoDB
     *
     * @return CompletableFuture of Team
     */
    public CompletableFuture<Team> getTeam(String name) {
        return getTeamWithFields("name", name);
    }

    /**
     * This returns a Team from MongoDB
     * It searches using the {@link #getTeamWithFields(String, Object)}
     * which returns a CompletableFuture
     *
     * @param uuid The UUID that we are looking for inside MongoDB
     *
     * @return CompletableFuture of Team
     */
    public CompletableFuture<Team> getTeam(UUID uuid) {
        return getTeamWithFields("_id", uuid);
    }


    /**
     * @param field The field in mongo that you are searching for
     * @param value The value that we are looking for inside each mongo document.
     *
     * @return CompletableFuture of Team
     */
    private CompletableFuture<Team> getTeamWithFields(String field, Object value) {
        Stopwatch stopwatch = new Stopwatch();
        return CompletableFuture.supplyAsync( () -> {
            if (cachedTeams.containsKey(value.toString())) {
                stopwatch.build("team for " + value + " found in Cache");
                return cachedTeams.get(value.toString());
            }
            Document doc = plugin.getMongoHandler().getTeams().find(Filters.eq(field, value)).first();

            stopwatch.build("team for " + value + " found in MongoDB");

            if (doc == null) {
                return null;
            }

            return plugin.getGSON().fromJson(doc.toJson(), Team.class);
        });
    }

    /**
     * This returns the amount of Teams that exist inside MongoDB
     *
     * @return Amount of Teams
     */
    public long getTeamCount() {
        return plugin.getMongoHandler().getTeams().estimatedDocumentCount();
    }

    /**
     * @return Last latency of the database
     */
    public long getLastLatency() {
        return this.lastLatency;
    }


    /**
     * This sets the last latency of the database
     */
    public void setLastLatency(long latency) {
        this.lastLatency = latency;
    }
}
