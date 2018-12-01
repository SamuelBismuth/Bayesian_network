package temp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

class Cond_probTest {

	@Test
	void testDeleteEvidence() {
		HashMap<Condition, Double> probability = new HashMap<>();
		probability.put(
				new Condition(
						Arrays.asList(
								new Probability('A', "true"),
								new Probability('B', "true"),
								new Probability('C', "true"))), 
				0.2);
		HashMap<Condition, Double> probability2 = new HashMap<>();
		probability2.put(
				new Condition(
						Arrays.asList(
								new Probability('A', "false"),
								new Probability('B', "true"),
								new Probability('C', "true"))), 
				0.2);
		Cond_prob cp = new Cond_prob(probability2);
		List<Probability> evidence = Arrays.asList(new Probability('A', "false"));
		HashMap<Condition, Double> answer = cp.deleteEvidence(evidence);
		//System.out.println(answer.toString());
		fail("Not yet implemented");
	}

}
