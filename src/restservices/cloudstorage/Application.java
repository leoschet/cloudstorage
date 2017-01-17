package restservices.cloudstorage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@Path("/")
public class Application {

	private HashTable hash;
	

	private BlockingQueue<Message> queue;
	
	private Element readElement(String s) throws JAXBException {
		StringReader stringReader = new StringReader(s);
		JAXBContext jaxbContext = JAXBContext.newInstance(Element.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Element element = (Element) unmarshaller.unmarshal(stringReader);
		return element;
	}

	/**
	 * Constructor for ScorersServices
	 */
	public Application() {
		queue = new LinkedBlockingQueue<Message>();
		hash = HashTable.getInstance();
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/storeFile")
	public Response storeFile(String xml){
		
		try{
			Element element = readElement(xml);
			try {
				hash.add(element);
			} catch (ElementAlreadyExistsException e) {
				return Response.accepted("Element already exists").build();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch(JAXBException e){
			return Response.serverError().build();
		}
	
		return Response.ok("Element added!", MediaType.TEXT_PLAIN).build();
	}

	@POST
	//		@Produces(MediaType.TEXT_XML)
	@Path("/test")
	public Response test(String xml) {
		// TODO: Read data from xml and create Element, then send the Element to HashTable store it

		String response = "[116,101,115,116,101]";

		String[] byteValues = response.substring(1, response.length() - 1).split(",");
		byte[] bytes = new byte[byteValues.length];

		for (int i=0, len=bytes.length; i<len; i++) {
			bytes[i] = Byte.parseByte(byteValues[i].trim());     
		}

		File outputFile = new File("C:\\Users\\Leonardo\\Desktop\\Leonardo\\UniWien\\CloudComputing\\CloudStorageClient\\output.txt");

		try {
			FileOutputStream fileOuputStream = new FileOutputStream(outputFile);
			fileOuputStream.write(bytes);
			fileOuputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//				e.printStackTrace();
		} finally {} 

		return Response.ok(xml, MediaType.TEXT_PLAIN).build();
	}

	@GET
	@Path("/getFile/{key}")
	public Response getFile(@PathParam("key") String key) {
		// TODO: Search for key in HashTable
		return null;
	}

	@POST
	@Path("/delete/{key}")
	public Response delete(@PathParam("key") String key) {
		// TODO: Delete file with corresponding key from HashTable
		return null;
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/exportDatabase")
	public Response exportDatabase() {
		// TODO: Return all buckets files in one single xml 
		//			Response resp = Response.ok(xml, MediaType.TEXT_XML).build();
		return null;
	}

	@POST
	@Path("/importDatabase")
	public Response importDatabase(String xml) {
		// TODO: Load xml on HashTable
		return null;
	}

	public BlockingQueue<Message> getQueue() {
		return queue;
	}

	public void setQueue(BlockingQueue<Message> queue) {
		this.queue = queue;
	}
}
