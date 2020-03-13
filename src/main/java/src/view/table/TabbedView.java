package src.view.table;

import src.models.Entity;
import src.view.table.TablePanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TabbedView extends JTabbedPane {

	private List<Entity> entities;
	private List<TablePanel> tables;
	public static TablePanel activePanel;

	public TabbedView() {
		this.entities = new ArrayList<>();
		this.tables = new ArrayList<>();
	}

	public void addNewTab(Entity entity) {
		if (!entities.contains(entity)) {
			TablePanel table = new TablePanel(entity);
			activePanel = table;

			this.addTab(entity.getName(), table);
			int index = this.getTabCount() - 1;
			this.setSelectedIndex(index);

			this.entities.add(entity);
			this.tables.add(table);
		} else {
			int indexOf = entities.indexOf(entity);
			this.setSelectedIndex(this.indexOfComponent(tables.get(indexOf)));
		}

		activePanel = getTabelePanelAt(getSelectedIndex());
	}

	public Entity getSelectedEntity() {
		if (getSelectedComponent() == null) {
			return null;
		}

		return ((TablePanel) getSelectedComponent()).getEntity();
	}

	public TablePanel getTabelePanelAt(int index) {
		return tables.get(index);
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public List<TablePanel> getTables() {
		return tables;
	}
}
