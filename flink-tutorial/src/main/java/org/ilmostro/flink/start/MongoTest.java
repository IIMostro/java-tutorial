package org.ilmostro.flink.start;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class MongoTest {

    public static void main(String[] args) {

        MongoDatabase flink = MongoConfiguration.getMongoClient().getDatabase("nebula");
        MongoCollection<Document> collection = flink.getCollection("word");
        Document document = collection.find(Filters.eq("key", "A")).first();
        String value = document.getString("value");
        System.out.println(value);
    }
}
