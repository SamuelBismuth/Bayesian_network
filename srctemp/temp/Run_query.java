package temp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sam
 * This class is a library which run the query.
 */
public class Run_query {
	
	/**
	 * This method run all the queries.
	 * @param network
	 * @return a list of String with all the answers.
	 */
	public static List<String> run_queries(Network network) {
		List<String> answers = new ArrayList<>();
		for (Query query : network.getQueries()) {
			answers.add(create_answer(network, query));
		}
		return answers;	
	}

	/**
	 * This function divide the query into their algorithm.
	 * @param network
	 * @param query
	 * @return the answer in String.
	 */
	private static String create_answer(Network network, Query query) {
		switch(query.getAlgorithm()) {
		case '1':
			return Algorithms.algorithm_1(network, query);
		case '2':
			return Algorithms.algorithm_1(network, query);
		case '3':
			return Algorithms.algorithm_1(network, query);
		default:
			return null;
		}
	}
}
