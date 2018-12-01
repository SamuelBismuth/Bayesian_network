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
	 * @return the variables
	 */
	public Set<Variable> getVariables() {
		return variables;
	}
	
}
