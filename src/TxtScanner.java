import java.util.Iterator;

public class TxtScanner {

	/**
	 * This method check roughly the format of the txt.
	 * TODO: Need to improve the checking.
	 * @param iterator
	 * @throws Wrong_txt
	 */
	protected static void verifyingTxt(Iterator<String> iterator) throws WrongTxt {
		if(!iterator.next().contains("Network"))
			throw new WrongTxt("The file doesn't begin with Network...");
		String variables_string = iterator.next();
		if(!variables_string.contains("Variables:"))
			throw new WrongTxt("The file doesn't include Variables...");
		while(iterator.hasNext())
			if(iterator.next().contains("Queries"))
				return;
		throw new WrongTxt("The file doesn't include Queries...");
	}
}
