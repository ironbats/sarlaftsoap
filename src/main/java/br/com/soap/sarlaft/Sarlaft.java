package br.com.soap.sarlaft;

import br.com.soap.response.LoadWSInspektorResponse;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.NodeList;
import javax.xml.soap.*;

@Slf4j
public class Sarlaft {


    private static final String URL1 = "https://inspektortest.datalaft.com:8749/WSInspektor.asmx";
    private static final String SS2_NAMESPACE = "tem";
    private static final String SS2_NAMESPACE_URI = "http://tempuri.org/";


    public static void main(String[] args) throws Exception {

        Sarlaft sarlaft = new Sarlaft();

        LoadWSInspektorResponse loadWSInspektorResponse = sarlaft.createSoapWebService();
        System.out.println(loadWSInspektorResponse.getLoadWSInspektorResult());
    }

    public LoadWSInspektorResponse createSoapWebService() throws Exception {

        final SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

        final SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        SOAPMessage message = createSOAPRequest();
        final SOAPMessage soapMessage = soapConnection.call(message, URL1);

        final NodeList nodes = soapMessage.getSOAPBody().getElementsByTagName("LoadWSInspektorResponse");
        String result = nodes.item(0).getTextContent();
        LoadWSInspektorResponse response = new LoadWSInspektorResponse();
        response.setLoadWSInspektorResult(result);
        return response;


    }

    private static SOAPMessage createSOAPRequest() throws Exception {
        final MessageFactory messageFactory = MessageFactory.newInstance();
        final SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage);

        final MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.setHeader("Content-type", "text/xml; charset=utf-8");
        headers.setHeader("Content-Length", "length");

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        log.debug("Request SOAP Message: " + soapMessage.toString());

        return soapMessage;
    }

    private static void createSoapEnvelope(final SOAPMessage soapMessage) throws SOAPException {
        final SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        final SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(SS2_NAMESPACE, SS2_NAMESPACE_URI);

        // SOAP Body
        final SOAPBody soapBody = envelope.getBody();
        final SOAPElement soapBodyElem = soapBody.addChildElement("LoadWSInspektor", SS2_NAMESPACE);
        final SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("Numeiden", SS2_NAMESPACE);
        final SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("Nombre", SS2_NAMESPACE);
        final SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("Password", SS2_NAMESPACE);

        soapBodyElem1.addTextNode("felipe");
        soapBodyElem2.addTextNode("felipe");
        soapBodyElem3.addTextNode("123");
    }
}

