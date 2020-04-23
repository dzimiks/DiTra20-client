package src.view;

import src.constants.Constants;
import src.listeners.tree.GenerateTreeListener;
import src.view.tree.TreeView;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MenuBarView extends JMenuBar {

	public MenuBarView(TreeView treeView) {
		JMenu file = new JMenu("File");
		JMenuItem importDb = new JMenuItem("Import DB");
		importDb.setMnemonic(KeyEvent.VK_I);
		importDb.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
		importDb.setIcon(new ImageIcon(Constants.DATABASE_ICON));
		importDb.addActionListener(new GenerateTreeListener(treeView));

		file.setMnemonic(KeyEvent.VK_F);
		file.add(importDb);
		this.add(file);
	}
}
