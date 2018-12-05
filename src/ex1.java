import java.util.List;

/**
 * @author sam
 */
public class ex1 {

	/**
	 * The main method.
	 * TODO: Check input6 error.
	 * TODO: Check if everything correspond with the rules by testing and comparing with other.
	 * TODO: Check about the algo 3, ordering must be update?
	 * TODO: Check with the lecturer about the submission.
	 * TODO: Need to check how it's work with JAVAC.
	 * TODO: Complete the paper and explanation heuristic.
	 * TODO: javadoc.
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> txt = InputTxt.readFileInList("input/input2.txt"); 
		try {
			TxtScanner.verifyingTxt(txt.iterator());
		} catch (WrongTxt e) {
			e.printStackTrace();
		}
		Network network = TxtToObjects.createNetwork(txt);
		List<String> answers = RunQuery.runQueries(network);
		OutputTxt.writeAnswer(answers);
	}
	
}