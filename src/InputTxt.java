import java.util.*; 
import java.nio.charset.StandardCharsets; 
import java.nio.file.*; 
import java.io.*; 

/**
 * @author sam
 * This class read the txt.
 */
public class InputTxt {
	
	/**
	 * This function read the txt and convert him into a list of string.
	 * @param fileName
	 * @return the list of string.
	 */
	public static List<String> readFileInList(String fileName)  { 
		List<String> lines = Collections.emptyList(); 
		try { 
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
		return lines; 
	} 
	
}
