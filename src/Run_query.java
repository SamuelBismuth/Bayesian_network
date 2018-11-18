import java.util.ArrayList;
import java.util.List;

public class Run_query {

	public static List<String> run_queries(Network network) {
		List<String> answers = new ArrayList<>();
		for (Query query : network.getQueries())
			answers.add(create_answer(network, query));
		return answers;	
	}

	private static String create_answer(Network network, Query query) {
		switch(query.getAlgorithm()) {
		case '1':
			return Algorithms.algorithm_1(network, query);
		case '2':
			return Algorithms.algorithm_2(network, query);
		case '3':
			return Algorithms.algorithm_3(network, query);
		default:
			return null;
		}
	}
}
