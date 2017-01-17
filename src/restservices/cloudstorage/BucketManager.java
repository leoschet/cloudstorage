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
			Message answer;
			Bucket buck;
			Element element;
			
			while ((msg = queue.poll()) != null) {
				
				if(msg.getMessage().equals("searchInterval")){
					
					if(msg.getFirst()!= null && msg.getLast() != null){
						buck = searchInterval(msg.getFirst(), msg.getLast());
						answer = new Message("ok");
						answer.setBucket(buck);
						try {
							msg.getCaller().getQueue().put(answer);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				else if(msg.getMessage().equals("search")){
					
					if(msg.getKey() != null){
						try {
							element = search(msg.getKey());
							answer = new Message("ok");
							answer.setElement(element);
							try {
								msg.getCaller().getQueue().put(answer);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (ElementNotFoundException e1) {
							answer = new Message("ex");
							answer.setException(e1);
							try {
								msg.getCaller().getQueue().put(answer);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				else if(msg.getMessage().equals("remove")){
					if(msg.getKey() != null){
						try {
							remove(msg.getKey());
							answer = new Message("ok");
							try {
								msg.getCaller().getQueue().put(answer);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (ElementNotFoundException e1) {
							answer = new Message("ex");
							answer.setException(e1);
							try {
								msg.getCaller().getQueue().put(answer);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
				}
				else if(msg.getMessage().equals("overWrite")){
					if(msg.getElement() != null){
						try {
							overWrite(msg.getElement());
							answer = new Message("ok");
							try {
								msg.getCaller().getQueue().put(answer);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}catch (OverWriteException e1) {
							answer = new Message("ex");
							answer.setException(e1);
							try {
								msg.getCaller().getQueue().put(answer);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				else if(msg.getMessage().equals("add")){
					if(msg.getElement() != null){
						try {
							add(msg.getElement());
							answer = new Message("ok");
							try {
								msg.getCaller().getQueue().put(answer);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}catch (ElementAlreadyExistsException e1) {
							answer = new Message("ex");
							answer.setException(e1);
							try {
								msg.getCaller().getQueue().put(answer);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				else if(msg.getMessage().equals("all")){
					answer = new Message("ok");
					answer.setBucket(bucket);
					try {
						msg.getCaller().getQueue().put(answer);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
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
	
	public void overWrite(Element e) throws OverWriteException{
		try {
			bucket.remove(e.getKey());
		} catch (ElementNotFoundException e1) {}
		try {
			bucket.add(e);
		} catch (ElementAlreadyExistsException e1) {
			throw new OverWriteException();
		}
	}

	public void add(Element e) throws ElementAlreadyExistsException {
		bucket.add(e);
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
		} catch (JAXBException e) {  
			bucket = new Bucket();
		}  
		return bucket;
	}
	
}
