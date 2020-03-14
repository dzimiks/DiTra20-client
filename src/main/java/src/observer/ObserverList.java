package src.observer;

import java.util.ArrayList;
import java.util.List;

public class ObserverList {

	private List<MainObserver> observers;

	public ObserverList() {
		this.observers = new ArrayList<>();
	}

	/**
	 * Sends message to all registered observers.
	 *
	 * @param notification Notification about sent messages.
	 * @param object       Object that we are sending with the message.
	 */
	public void notifyObservers(ObserverNotification notification, Object object) {
		for (MainObserver observer : observers) {
			observer.update(notification, object);
		}
	}

	/**
	 * Registers new observer.
	 *
	 * @param observer New observer that will be added.
	 */
	public void addObserver(MainObserver observer) {
		this.observers.add(observer);
	}
}
