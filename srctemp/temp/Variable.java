package temp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sam
 * This class represents the object Variable.
 */
public class Variable {

	private char name; // The name is injective (PK).
	private List<String> values; 
	private List<Character> parents;
	private List<Cond_prob> c_p;

	/**
	 * Constructor.
	 * @param name
	 * @param values
	 * @param parents
	 * @param c_p
	 */
	public Variable(char name, List<String> values, List<Character> parents, List<Cond_prob> c_p) {
		this.name = name;
		this.values = values;
		this.parents = parents;
		this.c_p = c_p;
	}

	/**
	 * This method return the probability.
	 * @param value
	 * @param probabilities
	 * @return the probability.
	 */
	public double probability(String value, List<Probability> probabilities) {
		if (this.getParents() == null) 
			return find_probability_by_value_for_lone_soldier(value);
		else {
			List<Probability> parent_value = this.match_parents(probabilities);
			return find_value_by_condition(
					new Condition(new Probability(this.getName(), value),
							parent_value
							));
		}
	}

	/**
	 * This method return the probability for a lone soldier.
	 * Lone soldier = variable alone.
	 * @param value
	 * @return the probability of the lone soldier.
	 */
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

	/**
	 * This method find the value for a given condition.
	 * @param condition_query
	 * @return the probability for the condition.
	 */
	private double find_value_by_condition(Condition condition_query) {
		if (condition_query.getVariable_probabilty().getVariable_value()
				.equals(this.values.get(values.size() - 1))) {
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

	/**
	 * This method find the probability for a given condition.
	 * @param condition_query
	 * @return
	 */
	private double find_probability_by_condition(Condition condition_query) {
		for (Cond_prob cond_prob : this.getC_p()) 
			for(Condition condition : cond_prob.getProbability().keySet()) 
				if(condition.is_equal(condition_query))
					return cond_prob.getProbability().get(condition);
		return 0.0;
	}

	/**
	 * This method return a list of matched parent.
	 * @param probabilities
	 * @return list of probability.
	 */
	private List<Probability> match_parents(List<Probability> probabilities) {
		List<Probability> answer = new ArrayList<>();
		for (Probability probability : probabilities)
			for(Character parent : this.getParents())
				if (probability.getVariable_name() == parent)
					answer.add(probability);
		return answer;
	}

	/**
	 * This method return a list of the inversed values.
	 * @param value
	 * @return a list of String (values).
	 */
	public List<String> get_inverse(String value) {
		List<String> values_inverse = new ArrayList<>(this.values);
		values_inverse.remove(value);
		return values_inverse;
	}

	/**
	 * Get name.
	 * @return name.
	 */
	public char getName() {
		return this.name;
	}

	/**
	 * Get c_p.
	 * @return c_p.
	 */
	public List<Cond_prob> getC_p() {
		return this.c_p;
	}

	/**
	 * Get values.
	 * @return values.
	 */
	public List<String> getValues() {
		return values;
	}

	/**
	 * Get parents.
	 * @return parents.
	 */
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
