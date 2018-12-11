/**
 * @author sam
 * This class represents the object {@link Event}.
 */
public class Event {

	private String variable;  // The variable of the event.
	private Value value; // The value of the variable.

	/**
	 * Constructor for the {@link Event}.
	 * @param variable
	 * @param value
	 */
	public Event(String variable, Value value) {
		this.variable = variable;
		this.value = value;
	}

	/*##################Getters##################*/

	/**
	 * @return the variable
	 */
	public String getVariable() {
		return variable;
	}

	/**
	 * @return the value
	 */
	public Value getValue() {
		return value;
	}	

	public String toString() {
		return this.getVariable() + "=" + this.getValue();
	}

}
