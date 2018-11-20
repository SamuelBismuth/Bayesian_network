import java.util.HashMap;

public class Cond_prob {

	private HashMap<Condition, Double> probability;  // HashMap:  condition -> probability.

	public Cond_prob(HashMap<Condition, Double> probability) {
		this.probability = probability;
	}

	public HashMap<Condition, Double> getProbability() {
		return probability;
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
