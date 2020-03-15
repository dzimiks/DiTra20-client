package src.listeners.desktop;

import src.models.Attribute;
import src.models.Entity;
import src.models.Record;
import src.models.datatypes.DateType;
import src.repository.DatabaseImplementation;
import src.view.MainView;
import src.view.dialog.DeleteDataDialog;
import src.view.table.TabbedView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteDataListener implements ActionListener {

    private Entity entity;
    private int size;
    private TabbedView tabbedView;


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        entity = tabbedView.activePanel.getEntity();
        size = tabbedView.activePanel.getEntity().getChildren().size();

        DeleteDataDialog deleteDataDialog = new DeleteDataDialog(entity, size, tabbedView);
        deleteDataDialog.setVisible(true);
    }
}
