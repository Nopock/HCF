package me.nopox.hcf.storage.teams;

import com.mongodb.client.model.Filters;
import me.nopox.hcf.HCF;
import me.nopox.hcf.objects.Profile;
import me.nopox.hcf.objects.Team;
import me.nopox.hcf.utils.Stopwatch;
import org.bson.Document;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * This class will return the Team
 * object as a ComepletableFuture<Team>.
 * Using a CompletableFuture allows us to
 * parse the Team object async.
 *
 * @author Nopox
 */
public class TeamHandler {

    private final HCF plugin = HCF.getInstance();

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
            Document doc = plugin.getMongoHandler().getTeams().find(Filters.eq(field, value)).first();

            stopwatch.build("a team");
            return plugin.getGSON().fromJson(doc.toJson(), Team.class);
        });
    }
}
