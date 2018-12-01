import java.util.Set;

public class CPTs {

	private Set<CPT> cpts;  // Set of table.

	/**
	 * Constructor for {@link CPTs}.
	 * @param cpts
	 */
	public CPTs(Set<CPT> cpts) {
		this.cpts = cpts;
	}

	/**
	 * @return the cpts
	 */
	public Set<CPT> getCpts() {
		return cpts;
	}

	public void updateAll(Values values, String variableName) {
		for(CPT cpt : cpts)
			cpt.update(values, variableName);
	}
	
}
