import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sam
 * This class represents the Object Factors.
 */
public class Factor {

	private List<Variable> variables;
	private List<Cond_prob> c_p;
	private Network network;

	public Factor(List<Variable> variables, List<Cond_prob> c_p, Network network) {
		this.variables = variables;
		this.c_p = c_p;
		this.network = network;
	}

	public List<Variable> getVariables() {
		return variables;
	}

	public List<Cond_prob> getC_p() {
		return c_p;
	}

	protected List<Variable> variable_union(Factor factor) {
		return Stream.concat(
				factor.getVariables().stream(), 
				this.getVariables().stream()).
				collect(Collectors.toList());
	}

	@Override
	public String toString() {
		String answer = "";
		for (Variable variable : this.getVariables()) 
			answer += variable.getName();
		answer += "\n";
		for (Cond_prob cp : this.getC_p())
			answer += cp.toString();
		return answer;
	}

	protected boolean contains(Variable variable) {
		return this.getVariables().contains(variable);
	}

	protected List<Cond_prob> c_p_union(Factor factor) {
		return merge_table(this.minimum_variable(factor), this.maximum_variable(factor));
	}

	private List<Cond_prob> merge_table(Factor minimum_factor, Factor maximum_factor) {
		System.out.println(minimum_factor);
		System.out.println(maximum_factor);
		List<Variable> variables_match = match_variable(
						(Condition) maximum_factor.getC_p().get(0).getProbability().keySet().toArray()[0], 
						(Condition) minimum_factor.getC_p().get(0).getProbability().keySet().toArray()[0]);
		for(Cond_prob c_p : maximum_factor.getC_p()) {
			
		}
		return null;
	}

	private List<Variable> match_variable(Condition cond1, Condition cond2) {
		return network.find_variables_by_names(cond1.match_variable(cond2));
	}

	private Factor maximum_variable(Factor factor) {
		if(factor.getVariables().size() == this.getVariables().size())
			return this;
		if(factor.getVariables().size() > this.getVariables().size())
			return factor;
		return this;
	}

	private Factor minimum_variable(Factor factor) {
		if(factor.getVariables().size() == this.getVariables().size())
			return factor;
		if(factor.getVariables().size() < this.getVariables().size())
			return factor;
		return this;
	}
}
