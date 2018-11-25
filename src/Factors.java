import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import jdk.internal.dynalink.linker.LinkerServices.Implementation;

/**
 * @author sam
 * This class represents the object factors, which include some {@link Factor}.
 */
public class Factors {

	private List<Factor> factors;  // The factors.
	private List<Variable> variable_factors;  // All the variables of the factors.
	private Variable query;  // The query we need to answer.

	/**
	 * Constructor.
	 * @param factors
	 * @param variable_factors
	 * @param query
	 */
	public Factors(List<Factor> factors, List<Variable> variable_factors, Variable query) {
		this.factors = factors;
		this.variable_factors = variable_factors;
		this.query = query;
	}

	/**
	 * This function run a query.
	 * i.e., this method do: union, and eliminate for all the variables of the factors.
	 */
	public void run(boolean flag_algo2) {
		if(flag_algo2)
			Collections.sort(variable_factors, new Variable_comparator_algorithm2());
		else
			Collections.sort(variable_factors, new Variable_comparator_algorithm3(this));
		for(Variable variable : variable_factors) {	
			if (factors.size() == 1) {
				factors.set(0, eliminate(factors.get(0), variable)); 
				return;
			}
			Factor factor = unionAll(match(variable), variable);  // join.
			factor = eliminate(factor, variable); 
			factors.removeAll(match(variable));
			factors.add(factor);
		}
	}

	/**
	 * This method do the union between all the factors.
	 * @param factors
	 * @param variable
	 * @return
	 */
	protected Factor unionAll(List<Factor> factors, Variable variable) {
		while(factors.size() > 1) {
			Collections.sort(factors, new Factor_comparator());
			factors.set(0, factors.get(0).join(factors.get(1)));		
			factors.remove(1);
		}
		return factors.get(0);
	}

	/**
	 * This function eliminate the variable from the factor.
	 * @param factor
	 * @param variable
	 * @return the new factor.
	 */
	private Factor eliminate(Factor factor, Variable variable) {
		factor.getVariables().remove(variable);
		List<List<Probability>> new_cp = 
				Util.cartesian_product(Util.create_list_list(factor.getVariables()));
		List<Cond_prob> c_p = new ArrayList<>();
		Algorithms.addition_counter += new_cp.size();
		for (List<Probability> prob1 : new_cp) {
			double d = 0.0;
			for (Cond_prob cp : factor.getC_p()) 
				for(Condition cond : cp.getProbability().keySet()) 
					if (Util.are_contained(cond, prob1)) {
						d += cp.getProbability().get(cond);
					}
			HashMap<Condition, Double> probability = new HashMap<>();
			probability.put(new Condition(prob1), d);
			c_p.add(new Cond_prob(probability));
		}
		return new Factor(factor.getVariables(), c_p);
	}

	/**
	 * This method for a given variable return all the factor to deal with.
	 * @param variable
	 * @return factors.
	 */
	protected List<Factor> match(Variable variable) {
		List<Factor> factors = new ArrayList<>();
		for (Factor factor : this.getFactors())
			if(factor.contains(variable))
				factors.add(factor);
		return factors;
	}

	/**
	 * Get factors.
	 * @return factors.
	 */
	protected List<Factor> getFactors() {
		return factors;
	}

	/**
	 * Get variable factors.
	 * @return variable_factors.
	 */
	protected List<Variable> getVariable_factors() {
		return variable_factors;
	}

	/**
	 * Get query.
	 * @return the variable of the query.
	 */
	protected Variable getQuery() {
		return query;
	}
}

/**
 * @author sam
 * This class {@link Implementation} Comparator, two compare two Variable.
 */
class Variable_comparator_algorithm2 implements Comparator<Variable> {

	/**
	 * The method compare.
	 */
	@Override
	public int compare(Variable o1, Variable o2) {
		return o1.getName() > o2.getName() ? 1 : 
			o1.getName() ==  o2.getName() ? -0 : -1;

	}

}

/**
 * @author sam
 * This class {@link Implementation} Comparator, two compare two Variable.
 */
class Variable_comparator_algorithm3 implements Comparator<Variable> {

	private Factors factors;

	/**
	 * Constructor.
	 * @param factors
	 */
	public Variable_comparator_algorithm3(Factors factors) {
		this.factors = factors;
	}

	/**
	 * The method compare.
	 */
	@Override
	public int compare(Variable o1, Variable o2) {
		return factors.match(o1).size() > factors.match(o2).size() ? 1 : 
			 factors.match(o1).size() ==  factors.match(o2).size() ? -0 : -1;

	}
}
