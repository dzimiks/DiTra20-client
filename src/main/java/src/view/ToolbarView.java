package src.view;

import src.constants.Constants;
import src.listeners.tree.GenerateTreeListener;
import src.main.Main;
import src.models.Warehouse;
import src.view.tree.TreeView;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ToolbarView extends JToolBar {

	private TreeView treeView;

	public ToolbarView(TreeView treeView) {
		this.treeView = treeView;

		JButton connectToDB = new JButton();
		connectToDB.setToolTipText("Connect to DB");
		connectToDB.setIcon(new ImageIcon(Constants.DATABASE_ICON));
		connectToDB.addActionListener(new GenerateTreeListener(treeView));

		this.add(connectToDB);
		this.setFloatable(false);
	}

	public TreeView getTreeView() {
		return treeView;
	}
}
