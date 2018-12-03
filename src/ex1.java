import java.util.List;

/**
 * @author sam
 */
public class ex1 {

	/**
	 * The main method.
	 * TODO: Need to check how it's work with JAVAC.
	 * TODO: Check if everything correspond with the rules.
	 * TODO: Implements tests.
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> txt = InputTxt.readFileInList("input/input.txt"); 
		try {
			TxtScanner.verifyingTxt(txt.iterator());
		} catch (WrongTxt e) {
			e.printStackTrace();
		}
		Network network = TxtToObjects.createNetwork(txt);
		List<String> answers = RunQuery.runQueries(network);
		System.out.println(answers);
		OutputTxt.writeAnswer(answers);
	}
}
