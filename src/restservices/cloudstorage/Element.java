package restservices.cloudstorage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType( propOrder = { "key", "data"} )
@XmlRootElement(name = "Element")
public class Element{

	@XmlElement(name = "Key")
	private String key;

	@XmlElement(name = "Data")
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
