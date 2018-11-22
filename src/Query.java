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
