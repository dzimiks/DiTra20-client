package src.models;

import src.models.tree.Node;

public class InformationResource extends Node {

	public InformationResource(String name) {
		super(name);
	}

	public InformationResource(InformationResource resource) {
		super(resource.getName());

		for (int i = 0; i < resource.getChildCount(); i++) {
			this.addChild(new Entity((Entity) resource.getChildAt(i)));
		}
	}
}
