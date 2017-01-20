package restservices.cloudstorage;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import restservices.exception.ElementAlreadyExistsException;
import restservices.exception.ElementNotFoundException;
import restservices.exception.InvalidInputException;

@Path("/")
public class Application {

	static String ELEMENT_ALREADY_EXISTS = "Element Already Exists!";
	static String INVALID_INPUT = "Invalid Input!"; 
	static String ELEMENT_NOT_FOUND = "Elment Not Found!";
	
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
			Element element = Parser.elementToParsableObject(xml);
			functions.insert(element);
			return Response.ok("Element added!", MediaType.TEXT_PLAIN).build();
		}catch (ElementAlreadyExistsException e) {
			return Response.notModified(ELEMENT_ALREADY_EXISTS).build();
		}catch (InvalidInputException e) {
			return Response.notModified(INVALID_INPUT).build();		
		} catch (Exception e) {

			return Response.serverError().build();
		}
	}

	@GET
	@Path("/getFile")
	public Response getFile(@QueryParam("key") String key) {
		try{
			Element element = functions.search(key);
			String xml = Parser.toString(element);
			return Response.ok(xml, MediaType.TEXT_XML).build();
		} catch (InvalidInputException e) {
			return Response.notModified(INVALID_INPUT).build();	
		} catch (ElementNotFoundException e) {
			return Response.notModified(ELEMENT_NOT_FOUND).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.serverError().build();
		}
	}
	@GET
	@Path("/getFiles")
	public Response getFiles(@QueryParam("firstKey") String firstKey,@QueryParam("lastKey") String lastKey) {
		try{
			Bucket bucket = functions.searchInterval(firstKey, lastKey);
			String xml = Parser.toString(bucket);
			return Response.ok(xml, MediaType.TEXT_XML).build();
		/*}catch (InvalidInputException e) {
			return Response.notModified(INVALID_INPUT).build();
			*/
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.serverError().build();
		}
	}
	@GET
	@Path("/delete")
	public Response delete(@QueryParam("key") String key) {
		// TODO: Delete file with corresponding key from HashTable
		try{
			functions.cleanDataBase();
			//functions.remove(key);
			return Response.ok("Element removed", MediaType.TEXT_PLAIN).build();
			
		} catch (InvalidInputException e) {
			return Response.notModified(INVALID_INPUT).build();
			
		} catch (ElementNotFoundException e) {
			return Response.notModified(ELEMENT_NOT_FOUND).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.serverError().build();
		}
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/exportDatabase")
	public Response exportDatabase() {
		try {
			Bucket bucket = functions.exportElements();
			String xml = Parser.toString(bucket);
			return Response.ok(xml, MediaType.TEXT_XML).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@POST
	@Path("/importDatabase")
	public Response importDatabase(String xml) {
		try{
			Bucket bucket = Parser.bucketToParsableObject(xml);
			functions.importElements(bucket, true);
			return Response.ok("Database imported", MediaType.TEXT_PLAIN).build();
		}catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@POST
	@Path("/cleanDatabase")
	public Response cleanDatabase() {
		try{
			functions.cleanDataBase();
			return Response.ok("Element removed", MediaType.TEXT_PLAIN).build();
		
		} catch (Exception e) {
			return Response.serverError().build();
			
		}
	}
	
}
