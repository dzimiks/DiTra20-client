package src.listeners.desktop;

import src.models.Entity;
import src.view.MainView;
import src.view.dialog.DeleteDataDialog;
import src.view.table.TabbedView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteDataListener implements ActionListener {

	private Entity entity;
	private int size;
	private TabbedView tabbedView;

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		entity = TabbedView.activePanel.getEntity();
		size = TabbedView.activePanel.getEntity().getChildren().size();
		tabbedView = MainView.getInstance().getDesktopView().getTabbedView();

		DeleteDataDialog deleteDataDialog = new DeleteDataDialog(entity, size, tabbedView);
		deleteDataDialog.setVisible(true);
	}
}
