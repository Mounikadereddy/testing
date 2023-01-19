
package gov.va.vba.rbps.services.ws.client.mapping.org;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the gov.va.vba.rbps.services.ws.client.mapping.org package. 
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

    private final static QName _FindPOAsByFileNumbersResponse_QNAME = new QName("http://org.services.vetsnet.vba.va.gov/", "findPOAsByFileNumbersResponse");
    private final static QName _FindPOAsByFileNumbers_QNAME = new QName("http://org.services.vetsnet.vba.va.gov/", "findPOAsByFileNumbers");


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: gov.va.vba.rbps.services.ws.client.mapping.org
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FindPOAsByFileNumbers }
     * 
     */
    public FindPOAsByFileNumbers createFindPOAsByFileNumbers() {
        return new FindPOAsByFileNumbers();
    }

    /**
     * Create an instance of {@link FindPOAsByFileNumbersResponse }
     * 
     */
    public FindPOAsByFileNumbersResponse createFindPOAsByFileNumbersResponse() {
        return new FindPOAsByFileNumbersResponse();
    }


    /**
     * Create an instance of {@link PoaSearchResult }
     * 
     */
    public PoaSearchResult createPoaSearchResult() {
        return new PoaSearchResult();
    }


    /**
     * Create an instance of {@link PowerOfAttorneyDTO }
     * 
     */
    public PowerOfAttorneyDTO createPowerOfAttorneyDTO() {
        return new PowerOfAttorneyDTO();
    }


    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindPOAsByFileNumbersResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://org.services.vetsnet.vba.va.gov/", name = "findPOAsByFileNumbersResponse")
    public JAXBElement<FindPOAsByFileNumbersResponse> createFindPOAsByFileNumbersResponse(FindPOAsByFileNumbersResponse value) {
        return new JAXBElement<FindPOAsByFileNumbersResponse>(_FindPOAsByFileNumbersResponse_QNAME, FindPOAsByFileNumbersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindPOAsByFileNumbers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://org.services.vetsnet.vba.va.gov/", name = "findPOAsByFileNumbers")
    public JAXBElement<FindPOAsByFileNumbers> createFindPOAsByFileNumbers(FindPOAsByFileNumbers value) {
        return new JAXBElement<FindPOAsByFileNumbers>(_FindPOAsByFileNumbers_QNAME, FindPOAsByFileNumbers.class, null, value);
    }



}
