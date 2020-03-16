package src.listeners.tree;

import src.models.Entity;
import src.models.Relation;
import src.view.table.TabbedView;
import src.view.tree.TreeView;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

public class TreeListener implements TreeSelectionListener {

	private TreeView treeView;
	private TabbedView tabbedView;

	public TreeListener(TreeView treeView, TabbedView tabbedView) {
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
			System.out.println("TreeListener: " + entity + " => " + entity.getRelations());

			for (Relation relation : entity.getRelations()) {
				System.out.println(">>> TreeListener - getReferencedAttributes: " + relation.getReferencedAttributes());
			}

			tabbedView.addNewTab(entity);
//			System.out.println("TreeListener - Clicked on " + entity);
		}
	}
}
