package restservices.cloudstorage;

public class Message {

	private String key;
	private Bucket bucket;
	private Element element;
	private String message;
	private Exception exception;
	private String ownner;
	private Functions caller;
	private String first;
	private String last;
	
	public Message(String message, Functions caller){
		this.setMessage(message);
		this.setCaller(caller);
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Bucket getBucket() {
		return bucket;
	}
	public void setBucket(Bucket bucket) {
		this.bucket = bucket;
	}
	public Element getElement() {
		return element;
	}
	public void setElement(Element element) {
		this.element = element;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public String getOwnner() {
		return ownner;
	}
	public void setOwnner(String ownner) {
		this.ownner = ownner;
	}
	public Functions getCaller() {
		return caller;
	}
	public void setCaller(Functions caller) {
		this.caller = caller;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
}
