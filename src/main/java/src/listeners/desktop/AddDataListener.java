package src.listeners.desktop;

import src.models.Entity;
import src.view.dialog.AddDataDialog;
import src.view.table.TabbedView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddDataListener implements ActionListener {

    private Entity entity;
    private int size;

    @Override
    public void actionPerformed(ActionEvent e) {
        entity = TabbedView.activePanel.getEntity();
        size = TabbedView.activePanel.getEntity().getChildren().size();

//        System.out.println(entity.getName());
//        System.out.println(size);

        AddDataDialog addDataDialog = new AddDataDialog(entity, size);
        addDataDialog.setVisible(true);
    }

}
