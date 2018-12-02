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

	public String toString() {
		String answer = "";
		for (CPT cpt : this.getCpts())
			answer += cpt.toString();
		return answer;
	}
	
}
