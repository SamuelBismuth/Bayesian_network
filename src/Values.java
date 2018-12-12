import java.util.Set;

/**
 * @author sam
 * This class represents the object {@link Values}.
 */
public class Values {

	private Set<Value> values;  // Set of Value.

	/**
	 * Constructor, all fields are required.
	 * @param values
	 */
	public Values(Set<Value> values) {
		this.values = values;
	}

	/**
	 * This function returns the hidden value between all the values.
	 * @return the hidden {@link Value}
	 */
	protected Value getHidden() {
		return this.getValues().stream().reduce((first, second) -> second)
				.orElse(null);
	}

	/*##################Getter##################*/

	/**
	 * @return the values
	 */
	public Set<Value> getValues() {
		return values;
	}

}
