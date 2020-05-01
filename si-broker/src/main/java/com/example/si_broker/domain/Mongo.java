package com.example.si_broker.domain;

import com.example.si_broker.utils.Constants;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Mongo {

    public static void main(String[] args) {
        String DB_NAME = "tim_403_2_broker_si2019";
        int DB_PORT = 27017;
        char[] DB_PASS = "8B#9#g@E!Vz=?wyV".toCharArray();

        MongoCredential credential = MongoCredential.createCredential(DB_NAME, DB_NAME, DB_PASS);
        ServerAddress serverAddress = new ServerAddress(Constants.DB_HOST, DB_PORT);
        MongoClientOptions options = MongoClientOptions.builder().build();

        MongoClient mongoClient = new MongoClient(serverAddress, credential, options);

        MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
        MongoCollection<Document> usersCollection = mongoDatabase.getCollection("users");
        MongoIterable<String> collections = mongoDatabase.listCollectionNames();

        Document document1 = new Document("id", UUID.randomUUID().toString())
                .append("firstName", "first name")
                .append("lastName", "last name")
                .append("username", "123")
                .append("password", "123")
                .append("email", "123@gmail.com")
                .append("role", "normal");

        Document document2 = new Document("id", UUID.randomUUID().toString())
                .append("firstName", "Vanja")
                .append("lastName", "Paunović")
                .append("username", "dzimiks")
                .append("password", "dzimiks")
                .append("email", "vana997@gmail.com")
                .append("role", "admin");

        Document document3 = new Document("id", UUID.randomUUID().toString())
                .append("firstName", "Milan")
                .append("lastName", "Mitić")
                .append("username", "miki")
                .append("password", "milan")
                .append("email", "tkemi@gmail.com")
                .append("role", "admin");

        List<Document> documents = new ArrayList<>();
        documents.add(document1);
        documents.add(document2);
        documents.add(document3);

        for (Document document : documents) {
            usersCollection.insertOne(document);
        }

        System.out.println("ALL COLLECTIONS:");

        for (String collection : collections) {
            System.out.println(collection);
        }

        MongoCursor<Document> mongoCursor = usersCollection.find().iterator();

        System.out.println("Database: " + mongoDatabase.getName());
        System.out.println("Users collection document count: " + usersCollection.countDocuments());

        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next().toJson());
        }
    }
}
