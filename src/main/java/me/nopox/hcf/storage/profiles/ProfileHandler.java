package me.nopox.hcf.storage.profiles;

import com.mongodb.client.model.Filters;
import lombok.Getter;
import me.nopox.hcf.HCF;
import me.nopox.hcf.objects.Profile;
import me.nopox.hcf.utils.CC;
import me.nopox.hcf.utils.Stopwatch;
import org.bson.Document;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * This class allows you to easily
 * fetch any of the users profiles by
 * an array of inputs. Each function returns a
 * CompletableFuture of Profile allowing us to parse
 * the profile async.
 *
 * @author Nopox
 */
public class ProfileHandler {

    private long lastLatency;

    private final HCF plugin = HCF.getInstance();

    @Getter public HashMap<String, Profile> cachedProfiles = new HashMap<>();


    /**
     * This returns a profile from MongoDB by UUID
     * It searches using the {@link #getProfileWithFields(String, Object)}
     *
     * @param id The user's UUID that we search for.
     *
     * @return CompletableFuture of Profile
     */
    public CompletableFuture<Profile> getProfile(String id) {
        return getProfileWithFields("_id", id);
    }


    /**
     * @param field The field in MongoDB that you are searching for
     * @param value The value that we are looking for inside each mongo document.
     *
     * @return CompletableFuture of Profile
     */
    public CompletableFuture<Profile> getProfileWithFields(String field, Object value) {
        Stopwatch stopwatch = new Stopwatch();
        return CompletableFuture.supplyAsync( () -> {
            if (cachedProfiles.containsKey(value)) {
                return cachedProfiles.get(value);
            }
            Document doc = plugin.getMongoHandler().getProfiles().find(Filters.eq(field, value)).first();

            stopwatch.build("a profile");

            if (doc == null) {
                return null;
            }

            return plugin.getGSON().fromJson(doc.toJson(), Profile.class);
        });

    }

    /**
     * This returns the amount of Profiles that exist inside MongoDB
     *
     * @return Amount of Profiles
     */
    public long getProfileCount() {
        return plugin.getMongoHandler().getProfiles().estimatedDocumentCount();
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

    /**
     * Creates a new profile for the user
     */
    public void create(String id, long pvpTimer) {
        Profile profile = new Profile(id, null, 0, 0, 0, 0,0, 0, pvpTimer);
        profile.saveToCache();
    }
}
