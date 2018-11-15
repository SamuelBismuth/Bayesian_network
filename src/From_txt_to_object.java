import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		while(!line.trim().isEmpty()) {
			//TODO: Implement the conversion of the cond_prob.
			line = it.next();
		}
		return null;
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
