package temp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sam
 * This class is a library for the implementation of the algorithms.
 */
public class Util {

	/**
	 * This function calculate the sum of the inverse.
	 * e.g: If we want to calculate the inverse of B=true such that B has the values true
	 * and false, then we calculate the value of B=false.
	 * @param network
	 * @param variable_dependencies
	 * @param variable_probabilty
	 * @return the sum of all the marginalization.
	 */
	protected static double calculate_inverse_marginalization(
			Network network, 
			List<Probability> variable_dependencies,
			Probability variable_probabilty) {
		double answer = 0.0;
		List<String> values = network.find_variable_by_name(
				variable_probabilty.getVariable_name()).
				get_inverse(variable_probabilty.getVariable_value());
		Algorithms.addition_counter--;
		for (String value : values) {
			Algorithms.addition_counter++;
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
		Algorithms.addition_counter--;
		for (List<Probability> list_probability : joint_probability) { 
			Algorithms.addition_counter++;
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
			List<Probability> probabilities2, 
			Network network) {
		double answer = 0.0;
		Algorithms.addition_counter--;
		for(Probability probability : probabilities2) {
			Algorithms.addition_counter++;
			answer += network.calculate_probability(Stream.concat(
					probabilities.stream(), 
					Collections.singletonList(probability).stream()).
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
	protected static double prepare_marginalization(
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
	 * The method implements a list of a list for the cartesian product.
	 * @param variables_not_on_the_query
	 * @return a list of a list of probability.
	 */
	protected static List<List<Probability>> create_list_list(
			List<Variable> variables_not_on_the_query) {
		List<List<Probability>> list_list_probabilities = new ArrayList<List<Probability>>();
		for(int i = 0; i < variables_not_on_the_query.size(); i++) 
			list_list_probabilities.add(create_list_probabilities(
					variables_not_on_the_query.get(i).getName(), 
					variables_not_on_the_query.get(i).getValues()));
		return list_list_probabilities;
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
	 * This method search for all the variable of the query.
	 * @param network
	 * @param query
	 * @return List<Variable>.
	 */
	protected static List<Variable> get_factors_variable(Network network, Query query) {
		List<Variable> factors = 
				network.not_on_the_query(query.get_all_probability());
		factors.add(network.get_searched_query(query));
		return factors;
	}

	/**
	 * This method check if a Condition and a line of probability are equal.
	 * @param cond
	 * @param line
	 * @return true if equal, else false.
	 */
	static boolean are_equal(Condition cond, List<Probability> line) {
		for(Probability probability : cond.getJoin_probability()) 
			if(!contain(line, probability)) 
				return false;
		return true;
	}

	/**
	 * This method check if a Condition is include into a list of probability.
	 * @param cond
	 * @param prob1
	 * @return true if include, else false.
	 */
	public static boolean are_contained(Condition cond, List<Probability> prob1) {
		for(Probability probability : prob1) 
			if (!contain(cond.getJoin_probability(), probability))
				return false;
		return true;
	}

	/**
	 * This function check if a probability is contained into a list of probability.
	 * @param line
	 * @param probability
	 * @return true if contained, else false.
	 */
	private static boolean contain(List<Probability> line, Probability probability) {
		for (Probability line_prob : line) 
			if (line_prob.getVariable_value().equals(probability.getVariable_value())
					&& line_prob.getVariable_name() == probability.getVariable_name())
				return true;
		return false;
	}
	
	/**
	 * This method check first if the is variable in common between a Condition and a
	 * list of probability. If exist, it's check if the values are equal.
	 * @param cond
	 * @param line
	 * @return true if equals, else false.
	 */
	public static boolean if_exist_so_equal(Condition cond, List<Probability> line) {
		boolean flag = true;
		for (Probability probability : cond.getJoin_probability()) {
			List<Probability> list_prob = line.stream().filter(item->probability.getVariable_name()
					== item.getVariable_name()).collect(Collectors.toList());
			for(Probability prob2 : list_prob) {
				if (!prob2.getVariable_value().equals(probability.getVariable_value())) {
					flag = false;
				}
			}
		}
		return flag;
	}
}