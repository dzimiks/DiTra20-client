package src.listeners.tree;

import src.models.Entity;
import src.view.tree.TreeView;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

public class DoubleClickListener implements TreeSelectionListener {

	private TreeView treeView;

	public DoubleClickListener(TreeView treeView) {
		this.treeView = treeView;
	}

	@Override
	public void valueChanged(TreeSelectionEvent event) {
		Object node = event.getPath().getLastPathComponent();

		if (node == null) {
			return;
		}

		if (node instanceof Entity) {
			System.out.println("Clicked on " + node);
		}
	}
}
