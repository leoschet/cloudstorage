package restservices.cloudstorage;

public enum ERequestMessageType implements IMessageType {
	ADD, OVERWRITE, REMOVE, SEARCH, SEARCH_INTERVAL, GET_ALL, CLEAN
}