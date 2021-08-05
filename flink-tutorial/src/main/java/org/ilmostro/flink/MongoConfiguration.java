package org.ilmostro.flink;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;

public class MongoConfiguration {

    private static MongoClient mongoClient = null;

    private MongoConfiguration() {

    }

    static {
        initProperties();
    }

    public static MongoClient getMongoClient(){
        return mongoClient;
    }

    public static MongoDatabase getDatabase(String dbName) {
        return mongoClient.getDatabase(dbName);
    }

    /**
     * 初始化连接池
     */
    private static void initProperties() {
        try {
            MongoClientOptions.Builder mcob = MongoClientOptions.builder();
            mcob.connectionsPerHost(1000);
            mcob.readPreference(ReadPreference.secondaryPreferred());
            mcob.connectionsPerHost(10);
            mcob.threadsAllowedToBlockForConnectionMultiplier(10);
            MongoClientOptions mco = mcob.build();

            ServerAddress serverAddress = new ServerAddress("192.168.205.20", 27017);

            MongoCredential credential = MongoCredential.createCredential("nebula", "nebula", "123456".toCharArray());
            mongoClient = new MongoClient(serverAddress, credential, mco);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
}
