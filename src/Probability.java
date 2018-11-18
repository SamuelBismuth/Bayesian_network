
public class Probability {

	private char variable_name;
	private String variable_value;
	
	public Probability(char variable_name, String variable_value) {
		this.variable_name = variable_name;
		this.variable_value = variable_value;
	}

	@Override
	public String toString() {
		return variable_name + "=" + variable_value;
	}
	
	
	
	
}
