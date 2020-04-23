package src.view.tree;

import src.constants.Constants;
import src.models.Attribute;
import src.models.Entity;
import src.models.InformationResource;
import src.models.Warehouse;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class TreeCellRendered extends DefaultTreeCellRenderer {

	@Override
	public Component getTreeCellRendererComponent(
			JTree tree,
			Object value,
			boolean selected,
			boolean expanded,
			boolean leaf,
			int row,
			boolean hasFocus
	) {
		super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

		String iconPath = null;

		if (value instanceof Warehouse) {
			iconPath = Constants.WAREHOUSE_ICON;
		} else if (value instanceof InformationResource) {
			iconPath = Constants.INFORMATION_RESOURCE_ICON;
		} else if (value instanceof Entity) {
			iconPath = Constants.ENTITY_ICON;
		} else if (value instanceof Attribute) {
			iconPath = Constants.ATTRIBUTE_ICON;
		}

		try {
			if (iconPath != null) {
				Icon icon = new ImageIcon(iconPath);
				this.setIcon(icon);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setText(value.toString());
		return this;
	}
}
