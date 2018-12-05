import java.util.List;
import java.util.Set;

/**
 * @author sam
 * This class represents the object {@link CPTs}.
 */
public class CPTs {

	private Set<CPT> cpts;  // Set of tables.

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
	 * @return the probability given the {@link Value} of the dynamic event
	 */
	protected double getProbabilityAloneSoldier(Value value) {
		return this.getCpts().iterator().next().getProbability(value);
	}
	
	/**
	 * This function return the probability of the next query:
	 * Given the value of the stable event and the value of the dynamic event, 
	 * we can assume that there is only one match, and then only one probability.
	 * This is the probability that this method returns.
	 * @param value
	 * @param parentsValues
	 * @return the probability in double
	 */
	protected double getProbability(Value value, List<Event> parentsValues) {
		for (CPT cpt : this.getCpts()) 
			if (cpt.matchStaticEvent(parentsValues)) 
				return cpt.getProbability(value);
		return 0.0;
	}
	
	/*##################Getter##################*/

	/**
	 * @return the cpts
	 */
	public Set<CPT> getCpts() {
		return cpts;
	}

}
