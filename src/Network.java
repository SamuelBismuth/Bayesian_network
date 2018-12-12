/**
 * @author sam
 * This class represents the object Network.
 */
public class Network {

	static Network instance = null;  // Singleton implementation.

	private Variables variables;  // The variables of the Network.
	private Queries queries;  // The queries of the Network.

	/**
	 * This static method comes from the singleton implementation.
	 * Either a new network is created, or the actual network is returned.
	 * @param variables
	 * @param queries
	 * @return the network.
	 */
	static protected Network getInstance(Variables variables, Queries queries) {
		if(instance != null) 
			return instance;
		instance = new Network(variables, queries);
		return instance;
	}

	/**
	 * The constructor for the {@link Network}.
	 * Since the network have a singleton implementation, the constructor is private.
	 * Source: https://www.geeksforgeeks.org/singleton-design-pattern/
	 * @param variables
	 * @param queries
	 */
	private Network(Variables variables, Queries queries) {
		this.variables = variables;
		this.queries = queries;
	}

	/*##################Getters##################*/

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
