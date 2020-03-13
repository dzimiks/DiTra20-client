package src.controllers;

import src.listeners.desktop.ShowTableListener;
import src.view.table.TabbedView;

public class DesktopController {

	private TabbedView tabbedView;

	public DesktopController(TabbedView tabbedView) {
		this.tabbedView = tabbedView;
		this.tabbedView.addChangeListener(new ShowTableListener(tabbedView));
	}
}
