package me.nopox.hcf.objects;

import com.mongodb.client.model.UpdateOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.nopox.hcf.HCF;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.bukkit.Location;

import java.util.List;
import java.util.Set;
import java.util.UUID;

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
    public void save() {
        Document current = Document.parse(HCF.getInstance().getGSON().toJson(this));
        Document update = new Document("$set", current);

        HCF.getInstance().getMongoHandler().getTeams().updateOne(new Document("_id", id), update, (new UpdateOptions()).upsert(true));
    }


}
