package src.listeners.desktop;

import src.observer.ObserverNotification;
import src.view.DesktopView;
import src.view.MainView;
import src.view.table.TabbedView;
import src.view.tree.MainTree;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.TreePath;

public class ShowTableListener implements ChangeListener {

	private TabbedView tabbedView;

	public ShowTableListener(TabbedView tabbedView) {
		this.tabbedView = tabbedView;
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		TabbedView selected = (TabbedView) event.getSource();
		int selectedIndex = selected.getSelectedIndex();
		String title = selected.getTitleAt(selectedIndex);
		TreePath treePath = selected.getSelectedEntity().getPath();
//		System.out.println("ShowTableListener: " + treePath);

		MainView.getInstance()
				.getTreeView()
				.getRoot()
				.getObserverList()
				.notifyObservers(ObserverNotification.TREE_SELECT, treePath);

		for (int i = 0; i < tabbedView.getEntities().size(); i++) {
			if (tabbedView.getEntities().get(i).getName().equals(title)) {
				// TODO: Set active panel, so we can get it from toolbar
				TabbedView.activePanel = tabbedView.getTabelePanelAt(i);
			}
		}

		DesktopView desktopView = MainView.getInstance().getDesktopView();

		desktopView.getMainPanel().removeAll();
		desktopView.getMainPanel().validate();
		desktopView.getMainPanel().repaint();

		desktopView.getIndexSplit().setDividerLocation(0);
		desktopView.getIndexSplit().setLeftComponent(desktopView.getIndexPanel());

		desktopView.getSplitPane().revalidate();
		desktopView.getSplitPane().repaint();
	}
}
