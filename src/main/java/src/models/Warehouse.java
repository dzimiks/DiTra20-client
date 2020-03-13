package src.models;

import src.constants.Constants;
import src.models.tree.Node;
import src.models.tree.NodeFactory;

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

			while (allTables.next()) {
				String tableName = allTables.getString("TABLE_NAME");
				ResultSet allColumns = metaData.getColumns(null, null, tableName, null);
				Node resource = NodeFactory.getInstance().getNode("IR", tableName);

				// Get all relations between tables
				printForeignKeys(dbConnection, metaData, tableName);
				System.out.println("Table: " + tableName);

				while (allColumns.next()) {
					String columnName = allColumns.getString("COLUMN_NAME");
					Entity entity = (Entity) NodeFactory.getInstance().getNode("ENT", columnName);
					resource.addChild(entity);
				}

				Warehouse.getInstance().addChild(resource);
				tablesCount++;
			}

			System.out.println("\nFound " + tablesCount + " tables\n");

			ResultSet allColumns = metaData.getColumns(null, null, Constants.DB_NAME, null);
			int columnCount = 0;

			while (allColumns.next()) {
				System.out.println(allColumns.getString("COLUMN_NAME"));
				columnCount++;
			}

			System.out.println("\nFound " + columnCount + " columns\n");
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
