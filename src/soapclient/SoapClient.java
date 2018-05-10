/*
CREDIT TO:
https://stackoverflow.com/questions/15948927/working-soap-client-example
@ acdcjunior


EDITED BY Danny
*/

package soapclient;

import javax.xml.soap.*;


public class SoapClient {
    
    private String email;
    private String password;
    
    public SoapClient(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    public void callApi() {
        String soapEndpointUrl = "http://localhost:8080/soap/adminauth";
        String soapAction = "http://localhost:8080/soap/adminauth";

        callSoapWebService(soapEndpointUrl, soapAction);
    }
   
    private void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "ns0";
        String myNamespaceURI = "http://soap/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

            /*
            Constructed SOAP Request Message:
            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:myNamespace="https://www.w3schools.com/xml/">
                <SOAP-ENV:Header/>  
                <SOAP-ENV:Body>
                    <myNamespace:CelsiusToFahrenheit>
                        <myNamespace:Celsius>100</myNamespace:Celsius>
                    </myNamespace:CelsiusToFahrenheit>
                </SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
            */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("login", myNamespace);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("email");
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("password");
        soapBodyElem1.addTextNode(email);
        soapBodyElem2.addTextNode(password);
    }

    private void callSoapWebService(String soapEndpointUrl, String soapAction) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
    }

    private SOAPMessage createSOAPRequest(String soapAction) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }
}