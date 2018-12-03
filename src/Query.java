import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sam
 * This class represents the object {@link Query}.
 */
public class Query {

	private Event query;  // The event of the query.
	private Evidences evidences;  // The evidences of the query.
	private char algorithm;  // The used algorithm to answer the query.
	
	/**
	 * Constructor for the {@link Query}, all the fields are required.
	 * @param query
	 * @param evidences
	 * @param algorithm
	 */
	public Query(Event query, Evidences evidences, char algorithm) {
		super();
		this.query = query;
		this.evidences = evidences;
		this.algorithm = algorithm;
	}

	protected Set<String> getAllVariableName() {
		return UtilList.concatenateItemWithSet(
				this.getEvidences().getEvents().getEvents(),this.getQuery()).
				stream().map(item->item.getVariable()).collect(Collectors.toSet());
	}
	
	/**
	 * @return the query
	 */
	public Event getQuery() {
		return query;
	}

	/**
	 * @return the evidences
	 */
	public Evidences getEvidences() {
		return evidences;
	}

	/**
	 * @return the algorithm
	 */
	public char getAlgorithm() {
		return algorithm;
	}
	
	public String toString() {
		return "P(" + this.getQuery().toString() + "|" + this.getEvidences().toString() + ")" +  
				"Algo :"+ this.getAlgorithm();
	}
	
}
