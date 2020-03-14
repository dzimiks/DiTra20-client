package src.view.dialog;


import src.models.Attribute;
import src.models.Entity;
import src.models.tree.Node;
import src.view.MainView;
import src.view.table.TabbedView;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class UpdateDataDialog extends JDialog implements Serializable {

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
    private TabbedView tabbedView;

    public UpdateDataDialog(Entity entity, int size, TabbedView tabbedView) {
        this.entity = entity;
        this.size = size;
        this.tabbedView = tabbedView;
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
        String selectedData = "";

        int selectedRow = tabbedView.getActivePanel().getTable().getSelectedRow();
        int numberOfColumns = tabbedView.getActivePanel().getTable().getColumnCount();

        for (int j = 0; j < numberOfColumns; j++) {
            selectedData += " " + tabbedView.getActivePanel().getTable().getValueAt(selectedRow, j);
        }

        String[] cellValues = selectedData.split(" ");
//        for (int i = 0; i < cellValues.length; i++)
//            System.out.println("CellValue: " + cellValues[i]);


        for (int i = 0; i < size; i++) {

            this.attr = (Attribute) this.attributes.get(i);
            this.labels[i] = new JLabel(parseName(attr.getName()));
            this.textFields[i] = new JTextField();
            this.textFields[i].setText(cellValues[i+1]);

            this.labels[i].setBounds(x_left, y, width, height);
            this.textFields[i].setBounds(x_right, y, width, height);

            y += 40;
            add(labels[i]);
            add(textFields[i]);
        }


        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");

        btnOk.setBounds(135, y + 50, 50, height);
        btnCancel.setBounds(195, y + 50, 80, height);

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