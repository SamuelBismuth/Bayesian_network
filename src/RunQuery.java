import java.util.ArrayList;
import java.util.List;

/**
 * @author sam
 * This class is a library which run the query.
 */
public class RunQuery {

	/**
	 * This method run all the queries.
	 * @param network
	 * @return a list of String with all the answers
	 */
	public static List<String> runQueries(Network network) {
		List<String> answers = new ArrayList<>();
		for (Query query : network.getQueries().getQueries()) {
			answers.add(create_answer(network, query));
		}
		return answers;	
	}

	/**
	 * This function divide the query into their algorithm.
	 * Attention: this method must never return null.
	 * @param network
	 * @param query
	 * @return the answer in {@link String}
	 */
	private static String create_answer(Network network, Query query) {
		switch(query.getAlgorithm()) {
		case '1':
			return Algorithms.algorithm1(network, query);
		case '2':
			return Algorithms.algorithm2(network, query);
		case '3':
			return Algorithms.algorithm3(network, query);
		default:
			return null;
		}
	}

}
