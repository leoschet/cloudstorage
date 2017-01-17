package restservices.cloudstorage;

import java.io.StringReader;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@Path("/")
public class Application {

	private Element readElement(String s) throws JAXBException {
		StringReader stringReader = new StringReader(s);
		JAXBContext jaxbContext = JAXBContext.newInstance(Element.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Element element = (Element) unmarshaller.unmarshal(stringReader);
		return element;
	}

	/**
	 * Constructor for Application service
	 */
	public Application() { }

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/storeFile")
	public Response storeFile(String xml){
		
		try{
			Element element = readElement(xml);
		}
		catch(JAXBException e){
			return Response.serverError().build();
		}
	
		return Response.ok("Element added!", MediaType.TEXT_PLAIN).build();
	}

	@GET
	@Path("/getFile")
	public Response getFile(@QueryParam("key") String key) {
		// TODO: Search for key in HashTable
		return null;
	}

	@POST
	@Path("/delete")
	public Response delete(@QueryParam("key") String key) {
		// TODO: Delete file with corresponding key from HashTable
		return null;
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/exportDatabase")
	public Response exportDatabase() {
		// TODO: Return all buckets files in one single xml 
		return null;
	}

	@POST
	@Path("/importDatabase")
	public Response importDatabase(String xml) {
		// TODO: Load xml on HashTable
		return null;
	}
}
