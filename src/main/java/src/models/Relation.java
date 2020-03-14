package src.models;

import java.util.ArrayList;
import java.util.List;

public class Relation {

	private String name;
	private static int idGenerator;

	private Entity referencedEntity;
	private List<Attribute> referencedAttributes;
	private List<Attribute> referringAttributes;

	public Relation(String name) {
		this.name = name;
		this.referencedAttributes = new ArrayList<>();
		this.referringAttributes = new ArrayList<>();
	}

	public Relation(List<Attribute> referringAttributes, List<Attribute> referencedAttributes, Entity referencedEntity) {
		this.name = Integer.toString(idGenerator++);
		this.referringAttributes = referringAttributes;
		this.referencedAttributes = referencedAttributes;
		this.referencedEntity = referencedEntity;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static int getIdGenerator() {
		return idGenerator;
	}

	public static void setIdGenerator(int idGenerator) {
		Relation.idGenerator = idGenerator;
	}

	public Entity getReferencedEntity() {
		return referencedEntity;
	}

	public void setReferencedEntity(Entity referencedEntity) {
		this.referencedEntity = referencedEntity;
	}

	public List<Attribute> getReferencedAttributes() {
		return referencedAttributes;
	}

	public void setReferencedAttributes(List<Attribute> referencedAttributes) {
		this.referencedAttributes = referencedAttributes;
	}

	public List<Attribute> getReferringAttributes() {
		return referringAttributes;
	}

	public void setReferringAttributes(List<Attribute> referringAttributes) {
		this.referringAttributes = referringAttributes;
	}
}
