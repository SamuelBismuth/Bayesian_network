import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sam
 * This class is a library (only static function).
 * Here all the functions which convert the txt into objects.
 */
public class TxtToObjects {

	static List<String> dependentVariable;

	/**
	 * This method creates the network from the txt.
	 * @param txt
	 * @return the object {@link Network}
	 */
	protected static Network createNetwork(List<String> txt) {
		Iterator<String> it = txt.subList(1, txt.size()).iterator();
		Variables variables = createVariables(it);
		it.next();
		Queries queries = createQueries(it);
		return new Network(variables, queries);
	}

	/**
	 * The function creates a new {@link Probability}.
	 * The parameter must be of the form "true,true,true,0.95"
	 * @param probability
	 * @return the {@link Probability}.
	 */
	protected static Probability createProbability(String probability) {
		List<String> stringSplited = Arrays.asList(probability.split(","));
		return new Probability(
				createEvents(stringSplited.subList(0, stringSplited.size() - 1)), 
				createDouble(stringSplited.get(stringSplited.size() - 1)));
	}

	// Private statics functions.

	/**
	 * This method creates the {@link Variables} of the {@link Network}.
	 * @param it
	 * @return the {@link Variables}.
	 */
	private static Variables createVariables(Iterator<String> it) {
		// - 11 for variables: and + 1 for the upper bound.
		int numberOfVariable = ((it.next().length() - 11) + 1 )/ 2;  
		Set<Variable> variables = new LinkedHashSet<Variable>();
		it.next();
		for (int i = 0; i < numberOfVariable; i++) 
			variables.add(createVariable(it));
		return new Variables(variables);
	}

	/**
	 * This method creates one {@link Variable}.
	 * @param it
	 * @return the object {@link Variable}
	 */
	private static Variable createVariable(Iterator<String> it) {
		String variableName =  it.next().split(" ")[1];
		Values values = createValues(it.next().split(":")[1]);
		Set<String> parents = createParents(it.next().split(" ")[1]);
		if(parents == null) 
			dependentVariable = UtilList.concatenateItemWithlist(null, variableName);
		else
			dependentVariable = UtilList.concatenateItemWithlist(new ArrayList<>(parents), variableName);
		CPTs cpts = createCPTs(it, values, variableName);
		return new Variable(variableName, values, parents, cpts);
	}

	/**
	 * This function creates the object {@link Values}.
	 * The parameter must be of the form " set, noset, maybe".
	 * @param values
	 * @return the {@link Values}
	 */
	private static Values createValues(String values) {
		Set<Value> setValues = new LinkedHashSet<>();
		for (String string : values.split(",")) {
			string = string.replaceAll("\\s+","");
			setValues.add(createValue(string));
		}
		return new Values(setValues);
	}

	/**
	 * This function create one {@link Value}.
	 * The parameter must be of the form " set"
	 * @param value
	 * @return the {@link Value}
	 */
	private static Value createValue(String value) {
		value.replaceAll("\\s+","");
		return new Value(value);
	}

	/**
	 * This method creates the parents.
	 * The parameter must be of the form " none" or "A,B"
	 * @param parents
	 * @return a set of {@link String}
	 */
	private static Set<String> createParents(String parents) {
		if (parents.contains("none"))
			return null;
		Set<String> setParents = new LinkedHashSet<>();
		for (String string : parents.split(",")) {
			string = string.replaceAll("\\s+","");
			setParents.add(string);
		}
		return setParents;
	}

	/**
	 * This method creates the {@link CPTs}.
	 * @param it
	 * @param variableName 
	 * @param values 
	 * @return the {@link CPTs}
	 */
	private static CPTs createCPTs(Iterator<String> it, Values values, String variableName) {
		it.next(); // "CPT:" line.
		Set<CPT> cpt = new LinkedHashSet<>();
		String line = it.next();
		while(!line.trim().isEmpty()) {
			CPT cptNotComplete = createCPT(line);
			cptNotComplete.update(values, variableName);
			cpt.add(cptNotComplete);
			line = it.next();
		}
		return new CPTs(cpt);
	}

	/**
	 * This function create one {@link CPT}.
	 * The parameter must be of the form "true,set,=go,0.25,=stay,0.7"
	 * @param cpt
	 * @return the {@link CPT}.
	 */
	private static CPT createCPT(String cpt) {
		Set<Probability> setProbability = new LinkedHashSet<>();
		String[] strings = cpt.split("=");
		for(int i = 1; i <  strings.length; i ++)
			setProbability.add(createProbability(strings[0] + strings[i]));
		return new CPT(setProbability);
	}

	/**
	 * The method create a new {@link Events}.
	 * The parameter must be of the form List: true,true,...
	 * @param events
	 * @return {@link Events}
	 */
	private static Events createEvents(List<String> events) {
		Set<Event> setEvents = new LinkedHashSet<>();
		Iterator<String> it = dependentVariable.iterator();
		for(String event : events) {
			if (event.contains("="))
				setEvents.add(createEvent(event));
			else
				setEvents.add(createEvent(it.next() + "=" + event));
		}
		return new Events(setEvents);
	}

	/**
	 * This methode create a new {@link Event}.
	 * The parameter must be of the form "A=true"
	 * @param event
	 * @return {@link Event}
	 */
	private static Event createEvent(String event) {
		String[] stringSplited = event.split("=");
		return new Event(stringSplited[0], createValue(stringSplited[1]));
	}

	/**
	 * This function creates a {@link Double}.
	 * The parameter must be of the form "0.8".
	 * @param doub
	 * @return the number of the probability.
	 */
	private static double createDouble(String doub) {
		return Double.parseDouble(doub);
	}

	/**
	 * This functions creates the object {@link Queries}.
	 * @param it
	 * @return {@link Queries}.
	 */
	private static Queries createQueries(Iterator<String> it) {
		Set<Query> queries = new LinkedHashSet<>();
		String line = it.next();
		while(!line.trim().isEmpty()) {
			queries.add(createQuery(line));
			line = it.next();
		}
		return new Queries(queries);
	}

	/**
	 * This functions creates a single {@link Query}.
	 * The parameter must be of the form "P(C=run|B=set,A=true),1"
	 * @param query
	 * @return {@link Query}
	 */
	private static Query createQuery(String query) {
		String[] strings = query.split("\\)");
		String[] stringss = strings[0].split("\\|");
		return new Query(createEvent(stringss[0].substring(2, stringss[0].length())), 
				createEvidences(stringss[1].substring(0, stringss[1].length())), 
				strings[1].charAt(1));
	}

	/**	
	 * This method create the {@link Evidences} for the {@link Query}.
	 * The parameter must be of the forthis.getAlgorithm()m "A=true,B=true...".
	 * @param evidences
	 * @return {@link Evidences}
	 */
	private static Evidences createEvidences(String evidences) {
		return new Evidences(createEvents(Arrays.asList(evidences.split(","))));
	}

}
