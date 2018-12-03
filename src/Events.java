import java.util.ArrayList;
import java.util.List;
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

	/**
	 * TODO: Implement it.
	 * @param listEvent
	 * @return
	 */
	public boolean isContained(List<Event> listEvent) {
		System.out.println("this" + this.toString());
		System.out.println(listEvent.toString());
		return false;
	}
	
}
