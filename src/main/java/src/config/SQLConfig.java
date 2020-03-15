package src.config;

import src.constants.Constants;
import src.models.*;
import src.models.datatypes.CharType;
import src.models.datatypes.DateType;
import src.models.datatypes.VarCharType;
import src.models.tree.Node;
import src.models.tree.NodeFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

	private void getAttributes(ResultSet allColumns, Node entity) throws SQLException {
		while (allColumns.next()) {
			String columnName = allColumns.getString("COLUMN_NAME");
			String columnType = allColumns.getString("TYPE_NAME");
			String columnSize = allColumns.getString("COLUMN_SIZE");
			String decimalDigits = allColumns.getString("DECIMAL_DIGITS");
			String isNullable = allColumns.getString("IS_NULLABLE");
			Class<?> valueClass = null;

			switch (columnType) {
				case "CHAR":
					valueClass = CharType.class;
					break;
				case "VARCHAR":
				case "LONGTEXT":
					valueClass = VarCharType.class;
					break;
				case "DATE":
					valueClass = DateType.class;
					break;
				case "BOOLEAN":
				case "BIT":
					valueClass = Boolean.class;
					break;
				case "INT":
				case "SMALLINT":
					valueClass = Integer.class;
					break;
				case "BIGINT":
					valueClass = Long.class;
					break;
				case "DECIMAL":
					valueClass = BigDecimal.class;
					break;
				case "LONGBLOB":
					valueClass = Blob.class;
					break;
				default:
					throw new SQLException("Unknown type: " + columnType);
			}

			Attribute attribute = (Attribute) NodeFactory.getInstance().getNode("ATTR", columnName);
			attribute.setLength(Integer.parseInt(columnSize));
			attribute.setValueClass(valueClass);
			entity.addChild(attribute);
		}
	}

	private void getColumns(DatabaseMetaData metaData, Node entity, String tableName) throws SQLException {
		ResultSet allColumns = metaData.getColumns(null, null, tableName, null);
		ResultSet foreignKeys = metaData.getImportedKeys(Constants.DB_NAME, null, tableName);

		// TODO
		getAttributes(allColumns, entity);

		List<Attribute> referringAttributes = new ArrayList<>();
		List<Attribute> referencedAttributes = new ArrayList<>();
		Entity referencedEntity = null;
		int magicCounter = 0;

		// Get all relations between tables
		while (foreignKeys.next()) {
			// Relation Foreign Key
			String fkTableName = foreignKeys.getString("FKTABLE_NAME");
			String fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");

			// Relation Primary Key
			String pkTableName = foreignKeys.getString("PKTABLE_NAME");
			String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");

//			System.out.println(fkTableName + "." + fkColumnName + " -> " + pkTableName + "." + pkColumnName);

			if (magicCounter == 0) {
				ResultSet allColumnsRelation = metaData.getColumns(null, null, pkTableName, null);
				referencedEntity = new Entity(pkTableName);
				getAttributes(allColumnsRelation, referencedEntity);
			}

			magicCounter++;

			for (Node node : entity.getChildren()) {
				if (node.getName().equals(pkColumnName)) {
					Attribute attribute = (Attribute) node;
					attribute.setPrimaryKey(true);
					// TODO: Add referencedAttributes
					referencedAttributes.add(attribute);
				}

				if (node.getName().equals(fkColumnName)) {
					Attribute attribute = (Attribute) node;
					// TODO: Add referringAttributes
					referringAttributes.add(attribute);
				}
			}
		}

		// TODO: Add relations
		((Entity) entity).addRelations(new Relation(referringAttributes, referencedAttributes, referencedEntity));
		// TODO: TODO^2
//		System.out.println("referencedEntity: " + (referencedEntity != null ? referencedEntity.getChildren() : null));
//		System.out.println(entity.getName() + " => RELATIONS: " + ((Entity) entity).getRelations());
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
