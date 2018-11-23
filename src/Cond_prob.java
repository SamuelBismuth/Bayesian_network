import java.util.HashMap;

/**
 * @author sam
 * This class represents the object conditional probability.
 * i.e CPT is the txt file.
 */
public class Cond_prob {

	private HashMap<Condition, Double> probability;  // HashMap:  condition -> probability.

	/**
	 * Constructor.
	 * @param probability
	 */
	public Cond_prob(HashMap<Condition, Double> probability) {
		this.probability = probability;
	}

	/**
	 * Get probability.
	 * @return probability.
	 */
	protected HashMap<Condition, Double> getProbability() {
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
