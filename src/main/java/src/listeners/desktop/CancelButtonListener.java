package src.listeners.desktop;

import src.view.dialog.AddDataDialog;
import src.view.table.TabbedView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.sun.glass.ui.Cursor.setVisible;

public class CancelButtonListener implements ActionListener {

    private AddDataDialog addDataDialog;

    public CancelButtonListener(AddDataDialog addDataDialog) {
        this.addDataDialog = addDataDialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addDataDialog.setVisible(false);
    }
}
