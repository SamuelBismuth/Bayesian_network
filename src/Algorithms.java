import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sam
 * Class Algorithms.
 * This class includes only static method (library).
 * The three main methods are the three implemented algorithms.
 * Each algorithm answer to a query of the format: P(A|B,C).
 * To answer to all the query we have a Bayesian Network already build.
 * Here is the format for the answer:
 * 
 * 1: The result rounded five numbers after the point.
 *  ,
 * 2: The number of addition.
 * ,
 * 3: The number of multiplication.
 */
public class Algorithms {

	// Two static variable counter.
	static protected int addition_counter 		 = 0,
					     mulitiplication_counter = 0;
	// The format for the round.
	static DecimalFormat df = new DecimalFormat("#0.00000");

	/**
	 * This function implements the steps of the algorithm 1.
	 * Notice that the algorithm 1 is not really an algorithm but more a simple prediction.
	 * @param network
	 * @param query
	 * @return the result of the query including the counter in the well form.
	 */
	protected static String algorithm_1(Network network, Query query) {	
		addition_counter = mulitiplication_counter = 0; 
		double Y1 = prepare_marginalization(network, query.get_all_probability());
		double Y2 = calculate_inverse_marginalization(
				network, 
				query.getCondition().getVariable_dependencies(),
				query.getCondition().getVariable_probabilty());
		addition_counter++;
		return df.format((1 / (Y1 + Y2)) * Y1) + "," +
		Integer.toString(addition_counter) + "," +
		Integer.toString(mulitiplication_counter);
	}

	/**
	 * This function calculate the sum of the inverse.
	 * e.g: If we want to calculate the inverse of B=true such that B has the values true
	 * and false, then we calculate the value of B=false.
	 * @param network
	 * @param variable_dependencies
	 * @param variable_probabilty
	 * @return the sum of all the marginalization.
	 */
	private static double calculate_inverse_marginalization(
			Network network, 
			List<Probability> variable_dependencies,
			Probability variable_probabilty) {
		double answer = 0.0;
		List<String> values = network.find_variable_by_name(
				variable_probabilty.getVariable_name()).
				get_inverse(variable_probabilty.getVariable_value());
		addition_counter--;
		for (String value : values) {
			addition_counter++;
			answer += prepare_marginalization(
					network, 
					Stream.concat(
							variable_dependencies.stream(), 
							Collections.singletonList(
									new Probability(variable_probabilty.getVariable_name(),
											value)).stream()).
					collect(Collectors.toList()));
		}
		return answer;
	}

	/**
	 * This function prepare our query for the marginalization.
	 * i.e, it is format our query from P(A|B,C) to P(A,B,C,E).
	 * @param network
	 * @param probabilities
	 * @return the result of the marginalization.
	 */
	private static double prepare_marginalization(
			Network network, 
			List<Probability> probabilities) {
		List<Variable> variables_not_on_the_query = network.not_on_the_query(probabilities);
		if (variables_not_on_the_query.size() == 0) 
			return network.calculate_probability(probabilities); 
		if (variables_not_on_the_query.size() == 1) 
			return distribution(probabilities, 
					create_list_probabilities(
							variables_not_on_the_query.get(0).getName(), 
							variables_not_on_the_query.get(0).getValues()), 
					network);
		return calculate_marginalization(
				network, 
				cartesian_product(create_list_list(variables_not_on_the_query)),
				probabilities);
	}

	/**
	 * This function calculate the marginalization given the probability 
	 * of the form P(A, B, C).
	 * @param network
	 * @param probabilities
	 * @return the result of the marginalization.
	 */
	private static double calculate_marginalization(
			Network network, 
			List<List<Probability>> joint_probability,
			List<Probability> probabilities) {
		double answer = 0.0;
		addition_counter--;
		for (List<Probability> list_probability : joint_probability) { 
			addition_counter++;
			answer += network.calculate_probability(
					Stream.concat(list_probability.stream(), probabilities.stream())
					.collect(Collectors.toList()));
		}
		return answer;
	}

	/**
	 * This method is used when there is no need to proceed a Cartesian product.
	 * That means when there is only one hidden variable.
	 * @param probabilities
	 * @param probabilities2
	 * @param network
	 * @return the probability of the event.
	 */
	private static double distribution(
			List<Probability> probabilities, 
			List<Probability> probabilities2, Network network) {
		double answer = 0.0;
		addition_counter--;
		for(Probability probability : probabilities2) {
			addition_counter++;
			answer += network.calculate_probability(Stream.concat(
					probabilities.stream(), 
					Collections.singletonList(probability).stream()).
					collect(Collectors.toList()));
		}
		return answer;
	}

	/**
	 * The method implements a list of a list for the cartesian product.
	 * @param variables_not_on_the_query
	 * @return a list of a list of probability.
	 */
	private static List<List<Probability>> create_list_list(
			List<Variable> variables_not_on_the_query) {
		List<List<Probability>> list_list_probabilities = new ArrayList<List<Probability>>();
		for(int i = 0; i < variables_not_on_the_query.size(); i++) 
			list_list_probabilities.add(create_list_probabilities(
					variables_not_on_the_query.get(i).getName(), 
					variables_not_on_the_query.get(i).getValues()));
		return list_list_probabilities;
	}
	
	/**
	 * This method do a Cartesian product for two given list.
	 * This method is implemented from: 
	 * https://stackoverflow.com/questions/714108/cartesian-product-of-arbitrary-sets-in-java.
	 * @param lists
	 * @return the result of the Cartesian product.
	 */
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

	/**
	 * This method is create a list of Probability.
	 * @param name
	 * @param values
	 * @return a list of probability.
	 */
	private static List<Probability> create_list_probabilities(char name, List<String> values) {
		List<Probability> probabilities = new ArrayList<>();
		for(String value : values)
			probabilities.add(new Probability(name, value));
		return probabilities;
	}

	/**
	 * This function implements the algorithm Variable elimination.
	 * @param network
	 * @param query
	 * @return the result of the query including the counter in the well form.
	 */
	protected static String algorithm_2(Network network, Query query) {
		Factors factors = network.create_factors(get_factors_variable(network, query), 
				network.get_searched_query(query));
		factors.run();
		return null;
	}
	
	private static List<Variable> get_factors_variable(Network network, Query query) {
		List<Variable> factors = 
				network.not_on_the_query(query.get_all_probability());
		factors.add(network.get_searched_query(query));
		return factors;
	}

	protected static String algorithm_3(Network network, Query query) {
		return null;
	}

}
