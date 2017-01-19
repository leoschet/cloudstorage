package restservices.cloudstorage.Exception;

public class InvalidKeyException extends Exception{

	private static final long serialVersionUID = 1L;

	public InvalidKeyException(){
		super("Invalid key exception.");
	}
}