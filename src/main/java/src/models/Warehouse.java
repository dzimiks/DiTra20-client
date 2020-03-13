package src.models;

import src.constants.Constants;
import src.models.tree.Node;
import src.models.tree.NodeFactory;

import java.sql.*;

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

	private void getColumns(ResultSet allColumns, Node entity) throws SQLException {
		while (allColumns.next()) {
			String columnName = allColumns.getString("COLUMN_NAME");
			Attribute attribute = (Attribute) NodeFactory.getInstance().getNode("ATTR", columnName);
			entity.addChild(attribute);
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
			ResultSet allColumns = metaData.getColumns(null, null, tableName, null);
			getColumns(allColumns, entity);

			// Get all relations between tables
//			printForeignKeys(dbConnection, metaData, tableName);
			System.out.println("Table name: " + tableName);

			// Add node
			dbName.addChild(entity);
			tablesCount++;
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
