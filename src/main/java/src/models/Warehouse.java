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
			Class.forName(Constants.DB_DRIVER);

			// Connect to the database
			dbConnection = DriverManager.getConnection(Constants.DB_URL);

			if (dbConnection == null) {
				throw new Exception("Couldn't establish a connection: " + Constants.DB_NAME + " at host " + Constants.DB_HOST);
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
