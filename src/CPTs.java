import java.util.List;
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
	 * Assuming there is only one table since the {@link Variable} is without parent.
	 * @param value
	 * @return
	 */
	protected double getProbabilityAloneSoldier(Value value) {
		return this.getCpts().iterator().next().getProbability(value);
	}
	
	protected double getProbability(Value value, List<Event> parentsValues) {
		for (CPT cpt : this.getCpts()) 
			if (cpt.matchStaticEvent(parentsValues)) 
				return cpt.getProbability(value);
		return 0.0;
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
