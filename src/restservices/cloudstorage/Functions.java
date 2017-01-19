package restservices.cloudstorage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import restservices.cloudstorage.Exception.InvalidKeyException;

public class Functions {
	
	private BlockingQueue<Message> responseQueue;
	private HashTable instance;

	public Functions(){
		responseQueue = new LinkedBlockingQueue<Message>();
		instance = HashTable.getInstance();
	}

	public BlockingQueue<Message> getQueue(){
		return this.responseQueue;
	}
	
	public void insert(Element e) throws Exception{
		add(e, false);
	}

	public void overwrite(Element e) throws Exception {
		add(e, true);
	}

	public void remove(String key) throws Exception {
		
		Message request = new Message(ERequestMessageType.REMOVE, this);
		request.setSearchKey(key);
		
		instance.queueMessage(request);

		Message response = getResponse();
		if (response.type == EResponseMessageType.ERR)
			throw response.getException();
	}

	public Element search(String key) throws Exception {
		
		Message request = new Message(ERequestMessageType.SEARCH, this);
		request.setSearchKey(key);
		
		instance.queueMessage(request);

		Message response = getResponse();
		
		if(response.type == EResponseMessageType.ERR)
			throw response.getException();

		return response.getElement();
	}

	public Bucket searchInterval(String firstKey, String lastKey) throws InterruptedException, InvalidKeyException {
		
		Message request = new Message(ERequestMessageType.SEARCH_INTERVAL, this);
		request.setSearchInterval(firstKey, lastKey);
		
		int bucketsAmount = instance.queueMessageInterval(request);
		
		Bucket bucket = new Bucket();
		for (int i = 0; i <= bucketsAmount; i += 1) {
			Message response = getResponse();
			
			if(response.type == EResponseMessageType.OK)
				if(response.getBucket() != null && response.getBucket().hasElements())
					bucket.merge(response.getBucket());
		}
		
		bucket.sort();
		return bucket;
	}

	public Bucket exportElements() throws InvalidKeyException, InterruptedException{
		Message request = new Message(ERequestMessageType.GET_ALL, this);
		
		int bucketsAmount = instance.queueMessageAll(request);
		
		Bucket bucket = new Bucket();
		Message response = null;
		for (int i = 0; i <= bucketsAmount; i += 1) {
			response = getResponse();
			
			if(response.type == EResponseMessageType.OK)
				if(response.getBucket() != null && response.getBucket().hasElements())
					bucket.merge(response.getBucket());
		}
		
		bucket.sort();
		return bucket;
	}

	public void importElements(Bucket bucket, boolean overwrite) throws Exception  {
		for(int i = 0; i < bucket.size(); i++)
			add(bucket.search(i), overwrite);
	}
	
	public void cleanDataBase() throws Exception{
		Message request = new Message(ERequestMessageType.CLEAN, this);
		
		int bucketsAmount = instance.queueMessageAll(request);
		
		Message response;
		for (int i = 0; i <= bucketsAmount; i += 1) {
			response = getResponse();
			if(response.type == EResponseMessageType.ERR)
				throw response.getException();
		}
		
	}
	
	private void add(Element element, boolean overwrite) throws Exception {
		Message request;
		
		if(overwrite)
			request = new Message(ERequestMessageType.OVERWRITE, this);
		else
			request = new Message(ERequestMessageType.ADD, this);

		request.setElement(element);
		
		instance.queueMessage(request);

		Message message = getResponse();
		if (message.type == EResponseMessageType.ERR)
			throw message.getException();
	}
	
	private Message getResponse() {
		Message msg = null;
		
		while (msg == null) {
			msg = responseQueue.poll();
		}
		
		return msg;
	}
}
