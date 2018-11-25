import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jdk.internal.dynalink.linker.LinkerServices.Implementation;

/**
 * @author sam
 * This class represents the Object Factors.
 */
public class Factor {

	private List<Variable> variables;  // The variables of the factor.
	private List<Cond_prob> c_p;  // The table of the factor.
 
	/**
	 * Constructor.
	 * @param variables
	 * @param c_p
	 */
	public Factor(List<Variable> variables, List<Cond_prob> c_p) {
		this.variables = variables;
		this.c_p = c_p;
	}
	
	/**
	 * Get the variable in common between this factor and the argument factor.
	 * @param factor
	 * @return List<Variable>.
	 */
	protected List<Variable> variable_union(Factor factor) {
		return Util_list.concatenate_two_list(factor.getVariables(), this.getVariables());
	}

	/**
	 * Check if the variable is include in variables.
	 * @param variable
	 * @return true if the variable is include else false.
	 */
	protected boolean contains(Variable variable) {
		return this.getVariables().contains(variable);
	}

	/**
	 * This method return the factor with the biggest number of variable.
	 * @param factor
	 * @return either factor or this.
	 */
	protected Factor maximum_variable(Factor factor) {
		if(factor.getVariables().size() == this.getVariables().size())
			return this;
		if(factor.getVariables().size() > this.getVariables().size())
			return factor;
		return this;
	}

	/**
	 * This method return the factor with the lowest number of variable.
	 * @param factor
	 * @return either factor or this.
	 */
	protected Factor minimum_variable(Factor factor) {
		if(factor.getVariables().size() == this.getVariables().size())
			return factor;
		if(factor.getVariables().size() < this.getVariables().size())
			return factor;
		return this;
	}

	/**
	 * This function join two factor.
	 * @param fac2
	 * @return the new factor.
	 */
	public Factor join(Factor fac2) {
		List<Variable> variables = Stream.concat(this.getVariables().stream(), 
				fac2.getVariables().stream()).distinct().collect(Collectors.toList());
		// Cartesian product.
		List<List<Probability>> new_factor = 
				Util.cartesian_product(Util.create_list_list(variables));
		List<Cond_prob> cp = new ArrayList<>();
		for (List<Probability> line : new_factor) {
			double one = fac2.get_match(line);
			double two = this.get_match(line);
			HashMap<Condition, Double> probability = new HashMap<>();
			Algorithms.mulitiplication_counter++;
			probability.put(new Condition(line), one * two);
			cp.add(new Cond_prob(probability));
		}
		return new Factor(variables, cp);
	}

	/**
	 * This function return the probability from the match between the line and the cp.
	 * @param line
	 * @return the probability.
	 */
	private double get_match(List<Probability> line) {
		for(Cond_prob cp : this.getC_p()) 
			for(Condition cond : cp.getProbability().keySet()) 
				if (Util.if_exist_so_equal(cond, line)) 
					return cp.getProbability().get(cond);
		return 0;
	}

	/**
	 * TODO: Check if the division is counted as a multiplication?!!
	 * TODO: Maybe it's only one multiplication (only the one which interested us).
	 * This method normalize the factor.
	 */
	public void normalize() {
		double lambda = 0.0;
		//Algorithms.addition_counter++;
		for(Cond_prob cp : this.getC_p()) 
			lambda += cp.get_sum();
		for(Cond_prob cp : this.getC_p()) {
			for(Condition cond : cp.getProbability().keySet()) {
				//Algorithms.mulitiplication_counter++;
				cp.getProbability().put(cond, cp.getProbability().get(cond) / lambda);
			}
		}	
	}

	/**
	 * This method gives the final double for the final answer of the query.
	 * @param variable_value
	 * @return the probability.
	 */
	public double get_final_double(String variable_value) {
		for (Cond_prob cp : this.getC_p()) {
			for (Condition cond : cp.getProbability().keySet())
				for (Probability prob : cond.getJoin_probability())
					if(prob.getVariable_value().equals(variable_value))
						return cp.getProbability().get(cond);
		}
		return 0.0;
	}
	
	/**
	 * Get the variables.
	 * @return variables.
	 */
	public List<Variable> getVariables() {
		return variables;
	}

	/**
	 * Get the Cond_Prob.
	 * @return c_p.
	 */
	public List<Cond_prob> getC_p() {
		return c_p;
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
}

/**
 * @author sam
 * This class {@link Implementation} Comparator, two compare two factors.
 */
class Factor_comparator implements Comparator<Factor> {

	/**
	 * This function compare two factor as the next way:
	 * the size of the {@link Cond_prob} of the two factors.
	 */
	@Override
	public int compare(Factor o1, Factor o2) {
		return o1.getC_p().size() > o2.getC_p().size() ? 1 : 
			o1.getC_p().size() == o2.getC_p().size() ? -0 : -1;
	}
}
