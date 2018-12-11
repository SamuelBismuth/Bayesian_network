import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sam
 * This class represents the object {@link Factor}.
 */
public class Factor {

	private Set<Variable> variables;  // The variables of the factor.
	private Set<Probability> probability;  // The table of the factor.

	/**
	 * Constructor for the object {@link Factor}.
	 * @param variables
	 * @param probability
	 */
	public Factor(Set<Variable> variables, Set<Probability> probability) {
		this.variables = variables;
		this.probability = probability;
	}
	
	/**
	 * This function join two factors.
	 * @See the algorithm variable elimination for more details.
	 * @param fac
	 * @return the new factor.
	 */
	public Factor join(Factor fac) {
		Set<Variable> variables = Stream.concat(this.getVariables().stream(), 
				fac.getVariables().stream()).distinct().collect(Collectors.toSet());
		// Cartesian product.
		List<List<Event>> newFactor = 
				Util.cartesianProduct(new Variables(new LinkedHashSet<>(variables)));
		Set<Probability> probabilities = new LinkedHashSet<>();
		for (List<Event> line : newFactor) {
			double one = fac.getMatch(line);
			double two = this.getMatch(line);
			Algorithms.mulitiplicationCounter++;
			probabilities.add(new Probability(new Events(new LinkedHashSet<>(line)), one * two));
		}
		return new Factor(variables, probabilities);
	}

	/**
	 * This function normalize the table of the {@link Factor}.
	 * Since we're using this function only on the end of the algorithm, 
	 * we're able to assume that there is only one factor.
	 */
	protected void normalize() {
		Algorithms.additionCounter += this.getProbability().size() - 1;
		double lambda = 0.0;
		for(Probability prob : this.getProbability()) 
			lambda += prob.getProbability();
		for(Probability prob2 : this.getProbability()) 
			prob2.setProbability(prob2.getProbability() / lambda);
	}

	/**
	 * This function return the final probability given the value of the query.
	 * Attention, this function must never return 0.0. 
	 * @param value
	 * @return the probability
	 */
	protected double getFinalProbability(Value value) {
		for (Probability probability : this.getProbability()) {
			if(probability.getEvents().getEvents().iterator().next()
					.getValue().getValue().equals(value.getValue()))
				return probability.getProbability();
		}
		return 0.0;
	}

	/*##################Private##################*/

	/**
	 * Given a list of {@link Event}, this function returns the double corresponding to the
	 * {@link Value} of the events.
	 * @param events
	 * @return the probability
	 */
	private double getMatch(List<Event> events) {
		for(Probability probability : this.getProbability()) 
			if (probability.match(new Evidences(new Events(new LinkedHashSet<>(events)))))
				return probability.getProbability();
		return 0;
	}		

	/*##################Getters##################*/

	/**
	 * @return the variables
	 */
	public Set<Variable> getVariables() {
		return variables;
	}

	/**
	 * @return the probability
	 */
	public Set<Probability> getProbability() {
		return probability;
	}

	public String toString() {
		return "Var: " + this.getVariables().stream().map(item->item.getName()).collect(Collectors.toList()).toString() + "Prob: " + this.getProbability().toString() + "\n";
	}

}

/**
 * @author sam
 * This class {@link FactorComparator} Comparator, two compare two factors.
 */
class FactorComparator implements Comparator<Factor> {

	/**
	 * This function compare two factor as the next way:
	 * the size of the {@link Probability} of the two factors.
	 */
	@Override
	public int compare(Factor o1, Factor o2) {
		return o1.getProbability().size() > o2.getProbability().size() ? 1 : 
			o1.getProbability().size() == o2.getProbability().size() ? -0 : -1;
	}

}
