/*
 * WSOutputXMLReader.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.ws.client;


import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.FindByDataSuppliedResponse;
import gov.va.vba.rbps.services.ws.client.util.XmlUnmarshaller;


public class WSOutputXMLReader {


    public static final FindByDataSuppliedResponse readUserInformationResponse() {

        String xmlPath = "gov/va/vba/rbps/services/ws/client/handler/vonapp/userInformationResponse1.xml";

        return (FindByDataSuppliedResponse) new XmlUnmarshaller().loadResponse( xmlPath, FindByDataSuppliedResponse.class );
    }


    public static final FindByDataSuppliedResponse readUserInformationResponseWith674() {

        String xmlPath = "gov/va/vba/rbps/services/ws/client/handler/vonapp/userInformationResponseWith674.xml";

        return (FindByDataSuppliedResponse) new XmlUnmarshaller().loadResponse( xmlPath, FindByDataSuppliedResponse.class );
    }


    public static final FindByDataSuppliedResponse readUserInformationResponseWith674DatesDontMatch() {

        String xmlPath = "gov/va/vba/rbps/services/ws/client/handler/vonapp/userInformationResponseWith674DatesDontMatch.xml";

        return (FindByDataSuppliedResponse) new XmlUnmarshaller().loadResponse( xmlPath, FindByDataSuppliedResponse.class );
    }


    public static final FindByDataSuppliedResponse readUserInformationResponseWithInvalidClaimId() {

        String xmlPath = "gov/va/vba/rbps/services/ws/client/handler/vonapp/userInformationResponseInvalidClaimId.xml";

        return (FindByDataSuppliedResponse) new XmlUnmarshaller()
                        .loadResponse( xmlPath, FindByDataSuppliedResponse.class );
    }
}
