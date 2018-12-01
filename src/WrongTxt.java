/**
 * @author sam
 * This class handle error of txt.
 */
@SuppressWarnings("serial")
public class WrongTxt extends Exception {

	/**
	 * Constructor.
	 * @param message
	 */
	public WrongTxt(String message) {
	    super(message);
	}

}