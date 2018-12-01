import java.util.Set;

/**
 * @author sam
 * This class represents the object {@link Events}.
 */
public class Events {

	private Set<Event> events;  // A set of event.

	/**
	 * Constructor for {@link Events}.
	 * @param events
	 */
	public Events(Set<Event> events) {
		this.events = events;
	}

	/**
	 * @return the events
	 */
	public Set<Event> getEvents() {
		return events;
	}
	
}
