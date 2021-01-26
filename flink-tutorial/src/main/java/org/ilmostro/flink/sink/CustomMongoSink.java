package org.ilmostro.flink.sink;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.bson.Document;
import org.ilmostro.flink.MongoConfiguration;

import java.math.BigDecimal;

public class CustomMongoSink extends RichSinkFunction<Tuple2<String, BigDecimal>> {

    MongoClient mongoClient = null;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        mongoClient = MongoConfiguration.getMongoClient();
    }

    @Override
    public void close() throws Exception {
        super.close();
        mongoClient.close();
    }

    @Override
    public void invoke(Tuple2<String, BigDecimal> value, Context context) throws Exception {
        MongoDatabase flink = mongoClient.getDatabase("flink");
        MongoCollection<Document> collection = flink.getCollection("flink");
        Document doc = new Document();
        doc.put("name", value.f0);
        doc.put("money", value.f1);
        collection.insertOne(doc);
    }
}
