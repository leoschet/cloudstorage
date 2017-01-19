package restservices.cloudstorage;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Parser<T> {

	public T toParsableObject(String xml) throws JAXBException {
		StringReader stringReader = new StringReader(xml);
		JAXBContext jaxbContext = JAXBContext.newInstance(Element.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		T instance = (T) unmarshaller.unmarshal(stringReader);
		return instance;
	}

	public String toString(T instance) throws JAXBException {

		JAXBContext contextObj = JAXBContext.newInstance(Element.class);  
		Marshaller marshallerObj = contextObj.createMarshaller();  
		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
		StringWriter stringWriter = new StringWriter();
		marshallerObj.marshal(instance, stringWriter);

		return stringWriter.toString();
	}
}
