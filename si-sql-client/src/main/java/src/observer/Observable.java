package src.observer;

public interface Observable {

	/**
	 * Registers new observer.
	 *
	 * @param observer New observer that will be added.
	 */
	public void addObserver(MainObserver observer);
}
