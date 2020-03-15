package src.listeners.desktop;

import src.view.dialog.AddDataDialog;
import src.view.table.TabbedView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CancelButtonListener implements ActionListener {

    private JDialog dialogToClose;

    public CancelButtonListener(JDialog dialogToClose) {
        this.dialogToClose = dialogToClose;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dialogToClose.setVisible(false);
    }
}
