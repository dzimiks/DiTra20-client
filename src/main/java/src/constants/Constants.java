package src.constants;

public class Constants {

	/**
	 * Project utils
	 */
	public static final String PROJECT_NAME = "DiTra20";
	public static final String NIMBUS_LOOK_AND_FEEL = "javax.swing.plaf.nimbus.NimbusLookAndFeel";

	/**
	 * DB Utils
	 */
	public static final String DB_NAME = "tim_403_2_si2019";
	public static final String DB_USERNAME = "tim_403_2_si2019";
	public static final String DB_PASSWORD = "QJfcAmGb";
	public static final String DB_HOST = "64.225.110.65";
	public static final String DB_PORT = "3306";
	// DB_URL: jdbc:mysql://tim_403_2_si2019:QJfcAmGb@64.225.110.65:3306/tim_403_2_si2019
	public static final String DB_URL = "jdbc:mysql://" + DB_USERNAME + ":" + DB_PASSWORD + "@" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
	public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

	/**
	 * Toolbar icons
	 */
	public static final String RESOURCES_PATH = "./src/main/resources";
	public static final String FAVICON = RESOURCES_PATH + "/images/favicon.png";
	public static final String ADD_ICON = RESOURCES_PATH + "/images/toolbar/add.png";
	public static final String DATABASE_ICON = RESOURCES_PATH + "/images/toolbar/database.png";
	public static final String DELETE_ICON = RESOURCES_PATH + "/images/toolbar/delete.png";
	public static final String EDIT_ICON = RESOURCES_PATH + "/images/toolbar/edit.png";
	public static final String IMPORT_ICON = RESOURCES_PATH + "/images/toolbar/import.png";
	public static final String READ_ICON = RESOURCES_PATH + "/images/toolbar/read.png";
	public static final String RELATIONS_ICON = RESOURCES_PATH + "/images/toolbar/relations.png";
	public static final String SUBMIT_ICON = RESOURCES_PATH + "/images/toolbar/submit.png";
	public static final String UPDATE_ICON = RESOURCES_PATH + "/images/toolbar/update.png";
	public static final String FETCH_ICON = RESOURCES_PATH + "/images/toolbar/fetch.png";

	/**
	 * Tree icons
	 */
	public static final String ATTRIBUTE_ICON = RESOURCES_PATH + "/images/tree/attribute.png";
	public static final String ENTITY_ICON = RESOURCES_PATH + "/images/tree/entity.png";
	public static final String INFORMATION_RESOURCE_ICON = RESOURCES_PATH + "/images/tree/information-resource.png";
	public static final String WAREHOUSE_ICON = RESOURCES_PATH + "/images/tree/warehouse.png";

	/**
	 * Screen size
	 */
	public static final int SCREEN_SIZE_WIDTH = 1440;
	public static final int SCREEN_SIZE_HEIGHT = 1080;
	public static final int SCROLL_PANE_WIDTH = 250;
	public static final int SCROLL_PANE_HEIGHT = 600;
	public static final int TREE_SCROLL_PANE_WIDTH = 50;
	public static final int TREE_SCROLL_PANE_HEIGHT = 1000;
}
