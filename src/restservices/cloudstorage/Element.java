package restservices.cloudstorage;


public class Element{

	private String key;
	private String type;
	private byte[] data;
	
	
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public Element(String key, String type, byte[] data){
		this.key = key;
		this.data = data;
		this.type = type;
	}
	public Element(){
		this.key = null;
		this.data = null;
		this.type = null;
	}
	
}
