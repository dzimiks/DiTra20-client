package src.view.tree;

import src.constants.Constants;
import src.controllers.TreeController;
import src.models.Warehouse;
import src.models.tree.Node;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

public class TreeView extends JPanel {

	private Node root;
	private MainTree tree;
	private DefaultTreeModel treeModel;
	private TreeController treeController;

	public TreeView() {
		init();
		this.treeController = new TreeController(this);
	}

	private void init() {
		this.root = Warehouse.getInstance();
		this.setPreferredSize(new Dimension(Constants.SCROLL_PANE_WIDTH, Constants.SCREEN_SIZE_HEIGHT));

		this.treeModel = new DefaultTreeModel(root);
		this.treeModel.setRoot(root);

		this.tree = new MainTree();
		this.tree.setModel(treeModel);

		TreeCellRendered cellRendered = new TreeCellRendered();
		this.tree.setCellRenderer(cellRendered);
		this.tree.setShowsRootHandles(true);

		TreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
		selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.tree.setSelectionModel(selectionModel);

		this.setLayout(new BorderLayout());
		this.setMinimumSize(new Dimension(Constants.SCREEN_SIZE_WIDTH, Constants.SCREEN_SIZE_HEIGHT));

		JScrollPane treeScrollPane = new JScrollPane(tree);
		treeScrollPane.setSize(new Dimension(Constants.TREE_SCROLL_PANE_WIDTH, Constants.TREE_SCROLL_PANE_HEIGHT));
		treeScrollPane.setMaximumSize(new Dimension(Constants.TREE_SCROLL_PANE_WIDTH, Constants.TREE_SCROLL_PANE_HEIGHT));
		treeScrollPane.setMinimumSize(new Dimension(Constants.TREE_SCROLL_PANE_WIDTH, Constants.TREE_SCROLL_PANE_HEIGHT));
		treeScrollPane.setPreferredSize(new Dimension(Constants.TREE_SCROLL_PANE_WIDTH, Constants.TREE_SCROLL_PANE_HEIGHT));
		treeScrollPane.setAutoscrolls(true);

		this.add(treeScrollPane, BorderLayout.NORTH);
	}

	public Node getRoot() {
		return root;
	}

	public MainTree getTree() {
		return tree;
	}

	public TreeController getTreeController() {
		return treeController;
	}

	public Node getSelectedNode() {
		Object object = tree.getLastSelectedPathComponent();

		if (object instanceof Node) {
			return (Node) object;
		}

		return null;
	}

	public void refresh() {
		treeModel.reload();
	}
}
