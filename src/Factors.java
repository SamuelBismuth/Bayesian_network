import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author sam
 * This class represents the object {@link Factors}, which include some {@link Factor}.
 */
public class Factors {

	private Set<Factor> factors;  // The factors.
	private Set<Variable> variableFactors;  // All the variables of the factors.
	private Event query;  // The query we need to answer.

	/**
	 * Constructor for {@link Factors}.
	 * @param factors2
	 * @param variableFactors
	 * @param query
	 */
	public Factors(Set<Factor> factors2, Set<Variable> variableFactors, Event query) {
		this.factors = factors2;
		this.variableFactors = variableFactors;
		this.query = query;
	}

	/**
	 * This function run a query.
	 * i.e., this method do: union, and eliminate for all the variables of the factors.
	 */
	public void run(boolean flagAlgo2) {
		if(flagAlgo2)
			Collections.sort(new ArrayList<>(this.getVariableFactors()),
					new VariableComparatorAlgorithm2());
		//else
		//Collections.sort(variable_factors, new Variable_comparator_algorithm3(this));
		while(this.getFactors().size() > 1) {
			for(Variable variable : this.getVariableFactors()) {	
				if (this.getFactors().size() == 1) {
					Factor factor = eliminate(this.getFactors().iterator().next(), variable); 
					this.getFactors().clear();
					this.getFactors().add(factor);
					return;
				}
				Factor factor = unionAll(match(variable), variable);  // join.
				factor = eliminate(factor, variable); 
				factors.removeAll(match(variable));
				factors.add(factor);
			}
		}
	}

	/**
	 * This function eliminate the variable from the factor.
	 * @param factor
	 * @param variable
	 * @return the new factor.
	 */
	private Factor eliminate(Factor factor, Variable variable) {
		factor.getVariables().remove(variable);
		List<List<Event>> events = Util.cartesianProduct(new Variables(factor.getVariables()));
		c_p = new ArrayList<>();
		Algorithms.additionCounter += events.size();
		for (List<Event> event1 : events) {
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
	 * @return the factors
	 */
	public Set<Factor> getFactors() {
		return factors;
	}

	/**
	 * @return the variableFactors
	 */
	public Set<Variable> getVariableFactors() {
		return variableFactors;
	}

	/**
	 * @return the query
	 */
	public Event getQuery() {
		return query;
	}

}

/**
 * @author sam
 * This class {@link Implementation} Comparator, two compare two Variable.
 */
class VariableComparatorAlgorithm2 implements Comparator<Variable> {

	/**
	 * The method compare.
	 */
	@Override
	public int compare(Variable o1, Variable o2) {
		return o1.getName().compareTo(o2.getName());

	}

}

/**
 * @author sam
 * This class {@link Implementation} Comparator, two compare two Variable.
 */
/*class VariableComparatorAlgorithm3 implements Comparator<Variable> {

	private Factors factors;

	/**
 * Constructor.
 * @param factors
 */
/*public VariableComparatorAlgorithm3(Factors factors) {
		this.factors = factors;
	}

	/**
 * The method compare.
 */
/*@Override
	public int compare(Variable o1, Variable o2) {
		return factors.match(o1).size() > factors.match(o2).size() ? 1 : 
			 factors.match(o1).size() ==  factors.match(o2).size() ? -0 : -1;

	}
}*/
