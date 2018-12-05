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
	
	/**
	 * This method tests if the list of {@link Event} that we pass as a parameter is include 
	 * into this set of {@link Event}.
	 * @param listEvent
	 * @return true if is included, else false
	 */
	protected boolean isIncluded(List<Event> listEvent) {
		return listEvent.stream().
				allMatch(thisEvent -> 
				{ for (Event evidenceEvent : this.getEvents())
					if(evidenceEvent.getVariable().equals(thisEvent.getVariable())
							&& !evidenceEvent.getValue().getValue().
							equals(thisEvent.getValue().getValue())) 
						return false;
				return true;
				});
	}
	
	/**
	 * This method return the dynamic event of the set.
	 * @return {@link Event}
	 */
	protected Event getDynamicEvent() {
		return new ArrayList<>(this.getEvents()).get(this.getEvents().size() - 1);
	}
	
	/*##################Getter##################*/

	/**
	 * @return the events
	 */
	public Set<Event> getEvents() {
		return events;
	}
	
}
