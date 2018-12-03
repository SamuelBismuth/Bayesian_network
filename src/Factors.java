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
	private Variables variableFactors;  // All the variables of the factors.
	private Variables variables;
	private Query query;  // The query we need to answer.

	/**
	 * Constructor for {@link Factors}.
	 * @param factors2
	 * @param variableFactors
	 * @param query
	 */
	public Factors(Set<Factor> factors2, Variables variableFactors, Variables variables, Query query) {
		this.factors = factors2;
		this.variableFactors = variableFactors;
		this.variables = variables;
		this.query = query;
	}

	/**
	 * This function run a query.
	 * i.e., this method do: union, and eliminate for all the variables of the factors.
	 */
	public void run() {
		for (Variable variable : this.getHiddenVariable()) {
			Factor factor = unionAll(this.match(variable), variable);  // join.
			factor = eliminate(factor, variable); 
			this.getFactors().removeAll(match(variable));
			this.getFactors().add(factor);
		}
		for (Variable variable : this.getRemainingVariable()) {
			Factor factor = unionAll(this.match(variable), variable);  // join.
			factor = eliminate(factor, variable); 
			this.getFactors().removeAll(match(variable));
			this.getFactors().add(factor);
		}
	}

	private List<Variable> getHiddenVariable() {
		List<Variable> hiddenVariableSorted = this.getVariableFactors().getVariables().stream().
				filter(var -> {
					for(Variable varRemaining : this.getRemainingVariable())
						if (varRemaining.getName().equals(var.getName()))
							return false;
					return true;
				}).collect(Collectors.toList());
		Collections.sort(hiddenVariableSorted, new VariableComparatorAlgorithm2());
		return hiddenVariableSorted;
	}

	private List<Variable> getRemainingVariable() {
		List<Variable> remainingVariableSorted =  new ArrayList<>(this.getVariables().
				findVariablesByNames(UtilList.concatenateItemWithSet(
				query.getEvidences().getEvents().getEvents().
				stream().map(event -> event.getVariable()).collect(Collectors.toSet()),
				query.getQuery().getVariable())));
		Collections.sort(remainingVariableSorted, new VariableComparatorAlgorithm2());
		return remainingVariableSorted;
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
				if (probability.getEvents().isIncluded(listEvent)) 
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
	public Variables getVariableFactors() {
		return variableFactors;
	}

	/**
	 * @return the variables
	 */
	public Variables getVariables() {
		return variables;
	}

	/**
	 * @return the query
	 */
	public Query getQuery() {
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
