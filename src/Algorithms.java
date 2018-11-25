import java.text.DecimalFormat;

/**
 * @author sam
 * Class Algorithms.
 * This class includes only static method (library).
 * The three main methods are the three implemented algorithms.
 * Each algorithm answer to a query of the format: P(A|B,C).
 * To answer to all the query we have a Bayesian Network already build.
 * Here is the format for the answer:
 * 
 * 1: The result rounded five numbers after the point.
 *  ,
 * 2: The number of addition.
 * ,
 * 3: The number of multiplication.
 */
public class Algorithms {

	// Two static variable counter.
	static protected int addition_counter 		 = 0,
						 mulitiplication_counter = 0;
	// The format for the round.
	static DecimalFormat df = new DecimalFormat("#0.00000");

	/**
	 * This function implements the steps of the algorithm 1.
	 * Notice that the algorithm 1 is not really an algorithm but more a simple prediction.
	 * @param network
	 * @param query
	 * @return the result of the query including the counter in the well form.
	 */
	protected static String algorithm_1(Network network, Query query) {	
		addition_counter = mulitiplication_counter = 0; 
		double Y1 = Util.prepare_marginalization(
				network, 
				query.get_all_probability());
		double Y2 = Util.calculate_inverse_marginalization(
				network, 
				query.getCondition().getVariable_dependencies(),
				query.getCondition().getVariable_probabilty());
		addition_counter++;
		return df.format((1 / (Y1 + Y2)) * Y1) + "," +
		Integer.toString(addition_counter) + "," +
		Integer.toString(mulitiplication_counter);
	}

	/**
	 * This function implements the algorithm Variable elimination.
	 * @param network
	 * @param query
	 * @return the result of the query including the counter in the well form.
	 */
	protected static String algorithm_2(Network network, Query query) {
		addition_counter = mulitiplication_counter = 0; 
		Factors factors = network.create_factors(Util.get_factors_variable(network, query), 
				network.get_searched_query(query), query.getCondition().getVariable_dependencies());
		factors.run();
		Factor factor = factors.unionAll(factors.getFactors(), network.get_searched_query(query));
		factor.normalize();
		return df.format(factor.get_final_double(query.getCondition().
				getVariable_probabilty().getVariable_value())) + "," +
		Integer.toString(addition_counter) + "," +
		Integer.toString(mulitiplication_counter);
	}

	/**
	 * TODO: What is the difference between 2 and 3???
	 * @param network
	 * @param query
	 * @return
	 */
	protected static String algorithm_3(Network network, Query query) {
		return null;
	}

	
}
