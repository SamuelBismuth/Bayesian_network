import java.util.List;

public class Condition {

	private Probability variable_probabilty; // variable probability.
	private List<Probability> variable_dependencies; // HashMap: parent value -> parent variable name.

	public Condition(Probability variable_probabilty, List<Probability> variable_dependencies) {
		this.variable_probabilty = variable_probabilty;
		this.variable_dependencies = variable_dependencies;
	}

	public boolean is_condition_by_value(String value) {
		if (this.getVariable_probabilty().getVariable_value().equals(value))
			return true;
		return false;
	}

	public Probability getVariable_probabilty() {
		return variable_probabilty;
	}


	public List<Probability> getVariable_dependencies() {
		return variable_dependencies;
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
	
	public boolean is_equal(Condition condition) {
	    return (this.getVariable_probabilty().is_equal(condition.getVariable_probabilty())) &&
	    		this.is_dependencies_equal(condition);
	}
	
	public boolean is_cached_value(Condition condition) {
		 return (this.getVariable_probabilty().getVariable_name() == 
				 (condition.getVariable_probabilty().getVariable_name())) &&
		    		this.is_dependencies_equal(condition);
	}

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
}
