package src.view;

import src.controllers.DesktopController;
import src.controllers.TreeController;
import src.view.table.TabbedView;
import src.view.table.TableToolbarView;
import src.view.tree.TreeView;

import javax.swing.*;
import java.awt.*;

public class DesktopView extends JPanel {

	private TreeView treeView;
	private TabbedView tabbedView;
	private TableToolbarView tableToolbarView;

	private TreeController treeController;
	private DesktopController desktopController;

	private JSplitPane splitPane;
	private JSplitPane indexSplit;
	private JPanel indexPanel;
	private JPanel mainPanel;

	public DesktopView(TreeView treeView) {
		this.setLayout(new BorderLayout());

		this.treeView = treeView;
		this.tabbedView = new TabbedView();
		this.tableToolbarView = new TableToolbarView(null);

		this.treeController = new TreeController(treeView, tabbedView);
		this.desktopController = new DesktopController(tabbedView);

		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new BorderLayout());

		this.indexPanel = new JPanel();
		this.indexPanel.setLayout(new BorderLayout());

		this.indexSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, indexPanel, tabbedView);
		this.indexSplit.setDividerLocation(0);

		this.splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, indexSplit, mainPanel);
		this.splitPane.setDividerLocation(350);

		this.add(tableToolbarView, BorderLayout.NORTH);
		this.add(splitPane, BorderLayout.CENTER);
	}

	public TreeView getTreeView() {
		return treeView;
	}

	public TabbedView getTabbedView() {
		return tabbedView;
	}

	public TreeController getTreeController() {
		return treeController;
	}

	public DesktopController getDesktopController() {
		return desktopController;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

	public JSplitPane getIndexSplit() {
		return indexSplit;
	}

	public JPanel getIndexPanel() {
		return indexPanel;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}
}
