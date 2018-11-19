import java.util.List;

public class Network {

	private List<Variable> variables;
	private List<Query> queries;
	
	public Network(List<Variable> variables, List<Query> queries) {
		this.variables = variables;
		this.queries = queries;
	}
	
	public Variable find_variable_by_name(char variable_name) {
		for(Variable variable : variables) {
			if(variable.getName() == variable_name)
				return variable;
		}
		return null;
	}
	
	// TODO: Must for all the probabilities, see if there is a match between all the condition of the network.
	public double find_probability_by_condition(List<Probability> probabilities) {
		return 0.0;
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
