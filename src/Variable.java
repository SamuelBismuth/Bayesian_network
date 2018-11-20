import java.util.ArrayList;
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

	public double probability(String value, List<Probability> probabilities) {
		if (this.getParents() == null) {
			//System.out.println("Variable" + this.getName());
			//System.out.println("Value" + value);
			return 1.0; // return getDouble();
		}
		//System.out.println("Variable" + this.getName());
		//System.out.println("Value" + value);
		//System.out.println("Parents" + this.getParents().toString());
		//System.out.println("Probabilities" + probabilities.toString());
		List<Probability> parent_value = this.match_parents(probabilities);
		//System.out.println("Parent value" + parent_value.toString());
		//System.out.println("#####################################");
		System.out.println(find_value_by_condition(
				new Condition(new Probability(this.getName(), value),
						parent_value
						)));
		return name;
	}

	private double find_value_by_condition(Condition condition_query) {
		for (Cond_prob cond_prob : this.getC_p()) {
			for(Condition condition : cond_prob.getProbability().keySet()) {
				//System.out.println("Condition" + condition.toString());
				//System.out.println("condition query" + condition_query.toString());
				if(condition_query.is_equal(condition)) {
					System.out.println("hello");
					return cond_prob.getProbability().get(condition);
				}
			}
		}
		return 0.0;

	}

	private List<Probability> match_parents(List<Probability> probabilities) {
		List<Probability> answer = new ArrayList<>();
		for (Probability probability : probabilities)
			for(Character parent : this.getParents())
				if (probability.getVariable_name() == parent)
					answer.add(probability);
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
