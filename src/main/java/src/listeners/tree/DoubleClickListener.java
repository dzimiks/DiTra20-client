package src.listeners.tree;

import src.models.Entity;
import src.view.table.TabbedView;
import src.view.tree.TreeView;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

public class DoubleClickListener implements TreeSelectionListener {

	private TreeView treeView;
	private TabbedView tabbedView;

	public DoubleClickListener(TreeView treeView, TabbedView tabbedView) {
		this.treeView = treeView;
		this.tabbedView = tabbedView;
	}

	@Override
	public void valueChanged(TreeSelectionEvent event) {
		Object node = event.getPath().getLastPathComponent();

		if (node == null) {
			return;
		}

		if (node instanceof Entity) {
			Entity entity = (Entity) node;

			tabbedView.addNewTab(entity);
			System.out.println("Clicked on " + entity);
		}
	}
}
