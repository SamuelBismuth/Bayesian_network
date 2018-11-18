import java.util.List;

public class ex1 {

	public static void main(String[] args) {
		List<String> list_txt = Input_txt.readFileInList("input/input.txt"); 
		try {
			Network network = From_txt_to_object.create_network(list_txt);
			System.out.println(network.find_variable_by_name('C').get_c_p().toString());
		} catch (Wrong_txt e) {
			e.printStackTrace();
		}
	}
	
}
