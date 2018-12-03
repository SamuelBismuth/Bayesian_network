import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
				Factor factor = unionAll(this.match(variable), variable);  // join.
				factor = eliminate(factor, variable); 
				factors.removeAll(match(variable));
				factors.add(factor);
			}
		}
	}
	
	private Factor unionAll(List<Factor> factors, Variable variable) {
		while(factors.size() > 1) {
			Collections.sort(factors, new FactorComparator());
			factors.set(0, factors.get(0).join(factors.get(1)));		
			factors.remove(1);
		}
		return factors.get(0);
	}
	
	private List<Factor> match(Variable variable) {
		List<Factor> factors = new ArrayList<>();
		for (Factor factor : this.getFactors()) {
			//System.out.println("factor variable" +factor.getVariables());
			//System.out.println("variable" +variable.getName());
			if(factor.getVariables().stream().
					map(var -> var.getName()).collect(Collectors.toList()).
					contains(variable.getName())) {
				factors.add(factor);
			}
		}
		return factors;
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
		Set<Probability> newProbability = new LinkedHashSet<>();
		Algorithms.additionCounter += events.size();
		for (List<Event> listEvent : events) {
			double d = 0.0;
			for (Probability probability : factor.getProbability()) 
				if (probability.getEvents().isContained(listEvent)) 
					d += probability.getProbability();
			newProbability.add(new Probability(new Events(new LinkedHashSet<>(listEvent)), d));
		}
		return new Factor(factor.getVariables(), newProbability);
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
