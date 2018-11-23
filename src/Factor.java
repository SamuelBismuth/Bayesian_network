import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sam
 * This class represents the Object Factors.
 */
public class Factor {

	// Here we need to chheck all the matches.
	private List<Variable> variables;
	private List<Cond_prob> c_p;

	public Factor(List<Variable> variables, List<Cond_prob> c_p) {
		this.variables = variables;
		this.c_p = c_p;
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

	protected Factor maximum_variable(Factor factor) {
		if(factor.getVariables().size() == this.getVariables().size())
			return this;
		if(factor.getVariables().size() > this.getVariables().size())
			return factor;
		return this;
	}

	protected Factor minimum_variable(Factor factor) {
		if(factor.getVariables().size() == this.getVariables().size())
			return factor;
		if(factor.getVariables().size() < this.getVariables().size())
			return factor;
		return this;
	}

}

class Factor_comparator implements Comparator<Factor> {

	@Override
	public int compare(Factor o1, Factor o2) {
		return o1.getC_p().size() < o2.getC_p().size() ? 1 : 
			o1.getC_p().size() == o2.getC_p().size() ? -0 : -1;
	}

}
