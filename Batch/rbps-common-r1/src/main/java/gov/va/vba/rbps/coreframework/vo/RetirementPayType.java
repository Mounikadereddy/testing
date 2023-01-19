/*
 * RetirementPayType.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.coreframework.vo;

import java.util.Arrays;
import java.util.List;

/**
 * This enum contains all the retirement pay types from MLTY_RTRMNT_PAY table.
 * 
 * @since July 28, 2011
 * @version 1.0
 * @author Keya.Chowdhury
 * 
 */
public enum RetirementPayType {

		CONVERTED("Converted Retired Pay Case", "CONVRPC"), 
		DMDC("DMDC", "U"),
		DISABILITY("Disability Retired Payment", "DRP"),
		REGULAR("Regular Retired Payment", "RRP"),
		RETIRED("Retired Payment-Reserves", "RPR"), 
		BENEFIT("Survivor's Benefit Plan", "SBP"),
		TEMPORARY_DISABILITY("Temporary Disability Retired Payment", "TDRP");
				
		private List<String> values;
		
		private RetirementPayType(String... values) {
			this.values = Arrays.asList(values);
		}

		public static RetirementPayType find(String name) {
			for (RetirementPayType retirementPay : RetirementPayType.values()) {
				if (retirementPay.values.contains(name))
					return retirementPay;
			}
			return null;
		}

		public String getValue() {
			return values.get(0);
		}
		
		public String getCode() {
			return values.get(1);
		}

}
