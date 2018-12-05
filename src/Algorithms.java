import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Set;

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
	public static int additionCounter = 0,
			mulitiplicationCounter = 0;
	// The format for the round.
	static DecimalFormat df = new DecimalFormat("#0.00000");

	/**
	 * This function implements the steps of the algorithm 1.
	 * Notice that the algorithm 1 is not really an algorithm but more a simple prediction.
	 * @param network
	 * @param query
	 * @return the result of the query including the counter in the well form
	 */
	protected static String algorithm1(Network network, Query query) {	
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
	 * @return the result of the query including the counter in the well form
	 */
	protected static String algorithm2(Network network, Query query) {
		additionCounter = mulitiplicationCounter = 0; 
		Set<Variable> deletedVariable = network.getVariables().
				deleteIrrelevant(network.getVariables().findVariablesByNames(query.getAllVariableName()));
		Factors factors = network.getVariables().createFactors(query);
		factors.run(true);
		Factor factor = factors.unionAll(new ArrayList<>(factors.getFactors()), network.getVariables().
				findVariableByName(query.getQuery().getVariable()));
		factor.normalize();
		network.getVariables().getVariables().addAll(deletedVariable);
		return df.format(factor.
				getFinalProbability(query.getQuery().getValue())) + "," +
				Integer.toString(additionCounter) + "," +
				Integer.toString(mulitiplicationCounter);
	}

	/**
	 * This function implements the algorithm Variable elimination with ordering the factos.
	 * The order is by the number of neighbor.
	 * @param network
	 * @param query
	 * @return the result of the query including the counter in the well form
	 */
	protected static String algorithm3(Network network, Query query) {
		additionCounter = mulitiplicationCounter = 0; 
		Set<Variable> deletedVariable = network.getVariables().
				deleteIrrelevant(network.getVariables().findVariablesByNames(query.getAllVariableName()));
		Factors factors = network.getVariables().createFactors(query);
		factors.run(false);
		Factor factor = factors.unionAll(new ArrayList<>(factors.getFactors()), network.getVariables().
				findVariableByName(query.getQuery().getVariable()));
		factor.normalize();
		network.getVariables().getVariables().addAll(deletedVariable);
		return df.format(factor.
				getFinalProbability(query.getQuery().getValue())) + "," +
				Integer.toString(additionCounter) + "," +
				Integer.toString(mulitiplicationCounter);
	}

}
