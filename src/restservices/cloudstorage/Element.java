package restservices.cloudstorage;


public class Element{

	private String key;
	private byte[] data;
	
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public Element(String key, byte[] data){
		this.key = key;
		this.data = data;
	}
	
	public Element(){
		this.key = null;
		this.data = null;
	}
	
}
