package src.view;

import src.config.SQLConfig;
import src.models.Attribute;
import src.models.Entity;
import src.models.Record;
import src.models.datatypes.CharType;
import src.models.datatypes.DateType;
import src.models.datatypes.VarCharType;
import src.models.tree.Node;
import src.view.table.TabbedView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RelationPanel extends JPanel {

	private Entity entity;
	private DefaultTableModel model;
	private JTable table;
	private String[] columnNames;
	private JScrollPane js;
	private List<Attribute> referringAttributes;
	private List<Attribute> referencedAttributes;

	public RelationPanel(Entity entity, ArrayList<Record> records) {
		this.entity = entity;
		this.setLayout(new BorderLayout());

		columnNames = new String[entity.getChildCount()];

		for (int i = 0; i < entity.getChildCount(); i++) {
			columnNames[i] = entity.getChildren().get(i).getName();
		}

		model = new DefaultTableModel(columnNames, 0);

		for (Record r : records) {
			model.addRow(r.getData().toArray());
			System.out.println("Ubacujem " + r.getData().toString());
		}

		System.out.println("Ubacio sam sve");

		model.fireTableDataChanged();
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(900, 300));
		table.setFillsViewportHeight(true);
		js = new JScrollPane(table);
		this.add(js);
	}

	public RelationPanel(Entity entity, List<Attribute> referringAttributes, List<Attribute> referencedAttributes) {
		System.out.println("RelationPanel constructor: " +
				entity + " -> " + referringAttributes + " <- " + referencedAttributes);

		this.entity = entity;
		this.referringAttributes = referringAttributes;
		this.referencedAttributes = referencedAttributes;
		this.setLayout(new BorderLayout());

		columnNames = new String[entity.getChildCount()];

		for (int i = 0; i < entity.getChildCount(); i++) {
			columnNames[i] = entity.getChildren().get(i).getName();
		}

		model = new DefaultTableModel(columnNames, 0);

		try {
			fetchRecords();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		model.fireTableDataChanged();
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(900, 300));
		table.setFillsViewportHeight(true);
		js = new JScrollPane(table);
		this.add(js);
	}

	private void fetchRecords() throws SQLException {
		System.out.println("Relation Panel - Fetching DB records...");

		while (model.getRowCount() != 0) {
			model.removeRow(0);
		}

		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM ").append(entity.getName()).append(" WHERE ");

		int selectedRow = TabbedView.activePanel.getTable().getSelectedRow();

		for (Attribute referencedAttribute : referencedAttributes) {
			int index = 0;
			Object[] attrs = TabbedView.activePanel.getEntity().getChildren().toArray();

			for (int j = 0; j < attrs.length; j++) {
				Attribute attr = (Attribute) attrs[j];

				if (attr.getName().equals(referencedAttribute.getName())) {
					index = j;
					break;
				}
			}

			Object obj = TabbedView.activePanel.getTable().getModel().getValueAt(selectedRow, index);
			query.append(referencedAttribute).append("=").append(parseClassType(obj)).append(" AND ");
		}

		query.delete(query.length() - 5, query.length());
		System.out.println(">>> Relation Panel query: " + query.toString());

		PreparedStatement statement = SQLConfig.getInstance().getDbConnection().prepareStatement(query.toString());
//		PreparedStatement statement = SQLConfig.getInstance().getDbConnection().prepareStatement("SELECT * FROM HIGH_EDUCATION_INSTITUTION");
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.getMetaData().getColumnCount() != entity.getChildCount()) {
			System.err.println("Database and MS out of sync.");
			return;
		}

		while (resultSet.next()) {
			Record record = new Record();

			// entity.getChildren()
			for (Node node : entity.getChildren()) {

				if (node instanceof Attribute) {
					Object value = resultSet.getObject(node.getName());

//					if (value instanceof ClobImpl) {
//						value = (ClobImpl) value;
//						record.addObject(clobToString((Clob) value));
//					}
//					else
					record.addObject(value);
				}
			}

			System.out.println("RELATION PANEL RECORD: " + record.getData());
			model.addRow(record.getData().toArray());
		}

		resultSet.close();
		statement.close();
	}

	public String parseClassType(Object obj) {
		if (obj.getClass() == CharType.class ||
				obj.getClass() == VarCharType.class ||
				obj.getClass() == String.class ||
				obj.getClass() == DateType.class) {
			return "'" + obj + "'";
		}

		return obj.toString();
	}
}
