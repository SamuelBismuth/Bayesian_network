package temp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * @author sam
 * This class is a junit test fot the function is equal of the class condition.
 */
class ConditionTest {

	@Test
	void testIs_equal() {
		Probability prob1 = new Probability('E', "true");
		Probability prob2 = new Probability('B', "true");
		List<Probability> list1 = new ArrayList<>();
		list1.add(prob1);
		list1.add(prob2);
		List<Probability> list2 = new ArrayList<>();
		list2.add(prob2);
		list2.add(prob1); // Notice the difference of the order.
		Condition condition1 = new Condition(new Probability('A', "true"), list1);
		Condition condition2 = new Condition(new Probability('A', "true"), list2);
		System.out.println();
		assertTrue(condition1.is_dependencies_equal(condition2));
	}

}
