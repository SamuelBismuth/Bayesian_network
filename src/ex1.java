import java.util.List;

public class ex1 {

	public static void main(String[] args) {
		List<String> list_txt = Input_txt.readFileInList("input/input2.txt"); 
		Network network = From_txt_to_object.create_network(list_txt);
		System.out.println(Run_query.run_queries(network));
		//System.out.println(network.toString());
	}
	
}
