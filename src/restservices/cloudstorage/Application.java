package restservices.cloudstorage;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class Application {

		private HashTable hash;
		
		/**
		 * Constructor for ScorersServices
		 */
		public Application() {
			// TODO: Read data from .txt
		}
		
		@GET
		@Produces(MediaType.TEXT_XML)
		@Path("/listAll")
		public Response listAll() {
			return null;
		}
		
		@POST
		@Path("/addElement/{id}")
		// TODO: Receive element data by param
		public Response addElement(@PathParam("id") int id) {
			return null;
		}
}
