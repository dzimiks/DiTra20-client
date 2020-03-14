package src.listeners.tree;

import src.models.Warehouse;
import src.view.tree.TreeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GenerateTreeListener implements ActionListener {

	private TreeView treeView;

	public GenerateTreeListener(TreeView treeView) {
		this.treeView = treeView;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			Warehouse.getInstance().loadWarehouse();
			this.treeView.refresh();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
