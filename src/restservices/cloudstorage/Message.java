package restservices.cloudstorage;

public class Message {

	public IMessageType type;
	public Functions caller;
	
	private String key;
	private Bucket bucket;
	private Element element;
	private String message;
	private Exception exception;
	private String ownner;
	private String firstKey;
	private String lastKey;
	
	public Message(IMessageType type, Functions caller) {
		this.type = type;
		this.caller = caller;
	}
	
	public Message(IMessageType type, Element el, Functions caller) {
		this.type = type;
		this.element = el;
		this.caller = caller;
	}
	
	public Message(IMessageType type, String key, Functions caller) {
		this.type = type;
		this.key = key;
		this.caller = caller;
	}
	
	public Message(IMessageType type, String firstKey, String lastKey, Functions caller) {
		this.type = type;
		this.firstKey = firstKey;
		this.lastKey = lastKey;
		this.caller = caller;
	}
	
	public Message(String message, Functions caller){
		this.setMessage(message);
		this.setCaller(caller);
	}
	
	public Message(String message){
		this.setMessage(message);
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
		return firstKey;
	}
	public void setFirst(String first) {
		this.firstKey = first;
	}
	public String getLast() {
		return lastKey;
	}
	public void setLast(String last) {
		this.lastKey = last;
	}
}
