import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Factors {

	private List<Factor> factors;
	private List<Variable> variable_factors;
	private Variable query;

	public Factors(List<Factor> factors, List<Variable> variable_factors, Variable query) {
		this.factors = factors;
		this.variable_factors = variable_factors;
		this.query = query;
	}

	public void run() {
		System.out.println(this.getFactors().toString());
		for(Variable variable : variable_factors) {
			//System.out.println(match(variable));
			
			Factor factor = unionAll(match(variable), variable);  // join.
			//System.out.println(factor.toString());
			factor = eliminate(factor, variable); 
			factors.removeAll(match(variable));
			factors.add(factor);
			System.out.println("#################");
		}
	}

	private List<Factor> match(Variable variable) {
		List<Factor> factors = new ArrayList<>();
		for (Factor factor : this.getFactors())
			if(factor.contains(variable))
				factors.add(factor);
		return factors;
	}

	private Factor unionAll(List<Factor> factors, Variable variable) {
		while(factors.size() > 1) {
			Collections.sort(factors, new Factor_comparator());
			//System.out.println(factors.get(0));
			factors.set(0, factors.get(0).join(factors.get(1)));		
			factors.remove(1);
		}
		return factors.get(0);
	}

	private Factor eliminate(Factor factor, Variable variable) {
		factor.getVariables().remove(variable);
		List<List<Probability>> new_cp = 
				Algorithms.cartesian_product(Algorithms.create_list_list(factor.getVariables()));
		List<Cond_prob> c_p = new ArrayList<>();
		for (List<Probability> prob1 : new_cp) {
			double d = 0.0;
			for (Cond_prob cp : factor.getC_p()) {
				for(Condition cond : cp.getProbability().keySet()) {
					if (Algorithms.are_contained(cond, prob1)) {
						d += cp.getProbability().get(cond);
					}
						
				}
			}
			HashMap<Condition, Double> probability = new HashMap<>();
			probability.put(new Condition(prob1), d);
			c_p.add(new Cond_prob(probability));
		}
		return new Factor(factor.getVariables(), c_p);
	}

	public List<Factor> getFactors() {
		return factors;
	}

	public List<Variable> getVariable_factors() {
		return variable_factors;
	}

	public Variable getQuery() {
		return query;
	}

}
