package src.view.dialog;


import src.listeners.desktop.CancelButtonListener;
import src.models.Attribute;
import src.models.Entity;
import src.models.Record;
import src.models.datatypes.CharType;
import src.models.datatypes.DateType;
import src.models.datatypes.VarCharType;
import src.models.tree.Node;
import src.repository.DatabaseImplementation;
import src.view.MainView;
import src.view.table.TabbedView;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

//TODO fetch data for foreign keys and put it in combox so client can choose
public class UpdateDataDialog extends JDialog implements Serializable {

    private int height = 25;
    private int width = 100;
    private int x_left = 20;
    private int x_right = 130;
    private int y = 10;

    private int size;
    private Entity entity;
    private ArrayList<Node> attributes;
    private Attribute attr;

    private JLabel[] labels;
    private JLabel[] types;
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
        this.types = new JLabel[size];

        StringBuilder selectedData = new StringBuilder();

        int selectedRow = TabbedView.getActivePanel().getTable().getSelectedRow();
        int numberOfColumns = TabbedView.getActivePanel().getTable().getColumnCount();

        for (int j = 0; j < numberOfColumns; j++) {
            String columnName = TabbedView.getActivePanel().getTable().getColumnName(j);
            String value = String.valueOf(TabbedView.getActivePanel().getTable().getValueAt(selectedRow, j));

            selectedData.append(value).append(" ");
        }

        String[] cellValues = selectedData.toString().split(" ");
//        System.out.println("CellVALUES LENGTH"+ cellValues.length);
//        for (int i = 0; i < cellValues.length; i++)
//            System.out.println("CellValue: " + cellValues[i]);


        for (int i = 0; i < size; i++) {

            this.attr = (Attribute) this.attributes.get(i);
            this.labels[i] = new JLabel(parseName(attr.getName()));
            this.textFields[i] = new JTextField();
            String cellText = cellValues[i];
//            System.out.println("Cell text"+cellText);
            this.textFields[i].setText(cellText);
            this.types[i] = new JLabel(getType(attr));

            this.labels[i].setBounds(x_left, y, width, height);
            this.textFields[i].setBounds(x_right, y, width, height);
            this.types[i].setBounds(x_right+120,y,width,height);

            y += 40;
            add(labels[i]);
            add(textFields[i]);
            add(types[i]);
        }
        Record oldRecord = prepareObjectForUpdate();

        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");

        btnCancel.addActionListener(new CancelButtonListener(this));
        btnOk.addActionListener(actionEvent -> {
            Record newRecord = prepareObjectForUpdate();
            DatabaseImplementation databaseImplementation = new DatabaseImplementation();
            databaseImplementation.updateRecord(newRecord,oldRecord);
            this.setVisible(false);
        });

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
    private String getType(Attribute attr2) {
        if (attr2.getValueClass() == VarCharType.class) {
            if (attr2.isPrimaryKey()) {
                return "*VarChar (" + attr2.getLength() + ")";
            }

            return "VarChar (" + attr2.getLength() + ")";
        }
        else if (attr2.getValueClass() == CharType.class) {
            if (attr2.isPrimaryKey()) {
                return "*Char (" + attr2.getLength() + ")";
            }

            return "Char (" + attr2.getLength() + ")";
        }
        else if (attr2.getValueClass() == DateType.class) {
            if (attr2.isPrimaryKey()) {
                return "*Date (" + attr2.getLength() + ")";
            }

            return "Date (" + attr2.getLength() + ")";
        }
        else if (attr2.getValueClass() == Boolean.class) {
            if (attr2.isPrimaryKey()) {
                return "*Boolean (" + attr2.getLength() + ")";
            }

            return "Boolean (" + attr2.getLength() + ")";
        }
        else if (attr2.getValueClass() == Integer.class) {
            if (attr2.isPrimaryKey()) {
                return "*Numeric (" + attr2.getLength() + ")";
            }

            return "Numeric (" + attr2.getLength() + ")";
        }

        return null;
    }

    public Record prepareObjectForUpdate(){
        Record record = new Record(entity);
        boolean error = false;
        Attribute attribute = null;
        String fieldText = null;

        for (int i = 0; i < size; i++) {
            attribute = ((Attribute) attributes.get(i));

            fieldText = this.textFields[i].getText();
            record.getTextFields().add(this.textFields[i].getText());

            if (attribute.isPrimaryKey() && fieldText.equals("")) {
                error = true;
                JOptionPane.showMessageDialog(new JFrame(), "Attribute " + attribute.getName() + " must be entered",
                        "Error", JOptionPane.ERROR_MESSAGE);
                break;
            }
            if (attribute.getLength() < fieldText.length()) {
                error = true;
                JOptionPane.showMessageDialog(new JFrame(), "Attribute " + attribute.getName()
                                + " mustn't be longer than" + attribute.getLength() + "characters", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            }
            if (!(fieldText.equals("")) && (attribute.getValueClass() == Boolean.class)
                    && !((fieldText.equals("true") || fieldText.equals("false")))) {
                error = true;
                JOptionPane.showMessageDialog(new JFrame(),
                        "Attribute " + attribute.getName() + " must be true or false, but is neither", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            }
            if (!(fieldText.equals("")) && attribute.getValueClass() == Integer.class) {
                try {
                    int a = Integer.parseInt(fieldText);
                } catch (NumberFormatException ex) {
                    error = true;
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Attribute " + attribute.getName() + " should be numeric value but is not", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
            if (!(fieldText.equals("")) && attribute.getValueClass() == DateType.class) {
                if (!DateType.isValidDate(fieldText)) {
                    error = true;
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Attribute " + attribute.getName() + " should be date format but it's not", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        }
        return record;
    }
}