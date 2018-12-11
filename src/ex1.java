import java.util.List;

/**
 * @author sam
 */
public class ex1 {

	/**
	 * The main method.
	 * TODO: Check network grades: 80.
	 * TODO: Before the submission delete tostring and reformat.
	 * TODO: Check if junit tests are required.
	 * TODO: Explanation of the heuristic.
	 * TODO: Submission must be as: src + pdf with explanation (network and moodle?).
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> txt = InputTxt.readFileInList("inputs/input.txt"); 
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