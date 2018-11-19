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
		for (Probability probability: variable_dependencies) 
			answer += probability.toString();
		return answer;
	}
	
	
}
