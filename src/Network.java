import java.util.ArrayList;
import java.util.List;

/**
 * @author sam
 * This class represents the object Network.
 */
public class Network {

	private List<Variable> variables;
	private List<Query> queries;
	
	/**
	 * Constructor.
	 * @param variables
	 * @param queries
	 */
	public Network(List<Variable> variables, List<Query> queries) {
		this.variables = variables;
		this.queries = queries;
	}
	
	/**
	 * This method return all the hidden variable.
	 * @param probabilities
	 * @return a list of the hidden Variable.
	 */
	protected List<Variable> not_on_the_query(List<Probability> probabilities) {
		List<Variable> variables_not_on_the_query = new ArrayList<>(variables);
		for (Probability probability : probabilities) 
			variables_not_on_the_query.remove(this.find_variable_by_name(probability.getVariable_name()));
		return variables_not_on_the_query;
	}
	
	/**
	 * This function return the variable given the variable name.
	 * @param variable_name
	 * @return the Variable.
	 */
	protected Variable find_variable_by_name(char variable_name) {
		for(Variable variable : variables) {
			if(variable.getName() == variable_name)
				return variable;
		}
		return null;
	}
	
	/**
	 * This function calculate the probability.
	 * @param probabilities
	 * @return the probability in double.
	 */
	protected double calculate_probability(List<Probability> probabilities) {
		double answer = 1.0;
		Algorithms.mulitiplication_counter--;
		for(Probability probability : probabilities) {
			Algorithms.mulitiplication_counter++;
			Variable variable = find_variable_by_name(probability.getVariable_name());
			answer *= variable.probability(probability.getVariable_value(), probabilities);
		}
		return answer;
	}

	/**
	 * Get the variables.
	 * @return variables.
	 */
	protected List<Variable> getVariables() {
		return variables;
	}

	/**
	 * Get the queries.
	 * @return queries.
	 */
	protected List<Query> getQueries() {
		return queries;
	}

	@Override
	public String toString() {
		String answer = "Network: \n";
		for(Variable variable : variables)
			answer += variable.toString() + "\n";
		for (Query query : queries) 
			answer += query.toString();
		return answer;
	}
	
}
