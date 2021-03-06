import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sam
 * This class represents the object {@link Variable}.
 */
public class Variable {

	private String name;  // The name is injective (PK).
	private Values values;  // The values of the variable.
	private Set<String> parents;  // The parents of the variable.
	private CPTs cpts;  // The condtion probability table of the variable.

	/**
	 * Constructor for a variable, all the fields are required.
	 * @param name
	 * @param values
	 * @param parents
	 * @param cpts
	 */
	public Variable(String name, Values values, Set<String> parents, CPTs cpts) {
		this.name = name;
		this.values = values;
		this.parents = parents;
		this.cpts = cpts;
	}

	/**
	 * This method checks if the events are include on the variable.
	 * @param events
	 * @return true if is include else false
	 */
	protected boolean isInclude(Events events) {
		for (Event event : events.getEvents())
			if(event.getVariable().equals(this.getName()))
				return false;
		return true;
	}

	/**
	 * For a given list of {@link Event}, this function returns a list of {@link Event}
	 * which are matched with the parameter.
	 * @param events
	 * @return the list of {@link Event}
	 */
	protected List<Event> matchParent(List<Event> events) {
		return events.
				stream().
				filter(thisEvent -> this.getParents().stream().
						anyMatch(parent -> parent.equals(thisEvent.getVariable()))).
				collect(Collectors.toList());
	}

	/*##################Getters##################*/

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the values
	 */
	public Values getValues() {
		return values;
	}

	/**
	 * @return the parents
	 */
	public Set<String> getParents() {
		return parents;
	}

	/**
	 * @return the cpts
	 */
	public CPTs getCpts() {
		return cpts;
	}

}
