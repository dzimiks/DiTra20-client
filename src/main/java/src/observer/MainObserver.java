package src.observer;

public interface MainObserver {

	/**
	 * Sends message to all registered observers.
	 *
	 * @param notification Notification about sent messages.
	 * @param object       Object that we are sending with the message.
	 */
	public void update(ObserverNotification notification, Object object);
}
