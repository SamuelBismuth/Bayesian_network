import java.util.Set;

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
