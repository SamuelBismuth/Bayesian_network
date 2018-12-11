/**
 * @author sam
 * This class represents the object {@link Value}.
 */
public class Value {

	private String value;  // The String of the value.

	/**
	 * Constructor for the {@link Value}
	 * @param value
	 */
	public Value(String value) {
		this.value = value;
	}

	/*##################Getter##################*/

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	public String toString() {
		return this.getValue();
	}
	
}
