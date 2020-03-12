package src.view.tree;

import src.constants.Constants;
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

	public TreeView() {
		init();
	}

	private void init() {
		this.root = Warehouse.getInstance();
		this.setPreferredSize(new Dimension(250, Constants.SCREEN_SIZE_HEIGHT));

		this.treeModel = new DefaultTreeModel(root);
		this.treeModel.setRoot(root);

		this.tree = new MainTree();
		this.tree.setModel(treeModel);

		TreeCellRendered tcr = new TreeCellRendered();
		this.tree.setCellRenderer(tcr);
		this.tree.setShowsRootHandles(true);

		TreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
		selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.tree.setSelectionModel(selectionModel);

		this.setLayout(new BorderLayout());
		this.setMinimumSize(new Dimension(1000, 1000));

		int height = 1000;
		JScrollPane treeScrollPane = new JScrollPane(tree);
		treeScrollPane.setSize(new Dimension(50, height));
		treeScrollPane.setMaximumSize(new Dimension(50, height));
		treeScrollPane.setMinimumSize(new Dimension(50, height));
		treeScrollPane.setPreferredSize(new Dimension(50, height));
		treeScrollPane.setAutoscrolls(true);

		this.add(treeScrollPane, BorderLayout.NORTH);
	}

	public Node getRoot() {
		return root;
	}

	public MainTree getTree() {
		return tree;
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
