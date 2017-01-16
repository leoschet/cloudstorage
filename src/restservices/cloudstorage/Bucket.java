package restservices.cloudstorage;

import java.util.Vector;

public class Bucket{
	private Vector<Element> elements;
	
	public Bucket(){
		this.setElements(new Vector<Element>());
	}

	public Vector<Element> getElements() {
		return elements;
	}

	private void setElements(Vector<Element> elements) {
		this.elements = elements;
	}
	public void add(Element e) throws Exception{
		for(int i = 0; i < elements.size(); i++)
		{
			if(elements.get(i).getKey().compareToIgnoreCase(e.getKey()) < 0) // current from list <= new 
			{
				elements.add(i,e);
			}
			else if(elements.get(i).getKey().compareToIgnoreCase(e.getKey()) == 0)
			{
				throw new Exception("Element Already Exists.");
			}
		}
	}
	
	
	public void remove(String key) throws Exception{
		
		boolean removed = false;
		
		for(int i = 0; i < elements.size(); i++)
		{
			if(elements.get(i).getKey().equalsIgnoreCase(key))
			{
				elements.remove(i);
				removed = true;
			}
		}
		
		if(!removed){
			throw new Exception("Element Not Found.");
		}
	}
	
	public Element search(String key) throws Exception{

		for(int i = 0; i < elements.size(); i++)
		{
			if(elements.get(i).getKey().equalsIgnoreCase(key))
			{
				return elements.get(i);
				
			}
			
		}
		throw new Exception("Element Not Found.");
	}
	
	public Vector<Element> searchInterval(String first, String last){
		
		Vector<Element> list = new Vector<Element>();

		for(int i = 0; i < elements.size(); i++){
			if(elements.get(i).getKey().compareToIgnoreCase(first) >= 0
					&& elements.get(i).getKey().compareToIgnoreCase(last) <= 0)
			{
				list.add(elements.get(i));
			}
		}

		return list;
	}
	
	
}
