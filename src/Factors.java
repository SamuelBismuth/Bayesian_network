import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
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
	private Variables variables; // All the variables of the network.
	private Query query;  // The query we need to answer.

	/**
	 * Constructor for {@link Factors}.
	 * @param factors2
	 * @param variableFactors
	 * @param variables
	 * @param query
	 */
	public Factors(Set<Factor> factors2, Variables variableFactors, Variables variables, Query query) {
		this.factors = factors2;
		this.variableFactors = variableFactors;
		this.variables = variables;
		this.query = query;
	}

	/**
	 * This function run a query without worried about the way to sort the {@link Factors}.
	 * i.e., this method do: union, and eliminate for all the variables of the factors.
	 */
	protected void run(boolean flag) {
		List<Variable> hiddenVariables = this.getHiddenVariable(flag);
		while(hiddenVariables.size() != 0) {
			if(flag)
				Collections.sort(hiddenVariables, new VariableComparatorAlgorithm2());
			else
				Collections.sort(hiddenVariables, new VariableComparatorAlgorithm3(new Variables(
						new LinkedHashSet<>(hiddenVariables))));
			Variable variable = hiddenVariables.get(0);
			Factor factor = unionAll(this.match(variable), variable);  // Join.
			factor = eliminate(factor, variable);  // Eliminate.
			this.getFactors().removeAll(match(variable));
			this.getFactors().add(factor);
			hiddenVariables.remove(variable);
		}
		List<Variable> remainingVariables = this.getRemainingVariable(flag);
		while(remainingVariables.size() != 0) {
			if(flag)
				Collections.sort(remainingVariables, new VariableComparatorAlgorithm2());
			else
				Collections.sort(remainingVariables, new VariableComparatorAlgorithm3(new Variables(
						new LinkedHashSet<>(remainingVariables))));
			Variable variable = remainingVariables.get(0);
			Factor factor = unionAll(this.match(variable), variable);  // Join.
			factor = eliminate(factor, variable);  // Eliminate.
			this.getFactors().removeAll(match(variable));
			this.getFactors().add(factor);
			remainingVariables.remove(variable);
		}
	}

	/**
	 * This method make the union of all the factors including the same variable.
	 * @param factors
	 * @param variable
	 * @return the new {@link Factor} received after all the join
	 */
	protected Factor unionAll(List<Factor> factors, Variable variable) {
		while(factors.size() > 1) {
			Collections.sort(factors, new FactorComparator());
			factors.set(0, factors.get(0).join(factors.get(1)));		
			factors.remove(1);
		}
		return factors.get(0);
	}

	/*##################Privates##################*/

	/**
	 * This method return the list of the hidden variables of the {@link Factors}.
	 * @return list of {@link Variable}
	 */
	private List<Variable> getHiddenVariable(boolean flag) {
		List<Variable> hiddenVariableSorted = this.getVariableFactors().getVariables().stream().
				filter(var -> {
					for(Variable varRemaining : this.getRemainingVariable(flag))
						if (varRemaining.getName().equals(var.getName()) || 
								query.getQuery().getVariable().equals(var.getName()))
							return false;
					if(!query.getQuery().getVariable().equals(var.getName()))
						return true;
					else
						return false;
				}).collect(Collectors.toList());
		if(flag)
			Collections.sort(hiddenVariableSorted, new VariableComparatorAlgorithm2());
		else
			Collections.sort(hiddenVariableSorted, new VariableComparatorAlgorithm3(this.getVariables()));
		return hiddenVariableSorted;
	}

	/**
	 * This method return the list of the remaining variables of the {@link Factors}.
	 * @return list of {@link Variable}
	 */
	private List<Variable> getRemainingVariable(boolean flag) {
		List<Variable> remainingVariableSorted =  new ArrayList<>(this.getVariables().
				findVariablesByNames(query.getEvidences().getEvents().getEvents().
						stream().map(event->event.getVariable()).
						filter(this.getVariableFactors().getVariables().stream().
								map(thisVariable -> thisVariable.getName()).
								collect(Collectors.toList())::contains)
						.collect(Collectors.toSet())));
		if(flag)
			Collections.sort(remainingVariableSorted, new VariableComparatorAlgorithm2());
		else
			Collections.sort(remainingVariableSorted, new VariableComparatorAlgorithm3(this.getVariables()));
		return remainingVariableSorted;
	}

	/**
	 * This method return all the factor included the variable.
	 * @param variable
	 * @return a list of {@link Factor}
	 */
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
		for (List<Event> listEvent : events) {
			double d = 0.0;
			Algorithms.additionCounter--;
			for (Probability probability : factor.getProbability()) 
				if (probability.getEvents().isIncluded(listEvent)) {
					Algorithms.additionCounter++;
					d += probability.getProbability();
				}
			newProbability.add(new Probability(new Events(new LinkedHashSet<>(listEvent)), d));
		}
		factors.remove(factor);
		return new Factor(factor.getVariables(), newProbability);
	}

	/*##################Getters##################*/

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
	 * The method compare by alphabetical order.
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
class VariableComparatorAlgorithm3 implements Comparator<Variable> {

	private Variables variables;

	/**
	 * Constructor.
	 * @param factors
	 */
	public VariableComparatorAlgorithm3(Variables variables) {
		this.variables = variables;
	}

	/**
	 * The method compare using the next ordering:
	 * Eliminate the variable which have the smallest neighbor possible.
	 */
	@Override
	public int compare(Variable o1, Variable o2) {
		Set<Variable> descendantO1 = new HashSet<>();
		Set<Variable> descendantO2 = new HashSet<>();
		this.getVariables().getNeighbor(descendantO1, o1); 
		this.getVariables().getNeighbor(descendantO2, o2); 
		return descendantO1.size() < descendantO2.size() ? 1 : 
			descendantO1.size() ==  descendantO2.size() ? -0 : -1;
	}

	/*##################Getter##################*/

	/**
	 * @return the variables
	 */
	public Variables getVariables() {
		return variables;
	}

}
