package me.nopox.hcf.storage;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;

/**
 * @author Embry_
 */
@Getter
public class MongoHandler {
    public MongoCollection<Document> teams;
    public MongoCollection<Document> profiles;

    public MongoHandler(){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase mongoDatabase = mongoClient.getDatabase("HCF");

        teams = mongoDatabase.getCollection("teams");
        profiles = mongoDatabase.getCollection("profiles");
    }

}
