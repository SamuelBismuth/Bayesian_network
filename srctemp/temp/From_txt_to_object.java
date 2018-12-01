package temp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author sam
 * This class is a library (only static function).
 * Here all the functions which convert the txt into objects.
 * TODO: Change the variable name into String.
 * TODO: Worried about type error.
 */
public class From_txt_to_object {
	
	/**
	 * This method create the network from the txt.
	 * @param list_txt
	 * @return The network.
	 * @throws Wrong_txt
	 */
	protected static Network create_network(List<String> list_txt) {
		try {
			verifying_txt(list_txt.iterator());
		} catch (Wrong_txt e) {
			e.printStackTrace();
		}
		Iterator<String> it = list_txt.subList(1, list_txt.size()).iterator();
		// - 11 for variables: and + 1 for the upper bound.
		int number_of_variable = ((it.next().length() - 11) + 1 )/ 2;  
		List<Variable> variables = new ArrayList<Variable>();
		it.next();
		for (int i = 0; i < number_of_variable; i++) 
			variables.add(create_variable(it));
		List<Query> queries = new ArrayList<>();
		it.next();
		String line = it.next();
		while(!line.trim().isEmpty()) {
			queries.add(create_query(line));
			line = it.next();
		}
		return new Network(variables, queries);
	}

	/**
	 * This function create the Query.
	 * @param string_query
	 * @return the query.
	 */
	private static Query create_query(String string_query) {
		String[] string_splited = string_query.split("\\|");
		Probability variable_probability = create_probability(string_splited[0].
									substring(2, string_splited[0].length()));
		String[] string_splited_twice = 
				string_splited[1].substring(0, string_splited[1].length() - 3).split(",");
		List<Probability> variable_dependencies = new ArrayList<>();
		for (int i = 0; i < string_splited_twice.length; i++)
			variable_dependencies.add(create_probability(string_splited_twice[i]));
		return new Query(new Condition(variable_probability, variable_dependencies), 
				string_query.charAt(string_query.length() - 1));
	}

	/**
	 * This method create a new Probability.
	 * @param variable
	 * @return the Probability.
	 */
	private static Probability create_probability(String variable) {
		String[] string_splited = variable.split("=");
		return new Probability(string_splited[0].charAt(0), string_splited[1]);
	}

	/**
	 * This function create the Variable.
	 * @param it
	 * @return the Variable.
	 */
	private static Variable create_variable(Iterator<String> it) {
		char variable_name =  it.next().charAt(4);
		List<String> values = create_values(it.next()); 
		List<Character> parents = create_parents(it.next());
		it.next(); // "CPT:" line.
		List<Cond_prob> c_p = new ArrayList<>();
		String line = it.next();
		while(!line.trim().isEmpty()) {
			c_p.add(create_cond_prob(variable_name, line, parents, values.get(values.size() - 1)));
			line = it.next();
		}
		return new Variable(variable_name, values, parents, c_p);
	}

	/**
	 * This method create the CPT.
	 * @param variable_name
	 * @param string_cond_prob
	 * @param parents
	 * @return the CPT.
	 */
	private static Cond_prob create_cond_prob(
			char variable_name,
			String string_cond_prob,
			List<Character> parents,
			String hidden_value) {
		Iterator<Character> it_cond_prob = from_string_to_iterator(string_cond_prob);
		List<Probability> dependencies = create_dependencies(it_cond_prob, parents);
		HashMap<Condition, Double> probability = new HashMap<>();
		double sum = 0.0;
		while(it_cond_prob.hasNext()) {
			Condition c = create_condition(variable_name, it_cond_prob, dependencies);
			double d = create_double(it_cond_prob);
			sum += d;
			probability.put(c, d);
		}
		probability.put(new Condition(
				new Probability(variable_name, hidden_value), 
				dependencies), 
				(1 - sum));
		return new Cond_prob(probability);
	}

	/**
	 * This function create the double which is the answer of the probability.
	 * @param it_cond_prob
	 * @return the double.
	 */
	private static Double create_double(Iterator<Character> it_cond_prob) {
		String double_string = "";
		char character = it_cond_prob.next();
		while(character != ',') {
			if(!it_cond_prob.hasNext()) {
				double_string += character;
				break;
			}
			double_string += character;
			character = it_cond_prob.next();
		}
		return Double.parseDouble(double_string);
	}

	/**
	 * This function create the condition.
	 * @param variable_name
	 * @param it_cond_prob
	 * @param dependencies
	 * @return the Condition.
	 */
	private static Condition create_condition(
			char variable_name, 
			Iterator<Character> it_cond_prob, 
			List<Probability> dependencies) {
		String value = "";
		char character = it_cond_prob.next();
		while(character != ',') {
			if (character!= '=')
				value += character;
			character = it_cond_prob.next();
		}
		return new Condition(new Probability(variable_name, value), dependencies);
	}

	/**
	 * This method create the dependencies.
	 * @param it_cond_prob
	 * @param parents
	 * @return list of Probability.
	 */
	private static List<Probability> create_dependencies(
			Iterator<Character> it_cond_prob, 
			List<Character> parents) {
		if(parents == null)
			return null;
		List<Probability> dependencies = new ArrayList<>();
		Iterator<Character> it = parents.iterator();
		String parent_value = "";
		char character = it_cond_prob.next();
		while(character != '=') {
			if(character == ',') {
				dependencies.add(new Probability(it.next(), parent_value));
				parent_value = "";
			}
			if(character != ',')
				parent_value += character;
			character = it_cond_prob.next();
		}
		return dependencies;
	}

	/**
	 * This method create the parent.
	 * @param string_parents
	 * @return list of Character which are the parent.
	 */
	private static List<Character> create_parents(String string_parents) {
		if(string_parents.contains("none"))
			return null;
		List<Character> parents = new ArrayList<>();
		for (int i = 9; i < string_parents.length(); i+=2) {
			parents.add(string_parents.charAt(i));
		}
		return parents;
	}

	/**
	 * This function create the values.
	 * @param string_values
	 * @return a list of String (the values).
	 */
	private static List<String> create_values(String string_values) {
		List<String> values = new ArrayList<>();
		String value = "";
		for (int i = 8; i < string_values.length(); i++) {
			if(string_values.charAt(i) == ',') {
				values.add(value);
				value = "";
				i+=2;
			}
			value += string_values.charAt(i);
		}
		values.add(value);
		return values;
	}
	
	/**
	 * This method check roughly the format of the txt.
	 * TODO: Need to improve the checking.
	 * @param iterator
	 * @throws Wrong_txt
	 */
	private static void verifying_txt(Iterator<String> iterator) throws Wrong_txt {
		if(!iterator.next().contains("Network"))
			throw new Wrong_txt("The file doesn't begin with Network...");
		String variables_string = iterator.next();
		if(!variables_string.contains("Variables:"))
			throw new Wrong_txt("The file doesn't include Variables...");
		while(iterator.hasNext())
			if(iterator.next().contains("Queries"))
				return;
		throw new Wrong_txt("The file doesn't include Queries...");
	}
	
	/**
	 * This method convert a string to an iterator char-char.
	 * @param string
	 * @return the iterator.
	 */
	private static Iterator<Character> from_string_to_iterator(String string) {
		char[] array = string.toCharArray();
		Stream<Character> myStreamOfCharacters = IntStream
		          .range(0, array.length)
		          .mapToObj(i -> array[i]);
		return myStreamOfCharacters.collect(Collectors.toList()).iterator();
	}
}
