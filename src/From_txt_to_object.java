import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.CharacterAction;

public class From_txt_to_object {
	
	/**
	 * Can we assume that the txt is always with the same format?
	 * -> Include space and space, and even comma.
	 * Can we assume the variable's name with a character?
	 * @param list_txt
	 * @return
	 * @throws Wrong_txt
	 */
	public static Network create_network(List<String> list_txt) throws Wrong_txt {
		if(!list_txt.get(0).contains("Network"))
			throw new Wrong_txt("The file doesn't begin with Network...");
		if(!list_txt.get(1).contains("Variables:"))
			throw new Wrong_txt("The file doesn't include Variables...");
		// - 11 for variables: and + 1 for the upper bound.
		int number_of_variable = ((list_txt.get(1).length() - 11) + 1 )/ 2;  
		List<Variable> variables = new ArrayList<Variable>();
		Iterator<String> it = list_txt.subList(3, list_txt.size()).iterator();
		for (int i = 0; i < number_of_variable; i++) {
			variables.add(create_variable(it));
		}
		// TODO.
		List<Query> queries = null;
		return new Network(variables, queries);
	}

	private static Variable create_variable(Iterator<String> it) {
		String line = "Not_Null";
		char variable_name =  it.next().charAt(4);
		List<String> values = create_values(it.next()); 
		List<Character> parents = create_parents(it.next());
		it.next(); // "CPT:" line.
		List<Cond_prob> c_p = new ArrayList<>();
		while(!line.trim().isEmpty()) {
			c_p.add(create_cond_prob(it.next(), parents));
			line = it.next();
		}
		return new Variable(variable_name, values, parents, c_p);
	}

	private static Cond_prob create_cond_prob(String string_cond_prob, List<Character> parents) {
		String[] array = string_cond_prob.split("");
		Iterator<String> it_cond_prob = new ArrayList<>(Arrays.asList(array)).iterator();
		HashMap<String, Character> dependencies = create_dependencies(it_cond_prob, parents);
		HashMap<Condition, Integer> probability = new HashMap<>();
		while(it_cond_prob.hasNext()) 
			probability.put(create_condition(it_cond_prob, dependencies), create_integer(it_cond_prob);
		
		return null;
	}

	private static Integer create_integer(Iterator<String> it_cond_prob) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Condition create_condition(Iterator<String> it_cond_prob, HashMap<String, 
											  Character> dependencies) {
		String value = "";
		String character = it_cond_prob.next();
		while(character != ",") {
			value += character;
			character = it_cond_prob.next();
		}
		return null;
	}

	private static HashMap<Condition, Integer> create_probability(Iterator<String> it_cond_prob,
																  HashMap<String, Character> dependencies) {
		
		for (int j = i++; j < string_cond_prob.length(); j++) {
			if(string_cond_prob.charAt(i) == ',') {
				Condition condition = new Condition(value, dependencies);
				value = "";
			}
			value += string_cond_prob.charAt(j);
		}
	}

	private static HashMap<String, Character> create_dependencies(Iterator<String> it_cond_prob, 
																  List<Character> parents) {
		HashMap<String, Character> dependencies = new HashMap<>();
		Iterator<Character> it = parents.iterator();
		String parent_value = "";
		String character = it_cond_prob.next();
		while(character != "=") {
			if(character == ",") {
				dependencies.put(parent_value, it.next());
				parent_value = "";
				character = it_cond_prob.next();
			}
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
