import java.util.List;

/**
 * @author sam
 */
public class ex1 {

	/**
	 * The main method.
	 * TODO: Need to check how it's work with JAVAC.
	 * TODO: Implements the output.
	 * TODO: Check if everything correspond with the rules.
	 * TODO: Implements tests.
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list_txt = Input_txt.readFileInList("input/input2.txt"); 
		Network network = From_txt_to_object.create_network(list_txt);
		System.out.println(Run_query.run_queries(network));
		//System.out.println(network.toString());
	}
	
}
