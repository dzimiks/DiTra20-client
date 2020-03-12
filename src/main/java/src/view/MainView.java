package src.view;

import src.constants.Constants;
import src.view.tree.TreeView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainView extends JFrame {

	private static MainView instance = null;

	private ToolbarView toolbarView;
	private TreeView treeView;

	private MainView() {
		init();
	}

	private void init() {
		setLookAndFeel();
		setIconImage();

		// Default settings
		this.setLayout(new BorderLayout());
		this.setTitle(Constants.PROJECT_NAME);
		this.setSize(new Dimension(Constants.SCREEN_SIZE_WIDTH, Constants.SCREEN_SIZE_HEIGHT));
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Initialize view
		this.treeView = new TreeView();
		this.toolbarView = new ToolbarView(treeView);
		this.add(toolbarView, BorderLayout.PAGE_START);

		JScrollPane scrollPane = new JScrollPane(treeView);
		scrollPane.setMinimumSize(new Dimension(250, 600));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, new JPanel());
		splitPane.setDividerLocation(300);

		this.add(splitPane, BorderLayout.CENTER);
	}

	private void setIconImage() {
		try {
			this.setIconImage(ImageIO.read(new File(Constants.FAVICON)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(Constants.NIMBUS_LOOK_AND_FEEL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MainView getInstance() {
		if (instance == null) {
			return new MainView();
		}

		return instance;
	}

	public ToolbarView getToolbarView() {
		return toolbarView;
	}

	public TreeView getTreeView() {
		return treeView;
	}
}
