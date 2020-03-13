package src.listeners.desktop;

import src.view.table.TabbedView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class FetchDataListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			TabbedView.activePanel.fetchData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
