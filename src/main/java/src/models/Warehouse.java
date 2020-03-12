package src.models;

import src.constants.Constants;
import src.metaschema.Deserializer;
import src.models.tree.Node;

import java.sql.*;

public class Warehouse extends Node {

	private static Warehouse instance;

	private String description;
	private String metaschemaString;
	private String location;
	private String type;
	private Connection dbConnection;

	public Warehouse(String name) {
		super(name);
	}

	public void loadWarehouse(String metaschemaString) throws Exception {
		this.metaschemaString = metaschemaString;
		Deserializer deserializer = new Deserializer(metaschemaString, this);
		deserializer.deserialize(metaschemaString, this);
	}

	public static Warehouse getInstance() {
		if (instance == null) {
			instance = new Warehouse(Constants.PROJECT_NAME);
		}

		return instance;
	}

	public void buildConnection() {
		try {
			// Important line so we can use SQL connection
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Connect to the database
			final String DB_NAME = "tim_403_2_si2019";
			final String DB_USERNAME = "tim_403_2_si2019";
			final String DB_PASSWORD = "QJfcAmGb";
			final String DB_HOST = "64.225.110.65";
			final String DB_PORT = "3306";
			// URL: jdbc:mysql://tim_403_2_si2019:QJfcAmGb@64.225.110.65:3306/tim_403_2_si2019
			final String DB_URL = "jdbc:mysql://" + DB_USERNAME + ":" + DB_PASSWORD + "@" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

			dbConnection = DriverManager.getConnection(DB_URL);

			if (dbConnection == null) {
				throw new Exception("Couldn't establish a connection: " + DB_NAME + " at host " + DB_HOST);
			}

			DatabaseMetaData metaData = dbConnection.getMetaData();
			String[] dbTypes = {"TABLE"};
			ResultSet allTables = metaData.getTables(null, null, null, dbTypes);
			int tablesCount = 0;

			// Print all columns from all system and user defined tables
			// ResultSet resultSet = metaData.getTables(null, null, "%", null);

			while (allTables.next()) {
				System.out.println(allTables.getString("TABLE_NAME"));
				tablesCount++;
			}

			System.out.println("\nFound " + tablesCount + " tables\n");

//			ResultSet allColumns = metaData.getColumns(null, null, "ROLE", null);
//			int columnCount = 0;
//
//			while (allColumns.next()) {
//				System.out.println(allColumns.getString("COLUMN_NAME"));
//				columnCount++;
//			}
//
//			System.out.println("\nFound " + columnCount + " columns\n");
			dbConnection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(int index) {
		super.getChildren().remove(index);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMetaschemaString() {
		return metaschemaString;
	}

	public void setMetaschemaString(String metaschemaString) {
		this.metaschemaString = metaschemaString;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Connection getDbConnection() {
		return dbConnection;
	}

	public void setDbConnection(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}
}
