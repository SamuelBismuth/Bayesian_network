package temp;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sam
 * This class represents the object conditional probability.
 * i.e CPT is the txt file.
 */
public class Cond_prob {

	private HashMap<Condition, Double> probability;  // HashMap:  condition -> probability.

	/**
	 * Get the sum of all the probabilities.
	 * @return the sum of all the probabilities.
	 */
	public double get_sum() {
		double ans = 0.0;
		for(Condition cond : this.getProbability().keySet()) 
			ans += this.getProbability().get(cond);
		return ans;
	}

	public void deleteEvidence(List<Probability> evidence) {
		for (Condition condition : this.getProbability().keySet()) 
			for (Probability probability : condition.getJoin_probability())
				if (probability.is_include(evidence))
					this.getProbability().remove(condition);
	}

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
