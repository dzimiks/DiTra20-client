package src.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import src.constants.Constants;

import java.sql.Connection;

public class MongoConfig extends DBConfig {

	public static void main(String[] args) {
		String DB_NAME = "tim_403_2_broker_si2019";
		int DB_PORT = 27017;
		char[] DB_PASS = "8B#9#g@E!Vz=?wyV".toCharArray();

		MongoCredential credential = MongoCredential.createCredential(DB_NAME, DB_NAME, DB_PASS);
		ServerAddress serverAddress = new ServerAddress(Constants.DB_HOST, DB_PORT);
		MongoClientOptions options = MongoClientOptions.builder().build();

		MongoClient mongoClient = new MongoClient(serverAddress, credential, options);

		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("dzimiks");

		mongoCollection.insertOne(new Document("virus", "koroman"));

		FindIterable<Document> findIterable = mongoCollection.find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();

		System.out.println("DB: " + mongoDatabase.getName());
		System.out.println("Collection document count: " + mongoCollection.countDocuments());

		while (mongoCursor.hasNext()) {
			System.out.println(mongoCursor.next().toJson());
		}
	}

	@Override
	public Connection getDbConnection() {
		return null;
	}

	@Override
	public void buildConnection() {

	}
}
