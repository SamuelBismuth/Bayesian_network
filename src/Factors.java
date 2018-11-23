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
			Factor factor = unionAll(match(variable), variable);  // join.
			System.out.println(factor);
			factor = eliminate(variable); // TODO: implement this. error null pointer from here.
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
			factors.set(0, factors.get(0).join( factors.get(1)));
			factors.remove(1);
		}
		return factors.get(0);
	}

	private Factor eliminate(Variable variable) {
		return null;
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
