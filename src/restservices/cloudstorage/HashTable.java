package restservices.cloudstorage;

import java.io.File;
import java.util.Vector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class HashTable {

	private static int SIZE = 36;

	private static HashTable instance;
	private BucketManager[] bucketManagers;
	private static String fileName = "Bucket_HEAD.xml";

	public static HashTable getInstance(){

		if(instance == null)
		{
			instance = new HashTable();
		}
		return instance;
	}

	private HashTable(){
		this.setBucketManagers(new BucketManager[SIZE]);
		for(int i = 0; i < SIZE; i++){
			bucketManagers[i].start();
		}
		readFile();
	}

	private void setBucketManagers(BucketManager[] bucketManagers) {
		this.bucketManagers = bucketManagers;
	}

	public BucketManager[] getBucketManagers() {
		return bucketManagers;
	}

	public void add(Element e) throws ElementAlreadyExistsException{

		char c = e.getKey().charAt(0);

		if( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') )
		{
			getInstance().getBucketManagers()[10 + c-'a'].add(e);
		}
		else if(c <= '0' && c <= '9')
		{
			getInstance().getBucketManagers()[c-'0'].add(e);
		}
	}

	public void overWrite(Element e) throws OverWriteException{

		char c = e.getKey().charAt(0);

		if( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') )
		{
			try {
				getInstance().getBucketManagers()[10 + c-'a'].remove(e.getKey());
			} catch (ElementNotFoundException e2) {}

			try {
				getInstance().getBucketManagers()[10 + c-'a'].add(e);
			} catch (ElementAlreadyExistsException e1) {
				throw new OverWriteException();
			}

		}
		else if(c <= '0' && c <= '9')
		{
			try {
				getInstance().getBucketManagers()[c-'0'].remove(e.getKey());
			} catch (ElementNotFoundException e1) {}

			try {
				getInstance().getBucketManagers()[c-'0'].add(e);
			} catch (ElementAlreadyExistsException e1) {
				throw new OverWriteException();
			}

		}
	}

	public void remove(String key) throws ElementNotFoundException{

		char c = key.charAt(0);

		if( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') )
		{
			getInstance().getBucketManagers()[10 + c-'a'].remove(key);
		}
		else if(c <= '0' && c <= '9')
		{
			getInstance().getBucketManagers()[c-'0'].remove(key);
		}
	}

	public Element search(String key) throws ElementNotFoundException{

		char c = key.charAt(0);

		Element e = null;

		if( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') )
		{
			e = getInstance().getBucketManagers()[10 + c-'a'].search(key);
		}
		else if(c <= '0' && c <= '9')
		{
			e = getInstance().getBucketManagers()[c-'0'].search(key);
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
				Vector<Element> el = getInstance().getBucketManagers()[i].searchInterval(first, last);
				elements.addAll(el);
			}
		}

		int i = 10;
		if(f >= 'a')
		{
			i = f-'a';
		}

		for(; i < 36 && i <= l-'a'; i++){
			Vector<Element> el = getInstance().getBucketManagers()[i].searchInterval(first, last);
			elements.addAll(el);
		}
		return elements;
	}

	public void addList(Vector<Element> list, boolean overWrite) throws OverWriteException, ElementAlreadyExistsException  {
		for(int i = 0; i < list.size(); i++)
		{
			try {
				add(list.get(i));
			} catch (ElementAlreadyExistsException e) {
				if(overWrite)
				{
					overWrite(list.get(i));
				}
				else
				{
					throw new ElementAlreadyExistsException();
				}
			}
		}
	}

	private void readFile() {
		Bucket bucket = null;
		try {  
			File file = new File(fileName);
			JAXBContext jaxbContext = JAXBContext.newInstance(Bucket.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
			bucket = (Bucket) jaxbUnmarshaller.unmarshal(file);  
			bucket.sort();
		} catch (JAXBException e) {  
			bucket = new Bucket();
		}  
		try {
			addList(bucket.getElements(),true);
		} catch (OverWriteException | ElementAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
