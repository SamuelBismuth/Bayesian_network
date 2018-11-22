import java.util.ArrayList;
import java.util.List;

public class Network {

	private List<Variable> variables;
	private List<Query> queries;
	
	public Network(List<Variable> variables, List<Query> queries) {
		this.variables = variables;
		this.queries = queries;
	}
	
	public List<Variable> not_on_the_query(List<Probability> probabilities) {
		List<Variable> variables_not_on_the_query = new ArrayList<>(variables);
		for (Probability probability : probabilities) 
			variables_not_on_the_query.remove(this.find_variable_by_name(probability.getVariable_name()));
		return variables_not_on_the_query;
	}
	
	public Variable find_variable_by_name(char variable_name) {
		for(Variable variable : variables) {
			if(variable.getName() == variable_name)
				return variable;
		}
		return null;
	}
	
	public double calculate_probability(List<Probability> probabilities) {
		double answer = 1.0;
		for(Probability probability : probabilities) {
			Algorithms.mulitiplication_counter++;
			Variable variable = find_variable_by_name(probability.getVariable_name());
			answer *= variable.probability(probability.getVariable_value(), probabilities);
		}
		return answer;
	}

	public List<Variable> getVariables() {
		return variables;
	}

	public List<Query> getQueries() {
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
