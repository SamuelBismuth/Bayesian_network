import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
		double answer = 0.0;
		List<Variable> variables_not_on_the_query = network.not_on_the_query(probabilities);
		List<Probability> joint_probability = new ArrayList<>();
		for(Variable variable : variables_not_on_the_query) {
			for(String value : variable.getValues()) {
				joint_probability.add(new Probability(variable.getName(), value));
			}
		}
		// TODO: Play with the number of variable to distribute.
		System.out.println(joint_probability);

		//answer += calculate_joint_probability(network, joint_probability.get(0));
		return network.find_probability_by_condition(probabilities);
	}

	public static String algorithm_2(Network network, Query query) {
		return null;
	}

	public static String algorithm_3(Network network, Query query) {
		return null;
	}

}
