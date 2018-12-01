/**
 * @author sam
 * This class represents the object Network.
 */
public class Network {
	
	private Variables variables;  // The variables of the Network.
	private Queries queries;  // The queries of the Network.
	
	/**
	 * The constructor for the {@link Network}.
	 * @param variables
	 * @param queries
	 */
	public Network(Variables variables, Queries queries) {
		this.variables = variables;
		this.queries = queries;
	}

	/**
	 * @return the variables
	 */
	public Variables getVariables() {
		return variables;
	}

	/**
	 * @return the queries
	 */
	public Queries getQueries() {
		return queries;
	}
	
}
