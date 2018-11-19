import java.util.ArrayList;
import java.util.List;

public class Algorithms {

	// 1: The result rounded five numbers after the point.
	// ,
	// 2: The number of addition.
	// ,
	// 3: The number of multiplication.

	/**
	 * Bayes rules: P(A|B) = P(A, B) / P(B)
	 * @param network
	 * @param query
	 * @return
	 */
	public static String algorithm_1(Network network, Query query) {	
		ArrayList<Probability> list_A = new ArrayList<Probability>(
				query.getCondition().getVariable_dependencies());
		list_A.add(query.getCondition().getVariable_probabilty());
		return Double.toString(
						calculate_marginalisation(network, list_A)
						/ calculate_marginalisation(network, query.getCondition().getVariable_dependencies()));
	}

	private static double calculate_marginalisation(Network network, List<Probability> probabilities) {
			return network.find_probability_by_condition(probabilities);
	}

	public static String algorithm_2(Network network, Query query) {
		return null;
	}

	public static String algorithm_3(Network network, Query query) {
		return null;
	}

}
