package src.view.dialog;

import src.listeners.desktop.CancelButtonListener;
import src.models.Attribute;
import src.models.Entity;
import src.models.Record;
import src.models.datatypes.CharType;
import src.models.datatypes.DateType;
import src.models.datatypes.VarCharType;
import src.models.tree.Node;
import src.repository.*;
import src.view.MainView;
import src.view.table.TabbedView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DeleteDataDialog extends JDialog {
    private int height = 25;

    private int size;
    private Entity entity;
    private ArrayList<Node> attributes;
    private Attribute attr;

    private JTextField[] textFields;
    private TabbedView tabbedView;

    public DeleteDataDialog(Entity entity, int size, TabbedView tabbedView) {
        this.entity = entity;
        this.size = size;
        this.tabbedView = tabbedView;
        this.attributes = (ArrayList<Node>) entity.getChildren();
        this.attr = new Attribute("Attribute");


        setLayout(null);
        setTitle("Delete Data");
        setSize(300, 200);
        setLocationRelativeTo(MainView.getInstance());
//        this.getContentPane().setBackground(Color.RED);
        initialize();
    }

    public void initialize() {
        this.textFields = new JTextField[size];

        StringBuilder selectedData = new StringBuilder();

        int selectedRow = TabbedView.getActivePanel().getTable().getSelectedRow();
        int numberOfColumns = TabbedView.getActivePanel().getTable().getColumnCount();


        for (int j = 0; j < numberOfColumns; j++) {
            String columnName = TabbedView.getActivePanel().getTable().getColumnName(j);
            String value = String.valueOf(TabbedView.getActivePanel().getTable().getValueAt(selectedRow, j));

            selectedData.append(value).append(" ");
        }

        String[] cellValues = selectedData.toString().split(" ");

        for (int i = 0; i < size; i++) {
            this.textFields[i] = new JTextField();
            String cellText = cellValues[i];
//            System.out.println("Cell text"+cellText);
            this.textFields[i].setText(cellText);
        }
        Record recordToDelete = prepareObjectForDeletion();
        JLabel label = new JLabel("Are you sure you want to delete selected data?");
        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");

        btnCancel.addActionListener(new CancelButtonListener(this));
        btnOk.addActionListener(actionEvent -> {
            RepositoryImplementor repositoryImplementor = new DatabaseImplementation();
//            RepositoryImplementor repositoryImplementor = new SQLBrokerImplementation();
            RepositoryImplementation repositoryImplementation = new RepositoryImplementation(repositoryImplementor);
            repositoryImplementation.deleteRecord(recordToDelete);
            this.setVisible(false);
        });

        label.setBounds(2,50,300,height);
        btnOk.setBounds(80, 130, 50, height);
        btnCancel.setBounds(150, 130, 80, height);

        add(label);
        add(btnOk);
        add(btnCancel);
    }

    public Record prepareObjectForDeletion(){
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
