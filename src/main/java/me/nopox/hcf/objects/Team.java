package me.nopox.hcf.objects;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.nopox.hcf.HCF;
import me.nopox.hcf.utils.CC;
import me.nopox.hcf.utils.Stopwatch;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;
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

    /**
     * This sends a player to a team's information.
     * @param player
     */
    public void sendTeamInformation(Player player){

        String line = "&7&m---------------------------------------";
        String homeString = (getHQ() == null ? "Not set" : getHQ().getBlock() + "&7, &r" +  getHQ().getBlockZ());

        StringBuilder coleaders = new StringBuilder(CC.translate("&eCo-Leaders&f:"));
        StringBuilder captains = new StringBuilder(CC.translate("&eCaptains&f:"));
        StringBuilder members = new StringBuilder(CC.translate("&eMembers&f:"));

        /*
        LATER BECAUSE IM FUCKING LAZY
        for (UUID uuid : this.members){
            members.append()
        }
         */

        // This is for when we do regions.
        if (getLeader() == null){
            player.sendMessage(CC.translate(line));
            player.sendMessage(CC.translate("&6&l" + getName())); // We have to add the color here. (I won't do it for right now)
            player.sendMessage(CC.translate("&eHQ: &r" + homeString));
            player.sendMessage(CC.translate(line));
            return;
        }


        player.sendMessage(CC.translate(line));
        player.sendMessage(CC.translate("&6&l" + name + " &7[" + getOnlineMembers().size() + "/" + this.members.size() + "] &7- &6HQ&r: " + homeString));
    }

    public Collection<Player> getOnlineMembers(){
        List<Player> online = new ArrayList<>();

        for (UUID uuid : members) {
            Player player = Bukkit.getPlayer(uuid);

            if (player != null && player.isOnline()){
                online.add(player);
            }
        }

        return online;
    }

}
