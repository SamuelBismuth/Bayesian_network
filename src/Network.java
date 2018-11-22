import java.util.ArrayList;
import java.util.List;

/**
 * @author sam
 * This class represents the object Network.
 */
public class Network {

	private List<Variable> variables;
	private List<Query> queries;
	private List<Factor> factors;

	/**
	 * Constructor.
	 * @param variables
	 * @param queries
	 */
	public Network(List<Variable> variables, List<Query> queries, List<Factor> factors) {
		this.variables = variables;
		this.queries = queries;
		this.factors = factors;
	}

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
	
	protected List<Variable> find_variables_by_names(List<Character> variables_names) {
		List<Variable> variables_by_names = new ArrayList<>();
		for(Character variable : variables_names) 
			variables_by_names.add(find_variable_by_name(variable));
		return variables_by_names;
	}

	public List<Factor> getFactors() {
		return factors;
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
	 * This method return all the dependencies variable given a list of variables.
	 * @param variables
	 * @return
	 */
	protected List<Variable> get_child(Variable variable) {
		List<Variable> variable_dependent = new ArrayList<>();
		for(Variable child : this.getVariables())
			if(child.getParents() != null)
				for (Character parent : child.getParents())
					if (parent == variable.getName())
						variable_dependent.add(child);
		return variable_dependent;
	}
	
	/**
	 * This function return the searched variable of the query.
	 * @param query
	 * @return Variable.
	 */
	protected Variable get_searched_query(Query query) {
		return find_variable_by_name(
				query.getCondition().
				getVariable_probabilty().
				getVariable_name());
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

	protected void setFactors(List<Factor> factors) {
		this.factors = factors;
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

	public void joinAll(List<Factor> factors) {
		Factor current = factors.get(0);
		for(int i = 1; i < factors.size(); i++) {
			current = join(current, factors.get(i));
		}
	}

	private Factor join(Factor factor, Factor factor2) {
		return new Factor(factor.variable_union(factor2), factor.c_p_union(factor2), this);
	}

}
