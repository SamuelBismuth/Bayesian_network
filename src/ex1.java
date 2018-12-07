import java.util.List;

/**
 * @author sam
 */
public class ex1 {

	/**
	 * The main method.
	 * TODO: Check input6 error.
	 * TODO: Check network grades: 60.
	 * TODO: Check about the algo 3, ordering must be update?
	 * TODO: Submission must be as: src + pdf with explanation (network and moodle?).
	 * TODO: Complete the paper and explanation heuristic.
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> txt = InputTxt.readFileInList("input.txt"); 
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