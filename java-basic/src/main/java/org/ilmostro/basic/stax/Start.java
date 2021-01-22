package org.ilmostro.basic.stax;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.stax.xml.Project;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;

@Slf4j
public class Start {

    public static void main(String[] args) throws JAXBException, XMLStreamException {

        JAXBContext jaxbContext = JAXBContext.newInstance(Project.class);

        Unmarshaller um = jaxbContext.createUnmarshaller();


        InputStream resource = Start.class.getClassLoader().getResourceAsStream("test_xml.xml");
        XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = xmlFactory.createXMLStreamReader(resource);

        // interested in "book" elements only. Skip up to first "book"
        while (reader.hasNext() && (!reader.isStartElement() || !reader.getLocalName().equals("项目"))) {
            reader.next();
        }

        // read a book at a time
        while (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
            JAXBElement<Project> boolElement = um.unmarshal(reader, Project.class);
            Project project = boolElement.getValue();
            if (project.getType() != null) {  //skip footer tag
                log.info("this project:{}", project);
            }

            if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
                reader.next();
            }
        }
    }
}
