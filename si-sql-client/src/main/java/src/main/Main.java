package src.main;

import src.view.MainView;

public class Main {

	public static void main(String[] args) {
		MainView view = MainView.getInstance();
		view.setVisible(true);
	}
}
