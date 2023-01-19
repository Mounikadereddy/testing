/*
 * VirtualVaWebServiceCaller.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.virtualva;


import gov.va.vba.rbps.lettergeneration.virtualva.ws.GetStatusByDate;
import gov.va.vba.rbps.lettergeneration.virtualva.ws.GetStatusByDateResponse;

import org.springframework.ws.client.core.WebServiceTemplate;


/**
 *      Class that actually calls the Virtual VA web service.
 */
public class VirtualVaWebServiceCaller {


	private WebServiceTemplate webServiceTemplate;



    public String callVirtualVaWebService() {

        //      TODO (MUCH LATER) : get from the properties file - probably by being part of the spring config file.
		String uri = "http://10.204.11.66:7001/VVA_WS/BFIJob";

		return ((GetStatusByDateResponse) webServiceTemplate.marshalSendAndReceive( uri,
		                                                 prepareArgumentsForVirtualVa() )).getReturn();
	}


	private GetStatusByDate prepareArgumentsForVirtualVa() {

		GetStatusByDate getStatusByDate = new GetStatusByDate();

		getStatusByDate.setBeginDate( VirtualVaWebServiceParameters.getBeginDate() );
		getStatusByDate.setEndDate( VirtualVaWebServiceParameters.getEndDate() );
		getStatusByDate.setFeedType( VirtualVaWebServiceParameters.getFeedType() );
		getStatusByDate.setUserID( VirtualVaWebServiceParameters.getUserID() );

		return getStatusByDate;
	}


    public void setWebServiceTemplate( final WebServiceTemplate webServiceTemplate ) {

        this.webServiceTemplate = webServiceTemplate;
    }
}
