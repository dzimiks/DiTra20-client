package src.models;

import src.constants.Constants;
import src.models.tree.Node;
import src.models.tree.NodeFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Warehouse extends Node {

	private static Warehouse instance;

	private String description;
	private String location;
	private String type;
	private Connection dbConnection;

	public Warehouse(String name) {
		super(name);
	}

	public void loadWarehouse() throws Exception {
		buildConnection();
	}

	private void printForeignKeys(Connection connection, DatabaseMetaData metaData, String tableName) throws SQLException {
		ResultSet foreignKeys = metaData.getImportedKeys(connection.getCatalog(), null, tableName);

		while (foreignKeys.next()) {
			// Relation From
			String fkTableName = foreignKeys.getString("FKTABLE_NAME");
			String fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");

			// Relation To
			String pkTableName = foreignKeys.getString("PKTABLE_NAME");
			String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");
			System.out.println(fkTableName + "." + fkColumnName + " -> " + pkTableName + "." + pkColumnName);
		}
	}

	public static Warehouse getInstance() {
		if (instance == null) {
			instance = new Warehouse(Constants.PROJECT_NAME);
		}

		return instance;
	}

	private void getColumns(DatabaseMetaData metaData, Node entity, String tableName) throws SQLException {
		ResultSet allColumns = metaData.getColumns(null, null, tableName, null);
		ResultSet foreignKeys = metaData.getImportedKeys(Constants.DB_NAME, null, tableName);
//		ArrayList<Attribute> attributes = new ArrayList<>();

		while (allColumns.next()) {
			String columnName = allColumns.getString("COLUMN_NAME");
			Attribute attribute = (Attribute) NodeFactory.getInstance().getNode("ATTR", columnName);
//			attributes.add(attribute);
			entity.addChild(attribute);
		}

		// Get all relations between tables
		while (foreignKeys.next()) {
			// Relation Foreign Key
			String fkTableName = foreignKeys.getString("FKTABLE_NAME");
			String fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");

			// Relation Primary Key
			String pkTableName = foreignKeys.getString("PKTABLE_NAME");
			String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");
//			System.out.println(fkTableName + "." + fkColumnName + " -> " + pkTableName + "." + pkColumnName);

			for (Node attribute : entity.getChildren()) {
				if (attribute.getName().equals(pkColumnName)) {
					((Attribute) attribute).setPrimaryKey(true);
				}
			}
		}
	}

	private void generateTree(DatabaseMetaData metaData) throws SQLException {
		String[] dbTypes = {"TABLE"};
		ResultSet allTables = metaData.getTables(null, null, null, dbTypes);
		int tablesCount = 0;
		InformationResource dbName = (InformationResource) NodeFactory.getInstance().getNode("IR", Constants.DB_NAME);

		while (allTables.next()) {
			String tableName = allTables.getString("TABLE_NAME");
			Node entity = NodeFactory.getInstance().getNode("ENT", tableName);
			getColumns(metaData, entity, tableName);

//			System.out.println("Table name: " + tableName);

			// Add node
			dbName.addChild(entity);
			tablesCount++;

			if (tablesCount % 10 == 0) {
				System.out.println("Fetched " + tablesCount + " tables");
			}
		}

		Warehouse.getInstance().addChild(dbName);

		System.out.println("\nFound " + tablesCount + " tables\n");
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
			generateTree(metaData);
			// TODO: Don't close connection!
			// dbConnection.close();
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
