package src.models;

import src.config.DBConfig;
import src.constants.Constants;
import src.models.tree.Node;

public class Warehouse extends Node {

	private static Warehouse instance;

	private String description;
	private String location;
	private String type;

	public Warehouse(String name) {
		super(name);
	}

	public void loadWarehouse() {
		DBConfig.getInstance().buildConnection();
	}

	public static Warehouse getInstance() {
		if (instance == null) {
			instance = new Warehouse(Constants.PROJECT_NAME);
		}

		return instance;
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
}
