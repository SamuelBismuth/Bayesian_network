import java.util.HashMap;

public class Cond_prob {
	
	private HashMap<Condition, Double> probability;  // HashMap:  condition -> probability.
	
	public Cond_prob(HashMap<Condition, Double> probability) {
		this.probability = probability;
	}
	
	@Override
	public String toString() {
		return probability.toString();
	}
}
