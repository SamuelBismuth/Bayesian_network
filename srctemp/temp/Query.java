package temp;

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
	 * This method return the main variable of the query.
	 * @param network
	 * @return Variable.
	 */
	protected Variable get_main_variable(Network network) {
		return network.find_variable_by_name(
				this.getCondition().
				getVariable_probabilty().
				getVariable_name());
	}
	
	/**
	 * Get all the variables of the query.
	 * @return List<Variable>.
	 */
	protected List<Variable> get_all_variable(Network network) {
		return network.find_variables_by_names(this.getCondition().get_variable());
	}
	
	/**
	 * Get all the probabilitoes of the query.
	 * @return List<Probability>.
	 */
	protected List<Probability> get_all_probability() {
		return this.getCondition().getJoin_probability();
	}

	/**
	 * Get the Condition.
	 * @return the condition.
	 */
	protected Condition getCondition() {
		return condition;
	}
	
	/**
	 * Get the algorithm.
	 * @return the algorithm.
	 */
	protected char getAlgorithm() {
		return algorithm;
	}

	@Override
	public String toString() {
		return "Query: " + 
				condition.toString() + ", algorithm=" + algorithm + "\n";
	}

}
