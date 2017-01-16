package restservices.cloudstorage;

import java.util.Vector;

public class HashTable {
	
	private static int SIZE = 36;
	
	private static HashTable instance;
	private Bucket[] buckets;
	
	public static HashTable getInstance(){
		
		if(instance == null)
		{
			instance = new HashTable();
		}
		
		return instance;
	}
	
	public HashTable(){
		this.setBuckets(new Bucket[SIZE]);
	}

	private void setBuckets(Bucket[] buckets) {
		this.buckets = buckets;
		
	}

	public Bucket[] getBuckets() {
		return buckets;
	}
	
	
	public void add(Element e) throws Exception{
		
		char c = e.getKey().charAt(0);
		
		if( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') )
		{
			getInstance().buckets[10 + c-'a'].add(e);
		}
		else if(c <= '0' && c <= '9')
		{
			getInstance().buckets[c-'0'].add(e);
		}
		
	}
	
	public void overWrite(Element e){
		
		char c = e.getKey().charAt(0);
		
		if( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') )
		{
			try {
				getInstance().buckets[10 + c-'a'].remove(e.getKey());
			} catch (Exception e1) {}
			try {
				getInstance().buckets[10 + c-'a'].add(e);
			} catch (Exception e1) {}
			
		}
		else if(c <= '0' && c <= '9')
		{
			try {
				getInstance().buckets[c-'0'].remove(e.getKey());
			} catch (Exception e1) {}
			try {
				getInstance().buckets[c-'0'].add(e);
			} catch (Exception e1) {}
		}
		
	}
	
	public void remove(String key) throws Exception{
		
		char c = key.charAt(0);
		
		if( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') )
		{
			getInstance().buckets[10 + c-'a'].remove(key);
		}
		else if(c <= '0' && c <= '9')
		{
			getInstance().buckets[c-'0'].remove(key);
		}
		
	}

	
	public Element search(String key) throws Exception{
		
		char c = key.charAt(0);
		
		Element e = null;
		
		if( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') )
		{
			e = getInstance().buckets[10 + c-'a'].search(key);
		}
		else if(c <= '0' && c <= '9')
		{
			e = getInstance().buckets[c-'0'].search(key);
		}
		
		return e;
	}
	
	public Vector<Element> searchInterval(String first, String last){

		Vector<Element> elements = new Vector<Element>();

		char f = first.charAt(0),
				l = last.charAt(0);
		
		if(f >= '0' && f <= '9')
		{
			for(int i = f-'0'; i < 10 && i <= l-'0'; i++)
			{
				Vector<Element> el = getInstance().buckets[i].searchInterval(first, last);
				elements.addAll(el);
			}
		}
		int i = 10;
		if(f >= 'a')
		{
			i = f-'a';
		}
		for(; i < 36 && i <= l-'a'; i++){
			Vector<Element> el = getInstance().buckets[i].searchInterval(first, last);
			elements.addAll(el);
		}
		
		return elements;
	}

}
