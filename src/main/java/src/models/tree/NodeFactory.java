package src.models.tree;

import src.models.Attribute;
import src.models.Entity;
import src.models.InformationResource;
import src.models.Warehouse;

public class NodeFactory {

	private static NodeFactory instance = null;

	public Node getNode(String type, String name) {
		if (type == null) {
			return null;
		}

		if (isTypeEqualToNode(type, "Warehouse")) {
			return Warehouse.getInstance();
		} else if (isTypeEqualToNode(type, "Information Resource")) {
			return new InformationResource(name);
		} else if (isTypeEqualToNode(type, "Entity")) {
			return new Entity(name);
		} else if (isTypeEqualToNode(type, "Attribute")) {
			return new Attribute(name);
		}

		return null;
	}

	public static NodeFactory getInstance() {
		if (instance == null) {
			return new NodeFactory();
		}

		return instance;
	}

	private boolean isTypeEqualToNode(String type, String nodeName) {
		return type.equalsIgnoreCase(nodeName);
	}
}
