import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sam
 * This class represents the object {@link Probability}.
 */
public class Probability {

	private Events events;  // All the events for the probability.
	private double probability;  // The probability (number).
	
	/**
	 * Constructor for {@link Probability}.
	 * @param events
	 * @param probability
	 */
	public Probability(Events events, double probability) {
		this.events = events;
		this.probability = probability;
	}

	protected Set<Event> getOtherEvents(String variableName) {
		if (this.getEvents().getEvents().size() == 1)
			return null;
		return events.
				getEvents().
				stream().
				filter(item -> item.getVariable().
						equals(variableName)).
						collect(Collectors.toSet());
	}
	
	/**
	 * @return the events
	 */
	public Events getEvents() {
		return events;
	}

	/**
	 * @return the probability
	 */
	public double getProbability() {
		return probability;
	}
	
	public String toString() {
		return this.getEvents().toString() + "=" + this.getProbability();
	}
	
}
