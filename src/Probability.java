
public class Probability {

	private char variable_name;
	private String variable_value;
	
	public Probability(char variable_name, String variable_value) {
		this.variable_name = variable_name;
		this.variable_value = variable_value;
	}
	
	protected boolean is_equal(Probability probability) {
		if (this.getVariable_name() == probability.getVariable_name() && 
				this.getVariable_value().equals(probability.getVariable_value()))
			return true;
		return false;
	}
	
	public char getVariable_name() {
		return variable_name;
	}

	public String getVariable_value() {
		return variable_value;
	}

	@Override
	public String toString() {
		return variable_name + "=" + variable_value;
	}
	
	
	
	
}
