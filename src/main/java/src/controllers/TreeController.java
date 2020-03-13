package src.controllers;

import src.listeners.tree.DoubleClickListener;
import src.view.table.TabbedView;
import src.view.tree.TreeView;

public class TreeController {

	private TreeView treeView;
	private TabbedView tabbedView;

	public TreeController(TreeView treeView, TabbedView tabbedView) {
		this.treeView = treeView;
		this.tabbedView = tabbedView;
		this.treeView.getTree().addTreeSelectionListener(new DoubleClickListener(treeView, tabbedView));
	}
}
