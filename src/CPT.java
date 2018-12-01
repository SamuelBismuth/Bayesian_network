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
		Set<Event> other = this.getTable().iterator().next().getOtherEvents(variableName);
		if (other == null)
			TxtToObjects.createProbability( 
					"=" + 
					values.getHidden().getValue() + "," +
					Double.toString(this.getSum()));
		else {
			for (Event event : this.getTable().iterator().next().getOtherEvents(variableName))
				prepareString += event.getValue().getValue() + ",";
			TxtToObjects.createProbability(prepareString + 
					"=" + 
					values.getHidden().getValue() + "," +
					Double.toString(this.getSum()));
		}
	}

	protected double getSum() {
		double answer = 0.0;
		for (Probability probability : this.getTable())
			answer += probability.getProbability();
		return answer;
	}

	/**
	 * @return the table
	 */
	public Set<Probability> getTable() {
		return table;
	}

}
