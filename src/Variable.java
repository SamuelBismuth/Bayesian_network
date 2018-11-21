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
			System.out.println("Variable" + this.getName());
			System.out.println("Value" + value);
			System.out.println("Probabilities" + probabilities.toString());
			System.out.println("Alone soldier");
			System.out.println(find_probability_by_value_for_lone_soldier(value));
			return find_probability_by_value_for_lone_soldier(value);
		}
		else {
			System.out.print("Variable" + this.getName());
			System.out.println("Value" + value);
			System.out.println("Probabilities" + probabilities.toString());
			List<Probability> parent_value = this.match_parents(probabilities);
			System.out.println("Parent value" + parent_value.toString());
			System.out.println(find_value_by_condition(
				new Condition(new Probability(this.getName(), value),
						parent_value
						)));
			return find_value_by_condition(
					new Condition(new Probability(this.getName(), value),
							parent_value
							));
		}
	}

	private double find_probability_by_value_for_lone_soldier(String value) {
		double sum = 0;
		for (Cond_prob cond_prob : this.getC_p()) {
			for(Condition condition : cond_prob.getProbability().keySet()) {
				if(condition.getVariable_probabilty().getVariable_value().equals(value))
					return cond_prob.getProbability().get(condition);
				sum += cond_prob.getProbability().get(condition);
			}
		}
		return 1 - sum;
	}

	// TODO: Ask if possible to assume the cached value as the last value of the arrays.
	private double find_value_by_condition(Condition condition_query) {
		System.out.println(condition_query.getVariable_probabilty().getVariable_value());
		System.out.println(this.values.get(values.size() - 1));
		if (condition_query.getVariable_probabilty().getVariable_value()
				.equals(this.values.get(values.size() - 1))) {
			System.out.println();
			double answer = 0.0;
			for(int i = 0; i < values.size() - 1; i ++) {
				answer += find_probability_by_condition(new Condition(
						new Probability(condition_query.getVariable_probabilty().getVariable_name(),
								values.get(i)),
						condition_query.getVariable_dependencies()
						));
			}
			return 1 - answer;
		}
		else 
			return find_probability_by_condition(condition_query);
	}

	private double find_probability_by_condition(Condition condition_query) {
		for (Cond_prob cond_prob : this.getC_p()) 
			for(Condition condition : cond_prob.getProbability().keySet()) 
				if(condition.is_equal(condition_query)) {
					System.out.println(condition.toString());
					return cond_prob.getProbability().get(condition);
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
