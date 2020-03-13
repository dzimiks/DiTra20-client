package src.controllers;

import src.listeners.tree.DoubleClickListener;
import src.view.tree.TreeView;

public class TreeController {

	private TreeView treeView;

	public TreeController(TreeView treeView) {
		this.treeView = treeView;
		this.treeView.getTree().addTreeSelectionListener(new DoubleClickListener(treeView));
	}
}
