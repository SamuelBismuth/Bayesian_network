import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sam
 * This class represents the object {@link Variables}.
 */
public class Variables {

	private Set<Variable> variables;

	/**
	 * Constructor for {@link Variables}.
	 * @param variables
	 */
	public Variables(Set<Variable> variables) {
		this.variables = variables;
	}

	protected Set<Variable> getOther(Events events) {
		return this.getVariables().
				stream().
				filter(variable ->variable.isInclude(events)).
				collect(Collectors.toSet());
	}

	/**
	 * This function return the variable given the variable name.
	 * @param variable_name
	 * @return the Variable.
	 */
	protected Variable findVariableByName(String variableName) {
		for(Variable variable : this.getVariables()) {
			if(variable.getName().equals(variableName))
				return variable;
		}
		return null;
	}

	protected double calculateProbability(List<Event> events) {
		double answer = 1.0;
		Algorithms.mulitiplicationCounter--;
		for(Event event : events) {
			Algorithms.mulitiplicationCounter++;
			Variable variable = this.findVariableByName(event.getVariable());
			if(variable.getParents() == null) 
				answer *= variable.getCpts().getProbabilityAloneSoldier(event.getValue());
			else {
				//System.out.println(variable.getCpts().getProbability(event.getValue(), 
					//	variable.matchParent(events)));
				answer *= variable.getCpts().getProbability(event.getValue(), 
						variable.matchParent(events));
			}
		}
		return answer;
	}

	/**
	 * @return the variables
	 */
	public Set<Variable> getVariables() {
		return variables;
	}

	public String toString() {
		String ans = "";
		for (Variable variable : this.getVariables())
			ans += variable.toString();
		return ans;
	}

}
