
public class Query {
	
	private Condition condition;
	private char algorithm;
	
	public Query(Condition condition, char algorithm) {
		this.condition = condition;
		this.algorithm = algorithm;
	}
	
	public Condition getCondition() {
		return condition;
	}

	public char getAlgorithm() {
		return algorithm;
	}

	@Override
	public String toString() {
		return "Query: " + 
				condition.toString() + ", algorithm=" + algorithm + "\n";
	}
	
	
	 
}
