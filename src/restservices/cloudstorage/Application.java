package restservices.cloudstorage;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Request;

@Path("/")
public class Application {

		private HashTable hash;
		
		/**
		 * Constructor for ScorersServices
		 */
		public Application() {
			// TODO: Read data from .txt
		}
		
		@POST
		@Path("/storeFile")
		public Response storeFile(String xml) {
			// TODO: Read data from xml and create Element, then send the Element to HashTable store it
			return null;
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
}
