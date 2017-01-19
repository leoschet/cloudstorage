package restservices.cloudstorage;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import restservices.exception.ElementAlreadyExistsException;
import restservices.exception.ElementNotFoundException;
import restservices.exception.InvalidKeyException;

@Path("/")
public class Application {

	Functions functions;
	
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
			Parser<Element> parse = new Parser<Element>();
			
			Element element = parse.toParsableObject(xml);
			functions.insert(element);
			return Response.ok("Element added!", MediaType.TEXT_PLAIN).build();
		}
		catch(JAXBException e){
			return Response.serverError().build();
		}
		catch (ElementAlreadyExistsException e) {
			// TODO nova execao
			return Response.serverError().build();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/getFile")
	public Response getFile(@QueryParam("key") String key) {
		
		try{
			Parser<Element> parse = new Parser<Element>();
			
			Element element = functions.search(key);
			String xml = parse.toString(element);
			return Response.ok(xml, MediaType.TEXT_XML).build();
			
		} catch (ElementNotFoundException e) {
			// TODO nova execao
			return Response.serverError().build();
		} catch (JAXBException e) {
			return Response.serverError().build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.serverError().build();
		}
	}

	@POST
	@Path("/delete")
	public Response delete(@QueryParam("key") String key) {
		// TODO: Delete file with corresponding key from HashTable
		try{
			functions.remove(key);
			return Response.ok("Element removed", MediaType.TEXT_PLAIN).build();
			
		} catch (ElementNotFoundException e) {
			return Response.serverError().build();
			
		} catch (JAXBException e) {
			return Response.serverError().build();
			
		} catch (Exception e) {
			return Response.serverError().build();
			
		}
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/exportDatabase")
	public Response exportDatabase() {
		try {
			Parser<Bucket> parse = new Parser<Bucket>();
			
			Bucket bucket = functions.exportElements();

			String xml = parse.toString(bucket);
			return Response.ok(xml, MediaType.TEXT_XML).build();
			
		} catch (InvalidKeyException | InterruptedException e) {
			return Response.serverError().build();
		} catch (JAXBException e) {
			return Response.serverError().build();
		}
	}

	@POST
	@Path("/importDatabase")
	public Response importDatabase(String xml) {
		// TODO: Load xml on HashTable
		try{
			Parser<Bucket> parse = new Parser<Bucket>();
			
			Bucket bucket = parse.toParsableObject(xml);
			functions.importElements(bucket, true);
			
			return Response.ok("Database imported", MediaType.TEXT_PLAIN).build();
		}
		catch(JAXBException e){
			return Response.serverError().build();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}
