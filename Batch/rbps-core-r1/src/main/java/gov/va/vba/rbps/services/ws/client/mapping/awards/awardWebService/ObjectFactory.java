
package gov.va.vba.rbps.services.ws.client.mapping.awards.awardWebService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the gov.va.vba.rbps.services.ws.client.mapping.awards.awardWebService package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FindStationOfJurisdictionResponse_QNAME = new QName("http://award.services.vetsnet.vba.va.gov/", "findStationOfJurisdictionResponse");
    private final static QName _FindStationOfJurisdiction_QNAME = new QName("http://award.services.vetsnet.vba.va.gov/", "findStationOfJurisdiction");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: gov.va.vba.rbps.services.ws.client.mapping.awards.awardWebService
     * 
     */
    public ObjectFactory() {
    }


    /**
     * Create an instance of {@link FindStationOfJurisdiction }
     * 
     */
    public FindStationOfJurisdiction createFindStationOfJurisdiction() {
        return new FindStationOfJurisdiction();
    }



    /**
     * Create an instance of {@link FindStationOfJurisdictionResponse }
     * 
     */
    public FindStationOfJurisdictionResponse createFindStationOfJurisdictionResponse() {
        return new FindStationOfJurisdictionResponse();
    }



    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindStationOfJurisdictionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://award.services.vetsnet.vba.va.gov/", name = "findStationOfJurisdictionResponse")
    public JAXBElement<FindStationOfJurisdictionResponse> createFindStationOfJurisdictionResponse(FindStationOfJurisdictionResponse value) {
        return new JAXBElement<FindStationOfJurisdictionResponse>(_FindStationOfJurisdictionResponse_QNAME, FindStationOfJurisdictionResponse.class, null, value);
    }


    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindStationOfJurisdiction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://award.services.vetsnet.vba.va.gov/", name = "findStationOfJurisdiction")
    public JAXBElement<FindStationOfJurisdiction> createFindStationOfJurisdiction(FindStationOfJurisdiction value) {
        return new JAXBElement<FindStationOfJurisdiction>(_FindStationOfJurisdiction_QNAME, FindStationOfJurisdiction.class, null, value);
    }


}
