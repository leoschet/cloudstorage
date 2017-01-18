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

	private BlockingQueue<Message> requestQueue;
	
	public BucketManager(int id) {
		this.fileName = "Bucket_" + id + ".xml";
	}

	public void run(){

		this.bucket = readFile();
		requestQueue = new LinkedBlockingQueue<Message>();

		while(true){
			Message request;
			Message response;

			request = getRequest();

			if(request.type == ERequestMessageType.SEARCH_INTERVAL){

				if (request.getFirstKey() != null && request.getLastKey() != null) {
					Bucket buck = searchInterval(request.getFirstKey(), request.getLastKey());
					response = new Message(EResponseMessageType.OK);
					response.setBucket(buck);

					try {
						request.getCaller().getQueue().put(response);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} else if (request.type == ERequestMessageType.SEARCH) {

				if(request.getKey() != null){
					try {
						Element element = search(request.getKey());
						response = new Message(EResponseMessageType.OK);
						response.setElement(element);
						try {
							request.getCaller().getQueue().put(response);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (ElementNotFoundException e1) {
						response = new Message(EResponseMessageType.ERR);
						response.setException(e1);
						try {
							request.getCaller().getQueue().put(response);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} else if (request.type == ERequestMessageType.REMOVE) {
				if(request.getKey() != null){
					try {
						remove(request.getKey());
						response = new Message(EResponseMessageType.OK);
						try {
							request.getCaller().getQueue().put(response);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (ElementNotFoundException e1) {
						response = new Message(EResponseMessageType.ERR);
						response.setException(e1);
						try {
							request.getCaller().getQueue().put(response);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			} else if (request.type == ERequestMessageType.OVERWRITE) {
				if(request.getElement() != null){
					try {
						overWrite(request.getElement());
						response = new Message(EResponseMessageType.OK);
						try {
							request.getCaller().getQueue().put(response);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}catch (OverWriteException e1) {
						response = new Message(EResponseMessageType.ERR);
						response.setException(e1);
						try {
							request.getCaller().getQueue().put(response);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} else if (request.type == ERequestMessageType.ADD) {
				if(request.getElement() != null){
					try {
						add(request.getElement());
						response = new Message(EResponseMessageType.OK);
						try {
							request.getCaller().getQueue().put(response);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}catch (ElementAlreadyExistsException e1) {
						response = new Message(EResponseMessageType.ERR);
						response.setException(e1);
						try {
							request.getCaller().getQueue().put(response);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} else if (request.type == ERequestMessageType.GET_ALL) {
				response = new Message(EResponseMessageType.OK);
				response.setBucket(bucket);
				try {
					request.getCaller().getQueue().put(response);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

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
