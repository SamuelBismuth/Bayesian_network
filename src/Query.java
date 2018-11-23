import java.util.List;

/**
 * @author sam
 * This class represents the object Query.
 */
public class Query {
	
	private Condition condition;
	private char algorithm;
	
	/**
	 * Constructor.
	 * @param condition
	 * @param algorithm
	 */
	public Query(Condition condition, char algorithm) {
		this.condition = condition;
		this.algorithm = algorithm;
	}

	/**
	 * Get the Condition.
	 * @return the condition.
	 */
	public Condition getCondition() {
		return condition;
	}
	
	protected Variable get_main_variable(Network network) {
		return network.find_variable_by_name(
				this.getCondition().
				getVariable_probabilty().
				getVariable_name());
	}
	
	protected List<Variable> get_all_variable(Network network) {
		return network.find_variables_by_names(this.getCondition().get_variable());
	}
	
	protected List<Probability> get_all_probability() {
		return this.getCondition().get_all();
	}
	
	/**
	 * Get the algorithm.
	 * @return the algorithm.
	 */
	public char getAlgorithm() {
		return algorithm;
	}

	@Override
	public String toString() {
		return "Query: " + 
				condition.toString() + ", algorithm=" + algorithm + "\n";
	}

}
