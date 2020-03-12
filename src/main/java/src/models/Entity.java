package src.models;

import src.models.tree.Node;

public class Entity extends Node {

	public Entity(String name) {
		super(name);
	}

	public Entity(Entity entity) {
		super(entity.getName());
	}
}
