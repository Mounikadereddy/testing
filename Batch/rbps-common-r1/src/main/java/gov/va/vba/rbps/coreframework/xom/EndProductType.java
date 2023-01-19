/*
 * EndProductType.java
 * 
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. use is subject to security terms
 *
 */

package gov.va.vba.rbps.coreframework.xom;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @since March 1, 2011
 * @version 1.0
 * @author Omar.Gaye
 * 
 **/
public enum EndProductType {
	COMPENSATION_EP130("130"),
	COMPENSATION_EP131("131"),
	COMPENSATION_EP132("132"),
	COMPENSATION_EP133("133"),
	COMPENSATION_EP134("134"),
	COMPENSATION_EP135("135"),
	COMPENSATION_EP136("136"),
	COMPENSATION_EP137("137"),
	COMPENSATION_EP138("138"),
	COMPENSATION_EP139("139");
//	COMPENSATION_EP150("150"),
//	COMPENSATION_EP155("155");
	
private List<String> values;
	
	private EndProductType(String... values) {
		this.values = Arrays.asList(values);
	}

	public static EndProductType find(String name) {
		for (EndProductType ep : EndProductType.values()) {
			if (ep.values.contains(name))
				return ep;
		}
		return null;
	}

	public String getValue() {
		return values.get(0);
	}
}
