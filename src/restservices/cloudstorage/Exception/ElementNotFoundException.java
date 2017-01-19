package restservices.cloudstorage.Exception;

public class ElementNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ElementNotFoundException(){
		super("Element Not Found.");
	}

}
