import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

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
		double A = calculate_marginalisation(network, list_A);
		double B = calculate_marginalisation(network, query.getCondition().getVariable_dependencies());
		return Double.toString(A/B);
	}

	private static double calculate_marginalisation(Network network, List<Probability> probabilities) {
		double answer = 0.0;
		List<Variable> variables_not_on_the_query = network.not_on_the_query(probabilities);
		if (variables_not_on_the_query.size() == 0) {
			return network.calculate_probability(probabilities); // TODO: Change this.
		}
		if (variables_not_on_the_query.size() == 1) {
			return distribution(probabilities, 
					create_list_probabilities(
							variables_not_on_the_query.get(0).getName(), 
							variables_not_on_the_query.get(0).getValues()), 
					network);
		}
		List<List<Probability>> list_list_probabilities = new ArrayList<List<Probability>>();
		for(int i = 0; i < variables_not_on_the_query.size(); i++) 
			list_list_probabilities.add(create_list_probabilities(
					variables_not_on_the_query.get(i).getName(), 
					variables_not_on_the_query.get(i).getValues()));
		List<List<Probability>> joint_probability = cartesian_product(list_list_probabilities);
		for (List<Probability> list_probability : joint_probability) 
			answer += network.calculate_probability(
					Stream.concat(list_probability.stream(), probabilities.stream())
					.collect(Collectors.toList()));
		return answer;
	}

	private static double distribution(List<Probability> probabilities, 
			List<Probability> probabilities2, Network network) {
		double answer = 0.0;
		for(Probability probability : probabilities2) {
			List<Probability> probabilities_sorted = new ArrayList<>(probabilities);
			probabilities_sorted.add(probability);
			answer += network.calculate_probability(probabilities_sorted);
		}
		return answer;
	}

	protected static List<List<Probability>> cartesian_product(List<List<Probability>> lists) {
		List<List<Probability>> resultLists = new ArrayList<List<Probability>>();
		if (lists.size() == 0) {
			resultLists.add(new ArrayList<Probability>());
			return resultLists;
		} else {
			List<Probability> firstList = lists.get(0);
			List<List<Probability>> remainingLists = cartesian_product(lists.subList(1, lists.size()));
			for (Probability condition : firstList) {
				for (List<Probability> remainingList : remainingLists) {
					ArrayList<Probability> resultList = new ArrayList<Probability>();
					resultList.add(condition);
					resultList.addAll(remainingList);
					resultLists.add(resultList);
				}
			}
		}
		return resultLists;
	}

	private static List<Probability> create_list_probabilities(char name, List<String> values) {
		List<Probability> probabilities = new ArrayList<>();
		for(String value : values)
			probabilities.add(new Probability(name, value));
		return probabilities;
	}

	public static String algorithm_2(Network network, Query query) {
		return null;
	}

	public static String algorithm_3(Network network, Query query) {
		return null;
	}

}
