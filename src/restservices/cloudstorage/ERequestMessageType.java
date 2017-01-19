package restservices.cloudstorage;

public enum ERequestMessageType implements IMessageType {
	ADD, OVERWRITE, REMOVE, SEARCH, SEARCH_INTERVAL, GET_ALL, CLEAN
}


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
else if (request.type == ERequestMessageType.CLEAN) {
	clean();
	response = new Message(EResponseMessageType.OK);
	try {
		request.getCaller().getQueue().put(response);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
