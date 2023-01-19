/*
 * RbpsClaimProcessorFactory.java
 * 
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.claimprocessor;

import gov.va.vba.rbps.claimprocessor.interfaces.ClaimProcessorInterface;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.xom.EndProductType;

import java.util.EnumMap;
import java.util.Map;

/**
 * The RBPS Claim Processor Factory
 * 
 * @since March 1, 2011
 * @version 1.0
 * @author Omar.Gaye
 * 
 * */
public class RbpsClaimProcessorFactory {
	private Map<EndProductType, ClaimProcessorInterface> claimProcessorMap = 
		new EnumMap<EndProductType, ClaimProcessorInterface>(EndProductType.class);
	
	//Spring dependency injection autowire by name
	private ClaimProcessorInterface ep130ClaimProcessorImpl;
	/*private ClaimProcessorInterface ep150ClaimProcessorImpl;
	private ClaimProcessorInterface ep155ClaimProcessorImpl;*/
	
	private boolean isLoaded = false;
	
	//load the processors
	public void load(){
		claimProcessorMap.put(EndProductType.COMPENSATION_EP130, ep130ClaimProcessorImpl);
		claimProcessorMap.put(EndProductType.COMPENSATION_EP131, ep130ClaimProcessorImpl);
		claimProcessorMap.put(EndProductType.COMPENSATION_EP132, ep130ClaimProcessorImpl);
		claimProcessorMap.put(EndProductType.COMPENSATION_EP133, ep130ClaimProcessorImpl);
		claimProcessorMap.put(EndProductType.COMPENSATION_EP134, ep130ClaimProcessorImpl);
		claimProcessorMap.put(EndProductType.COMPENSATION_EP135, ep130ClaimProcessorImpl);
		claimProcessorMap.put(EndProductType.COMPENSATION_EP136, ep130ClaimProcessorImpl);
		claimProcessorMap.put(EndProductType.COMPENSATION_EP137, ep130ClaimProcessorImpl);
		claimProcessorMap.put(EndProductType.COMPENSATION_EP138, ep130ClaimProcessorImpl);
		claimProcessorMap.put(EndProductType.COMPENSATION_EP139, ep130ClaimProcessorImpl);
		//claimProcessorMap.put(EndProductType.COMPENSATION_EP150, ep150ClaimProcessorImpl);
		//claimProcessorMap.put(EndProductType.COMPENSATION_EP155, ep155ClaimProcessorImpl);
		isLoaded = true;
	}

	public ClaimProcessorInterface getClaimProcessor(EndProductType endProductType){
		if (isLoaded == false)
			load();
		return claimProcessorMap.get(endProductType);
	}
	
	public void setEp130ClaimProcessorImpl(ClaimProcessorInterface ep130ClaimProcessorImpl){
		this.ep130ClaimProcessorImpl = ep130ClaimProcessorImpl;
	}
	
	/*public void setEp150ClaimProcessorImpl(ClaimProcessorInterface ep150ClaimProcessorImpl){
		this.ep150ClaimProcessorImpl = ep150ClaimProcessorImpl;
	}
	
	public void setEp155ClaimProcessorImpl(ClaimProcessorInterface ep155ClaimProcessorImpl){
		this.ep155ClaimProcessorImpl = ep155ClaimProcessorImpl;
	}*/
}
