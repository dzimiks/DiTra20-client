package src.view.table;

import src.constants.Constants;
import src.listeners.desktop.FetchDataListener;
import src.models.Entity;
import src.view.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class TableToolbarView extends JPanel {

	private JButton doAction;

	private JButton dbFetch;
	private JButton dbAdd;
	private JButton dbUpdate;
	private JButton dbDelete;
	private JButton dbShowRelations;

	private Entity entity;

	private JToolBar dbToolbar;

	public TableToolbarView(Entity entity) {
		this.entity = entity;
		this.setLayout(new BorderLayout());
		createDBToolbar();
		this.add(dbToolbar, BorderLayout.SOUTH);
	}

	private void createDBToolbar() {
		this.dbToolbar = new JToolBar();
		this.dbToolbar.setLayout(new WrapLayout(20, 15, 10));

		this.dbFetch = new JButton("Fetch Data");
		this.dbFetch.setIcon(new ImageIcon(Constants.FETCH_ICON));
		this.dbFetch.addActionListener(new FetchDataListener());
//		this.dbFetch.addActionListener(actionEvent -> {
//			try {
//				TabbedView.activePanel.fetchData();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		});

		this.dbAdd = new JButton("Add");
		this.dbAdd.setIcon(new ImageIcon(Constants.ADD_ICON));

		this.dbUpdate = new JButton("Update");
		this.dbUpdate.setIcon(new ImageIcon(Constants.UPDATE_ICON));

		this.dbDelete = new JButton("Delete");
		this.dbDelete.setIcon(new ImageIcon(Constants.DELETE_ICON));

		this.doAction = new JButton("Submit");
		this.doAction.setIcon(new ImageIcon(Constants.SUBMIT_ICON));

		this.dbShowRelations = new JButton("Show relations");
		this.dbShowRelations.setIcon(new ImageIcon(Constants.RELATIONS_ICON));

		dbToolbar.add(dbFetch);
		dbToolbar.addSeparator();
		dbToolbar.add(dbAdd);
		dbToolbar.add(dbUpdate);
		dbToolbar.add(dbDelete);
		dbToolbar.addSeparator();
		dbToolbar.add(dbShowRelations);
		dbToolbar.addSeparator();
		dbToolbar.add(doAction);
	}
}
