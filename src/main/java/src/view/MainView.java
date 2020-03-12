package src.view;

import src.constants.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainView extends JFrame {

	private static MainView instance = null;

	private MainView() {
		init();
	}

	private void init() {
		setLookAndFeel();
		setIconImage();
		setLayout(new BorderLayout());
		setTitle(Constants.PROJECT_NAME);
		setSize(new Dimension(Constants.SCREEN_SIZE_WIDTH, Constants.SCREEN_SIZE_HEIGHT));
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
}
