package src.view;

import src.controllers.TreeController;
import src.view.tree.TreeView;

import javax.swing.*;
import java.awt.*;

public class DesktopView extends JPanel {

	private TreeView treeView;
	private TabbedView tabbedView;
	private TreeController treeController;

	private JSplitPane splitPane;
	private JSplitPane indexSplit;
	private JPanel indexPanel;
	private JPanel mainPanel;

	public DesktopView(TreeView treeView) {
		this.setLayout(new BorderLayout());

		this.treeView = treeView;
		this.tabbedView = new TabbedView();
		this.treeController = new TreeController(treeView);

		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new BorderLayout());

		this.indexPanel = new JPanel();
		this.indexPanel.setLayout(new BorderLayout());

		this.indexSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, indexPanel, tabbedView);
		this.indexSplit.setDividerLocation(0);

		this.splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, indexSplit, mainPanel);
		this.splitPane.setDividerLocation(350);

//		this.add(tableToolbarView, BorderLayout.NORTH);
		this.add(splitPane, BorderLayout.CENTER);
	}
}
