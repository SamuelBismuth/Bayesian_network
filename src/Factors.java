import java.util.ArrayList;
import java.util.Collections;
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
		for(Variable variable : variable_factors) {
			join(variable);
			eliminate(variable);
		}
	}

	private void join(Variable variable) {
		unionAll(match(variable), variable);
	}

	private List<Factor> match(Variable variable) {
		List<Factor> factors = new ArrayList<>();
		for (Factor factor : this.getFactors())
			if(factor.contains(variable))
				factors.add(factor);
		return factors;
	}

	private void unionAll(List<Factor> factors, Variable variable) {
		while(factors.size() > 1) {
			Collections.sort(factors, new Factor_comparator());
			union(factors.get(0), factors.get(1), variable);
		}
	}
	
	/**
	 * TODO: HERE WE ARE.
	 * @param fac1
	 * @param fac2
	 * @param variable
	 */
	private void union(Factor fac1, Factor fac2, Variable variable) {
		for(Cond_prob cp : fac1.getC_p());
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
