
public class Query {
	
	private Condition condition;
	private int algorithm;
	
	public Query(Condition condition, int algorithm) {
		this.condition = condition;
		this.algorithm = algorithm;
	}

	@Override
	public String toString() {
		return "Query: " + 
				condition.toString() + ", algorithm=" + algorithm + "\n";
	}
	
	
	 
}
