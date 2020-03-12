package src.view.tree;

import src.constants.Constants;
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
		}
//		else if (value instanceof InformationResource) {
//			iconPath = "/treeImages/information-resource.png";
//		} else if (value instanceof Entity) {
//			iconPath = "/treeImages/entity.png";
//		} else if (value instanceof Attribute) {
//			iconPath = "/treeImages/attribute.png";
//		}

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
