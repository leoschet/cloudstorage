package restservices.cloudstorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class BucketManager extends Thread{

	private Bucket bucket;
	private String fileName;
	
	private BlockingQueue<Message> queue;
	
	public void run(){
		this.setBucket(readFile());
		queue = new LinkedBlockingQueue<Message>();
		while(true){
			Message msg;
			while ((msg = queue.poll()) != null) {
				// process msg
			}
			// do other stuff
		}
	}
		
	public BlockingQueue<Message> getQueue(){
		return this.queue;
	}
	
	public void setFileName(int id){
		this.setFileName("Bucket_"+id+".xml");
	}
	
	public Bucket getBucket() {
		return bucket;
	}
	public void setBucket(Bucket bucket) {
		this.bucket = bucket;
	}
	

	public void add(Element e) throws ElementAlreadyExistsException {
		bucket.add(e);
		bucket.sort();
		writeFile();
	}
	
	public void remove(String key) throws ElementNotFoundException{
		bucket.remove(key);
		writeFile();
	}
	
	public Element search(String key) throws ElementNotFoundException{
		return bucket.search(key);
	}
	
	public Bucket searchInterval(String first, String last){
		return new Bucket(bucket.searchInterval(first,last));
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private void writeFile(){		
	    try {
	    	JAXBContext contextObj = JAXBContext.newInstance(Bucket.class);  
		    Marshaller marshallerObj = contextObj.createMarshaller();  
		    marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
			marshallerObj.marshal(bucket, new FileOutputStream(fileName));
		} catch (FileNotFoundException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	private Bucket readFile(){
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
		return bucket;
	}
	
}
