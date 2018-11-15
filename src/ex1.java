import java.util.List;

public class ex1 {

	public static void main(String[] args) {
		List<String> list_txt = Input_txt.readFileInList("input/input.txt"); 
		try {
			From_txt_to_object.create_network(list_txt);
		} catch (Wrong_txt e) {
			e.printStackTrace();
		}
	}
	
}
