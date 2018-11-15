import java.util.List;

public class Variable {

	private List<Values> values;
	private List<Variable> parents;
	private List<Cond_prob> c_p;
	
	public Variable(List<Values> values, List<Variable> parents, List<Cond_prob> c_p) {
		this.values = values;
		this.parents = parents;
		this.c_p = c_p;
	}
	
}
