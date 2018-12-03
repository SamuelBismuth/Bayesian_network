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
	 * Constructor fot the object {@link Factor}.
	 * @param variables
	 * @param probability
	 */
	public Factor(Set<Variable> variables, Set<Probability> probability) {
		this.variables = variables;
		this.probability = probability;
	}

	/**
	 * This function join two factor.
	 * @param fac2
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

	private double getMatch(List<Event> events) {
		for(Probability probability : this.getProbability()) 
			if (probability.match(new Evidences(new Events(new LinkedHashSet<>(events)))))
				return probability.getProbability();
		return 0;
	}		

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
