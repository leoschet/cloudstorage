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
		private String str;
		
		/**
		 * Constructor for ScorersServices
		 */
		public Application() {
			// TODO: Read data from .txt
			this.str = "alooo ";
		}
		
		@GET
		@Produces(MediaType.TEXT_HTML)
		@Path("/listAll")
		public String listAll() {
			return "<html> " + "<title>" + "Hello Jersey" + "</title>"
					+ "<body><h1>" + this.str + "</h1></body>" + "</html> ";
		}
		
		@POST
		@Path("/addElement/{id}")
		// TODO: Receive element data by param
		public Response addElement(@PathParam("id") int id) {
			return null;
		}
		
		@POST
		@Produces(MediaType.TEXT_HTML)
		@Path("/storeElement")
		// TODO: Receive element data by param
		public Response storeElement() {
			this.str += "pegou!!!";
			Response resp = Response.ok("<html> " + "<title>" + "Hello Jersey" + "</title>" + "<body><h1>" + this.str + "</h1></body>" + "</html> ", MediaType.TEXT_HTML).build();
			return resp;
		}
}
