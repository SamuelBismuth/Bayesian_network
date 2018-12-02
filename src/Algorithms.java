import java.text.DecimalFormat;

/**
 * @author sam
 * Class Algorithms.
 * This class includes only static method (library).
 * The three main methods are the three implemented algorithms.
 * Each algorithm answer to a query of the format: P(A|B,C).
 * To answer to all the query we have a Bayesian Network already build.
 */
public class Algorithms {

	// Two static variable counter.
	public static int additionCounter 		  = 0,
			mulitiplicationCounter  = 0;
	// The format for the round.
	static DecimalFormat df = new DecimalFormat("#0.00000");

	/**
	 * This function implements the steps of the algorithm 1.
	 * Notice that the algorithm 1 is not really an algorithm but more a simple prediction.
	 * @param network
	 * @param query
	 * @return the result of the query including the counter in the well form.
	 * Y1:5.922425899999999E-4
		Y2:0.001491857649
		Y1:8.49017E-4
		Y2:1.5098300000000004E-4
	 */
	protected static String algorithm_1(Network network, Query query) {	
		additionCounter = mulitiplicationCounter = 0; 
		double Y1 = Util.prepareMarginalization(network, query);
		double Y2 = Util.prepareInverseMarginalization(network, query);
		additionCounter++;
		return df.format((1 / (Y1 + Y2)) * Y1) + "," +
		Integer.toString(additionCounter) + "," +
		Integer.toString(mulitiplicationCounter);
	}

	/**
	 * This function implements the algorithm Variable elimination.
	 * @param network
	 * @param query
	 * @return the result of the query including the counter in the well form.
	 */
	protected static String algorithm_2(Network network, Query query) {
		/*addition_counter = mulitiplication_counter = 0; 
		List<Variable> deleted_variable = network.delete_irrelevant(query.get_all_variable(network));
		Factors factors = network.create_factors(network.getVariables(), 
				query.getCondition().getVariable_dependencies());
		factors.run(true);
		Factor factor = factors.unionAll(factors.getFactors(), network.get_searched_query(query));
		factor.normalize();
		network.getVariables().addAll(deleted_variable);
		return df.format(factor.get_final_double(query.getCondition().
				getVariable_probabilty().getVariable_value())) + "," +
				Integer.toString(addition_counter) + "," +
				Integer.toString(mulitiplication_counter);*/
		return null;
	}

	/**
	 * TODO: Implements a new way to sort the factors and explained the logic.
	 * @param network
	 * @param query
	 * @return
	 */
	protected static String algorithm_3(Network network, Query query) {
		return null;
	}

}
