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
			if(variable.get_name() == variable_name)
				return variable;
		}
		return null;
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
