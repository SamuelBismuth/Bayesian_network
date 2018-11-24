import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	protected List<Variable> find_variables_by_names(List<Character> variables_names) {
		List<Variable> variables_by_names = new ArrayList<>();
		for(Character variable : variables_names) 
			variables_by_names.add(find_variable_by_name(variable));
		return variables_by_names;
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
	 * TODO: BFS???!!!
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

	@Override
	public String toString() {
		String answer = "Network: \n";
		for(Variable variable : variables)
			answer += variable.toString() + "\n";
		for (Query query : queries) 
			answer += query.toString();
		return answer;
	}

	/**
	 * Maybe no need child of the main variable? 
	 * @param get_factors_variable
	 * @return
	 */
	public Factors create_factors(
			List<Variable> get_factors_variable,
			Variable query,
			List<Probability> dependencies) {
		Set<Variable> set_variable = new HashSet<>(get_factors_variable);
		for(Variable variable : get_factors_variable) 
			set_variable.addAll(this.get_child(variable));
		List<Factor> factors = new ArrayList<>();
		for(Variable variable : set_variable) 
			factors.add(create_factor(variable, get_factors_variable, dependencies));
		get_factors_variable.remove(query);
		return new Factors(factors, get_factors_variable, query);
	}

	private Factor create_factor(Variable variable, 
			List<Variable> get_factors_variable, 
			List<Probability> dependencies) {
		List<Cond_prob> c_p = new ArrayList<>();
		List<Variable> factor_variables = this.get_side_variable(variable)
				.stream().filter(get_factors_variable::contains).collect(Collectors.toList());
		boolean flag = true;
		for (Cond_prob cp : variable.getC_p()) {
			for(Condition condition : cp.getProbability().keySet()) {
				for (Probability probability : condition.get_all()) {
					List<Probability> list_prob = dependencies.stream().filter(item->probability.getVariable_name()
							== item.getVariable_name()).collect(Collectors.toList());
					if(list_prob != null) {
						for(Probability prob2 : list_prob) {
							if (!prob2.getVariable_value().equals(probability.getVariable_value())) {
								flag = false;
							}
						}
					}
				}
				if(flag) {
					HashMap<Condition, Double> probability = new HashMap<>();
					probability.put(condition, cp.getProbability().get(condition));
					c_p.add(new Cond_prob(probability));
				}
				flag = true;
			}
		}
		return new Factor(factor_variables, c_p);
	}

	private List<Variable> get_side_variable(Variable variable) {
		if(variable.getParents() == null)
			return Collections.singletonList(variable);
		return  Stream.concat(
				this.find_variables_by_names(variable.getParents()).stream(), 
				Collections.singletonList(
						variable).stream()).
				collect(Collectors.toList());
	}

}
