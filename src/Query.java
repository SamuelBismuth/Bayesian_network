import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sam
 * This class represents the object Query.
 */
public class Query {
	
	private Condition condition;
	private char algorithm;

	/**
	 * Constructor.
	 * @param condition
	 * @param algorithm
	 */
	public Query(Condition condition, char algorithm) {
		this.condition = condition;
		this.algorithm = algorithm;
	}

	/**
	 * Get the Condition.
	 * @return the condition.
	 */
	public Condition getCondition() {
		return condition;
	}
	
	protected List<Probability> get_all() {
		return Stream.concat(
				this.getCondition().getVariable_dependencies().stream(), 
				Collections.singletonList(
						this.getCondition().getVariable_probabilty()).stream()).
				collect(Collectors.toList());
	}
	
	/**
	 * Get the algorithm.
	 * @return the algorithm.
	 */
	public char getAlgorithm() {
		return algorithm;
	}

	@Override
	public String toString() {
		return "Query: " + 
				condition.toString() + ", algorithm=" + algorithm + "\n";
	}

}
