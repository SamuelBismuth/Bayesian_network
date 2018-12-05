import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sam
 * This class represents the object {@link Variables}.
 */
public class Variables {

	private Set<Variable> variables;  // The set of variables.

	/**
	 * Constructor for {@link Variables}.
	 * @param variables
	 */
	public Variables(Set<Variable> variables) {
		this.variables = variables;
	}

	/**
	 * This method return all the different variables from the passing events.
	 * @param events
	 * @return set of {@link Variable}
	 */
	protected Set<Variable> getOther(Events events) {
		return this.getVariables().
				stream().
				filter(variable ->variable.isInclude(events)).
				collect(Collectors.toSet());
	}

	/**
	 * This function return the variable given the variable name.
	 * @param variableName
	 * @return the Variable.
	 */
	protected Variable findVariableByName(String variableName) {
		for(Variable variable : this.getVariables()) {
			if(variable.getName().equals(variableName))
				return variable;
		}
		return null;
	}

	/**
	 * This method find the variables given the names of the variables.
	 * @param variablesNames
	 * @return a list of variables
	 */
	protected Set<Variable> findVariablesByNames(Set<String> variablesNames) {
		Set<Variable> variablesByNames = new LinkedHashSet<>();
		for(String variable : variablesNames) 
			variablesByNames.add(findVariableByName(variable));
		return variablesByNames;
	}

	/**
	 * This method calculates the probability for a given list of {@link Event}
	 * @param events
	 * @return the probability
	 */
	protected double calculateProbability(List<Event> events) {
		double answer = 1.0;
		Algorithms.mulitiplicationCounter--;
		for(Event event : events) {
			Algorithms.mulitiplicationCounter++;
			Variable variable = this.findVariableByName(event.getVariable());
			if(variable.getParents() == null) 
				answer *= variable.getCpts().getProbabilityAloneSoldier(event.getValue());
			else 
				answer *= variable.getCpts().getProbability(event.getValue(), 
						variable.matchParent(events));
		}
		return answer;
	}

	/**
	 * This method delete from the network all the irrelevant {@link Variable} of the query.
	 * @param all
	 * @return all the deleted {@link Variable}
	 */
	protected Set<Variable> deleteIrrelevant(Set<Variable> all) {
		Set<Variable> relevantVariable = new LinkedHashSet<>(all);
		for(Variable variable : all) 
			this.getAncestor(relevantVariable, variable);
		Set<Variable> allVariable = new LinkedHashSet<>(this.getVariables());
		allVariable.removeAll(relevantVariable);
		this.setVariables(relevantVariable);
		return allVariable;
	}

	/**
	 * Given the query, this method creates all the {@link Factors}.
	 * @param query
	 * @return the object {@link Factors}
	 */
	protected Factors createFactors(Query query) {
		Set<Factor> factors = new LinkedHashSet<>();
		for(Variable variable : this.getVariables())
			factors.add(this.createFactor(query.getEvidences(), variable));
		return new Factors(factors, new Variables(UtilList.concatenateItemWithSet(
				this.getInverse(
						query.getEvidences().getEvents().getEvents().
						stream().map(event -> event.getVariable()).
						collect(Collectors.toSet())), 
				this.findVariableByName(query.getQuery().getVariable()))), 
				new Variables(this.getVariables()), query);
	}

	/*##################Privates##################*/

	/**
	 * For a given variable, this function computes all this ancestor.
	 * @param ancestors
	 * @param variable
	 */
	private void getAncestor(Set<Variable> ancestors, Variable variable) {
		if(variable.getParents() != null) {
			ancestors.addAll(this.findVariablesByNames(variable.getParents()));
			for (Variable parent : this.findVariablesByNames(variable.getParents()))
				getAncestor(ancestors, parent);
		}
	}

	/**
	 * Given a set of {@link String} including {@link Variables} names, this function computes
	 * the inverse of the set.
	 * @param variables
	 * @return the {@link Set} of {@link Variable}
	 */
	private Set<Variable> getInverse(Set<String> variables) {
		return this.getVariables().stream().filter(var -> {
			for(String var2 : variables)
				if(var.getName().equals(var2))
					return false;
			return true;
		}).collect(Collectors.toSet());
	}

	/**
	 * This functions creates a new {@link Factor}.
	 * @param evidences
	 * @param variable
	 * @return the new {@link Factor}
	 */
	private Factor createFactor(Evidences evidences, Variable variable) {
		Set<Probability> probabilities = new LinkedHashSet<>();
		for (CPT cpt : variable.getCpts().getCpts())
			for (Probability probability : cpt.getTable())
				if (probability.match(evidences))
					probabilities.add(probability);
		return new Factor(this.getFactorVariable(evidences, variable), probabilities);
	}

	/**
	 * This function return a {@link Set} of {@link Variable} which must be the {@link Factor} {@link Variable}.
	 * Can't return a null value.
	 * @param evidences
	 * @param variable
	 * @return a set of {@link Variable}
	 */
	private Set<Variable> getFactorVariable(Evidences evidences, Variable variable) {
		Set<Variable> setVariables = new LinkedHashSet<>();
		if (variable.getParents() != null) {
			Set<String> parentsMatched = new LinkedHashSet<>(variable.getParents());
			parentsMatched.removeAll(evidences.getEvents().getEvents().stream().
					map(event -> event.getVariable()).collect(Collectors.toList()));
			setVariables = this.findVariablesByNames(new LinkedHashSet<>(parentsMatched));
		}
		if(!evidences.getEvents().getEvents().stream().
				map(event -> event.getVariable()).collect(Collectors.toSet()).
				contains(variable.getName())) 
			setVariables.add(variable);
		return setVariables;
	}
	
	/*##################Getter and Setter##################*/

	/**
	 * @return the variables
	 */
	public Set<Variable> getVariables() {
		return variables;
	}

	/**
	 * @param variables the variables to set
	 */
	public void setVariables(Set<Variable> variables) {
		this.variables = variables;
	}

}
