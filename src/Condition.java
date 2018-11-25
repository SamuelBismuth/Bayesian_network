import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sam
 * This class represents the object condition.
 * A condition is composed of a probability and dependencies.
 */
public class Condition {

	private Probability variable_probabilty; // variable probability.
	private List<Probability> variable_dependencies; // HashMap: parent value -> parent variable name.
	private List<Probability> join_probability; // All the join probabilities.

	/**
	 * Constructor.
	 * @param variable_probabilty
	 * @param variable_dependencies
	 */
	public Condition(Probability variable_probabilty, List<Probability> variable_dependencies) {
		this.variable_probabilty = variable_probabilty;
		this.variable_dependencies = variable_dependencies;
		this.join_probability = Util_list.concatenate_item_with_list(variable_dependencies, variable_probabilty);
	}

	/**
	 * Constructor.
	 * @param join_probability
	 */
	public Condition(List<Probability> join_probability) {
		this.join_probability = join_probability;
	}

	/**
	 * This method return true if the value if the same value of the main probability.
	 * @param value
	 * @return true if the value is the same value of the condition.
	 */
	public boolean is_condition_by_value(String value) {
		if (this.getVariable_probabilty().getVariable_value().equals(value))
			return true;
		return false;
	}

	/**
	 * Check if two condition are equal.
	 * @param condition
	 * @return true if equals, else false.
	 */
	protected boolean is_equal(Condition condition) {
		return (this.getVariable_probabilty().is_equal(condition.getVariable_probabilty())) &&
				this.is_dependencies_equal(condition);
	}

	/**
	 * This method check if the condition is the inverse of the second condition.
	 * @param condition
	 * @return true if inverse else false.
	 */
	protected boolean is_cached_value(Condition condition) {
		return (this.getVariable_probabilty().getVariable_name() == 
				(condition.getVariable_probabilty().getVariable_name())) &&
				this.is_dependencies_equal(condition);
	}

	/**
	 * This function check if the dependencies are equal.
	 * @param condition
	 * @return true if dependencies are equal else false.
	 */
	protected boolean is_dependencies_equal(Condition condition) {
		boolean step = true;
		for (Probability prob1 : this.getVariable_dependencies()) {
			if(step == false)
				return false;
			step = false;
			for (Probability prob2 : condition.getVariable_dependencies()) 
				if (prob1.is_equal(prob2))
					step = true;
		}
		return step;
	}

	/**
	 * This function gives the list off all the variables of the Condition.
	 * @return
	 */
	protected List<Character> get_variable() {
		List<Character> variables = new ArrayList<>();
		for (Probability probability : this.getJoin_probability())
			variables.add(probability.getVariable_name());
		return variables;
	}

	/**
	 * This function return all the matched variable between the two Condition.
	 * @param cond2
	 * @return List<Character>.
	 */
	public List<Character> match_variable(Condition cond2) {
		return Util_list.get_match_from_two_list(this.get_variable(), cond2.get_variable());
	}

	/**
	 * Given another condition and a variable, this function check if the value for the given variable
	 * are the same on this Condition object and the condition.
	 * @param condition2
	 * @param variable
	 * @return true if the value for the variable are common, else false.
	 */
	public boolean in_common(Condition condition, Variable variable) {
		if(this.get_variable().stream().map(item->item.charValue()).
				collect(Collectors.toList()).contains(variable.getName()) &&
				condition.get_variable().stream().map(item->item.charValue()).
				collect(Collectors.toList()).contains(variable.getName()))
			return true;
		return false;
	}
	
	/**
	 * Get variable probability.
	 * @return the variable probability.
	 */
	public Probability getVariable_probabilty() {
		return variable_probabilty;
	}

	/**
	 * Get the variables dependencies.
	 * @return variable dependencies.
	 */
	protected List<Probability> getVariable_dependencies() {
		return variable_dependencies;
	}

	/**
	 * Get the join proabilities.
	 * @return
	 */
	protected List<Probability> getJoin_probability() {
		return join_probability;
	}
	
	@Override
	public String toString() {
		if (variable_probabilty == null)
			return join_probability.toString();
		if(variable_dependencies == null) 
			return variable_probabilty.toString();
		String answer = variable_probabilty.toString() + "|";
		for(Probability probability: variable_dependencies) 
			answer += probability.toString();
		return answer;
	}
}
