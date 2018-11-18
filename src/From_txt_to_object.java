import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class From_txt_to_object {
	
	/**
	 * Can we assume that the txt is always with the same format?
	 * -> Include space and space, and even comma.
	 * Can we assume the variable's name with a character?
	 * @param list_txt
	 * @return
	 * @throws Wrong_txt
	 * Possible checking:
	    if(!it.next().contains("Network"))
			throw new Wrong_txt("The file doesn't begin with Network...");
		String variables_string = it.next();
		if(!variables_string.contains("Variables:"))
			throw new Wrong_txt("The file doesn't include Variables...");
		if(!it.next().contains("Queries"))
			throw new Wrong_txt("The file doesn't include Queries...");
	 */
	public static Network create_network(List<String> list_txt) throws Wrong_txt {
		Iterator<String> it = list_txt.subList(1, list_txt.size()).iterator();
		// - 11 for variables: and + 1 for the upper bound.
		int number_of_variable = ((it.next().length() - 11) + 1 )/ 2;  
		List<Variable> variables = new ArrayList<Variable>();
		it.next();
		for (int i = 0; i < number_of_variable; i++) 
			variables.add(create_variable(it));
		List<Query> queries = new ArrayList<>();
		while(it.hasNext())
			queries.add(create_query(it.next()));
		return new Network(variables, queries);
	}

	private static Query create_query(String string_query) {
		//System.out.println(string_query);
		//char name = string_query.charAt(2);
		
		return null;
	}

	private static Variable create_variable(Iterator<String> it) {
		char variable_name =  it.next().charAt(4);
		List<String> values = create_values(it.next()); 
		List<Character> parents = create_parents(it.next());
		it.next(); // "CPT:" line.
		List<Cond_prob> c_p = new ArrayList<>();
		String line = it.next();
		while(!line.trim().isEmpty()) {
			c_p.add(create_cond_prob(variable_name, line, parents));
			line = it.next();
		}
		return new Variable(variable_name, values, parents, c_p);
	}

	private static Cond_prob create_cond_prob(char variable_name, String string_cond_prob, List<Character> parents) {
		Iterator<Character> it_cond_prob = from_string_to_iterator(string_cond_prob);
		List<Probability> dependencies = create_dependencies(it_cond_prob, parents);
		HashMap<Condition, Double> probability = new HashMap<>();
		while(it_cond_prob.hasNext()) 
			probability.put(create_condition(variable_name, it_cond_prob, dependencies), create_double(it_cond_prob));
		return new Cond_prob(probability);
	}

	private static Iterator<Character> from_string_to_iterator(String string) {
		char[] array = string.toCharArray();
		Stream<Character> myStreamOfCharacters = IntStream
		          .range(0, array.length)
		          .mapToObj(i -> array[i]);
		return myStreamOfCharacters.collect(Collectors.toList()).iterator();
	}
	
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

	private static Condition create_condition(char variable_name, Iterator<Character> it_cond_prob, 
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

	private static List<Probability> create_dependencies(Iterator<Character> it_cond_prob, 
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

	private static List<Character> create_parents(String string_parents) {
		if(string_parents.contains("none"))
			return null;
		List<Character> parents = new ArrayList<>();
		for (int i = 9; i < string_parents.length(); i+=2) {
			parents.add(string_parents.charAt(i));
		}
		return parents;
	}

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
}
