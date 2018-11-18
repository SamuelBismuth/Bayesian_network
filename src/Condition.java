import java.util.List;

public class Condition {
	
	private Probability variable_probabilty; // variable probability.
	private List<Probability> variable_dependencies; // HashMap: parent value -> parent variable name.
	
	public Condition(Probability variable_probabilty, List<Probability> variable_dependencies) {
		this.variable_probabilty = variable_probabilty;
		this.variable_dependencies = variable_dependencies;
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
