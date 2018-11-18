import java.util.List;

public class Variable {

	private char name; // The name is injective (PK).
	private List<String> values; 
	private List<Character> parents;
	private List<Cond_prob> c_p;
	
	public Variable(char name, List<String> values, List<Character> parents, List<Cond_prob> c_p) {
		this.name = name;
		this.values = values;
		this.parents = parents;
		this.c_p = c_p;
	}
	
	// TODO: Implement the function to get the number of the last variable.
	
	public char get_name() {
		return this.name;
	}
	
	public List<Cond_prob> get_c_p() {
		return this.c_p;
	}
	
	
	
}
