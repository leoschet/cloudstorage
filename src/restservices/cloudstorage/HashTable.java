package restservices.cloudstorage;

public class HashTable {

	private static int SIZE = 36;

	private static HashTable instance;
	private BucketManager[] bucketManagers;
	
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



}
