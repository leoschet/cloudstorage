package restservices.cloudstorage;

import java.io.File;
import java.io.FileOutputStream;

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
		
		@POST
		@Path("/storeFile")
		public Response storeFile(String xml) {
			// TODO: Read data from xml and create Element, then send the Element to HashTable store it
			
			
//			{"key":"sample.txt","buf":{"type":"Buffer","data":[116,101,115,116,101]}}
			String response = xml;
			
			String[] json = response.substring(1, response.length() - 1).split("\"");
			
			int x = json.length - 1;
			response = json[x];
			
			return Response.ok(response, MediaType.TEXT_PLAIN).build();
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
}
