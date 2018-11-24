import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sam
 * This class represents the Object Factors.
 */
public class Factor {

	// Here we need to check all the matches.
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

	public Factor join(Factor fac2) {
		List<Variable> variables = Stream.concat(this.getVariables().stream(), 
				fac2.getVariables().stream()).distinct().collect(Collectors.toList());
		// Cartesian product.
		List<List<Probability>> new_factor = 
				Algorithms.cartesian_product(Algorithms.create_list_list(variables));
		List<Cond_prob> cp = new ArrayList<>();
		for (List<Probability> line : new_factor) {
			double one = fac2.get_match(line);
			double two = this.get_match(line);
			HashMap<Condition, Double> probability = new HashMap<>();
			probability.put(new Condition(line), one * two);
			cp.add(new Cond_prob(probability));
		}
		return new Factor(variables, cp);
	}

	private double get_match(List<Probability> line) {
		for(Cond_prob cp : this.getC_p()) 
			for(Condition cond : cp.getProbability().keySet()) 
				if (Algorithms.if_exist_so_equal(cond, line)) 
					return cp.getProbability().get(cond);
		return 0;
	}

	public void normalize() {
		double lambda = 0.0;
		for(Cond_prob cp : this.getC_p()) 
			lambda += cp.get_sum();
		for(Cond_prob cp : this.getC_p()) {
			for(Condition cond : cp.getProbability().keySet()) {
				cp.getProbability().put(cond, cp.getProbability().get(cond) / lambda);
			}
		}
				
	}

	public double get_final_double(String variable_value) {
		for (Cond_prob cp : this.getC_p()) {
			for (Condition cond : cp.getProbability().keySet())
				for (Probability prob : cond.get_all())
					if(prob.getVariable_value().equals(variable_value))
						return cp.getProbability().get(cond);
		}
		return 0.0;
	}
}

class Factor_comparator implements Comparator<Factor> {

	@Override
	public int compare(Factor o1, Factor o2) {
		return o1.getC_p().size() < o2.getC_p().size() ? 1 : 
			o1.getC_p().size() == o2.getC_p().size() ? -0 : -1;
	}

}
