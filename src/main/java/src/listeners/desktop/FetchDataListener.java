package src.listeners.desktop;

import src.models.Record;
import src.repository.DatabaseImplementation;
import src.view.table.TabbedView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class FetchDataListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			TabbedView.activePanel.clearTable();
			DatabaseImplementation databaseImplementation = new DatabaseImplementation();
			List<Record> records = databaseImplementation.readRecords();
			System.out.println("DATA: " + records.toArray());
//			TabbedView.activePanel.getTableModel().addRow(records.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
