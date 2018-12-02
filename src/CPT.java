import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sam
 * This class represents the object {@link CPT}.
 */
public class CPT {

	private Set<Probability> table;

	/**
	 * Constructor for the {@link CPT}.
	 * @param table
	 */
	public CPT(Set<Probability> table) {
		this.table = table;
	}

	/**
	 * It's possible to assume values.get(values.size()) as the hidden value.
	 * @param values
	 */
	protected void update(Values values, String variableName) {
		String prepareString = "";
		if(this.getTable().iterator().next().getEvents().getEvents().size() > 1) 
			for (Event event : this.getStable())
				prepareString += event.getValue().getValue() + ",";
		this.getTable().add(TxtToObjects.createProbability(prepareString + 
				values.getHidden().getValue() + "," +
				Double.toString(1 - this.getSum())));
	}

	protected double getSum() {
		double answer = 0.0;
		for (Probability probability : this.getTable())
			answer += probability.getProbability();
		return answer;
	}

	protected Set<Event> getStable() {
		return new LinkedHashSet<>(new ArrayList<>
		(this.getTable().iterator().next().getEvents().getEvents())
		.subList(0, this.getTable().iterator().next().getEvents().getEvents().size() - 1));
	}

	protected double getProbability(Value value) {
		for (Probability probability : this.getTable())
			if(probability.getEvents().getDynamicEvent().getValue().getValue().
					equals(value.getValue())) 
				return probability.getProbability();
		return 0.0;
	}

	public boolean matchStaticEvent(List<Event> parentsValues) {
		for (Event event : this.getStable()) {
			if(!parentsValues.stream().
					anyMatch(eventParent -> 
					eventParent.getValue().getValue().
					equals(event.getValue().getValue()) &&
					eventParent.getVariable().
					equals(event.getVariable())))
				return false;;
		}
		return true;
	}

	/**
	 * @return the table
	 */
	public Set<Probability> getTable() {
		return table;
	}

	public String toString() {
		String answer = "";
		for (Probability probability : this.getTable())
			answer += probability.toString() + "\n";
		return answer;
	}

}
