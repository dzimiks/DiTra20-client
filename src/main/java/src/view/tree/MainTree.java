package src.view.tree;

import src.models.Warehouse;
import src.models.tree.Node;
import src.observer.MainObserver;
import src.observer.ObserverNotification;
import src.view.MainView;

import javax.swing.*;
import javax.swing.tree.TreePath;

public class MainTree extends JTree implements MainObserver {

	public MainTree() {
//		Warehouse.getInstance().addObserver(this);
	}

	@Override
	public void update(ObserverNotification notification, Object object) {
		switch (notification) {
			case TREE_SELECT:
				TreePath treePath = new TreePath(object);
				TreePath path = this.getSelectionPath();
				Node selectedNode = null;

				if (path != null && path.getLastPathComponent() instanceof Node) {
					selectedNode = (Node) path.getLastPathComponent();
//					System.out.println("MainTree - selectedNode is not null: " + selectedNode);
					path = selectedNode.getPath();
				}

//				System.out.println("MainTree - selectedNode: " + selectedNode);
//				System.out.println("MainTree - TREE PATH: " + treePath);
//				System.out.println("MainTree - MAIN LAST: " + path);

				if (treePath != path) {
//					System.out.println("DIFF");
					// TODO: It's not working
					// this.setSelectionPath(treePath);
				}

				break;
			default:
				break;
		}
	}
}
