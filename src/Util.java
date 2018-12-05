import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sam
 * This class is a library for the implementation of the algorithms.
 */
public class Util {

	/**
	 * This method prepare the marginalization for the Algorithm 1.
	 * @param network
	 * @param query
	 * @return the result of the marginalization
	 */
	protected static double prepareMarginalization(Network network, Query query) {
		Events events = new Events(UtilList.concatenateItemWithSet(
				query.getEvidences().getEvents().getEvents(), 
				query.getQuery()));
		Variables hiddenVariables = new Variables(network.getVariables().getOther(events));
		return calculateMarginalization(network, events, cartesianProduct(hiddenVariables));
	}

	/**
	 * This method prepare the inverse of the marginalization for the Algorithm 1.
	 * @param network
	 * @param query
	 * @return the result of the marginalization
	 */
	protected static double prepareInverseMarginalization(Network network, Query query) {
		double answer = 0.0;
		Algorithms.additionCounter--;
		for (Value value : network.getVariables().findVariableByName(
				query.getQuery().getVariable()).getValues().getValues()) {
			if (!value.getValue().equals(query.getQuery().getValue().getValue())) {
				Algorithms.additionCounter++;
				Events events = new Events(UtilList.concatenateItemWithSet(
						query.getEvidences().getEvents().getEvents(), 
						new Event(query.getQuery().getVariable(), value)));
				Variables hiddenVariables = new Variables(network.getVariables().getOther(events));
				answer += calculateMarginalization(network, events, cartesianProduct(hiddenVariables));
			}
		}
		return answer;

	}

	/**
	 * This method return the cartesian product of the given {@link Variables}.
	 * @param hiddenVariable
	 * @return a list of a list of {@link Event}
	 */
	protected static List<List<Event>> cartesianProduct(Variables hiddenVariable) {
		List<List<Event>> events = new ArrayList<List<Event>>();
		for(Variable variable : hiddenVariable.getVariables()) 
			events.add(createListEvent(
					variable.getName(), 
					variable.getValues()));
		return calculateCartesianProduct(events);
	}
	
	/*##################Privates##################*/

	/**
	 * This method calculate the marginalization.
	 * @param network
	 * @param events
	 * @param cartesianProduct
	 * @return the result of the marginalization
	 */
	private static double calculateMarginalization(
			Network network, 
			Events events, 
			List<List<Event>> cartesianProduct) {
		double answer = 0.0;
		Algorithms.additionCounter--;
		for (List<Event> listEvent : cartesianProduct) {
			Algorithms.additionCounter++;
			answer += network.getVariables().calculateProbability(
					Stream.concat(listEvent.stream(), events.getEvents().stream())
					.collect(Collectors.toList()));
		}
		return answer;
	}

	/**
	 * This method do a Cartesian product for two given list.
	 * This method is implemented from: 
	 * https://stackoverflow.com/questions/714108/cartesian-product-of-arbitrary-sets-in-java.
	 * @param lists
	 * @return the result of the Cartesian product.
	 */
	private static List<List<Event>> calculateCartesianProduct(List<List<Event>> lists) {
		List<List<Event>> resultLists = new ArrayList<List<Event>>();
		if (lists.size() == 0) {
			resultLists.add(new ArrayList<Event>());
			return resultLists;
		} else {
			List<Event> firstList = lists.get(0);
			List<List<Event>> remainingLists = calculateCartesianProduct(lists.subList(1, lists.size()));
			for (Event event : firstList) {
				for (List<Event> remainingList : remainingLists) {
					ArrayList<Event> resultList = new ArrayList<Event>();
					resultList.add(event);
					resultList.addAll(remainingList);
					resultLists.add(resultList);
				}
			}
		}
		return resultLists;
	}

	/**
	 * For a given name and values, this function create a list of event composed
	 * by the name and the numerous values.
	 * @param name
	 * @param values
	 * @return a list of {@link Event}
	 */
	private static List<Event> createListEvent(String name, Values values) {
		List<Event> events = new ArrayList<>();
		for(Value value : values.getValues())
			events.add(new Event(name, value));
		return events;
	}

}
