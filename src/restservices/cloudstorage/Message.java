package restservices.cloudstorage;

public class Message {

	public IMessageType type;
	private Functions caller;
	
	// Search element
	private String searchKey;

	// Search interval
	private String firstKey;
	private String lastKey;
	
	// Search interval, Get all
	private Bucket bucket;
	
	// Get element, Store element
	private Element element;
	
	// Error
	private Exception exception;
	
	public Message(EResponseMessageType type) {
		this.type = type;
	}
	
	public Message(ERequestMessageType type, Functions caller) {
		this.type = type;
		this.caller = caller;
	}
	
	public String getKey() {
		return searchKey;
	}
	
	public void setSearchKey(String key) {
		this.searchKey = key;
	}
	
	public void setSearchInterval(String firstKey, String lastKey) {
		this.firstKey = firstKey;
		this.lastKey = lastKey;
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
		this.searchKey = element.getKey();
	}
	
	public Exception getException() {
		return exception;
	}
	
	public void setException(Exception exception) {
		this.exception = exception;
	}
	
	public Functions getCaller() {
		return caller;
	}
	
	public String getFirstKey() {
		return firstKey;
	}
	
	public String getLastKey() {
		return lastKey;
	}
}
