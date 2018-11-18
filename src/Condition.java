import java.util.HashMap;

public class Condition {
	
	private String value; // variable name.
	private HashMap<String, Character> dependencies; // HashMap: parent value -> parent variable name.
	
	public Condition(String value, HashMap<String, Character> dependencies) {
		this.value = value;
		this.dependencies = dependencies;
	}

	@Override
	public String toString() {
		if(dependencies == null) 
			return "Condition [value=" + value + ", dependencies=null]";
		return "Condition [value=" + value + ", dependencies=" + dependencies.toString() + "]";
	}
	
	
}
