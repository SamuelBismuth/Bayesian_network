import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author sam
 * This class output a txt file in the good format:
 * S
 * 1: The result rounded five numbers after the point.
 * ,
 * 2: The number of addition.
 * ,
 * 3: The number of multiplication.
 */
public class OutputTxt {

	/**
	 * This function output the answer.
	 * @param answers
	 */
	public static void writeAnswer(List<String> answers) {	
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("output.txt"));
			for (String answer : answers)
				writer.write(answer + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
