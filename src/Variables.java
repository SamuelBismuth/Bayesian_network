import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sam
 * This class represents the object {@link Variables}.
 */
public class Variables {

	private Set<Variable> variables;

	/**
	 * Constructor for {@link Variables}.
	 * @param variables
	 */
	public Variables(Set<Variable> variables) {
		this.variables = variables;
	}

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

	public Set<Variable> deleteIrrelevant(Set<Variable> all) {
		Set<Variable> relevantVariable = new LinkedHashSet<>(all);
		for(Variable variable : all) 
			this.get_ancestor(relevantVariable, variable);
		Set<Variable> allVariable = new LinkedHashSet<>(this.getVariables());
		allVariable.removeAll(relevantVariable);
		this.setVariables(relevantVariable);
		return allVariable;
	}

	/**
	 * For a given variable, this function return all this ancestor.
	 * @param ancestors
	 * @param variable
	 */
	private void get_ancestor(Set<Variable> ancestors, Variable variable) {
		if(variable.getParents() != null) {
			ancestors.addAll(this.findVariablesByNames(variable.getParents()));
			for (Variable parent : this.findVariablesByNames(variable.getParents()))
				get_ancestor(ancestors, parent);
		}
	}

	protected Factors createFactors(Query query) {
		Set<Factor> factors = new LinkedHashSet<>();
		for(Variable variable : this.getVariables())
			factors.add(this.createFactor(query.getEvidences(), variable));
		return new Factors(factors, this.getVariables(), query.getQuery());
	}

	private Factor createFactor(Evidences evidences, Variable variable) {
		Set<Probability> probabilities = new LinkedHashSet<>();
		for (CPT cpt : variable.getCpts().getCpts())
			for (Probability probability : cpt.getTable())
				if (probability.match(evidences))
					probabilities.add(probability);
		//System.out.println(this.getFactorVariable(evidences, variable));
		return new Factor(this.getFactorVariable(evidences, variable), probabilities);
	}

	/**
	 * Can't return a null value.
	 * @param evidences
	 * @param variable
	 * @return
	 */
	private Set<Variable> getFactorVariable(Evidences evidences, Variable variable) {
		Set<Variable> setVariables = new LinkedHashSet<>();
		if (variable.getParents() != null) {
			Set<String> parentsMatched = variable.getParents();
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

	public String toString() {
		String ans = "";
		for (Variable variable : this.getVariables())
			ans += variable.toString();
		return ans;
	}

}
