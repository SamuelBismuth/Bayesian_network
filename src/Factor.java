import java.util.Set;

/**
 * @author sam
 * This class represents the object {@link Factor}.
 */
public class Factor {
	
	private Set<Variable> variables;  // The variables of the factor.
	private Set<Probability> probability;  // The table of the factor.
	
	/**
	 * Constructor fot the object {@link Factor}.
	 * @param variables
	 * @param probability
	 */
	public Factor(Set<Variable> variables, Set<Probability> probability) {
		this.variables = variables;
		this.probability = probability;
	}

	/**
	 * @return the variables
	 */
	public Set<Variable> getVariables() {
		return variables;
	}

	/**
	 * @return the probability
	 */
	public Set<Probability> getProbability() {
		return probability;
	}
	
}
