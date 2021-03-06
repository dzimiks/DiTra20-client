package src.view;

import src.models.Attribute;
import src.models.Entity;
import src.models.InformationResource;
import src.models.Relation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RelationView extends JPanel {

	private Entity entity;
	private InformationResource parentInfRes;
	private List<Relation> relations;
	private JTabbedPane tabbedPane;
	private ArrayList<Entity> entities;
	private ArrayList<RelationPanel> relationPanels;

	public RelationView(Entity entity) {
		this.entity = entity;
		this.parentInfRes = (InformationResource) entity.getParent();
		this.relations = entity.getRelations();

		this.tabbedPane = new JTabbedPane();

		this.setLayout(new FlowLayout());
		this.add(tabbedPane);
	}

	public void addNewDBTab(Entity entity, List<Attribute> referringAttributes, List<Attribute> referencedAttributes) {
		RelationPanel relationPanel = new RelationPanel(entity, referringAttributes, referencedAttributes);

//		System.out.println("RelationView - relationPanel ENT: " + entity.getName());


//		if (relationPanel == null) {
//			JOptionPane.showMessageDialog(
//					new JFrame(),
//					"RelationPanel is null!",
//					"RelationView: Error",
//					JOptionPane.ERROR_MESSAGE
//			);
//			return;
//		}

		tabbedPane.addTab(entity.getName(), relationPanel);
		tabbedPane.setSelectedIndex(0);
		this.revalidate();
		this.repaint();
	}
}
