/*
	 * MarriageTerminationType.java
	 * 
	 * Copyright 2011 U.S. Department of Veterans Affairs
	 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
	 *
	 */

package gov.va.vba.rbps.coreframework.xom;

import java.util.Arrays;
import java.util.List;

public enum MarriageTerminationType {
		DEATH("Death"), 
		DIVORCE("Divorce"),
		OTHER("Other");
		
		private List<String> values;
		
		private MarriageTerminationType(String... values) {
			this.values = Arrays.asList(values);
		}

		public static MarriageTerminationType find(String name) {
			for (MarriageTerminationType termination : MarriageTerminationType.values()) {
				if (termination.values.contains(name))
					return termination;
			}
			return null;
		}

		public String getValue() {
			return values.get(0);
		}

	}
	
