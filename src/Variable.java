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
	
	public double total_prob(String value) {
		double answer = 1.0;
		for (Cond_prob cond_prob : c_p) 
			answer *= cond_prob.cond_prob_by_value(value);
		return answer;
	}
	
	public char getName() {
		return this.name;
	}
	
	public List<Cond_prob> getC_p() {
		return this.c_p;
	}

	public List<String> getValues() {
		return values;
	}

	public List<Character> getParents() {
		return parents;
	}

	@Override
	public String toString() {
		String answer = "Variable: " + name + "\n"
				+ "values=" + values.toString() + "\n";
		if(parents != null)
				answer += "parents=" + parents.toString() + "\n";
		else
			answer += "parents=none \n";
		answer += c_p.toString();
		return answer;
	}
	
	
	
}
