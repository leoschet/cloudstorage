package restservices.cloudstorage;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Parser {

	public static Element elementToParsableObject(String xml) throws JAXBException {
		StringReader stringReader = new StringReader(xml);
		JAXBContext jaxbContext = JAXBContext.newInstance(Element.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Element instance = (Element) unmarshaller.unmarshal(stringReader);
		return instance;
	}

	public static String toString(Element instance) throws JAXBException {
		JAXBContext contextObj = JAXBContext.newInstance(Element.class);  
		Marshaller marshallerObj = contextObj.createMarshaller();  
		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
		StringWriter stringWriter = new StringWriter();
		marshallerObj.marshal(instance, stringWriter);

		return stringWriter.toString();
	}
	
	public static Bucket bucketToParsableObject(String xml) throws JAXBException {
		StringReader stringReader = new StringReader(xml);
		JAXBContext jaxbContext = JAXBContext.newInstance(Bucket.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Bucket instance = (Bucket) unmarshaller.unmarshal(stringReader);
		return instance;
	}

	public static String toString(Bucket instance) throws JAXBException {
		JAXBContext contextObj = JAXBContext.newInstance(Bucket.class);  
		Marshaller marshallerObj = contextObj.createMarshaller();  
		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
		StringWriter stringWriter = new StringWriter();
		marshallerObj.marshal(instance, stringWriter);

		return stringWriter.toString();
	}
}
