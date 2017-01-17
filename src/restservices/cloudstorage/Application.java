package restservices.cloudstorage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Path("/")
public class Application {

	Functions functions;
	
	private Element readElement(String s) throws JAXBException {
		StringReader stringReader = new StringReader(s);
		JAXBContext jaxbContext = JAXBContext.newInstance(Element.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Element element = (Element) unmarshaller.unmarshal(stringReader);
		return element;
	}

	private StringWriter getElement(Element element) throws JAXBException {

		JAXBContext contextObj = JAXBContext.newInstance(Element.class);  
		Marshaller marshallerObj = contextObj.createMarshaller();  
		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
		StringWriter stringWriter = new StringWriter();
		marshallerObj.marshal(element, stringWriter);

		return stringWriter;
	}

	/**
	 * Constructor for Application service
	 */
	public Application() { 
		this.functions = new Functions();
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/storeFile")
	public Response storeFile(String xml){
		
		try{
			Element element = readElement(xml);
			functions.insert(element);
		}
		catch(JAXBException e){
			return Response.serverError().build();
		}
		catch (ElementAlreadyExistsException e) {
			// TODO nova execao
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return Response.ok("Element added!", MediaType.TEXT_PLAIN).build();
	}

	@GET
	@Path("/getFile")
	public Response getFile(@QueryParam("key") String key) {
		
		try{
			getElement(functions.search(key));
		}		
		catch (ElementNotFoundException e) {
			// TODO nova execao
			return Response.serverError().build();
		}
		catch (JAXBException e) {
		 }
		
		catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.serverError().build();
		}
		
		
		return Response.ok("Element added!", MediaType.TEXT_PLAIN).build();
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
