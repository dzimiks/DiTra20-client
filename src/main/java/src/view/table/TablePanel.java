package src.view.table;

import src.models.Attribute;
import src.config.SQLConfig;
import src.models.Entity;
import src.models.tree.Node;
import src.repository.DatabaseImplementation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TablePanel extends JPanel {

	private Entity entity;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel tableModel;

	private String[] columnNames;

	public TablePanel(Entity entity) {
		this.entity = entity;
		this.setLayout(new BorderLayout());

		this.columnNames = new String[entity.getChildCount()];

		for (int i = 0; i < entity.getChildCount(); i++) {
			columnNames[i] = entity.getChildren().get(i).getName();
		}

		this.tableModel = new DefaultTableModel(columnNames, 0);
		this.table = new JTable(tableModel);

		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setPreferredScrollableViewportSize(new Dimension(900, 300));
		table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(table);
		this.add(scrollPane);
	}

	public void clearTable() {
		while (tableModel.getRowCount() != 0) {
			tableModel.removeRow(0);
		}
	}

	public Entity getEntity() {
		return entity;
	}

	public JTable getTable() {
		return table;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public String[] getColumnNames() {
		return columnNames;
	}
}
