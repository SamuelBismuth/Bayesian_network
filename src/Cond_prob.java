import java.util.HashMap;

public class Cond_prob {

	private HashMap<Condition, Double> probability;  // HashMap:  condition -> probability.

	public Cond_prob(HashMap<Condition, Double> probability) {
		this.probability = probability;
	}
	
	public double cond_prob_by_value(String value) {
		double added_value = 0.0;
		for (Condition condition : probability.keySet()) {
			added_value += probability.get(condition);
			if (condition.is_condition_by_value(value))
				return probability.get(condition);
		}
		return 1.0 - added_value;
	}

	@Override
	public String toString() {
		String answer = "";
		for (Condition key: probability.keySet()) {
			answer += "P(" + key.toString();
			answer += ")";
			answer += "=" + probability.get(key) + "\n";
		}
		return answer;
	}
}
