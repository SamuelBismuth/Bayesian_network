import java.util.Set;

/**
 * @author sam
 * This class represents the object {@link Queries}.
 */
public class Queries {

	private Set<Query> queries;  // The set of queries.

	/**
	 * Constructor for {@link Queries}.
	 * @param queries
	 */
	public Queries(Set<Query> queries) {
		this.queries = queries;
	}

	/*##################Getter##################*/

	/**
	 * @return the queries
	 */
	public Set<Query> getQueries() {
		return queries;
	}

	public String toString() {
		String ans = "";
		for (Query query : this.getQueries())
			ans += query.toString() + "\n";
		return ans;
	}

}
