import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sam
 * This class represents the object condition.
 * A condition is composed of a probability and dependencies.
 */
public class Condition {

	private Probability variable_probabilty; // variable probability.
	private List<Probability> variable_dependencies; // HashMap: parent value -> parent variable name.

	/**
	 * Constructor.
	 * @param variable_probabilty
	 * @param variable_dependencies
	 */
	public Condition(Probability variable_probabilty, List<Probability> variable_dependencies) {
		this.variable_probabilty = variable_probabilty;
		this.variable_dependencies = variable_dependencies;
	}

	/**
	 * @param value
	 * @return true if the value is the same value of the condition.
	 */
	public boolean is_condition_by_value(String value) {
		if (this.getVariable_probabilty().getVariable_value().equals(value))
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
			for (Probability prob2 : condition.getVariable_dependencies()) {
				if (prob1.is_equal(prob2))
					step = true;
			}
		}
		return step;
	}

	@Override
	public String toString() {
		if(variable_dependencies == null) 
			return variable_probabilty.toString();
		String answer = variable_probabilty.toString() + "|";
		for(Probability probability: variable_dependencies) 
			answer += probability.toString();
		return answer;
	}

	protected List<Character> get_variable() {
		if (this.getVariable_dependencies() == null) {
			List<Character> ans = new ArrayList<>();
			ans.add(this.getVariable_probabilty().getVariable_name());
			return ans;
		}
		List<Character> variables = new ArrayList<>();
		variables.add(this.getVariable_probabilty().getVariable_name());
		for (Probability probability : this.getVariable_dependencies())
			variables.add(probability.getVariable_name());
		return variables;
	}
	
	public List<Character> match_variable(Condition cond2) {
		return this.get_variable().stream().
				filter(cond2.get_variable()::contains).
				collect(Collectors.toList());
	}
	
	protected List<Probability> get_all() {
		return Stream.concat(
				this.getVariable_dependencies().stream(),
				Collections.singletonList(
						this.getVariable_probabilty()).stream()).
				collect(Collectors.toList());
	}
}
