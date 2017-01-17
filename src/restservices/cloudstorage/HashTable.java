package restservices.cloudstorage;

public class HashTable {

	private static int SIZE = 36;

	private static HashTable instance;
	private BucketManager[] bucketManagers;

	public static HashTable getInstance(){

		if(instance == null)
			instance = new HashTable();

		return instance;
	}

	private HashTable(){
		this.setBucketManagers(new BucketManager[SIZE]);
		for(int i = 0; i < SIZE; i++){
			bucketManagers[i] = new BucketManager();
			bucketManagers[i].setFileName(i);
			bucketManagers[i].start(); // TODO: set run argument
		}
	}

	private void setBucketManagers(BucketManager[] bucketManagers) {
		this.bucketManagers = bucketManagers;
	}

	public BucketManager[] getBucketManagers() {
		return bucketManagers;
	}

	public void queueMessage(Message msg) throws InvalidKeyException, InterruptedException {
		
		int hashIndex = mapKey(msg.getKey());
		bucketManagers[hashIndex].queueMessage(msg);
	}
	
	public int queueMessageInterval(Message msg) throws InvalidKeyException, InterruptedException {
		
		int firstIndex = mapKey(msg.getFirst());
		int lastIndex = mapKey(msg.getLast());
		for (int i = firstIndex; i <= lastIndex; i += 1)
			bucketManagers[i].queueMessage(msg);
		
		return lastIndex - firstIndex;
	}

	public int queueMessageAll(Message msg) throws InvalidKeyException, InterruptedException {

		for (int i = 0; i < SIZE; i += 1)
			bucketManagers[i].queueMessage(msg);
		return SIZE;
	}
	
	private int mapKey(String key) throws InvalidKeyException {
		
		int index = -1;

		char firstChar = key.charAt(0);

		if ((firstChar >= 'a' && firstChar <= 'z') || (firstChar >= 'A' && firstChar <= 'Z'))
			index = 10 + firstChar - 'a';
		else if (firstChar <= '0' && firstChar <= '9')
			index = firstChar - '0';
		else
			throw new InvalidKeyException();

		return index;
	}
}
