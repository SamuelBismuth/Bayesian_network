import java.util.Iterator;
import java.util.List;

public class From_txt_to_object {

	public static Network create_network(List<String> list_txt) throws Wrong_txt {
		Iterator<String> itr = list_txt.iterator(); 
		//System.out.println(itr.next());
		if(!itr.next().contains("Network"))
			throw new Wrong_txt("The file doesn't begin with Network...");
		while (itr.hasNext()) 
			System.out.println(itr.next()); 
		
		// TODO.
		List<Variable> variables = null;
		List<Query> queries = null;
		return new Network(variables, queries);
	}
	
}
