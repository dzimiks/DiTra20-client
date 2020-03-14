package src.config;

import src.constants.Constants;
import src.models.Attribute;
import src.models.InformationResource;
import src.models.Warehouse;
import src.models.tree.Node;
import src.models.tree.NodeFactory;

import java.sql.*;

public class SQLConfig extends DBConfig {

	private static SQLConfig instance;
	private Connection dbConnection;

	public SQLConfig() {
		System.out.println("Init SQL Config");
	}

	public static SQLConfig getInstance() {
		if (instance == null) {
			instance = new SQLConfig();
		}

		return instance;
	}

	@Override
	public Connection getDbConnection() {
		return dbConnection;
	}

	private void getColumns(DatabaseMetaData metaData, Node entity, String tableName) throws SQLException {
		ResultSet allColumns = metaData.getColumns(null, null, tableName, null);
		ResultSet foreignKeys = metaData.getImportedKeys(Constants.DB_NAME, null, tableName);

		while (allColumns.next()) {
			String columnName = allColumns.getString("COLUMN_NAME");
			Attribute attribute = (Attribute) NodeFactory.getInstance().getNode("ATTR", columnName);
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
			// System.out.println(fkTableName + "." + fkColumnName + " -> " + pkTableName + "." + pkColumnName);

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

	@Override
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
