package src.view.table;

import src.config.SQLConfig;
import src.constants.Constants;
import src.listeners.desktop.AddDataListener;
import src.listeners.desktop.DeleteDataListener;
import src.listeners.desktop.FetchDataListener;
import src.listeners.desktop.UpdateDataListener;
import src.models.Attribute;
import src.models.Entity;
import src.models.Record;
import src.models.Relation;
import src.view.DesktopView;
import src.view.MainView;
import src.view.RelationView;
import src.view.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableToolbarView extends JPanel {

	private JButton dbFetch;
	private JButton dbAdd;
	private JButton dbUpdate;
	private JButton dbDelete;
	private JButton dbShowRelations;

	private Entity entity;

	private JToolBar dbToolbar;
	private RelationView relationView;

	public TableToolbarView(Entity entity, RelationView relationView) {
		this.entity = entity;
		this.relationView = relationView;

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

		this.dbAdd = new JButton("Add");
		this.dbAdd.setIcon(new ImageIcon(Constants.ADD_ICON));
		this.dbAdd.addActionListener(new AddDataListener());

		this.dbUpdate = new JButton("Update");
		this.dbUpdate.setIcon(new ImageIcon(Constants.UPDATE_ICON));
		this.dbUpdate.addActionListener(new UpdateDataListener());

		this.dbDelete = new JButton("Delete");
		this.dbDelete.setIcon(new ImageIcon(Constants.DELETE_ICON));
		this.dbDelete.addActionListener(new DeleteDataListener());

		this.dbShowRelations = new JButton("Show relations");
		this.dbShowRelations.setIcon(new ImageIcon(Constants.RELATIONS_ICON));
		this.dbShowRelations.addActionListener(event -> {
			DesktopView desktopView = MainView.getInstance().getDesktopView();
			desktopView.setRelationView(relationView);
			desktopView.repaint();

			if (TabbedView.activePanel == null) {
				JOptionPane.showMessageDialog(
						new JFrame(),
						"TabbedView.activePanel is null!",
						"TableToolbarView: Error",
						JOptionPane.ERROR_MESSAGE
				);
				return;
			}

			Entity activeEntity = TabbedView.activePanel.getEntity();
			List<Relation> relations = activeEntity.getRelations();
			int selectedIndex = TabbedView.activePanel.getTable().getSelectedRow();
			Record currentRecord = new Record(entity);

			System.out.println("activeEntity: " + activeEntity);
			System.out.println("> relations: " + relations);
			System.out.println("selectedIndex: " + selectedIndex);
			System.out.println("activePanel.getColumnCount(): " + TabbedView.activePanel.getTable().getColumnCount());

			if (selectedIndex < 0) {
				JOptionPane.showMessageDialog(
						new JFrame(),
						"Selected index is " + selectedIndex,
						"TableToolbarView: Error",
						JOptionPane.ERROR_MESSAGE
				);
				return;
			}

			for (int i = 0; i < TabbedView.activePanel.getTable().getColumnCount(); i++) {
				Object obj = TabbedView.activePanel.getTable().getModel().getValueAt(selectedIndex, i);
				currentRecord.addObject(obj);
			}

			for (Relation relation : relations) { // Prolazim kroz sve svoje relacije
				Entity referencedEntity = relation.getReferencedEntity();
				List<Attribute> referringAttributes = relation.getReferringAttributes();
				List<Attribute> referencedAttributes = relation.getReferencedAttributes();
				List<Integer> referringIndex = new ArrayList<>();
				List<Integer> referencedIndex = new ArrayList<>();
				List<Record> recordsToShow = new ArrayList<>();

				for (Attribute referringAttribute : referringAttributes) { // Za svaku relaciju prolazim kroz listu Atributa
					// po kojima je u vezi sa drugim entitetom

					for (int k = 0; k < activeEntity.getChildCount(); k++) { // Prolazim kroz sve svoje atribute i gledam koji imaju isto
						// ime kao reffering attribute

						if (((Attribute) activeEntity.getChildAt(k)).getName().equals(referringAttribute.getName())) {

							referringIndex.add(k);
							//System.out.println("1. Atribut " + ((Attribute)activeEntity.getChildAt(k)).getName() + " Indeks " + j);
						}
					}
				}

				for (Attribute referencedAttribute : referencedAttributes) {

					for (int p = 0; p < referencedEntity.getChildCount(); p++) {

						if (((Attribute) referencedEntity.getChildAt(p)).getName().equals(referencedAttribute.getName())) {

							referencedIndex.add(p);
							//System.out.println("2. Atribut " + ((Attribute)referencedEntity.getChildAt(p)).getName() + " Indeks " + p);
						}

					}
				}

				// TODO
				for (int j = 0; j < TabbedView.activePanel.getTable().getModel().getRowCount(); j++) {
					Record newRecord = new Record(entity);
					boolean isOkRecord = true;

					for (int ind = 0; ind < TabbedView.activePanel.getTable().getColumnCount(); ind++) {
						Object obj = TabbedView.activePanel.getTable().getModel().getValueAt(j, ind);
						newRecord.addObject(obj);
					}

					for (int m = 0; m < referencedIndex.size(); m++) {
						if (!newRecord.getData().get(referencedIndex.get(m)).equals(currentRecord.getData().get(referringIndex.get(m)))) {
							isOkRecord = false;
							break;
						}
					}

					if (isOkRecord) {
						recordsToShow.add(newRecord);
					}
				}

				System.out.println("recordsToShow: " + recordsToShow);
				System.out.println("referencedEntity: " + referencedEntity);
				System.out.println("referringAttributes: " + referringAttributes);
				System.out.println("referencedAttributes: " + referencedAttributes);

				relationView.addNewDBTab(referencedEntity, referringAttributes, referencedAttributes);
				desktopView.repaint();
			}
		});

		dbToolbar.add(dbFetch);
		dbToolbar.addSeparator();
		dbToolbar.add(dbAdd);
		dbToolbar.add(dbUpdate);
		dbToolbar.add(dbDelete);
		dbToolbar.addSeparator();
		dbToolbar.add(dbShowRelations);
	}
}
