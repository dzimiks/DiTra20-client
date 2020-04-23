package src.models;

import src.models.tree.Node;
import src.models.tree.NodeFactory;

public class InformationResource extends Node {

	public InformationResource(String name) {
		super(name);
	}

	public InformationResource(InformationResource resource) {
		super(resource.getName());

		for (int i = 0; i < resource.getChildCount(); i++) {
			Entity selectedEntity = (Entity) resource.getChildAt(i);
			Entity entity = (Entity) NodeFactory.getInstance().getNode("ENT", selectedEntity.getName());
			this.addChild(entity);
		}
	}
}
