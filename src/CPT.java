import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sam
 * This class represents the object {@link CPT}.
 */
public class CPT {

	private Set<Probability> table;  // The table of our CPT.

	/**
	 * Constructor for the {@link CPT}.
	 * @param table
	 */
	public CPT(Set<Probability> table) {
		this.table = table;
	}

	/**
	 * It's possible to assume values.get(values.size()) as the hidden value.
	 * This function for update the CPT. That's mean that a probability is added to the table.
	 * I.e, if the table was : P(B=true) = 0.9, now the table is P(B=true)=0.9 and P(B=false)=0.1.
	 * @param values
	 * @param variableName
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

	/**
	 * This function returns the sum of all the probability of the table.
	 * After the update, the return of this function must be 1.
	 * @return the sum
	 */
	protected double getSum() {
		double answer = 0.0;
		for (Probability probability : this.getTable())
			answer += probability.getProbability();
		return answer;
	}

	/**
	 * This method returns all the stable {@link Event}.
	 * The meaning of stable event is the event of the table that doesn't change their {@link Value}.
	 * @return a set of {@link Event}
	 */
	protected Set<Event> getStable() {
		return new LinkedHashSet<>(new ArrayList<>
		(this.getTable().iterator().next().getEvents().getEvents())
		.subList(0, this.getTable().iterator().next().getEvents().getEvents().size() - 1));
	}

	/**
	 * This method return the probability of the table for a given {@link Value}.
	 * Attention, obviously since the value of the stable event doesn't change, here 
	 * the {@link Value} required is the value of the dynamic event.
	 * This function must never return 0.0, only one case of error. This make the mistake easily 
	 * recognized.
	 * @param value
	 * @return the probability
	 */
	protected double getProbability(Value value) {
		for (Probability probability : this.getTable())
			if(probability.getEvents().getDynamicEvent().getValue().getValue().
					equals(value.getValue())) 
				return probability.getProbability();
		return 0.0;
	}

	/**
	 * This function test if the the given list of {@link Event} which include {@link Value}
	 * of the parents are the same that the static/stable {@link Value}.
	 * Attention, there is a match if and only if all the {@link Value} and all the variable name
	 * of the parents match wit the static event of the table.
	 * @param parentsValues
	 * @return true if there is a match, else false.
	 */
	protected boolean matchStaticEvent(List<Event> parentsValues) {
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

	/*##################Getter##################*/

	/**
	 * @return the table
	 */
	public Set<Probability> getTable() {
		return table;
	}

}
