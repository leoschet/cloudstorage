package restservices.cloudstorage;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import restservices.cloudstorage.Exception.ElementAlreadyExistsException;
import restservices.cloudstorage.Exception.ElementNotFoundException;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType( propOrder = { "elements"} )
@XmlRootElement(name = "Bucket")
public class Bucket {

	@XmlElement(name = "Element")
	private Vector<Element> elements;

	public Bucket(){
		this.elements = new Vector<Element>();
	}
	
	public Bucket(Vector<Element> elements){
		this.elements = elements;
	}

	public void add(Element e) throws ElementAlreadyExistsException {
		try {
			search(e.getKey());
			throw new ElementAlreadyExistsException();
		} catch (ElementNotFoundException e1) {
			// TODO Auto-generated catch block
			elements.add(e);
		}
	}
	
	public void merge(Bucket bucket) {
		this.elements.addAll(bucket.getElements());
	}

	public void remove(String key) throws ElementNotFoundException{	
		boolean removed = false;		
		for(int i = 0; i < elements.size(); i++){
			if(elements.get(i).getKey().equalsIgnoreCase(key))
			{
				elements.remove(i);
				removed = true;
			}
		}
		if(!removed){
			throw new ElementNotFoundException();
		}
	}

	public Element search(String key) throws ElementNotFoundException{
		for(int i = 0; i < elements.size(); i++){
			if(elements.get(i).getKey().equalsIgnoreCase(key)){
				return elements.get(i);	
			}
		}
		throw new ElementNotFoundException();
	}
	
	public Element search(int index) throws IndexOutOfBoundsException{
		return this.elements.get(index);
	}

	public Vector<Element> searchInterval(String first, String last){	
		sort();
		Vector<Element> list = new Vector<Element>();
		for(int i = 0; i < elements.size(); i++){
			if(elements.get(i).getKey().compareToIgnoreCase(first) >= 0
					&& elements.get(i).getKey().compareToIgnoreCase(last) <= 0){
				list.add(elements.get(i));
			}
		}
		return list;
	}

	public void sort() {
		Collections.sort(elements, new Comparator<Element>() {
			@Override
			public int compare(Element e1, Element e2) {
				return e1.getKey().compareTo(e2.getKey());
			}           
		});
	}
	
	private Vector<Element> getElements() {
		return elements;
	}
	
	public int size() {
		return this.elements.size();
	}

	public boolean hasElements() {
		return (this.elements != null);
	}

}
