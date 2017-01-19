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
	private String data;
	
	public String getData() {
		return data;
	}
	
	public String getKey() {
		return key;
	}
	
	public Element(String key,String data){
		this.key = key;
		this.data = data;
	}
	
	public Element(){
		this.key = null;
		this.data = null;
	}
	
	
}