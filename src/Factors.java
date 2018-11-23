import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
		for(Variable variable : variable_factors) {
			Factor factor = unionAll(match(variable), variable);  // join.
			System.out.println(factor);
			eliminate(variable);
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
			factors.set(0, union(factors.get(0), factors.get(1), variable));
			factors.remove(1);
		}
		return factors.get(0);
	}
	
	/**
	 * TODO: HERE WE ARE.
	 * @param fac1
	 * @param fac2
	 * @param variable
	 */
	private Factor union(Factor fac1, Factor fac2, Variable variable) {
		for(Cond_prob cp1 : fac1.getC_p()) 
			for(Cond_prob cp2 : fac2.getC_p()) 
				for(Condition condition1 : cp1.getProbability().keySet()) 
					for(Condition condition2 : cp2.getProbability().keySet()) 
						if (condition1.in_common(condition2, variable)) 
							fac1 = fac1.join(fac2);	
		return fac1;
	}

	private void eliminate(Variable variable) {

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
