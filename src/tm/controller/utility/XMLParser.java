package tm.controller.utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import tm.model.MyLogger;
import tm.model.Participant;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class XMLParser {
    private static final String FILENAME = "tm/resources/participants.xml";

    public static List<Participant> parseParticipants(File file) {
        List<Participant> participants = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("participant");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element participantEl = (Element) nNode;
                    Node participantNickNode = participantEl.getElementsByTagName("nick_name").item(0);
                    if (participantNickNode == null)
                        throw new NullPointerException();
                    String participantNick = participantNickNode.getTextContent();
                    if (participantNick.isEmpty())
                        throw new IllegalArgumentException();
                    System.out.println(participantNick);
                    Participant p = new Participant(participantNick);
                    Node participantFirstNameNode = participantEl.getElementsByTagName("first_name").item(0);
                    Node participantLastNameNode = participantEl.getElementsByTagName("last_name").item(0);
                    Node participantAgeNode = participantEl.getElementsByTagName("age").item(0);
                    if (participantFirstNameNode != null && !participantFirstNameNode.getTextContent().isEmpty())
                        p.setFirstName(participantFirstNameNode.getTextContent());
                    if (participantLastNameNode != null && !participantLastNameNode.getTextContent().isEmpty())
                        p.setLastName(participantLastNameNode.getTextContent());
                    if (participantAgeNode != null && Utils.isInteger(participantAgeNode.getTextContent()))
                        p.setAge(Integer.parseInt(participantAgeNode.getTextContent()));
                    participants.add(p);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            MyLogger.LOG.log(Level.WARNING, "Parsovanie xml suboru zlyhalo");
        } catch (NullPointerException e){
            e.printStackTrace();
            MyLogger.LOG.log(Level.WARNING, "Parsovanie xml suboru zlyhalo (pri niektorom z ucastnikov chybal" +
                    " povninny parameter nick_name)");
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            MyLogger.LOG.log(Level.WARNING, "Parsovanie xml suboru zlyhalo (pri niektorom z ucastnikov bol element" +
                    " nick_name prazdny)");
        }
        return participants;
    }
}
