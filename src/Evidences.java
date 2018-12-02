/**
 * @author sam
 * This class represents the object {@link Evidences}.
 */
public class Evidences {

	private Events events;  // The events of the evidences.

	/**
	 * Constructor for the {@link Evidences}.
	 * @param events
	 */
	public Evidences(Events events) {
		this.events = events;
	}

	/**
	 * @return the events
	 */
	public Events getEvents() {
		return events;
	}
	
	public String toString() {
		return this.getEvents().toString();
	}
	
}
