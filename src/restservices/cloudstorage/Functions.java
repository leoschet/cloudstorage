package restservices.cloudstorage;

import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Functions {
	private BlockingQueue<Message> queue;
	
	public Functions(){
		queue = new LinkedBlockingQueue<Message>();
	}
	
	public BlockingQueue<Message> getQueue(){
		return this.queue;
	}
	
	private HashTable getInstance(){
		return HashTable.getInstance();
	}
	private void add(Element e, boolean overwrite) throws Exception{
		Message msg;
		if(overwrite){
			msg = new Message("overWrite",this);
		}
		else{
			msg = new Message("add",this);
		}
		
		msg.setElement(e);
		
		char c = e.getKey().charAt(0);

		if( c >= 'a' && c <= 'z') {
			getInstance().getBucketManagers()[10 + c-'a'].getQueue().put(msg);
		}
		else if(c >= 'A' && c <= 'Z') {
			getInstance().getBucketManagers()[10 + c-'A'].getQueue().put(msg);
		}
		else if(c <= '0' && c <= '9'){
			getInstance().getBucketManagers()[c-'0'].getQueue().put(msg);
		}
		Message message;
		while ((message = queue.poll()) != null);
		if(!message.getMessage().equals("ok") && message.getException()!= null){
			throw message.getException();
		}
	}
	public void insert(Element e) throws Exception{
		add(e,false);
	}
	public void overWrite(Element e) throws Exception {
		add(e,true);
	}

	public void remove(String key) throws Exception{
		Message msg = new Message("remove",this);
		msg.setKey(key);
		
		char c = key.charAt(0);

		if(c >= 'a' && c <= 'z') {
			getInstance().getBucketManagers()[10 + c-'a'].getQueue().put(msg);
		}
		else if (c >= 'A' && c <= 'Z') {
			getInstance().getBucketManagers()[10 + c-'A'].getQueue().put(msg);
		}
		else if(c <= '0' && c <= '9'){
			getInstance().getBucketManagers()[c-'0'].getQueue().put(msg);
		}
		Message message;
		while ((message = queue.poll()) != null);
		if(!message.getMessage().equals("ok") && message.getException()!= null){
			throw message.getException();
		}
	}

	public Element search(String key) throws Exception {
		Message msg = new Message("search",this);
		msg.setKey(key);
		char c = key.charAt(0);
		if( c >= 'a' && c <= 'z') {
			getInstance().getBucketManagers()[10 + c-'a'].getQueue().put(msg);
		}
		else if(c >= 'A' && c <= 'Z'){
			getInstance().getBucketManagers()[10 + c-'A'].getQueue().put(msg);
		}
		else if(c <= '0' && c <= '9'){
			getInstance().getBucketManagers()[c-'0'].getQueue().put(msg);
		}
		Message message;
		while ((message = queue.poll()) != null);
		if(!message.getMessage().equals("ok") && message.getException()!= null){
			throw message.getException();
		}
		else{
			return message.getElement();
		}
	}

	public Bucket searchInterval(String first, String last) throws InterruptedException{
		Message msg = new Message("searchInterval",this);
		msg.setFirst(first);
		msg.setLast(last);
		Bucket bucket = new Bucket();
		char f = first.charAt(0),
				l = last.charAt(0);

		if(f >= '0' && f <= '9'){
			for(int i = f-'0'; i < 10 && i <= l-'0'; i++){
				getInstance().getBucketManagers()[i].getQueue().put(msg);
			}
		}
		int i = 10;
		if(f >= 'a' && f <= 'z'){
			i = f-'a';
			for(; i < 36 && i <= l-'a'; i++){
				getInstance().getBucketManagers()[i].getQueue().put(msg);
			}
		}
		else if(f >= 'A' && f <= 'Z'){
			i = f-'A';
			for(; i < 36 && i <= l-'a'; i++){
				getInstance().getBucketManagers()[i].getQueue().put(msg);
			}
		}
		Message message;
		while ((message = queue.poll()) != null){
			if(message.getMessage().equals("ok")){
				if(message.getBucket()!= null && message.getBucket().getElements()!= null)
					bucket.getElements().addAll(message.getBucket().getElements());
			}
		}
		
		return bucket;
	}

	public void addList(Vector<Element> list, boolean overWrite) throws Exception  {
		for(int i = 0; i < list.size(); i++)
		{
			add(list.get(i),overWrite);
		}
	}
	
	public Bucket exportElements(){
		Bucket export = new Bucket();
		
		return export;
	}
	
	public void importElements(Bucket bucket){
		
	}

}
