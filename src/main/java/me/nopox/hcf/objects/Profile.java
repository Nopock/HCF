package me.nopox.hcf.objects;

import com.mongodb.client.model.UpdateOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.nopox.hcf.HCF;
import org.bson.Document;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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


    /**
     * Syncs the profile with the database,
     * make sure to call this after you have updated the profile.
     */
    public void save() {
        Document current = Document.parse(HCF.getInstance().getGSON().toJson(this));
        Document update = new Document("$set", current);

        HCF.getInstance().getMongoHandler().getProfiles().updateOne(new Document("_id", id), update, (new UpdateOptions()).upsert(true));
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
