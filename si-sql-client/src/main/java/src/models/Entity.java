package src.models;

import src.models.tree.Node;

import java.util.ArrayList;
import java.util.List;

public class Entity extends Node {

	private List<Relation> relations;

	public Entity(String name) {
		super(name);
		this.relations = new ArrayList<>();
	}

	public Entity(Entity entity) {
		super(entity.getName());
		this.relations = new ArrayList<>();
	}

	public void addRelations(Relation newRelation) {
		if (newRelation == null) {
			return;
		}

		if (this.relations == null) {
			this.relations = new ArrayList<>();
		}

		if (!this.relations.contains(newRelation)) {
			this.relations.add(newRelation);
		}
	}

	public Attribute findAttributeByName(String name) {
		for (Node a : this.getChildren()) {
			if (a instanceof Attribute && a.getName().equals(name)) {
				return (Attribute) a;
			}
		}

		return null;
	}

	public Relation getRelationAt(int index) {
		return this.relations.get(index);
	}

	public int getRelationCount() {
		return this.relations.size();
	}

	public void addRelation(Relation r) {
		this.relations.add(r);
	}

	public List<Relation> getRelations() {
		return relations;
	}
}
