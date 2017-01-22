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

import restservices.exception.ElementAlreadyExistsException;
import restservices.exception.ElementNotFoundException;
import restservices.exception.InvalidInputException;
import restservices.exception.OverWriteException;


public class BucketManager extends Thread{

	private Bucket bucket;
	private String fileName;

	private BlockingQueue<Message> requestQueue;
	
	public BucketManager(int id) {
		this.fileName = "/home/ccteam/tomcat/files/Bucket_" + id + ".xml";
	}

	public void run(){

		requestQueue = new LinkedBlockingQueue<Message>();
		this.bucket = readFile();

		while(true){
			Message request;
			Message response;

			request = getRequest();
			
			switch ((ERequestMessageType) request.type) {
			case ADD:
				response = add(request);
				break;
			case OVERWRITE:
				response = overwrite(request);
				break;
			case REMOVE:
				response = remove(request);
				break;
			case SEARCH:
				response = search(request);
				break;
			case SEARCH_INTERVAL:
				response = searchInterval(request);
				break;
			case GET_ALL:
				response = getAll();
				break;
			case CLEAN:
				response = clean();
				break;
			default:
				response = new Message(EResponseMessageType.ERR);
				response.setException(new InvalidInputException());
				break;
			}
			
			if(request.type == ERequestMessageType.ADD ||
					request.type == ERequestMessageType.OVERWRITE ||
					request.type == ERequestMessageType.REMOVE||
					request.type == ERequestMessageType.CLEAN){
				try {
					writeFile();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					response.setException(e);
				}
			}
			sendResponse(request,response);
		
			
		}
			
	}

	private void sendResponse(Message request, Message response) {
		try {
			request.getCaller().getQueue().put(response);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Message add(Message request){
		Message response;
		if(request.getElement() != null){
			try {
				this.bucket.add(request.getElement());
				//writeFile();
				response = new Message(EResponseMessageType.OK);
			}catch (ElementAlreadyExistsException e1) {
				response = new Message(EResponseMessageType.ERR);
				response.setException(e1);
			}
		}
		else{
			response = new Message(EResponseMessageType.ERR);
			response.setException(new InvalidInputException());
		}
		return response;
	}
	private Message search(Message request){
		Message response;
		if(request.getKey() != null){
			try {
				response = new Message(EResponseMessageType.OK);
				response.setElement(this.bucket.search(request.getKey()));
				
			} catch (ElementNotFoundException e1) {
				response = new Message(EResponseMessageType.ERR);
				response.setException(e1);
			}
		}
		else{
			response = new Message(EResponseMessageType.ERR);
			response.setException(new InvalidInputException());
		}
		return response;
	}
	
	private Message overwrite(Message request){
		Message response;
		if(request.getElement() != null){
			try {
				this.bucket.remove(request.getElement().getKey());
			} catch (ElementNotFoundException elementNotFoundException) {}
			try {
				this.bucket.add(request.getElement());
				response = new Message(EResponseMessageType.OK);
			} catch (ElementAlreadyExistsException elementAlreadyExistsException) {
				response = new Message(EResponseMessageType.ERR);
				response.setException(new OverWriteException());
			}
		}
		else{
			response = new Message(EResponseMessageType.ERR);
			response.setException(new InvalidInputException());
		}
		return response;
	}
	
	private Message remove(Message request){
		Message response;
		if(request.getKey() != null){
			try {
				this.bucket.remove(request.getKey());
				//writeFile();
				response = new Message(EResponseMessageType.OK);
			} catch (ElementNotFoundException elementNotFoundException) {
				response = new Message(EResponseMessageType.ERR);
				response.setException(elementNotFoundException);
			}
		}
		else{
			response = new Message(EResponseMessageType.ERR);
			response.setException(new InvalidInputException());
		}
		return response;
	}
	
	private Message searchInterval(Message request){
		Message response;
		if (request.getFirstKey() != null && request.getLastKey() != null) {
			Bucket bucket = new Bucket(this.bucket.searchInterval(request.getFirstKey(),request.getLastKey()));
			response = new Message(EResponseMessageType.OK);
			response.setBucket(bucket);
		}
		else{
			response = new Message(EResponseMessageType.ERR);
			response.setException(new InvalidInputException());
		}
		return response;
	}
	
	private Message getAll(){
		Message response;
		response = new Message(EResponseMessageType.OK);
		response.setBucket(this.bucket);
		return response;
	}
	
	private Message clean(){
		this.bucket = new Bucket();
		//writeFile();
		return new Message(EResponseMessageType.OK);
	}

	public String getFileName() {
		return fileName;
	}

	private void writeFile() throws FileNotFoundException, JAXBException{		
		
			JAXBContext contextObj = JAXBContext.newInstance(Bucket.class);  
			Marshaller marshallerObj = contextObj.createMarshaller();  
			marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
			marshallerObj.marshal(bucket, new FileOutputStream(fileName));
		
		 
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

	public void queueMessage(Message msg) throws InterruptedException {
		requestQueue.put(msg);
	}

	private Message getRequest() {
		Message msg = null;

		while (msg == null) {
			msg = requestQueue.poll();
		}

		return msg;
	}

}
