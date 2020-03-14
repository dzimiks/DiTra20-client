package src.view.dialog;


import src.listeners.desktop.CancelButtonListener;
import src.models.Attribute;
import src.models.Entity;
import src.models.tree.Node;
import src.view.MainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class AddDataDialog extends JDialog implements Serializable {

	private int height = 25;
	private int width = 180;
	private int x_left = 40;
	private int x_right = 250;
	private int y = 10;
	private int size;
	private Entity entity;
	private ArrayList<Node> attributes;
	private Attribute attr;
	
	private JLabel[] labels;
	private JTextField[] textFields;
	
	public AddDataDialog(Entity entity, int size) {
		this.entity = entity;
		this.size = size;
		this.attributes = (ArrayList<Node>) entity.getChildren();
		this.attr = new Attribute("Attribute");
		
		setLayout(null);
		setTitle(entity.getName());
		setSize(500, 700);

		setLocationRelativeTo(MainView.getInstance());
		initialize();
	}

	public void initialize() {
		this.labels = new JLabel[size];
		this.textFields = new JTextField[size];
		System.out.println("USO");
		System.out.println(size);

		for (int i = 0; i < size; i++) {

			this.attr = (Attribute) this.attributes.get(i);
			this.labels[i] = new JLabel(parseName(attr.getName()));
			this.textFields[i] = new JTextField();

			this.labels[i].setBounds(x_left, y, width, height);
			this.textFields[i].setBounds(x_right, y, width, height);

			y += 40;
			add(labels[i]);
			add(textFields[i]);
		}

		JButton btnOk = new JButton("OK");
		//btnOk.addActionListener(new OkButtonListener());

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new CancelButtonListener(this));

		btnOk.setBounds(155, y + 30, 50, height);
		btnCancel.setBounds(225, y + 30, 80, height);

		add(btnOk);
		add(btnCancel);
	}
	private String parseName(String name) {
		int n = name.length();
		StringBuilder sb = new StringBuilder();
		sb.append(name.charAt(0));

		for (int i = 1; i < n; i++) {
			char c = name.charAt(i);

			if (c >= 'A' && c <= 'Z') {
				c += 32;
				sb.append(" ");
			}

			sb.append(c);
		}

		sb.append(": ");
		return sb.toString();
	}

}