package me.nopox.hcf.storage.profiles;

import com.mongodb.client.model.Filters;
import me.nopox.hcf.HCF;
import me.nopox.hcf.objects.Profile;
import me.nopox.hcf.utils.CC;
import me.nopox.hcf.utils.Stopwatch;
import org.bson.Document;
import org.jetbrains.annotations.Nullable;

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

    private final HCF plugin = HCF.getInstance();

    /**
     * This returns a profile from MongoDB by Username
     * It searches using the {@link #getProfileWithFields(String, Object)}
     *
     * @param name The user's Username that we search for.
     *
     * @return CompletableFuture of Profile
     */
    public CompletableFuture<Profile> getProfile(String name) {

        return getProfileWithFields("username", name);
    }


    /**
     * This returns a profile from MongoDB by UUID
     * It searches using the {@link #getProfileWithFields(String, Object)}
     *
     * @param uuid The user's UUID that we search for.
     *
     * @return CompletableFuture of Profile
     */
    public CompletableFuture<Profile> getProfile(UUID uuid) {
        return getProfileWithFields("uuid", uuid);
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
            Document doc = plugin.getMongoHandler().getProfiles().find(Filters.eq(field, value)).first();

            stopwatch.build("a profile");

            if (doc == null) {
                return null;
            }

            return plugin.getGSON().fromJson(doc.toJson(), Profile.class);
        });

    }
}
