package restservices.cloudstorage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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

	public void remove(String key) throws Exception{
		Message msg = new Message(ERequestMessageType.REMOVE, key, this);

		instance.queueMessage(msg);

		Message message = getResponse();
		if (message.type == EResponseMessageType.ERR)
			throw message.getException();
	}

	public Element search(String key) throws Exception {
		Message msg = new Message(ERequestMessageType.SEARCH, key,this);

		instance.queueMessage(msg);

		Message message = getResponse();
		
		if(message.type == EResponseMessageType.ERR)
			throw message.getException();

		return message.getElement();
	}

	public Bucket searchInterval(String firstKey, String lastKey) throws InterruptedException, InvalidKeyException {
		Message msg = new Message(ERequestMessageType.SEARCH_INTERVAL, firstKey, lastKey, this);

		int bucketsAmount = instance.queueMessageInterval(msg);
		
		Bucket bucket = new Bucket();
		for (int i = 0; i <= bucketsAmount; i += 1) {
			Message message = getResponse();
			
			if(message.type == EResponseMessageType.OK)
				if(message.getBucket() != null && message.getBucket().getElements() != null)
					bucket.getElements().addAll(message.getBucket().getElements());
		}
		
		bucket.sort();
		return bucket;
	}

	public Bucket exportElements() throws InvalidKeyException, InterruptedException{
		Message msg = new Message(ERequestMessageType.GET_ALL, this);
		
		int bucketsAmount = instance.queueMessageAll(msg);
		
		Bucket bucket = new Bucket();
		
		for (int i = 0; i <= bucketsAmount; i += 1) {
			Message message = getResponse();
			
			if(message.type == EResponseMessageType.OK)
				if(message.getBucket() != null && message.getBucket().getElements() != null)
					bucket.getElements().addAll(message.getBucket().getElements());
		}
		
		bucket.sort();
		return bucket;
	}

	public void importElements(Bucket bucket, boolean overwrite) throws Exception  {
		for(int i = 0; i < bucket.getElements().size(); i++)
			add(bucket.getElements().get(i),overwrite);
	}
	
	private void add(Element el, boolean overwrite) throws Exception {
		Message msg;
		if(overwrite){
			msg = new Message(ERequestMessageType.OVERWRITE, el, this);
		}
		else{
			msg = new Message(ERequestMessageType.ADD, el, this);
		}

		instance.queueMessage(msg);

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
