package org.ilmostro.flink.start.source;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.bson.Document;
import org.ilmostro.flink.start.MongoConfiguration;

import java.util.Objects;

public class CustomRichMapFunction extends RichMapFunction<String, String> {

    private MongoClient mongoClient;

    @Override
    public void open(Configuration parameters) throws Exception {
        mongoClient = MongoConfiguration.getMongoClient();
        System.err.println("---------this is rich map function mongoClient hash:" + mongoClient.hashCode() + "  thread name:" + Thread.currentThread().getName());
    }

    @Override
    public void close() throws Exception {
        if(Objects.isNull(mongoClient)){
            return;
        }
        mongoClient.close();
    }

    @Override
    public String map(String value) throws Exception {
        MongoDatabase flink = mongoClient.getDatabase("nebula");
        MongoCollection<Document> collection = flink.getCollection("word");
        Document document = collection.find(Filters.eq("key", value)).first();
        return document.getString("value");
    }
}
