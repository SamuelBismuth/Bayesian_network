import java.util.ArrayList;
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
	
	protected Event getDynamicEvent() {
		return new ArrayList<>(this.getEvents()).get(this.getEvents().size() - 1);
	}
	
	public double getProbability(Value value) {
		return 0;
	}
	/**
	 * @return the events
	 */
	public Set<Event> getEvents() {
		return events;
	}
	
	public String toString() {
		String answer = "";
		for(Event event : this.getEvents())
			answer += event.toString() + ",";
		return answer;
	}
	
}
