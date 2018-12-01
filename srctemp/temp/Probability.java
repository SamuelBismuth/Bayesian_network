package temp;

import java.util.List;

/**
 * @author sam
 * This class represents the object Probability.
 */
public class Probability {

	private char variable_name;
	private String variable_value;

	/**
	 * Constructor.
	 * @param variable_name
	 * @param variable_value
	 */
	public Probability(char variable_name, String variable_value) {
		this.variable_name = variable_name;
		this.variable_value = variable_value;
	}

	/**
	 * This method check if a probability is include into a list of probability.
	 * @param probabilities
	 * @return true if include else false.
	 */
	protected boolean is_include(List<Probability> probabilities) {
		for (Probability probability : probabilities)
			if (!this.is_equal(probability))
				if (!this.getVariable_value().equals(probability.getVariable_value()))
						return false;
		return true;
	}
	
	/**
	 * This method check if two probability are equal.
	 * @param probability
	 * @return true if equals else false.
	 */
	protected boolean is_equal(Probability probability) {
		if (this.getVariable_name() == probability.getVariable_name() && 
				this.getVariable_value().equals(probability.getVariable_value()))
			return true;
		return false;
	}

	/**
	 * Get variable name.
	 * @return variable name.
	 */
	public char getVariable_name() {
		return variable_name;
	}

	/**
	 * Get variable value.
	 * @return the variable value.
	 */
	public String getVariable_value() {
		return variable_value;
	}

	@Override
	public String toString() {
		return variable_name + "=" + variable_value;
	}

}
