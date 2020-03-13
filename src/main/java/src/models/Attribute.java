package src.models;

import src.models.tree.Node;

public class Attribute extends Node {

	private boolean primaryKey;

	public Attribute(String name) {
		super(name);
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
}
