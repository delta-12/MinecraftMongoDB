package net.sytes.deltacloud.minecraftmongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import org.bson.Document;

import org.json.JSONObject;

import java.util.Objects;

public class MongoDBHandler {

    MinecraftMongoDB plugin;

    private final String databaseName;
    private final String collectionName;
    private final String serverName;

    private final MongoCollection<Document> collection;

    public MongoDBHandler(MinecraftMongoDB pluginInstance) {
        plugin = pluginInstance;
        String mongoClientUri = Objects.requireNonNull(plugin.getConfig().getString("mongo-client-uri"));
        databaseName = Objects.requireNonNull(plugin.getConfig().getString("database-name"));
        collectionName = Objects.requireNonNull(plugin.getConfig().getString("collection-name"));
        serverName = Objects.requireNonNull(plugin.getConfig().getString("server-name"));
        MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoClientUri));
        collection = accessServerCollection(mongoClient);
    }

    private MongoCollection<Document> accessServerCollection(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        return database.getCollection(collectionName);
    }

    private String getFieldValue(String field) {
        return new JSONObject(Objects.requireNonNull(collection.find(Filters.eq("name", serverName)).first()).toJson()).getString(field);
    }

    private void updateDB(String field, String value) {
        collection.updateOne(Filters.eq("name", serverName), Updates.set(field, value));
    }

    public void resetPlayerCount() {
        updateDB("onlinePlayers", "0");
    }
    public void setStatusOnline() {
        updateDB("status", "Online");
    }
    public void setStatusOffline() {
        updateDB("status", "Offline");
    }
    public void setMaxPlayers(String maxPlayers) {
        updateDB("maxPlayers", maxPlayers);
    }
    public void setWorldSeed(String seed) {
        updateDB("seed", seed);
    }
    public void updateOnlinePlayerCount(int update) {
        String currentOnlinePlayers = getFieldValue("onlinePlayers");
        String updatedOnlinePlayers = Integer.toString(Integer.parseInt(currentOnlinePlayers) + update);
        updateDB("onlinePlayers", updatedOnlinePlayers);
    }

    public String getDifficulty() {
        return getFieldValue("difficulty");
    }
    public void setDifficulty(String difficulty) {
        updateDB("difficulty", difficulty);
    }

    public String getGameMode() {
        return getFieldValue("gamemode");
    }
    public void setGameMode(String gameMode) {
        updateDB("gamemode", gameMode);
    }
}
