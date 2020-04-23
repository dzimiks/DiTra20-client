package src.listeners.desktop;


import src.models.Entity;
import src.view.dialog.AddDataDialog;
import src.view.dialog.UpdateDataDialog;
import src.view.table.TabbedView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateDataListener implements ActionListener {

    private Entity entity;
    private int size;
    private TabbedView tabbedView;

    @Override
    public void actionPerformed(ActionEvent e) {
        entity = TabbedView.activePanel.getEntity();
        size = TabbedView.activePanel.getEntity().getChildren().size();

//        System.out.println(entity.getName());
//        System.out.println(size);

        UpdateDataDialog updateDataDialog = new UpdateDataDialog(entity, size,tabbedView);
        updateDataDialog.setVisible(true);
    }
}
