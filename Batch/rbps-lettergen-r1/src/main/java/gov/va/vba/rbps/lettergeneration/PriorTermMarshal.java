/*
 * PriorTermMarshal.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;

/**
 *      This class is used to build the "Your award amount" table in
 *      the approve and approve_deny notification letters.  It's just
 *      a simplification of the data fed back by the AWARDS web service.
 *      We want the code in the velocity template to be very simple, this
 *      aids in that.
 */
public class PriorTermMarshal {

    boolean							priorSchoolTermRejected;
	private String					priorSchoolTermRejectedText;
    private String					priorSchoolTermRejectedText1;
    private String					priorSchoolTermRejectedText2;
    private String					priorSchoolTermRejectedText3;
    private String					priorSchoolTermRejectedText4;

    public PriorTermMarshal() {
        //      Do nothing.
    }

    public String getPriorSchoolTermRejectedText() {
		return priorSchoolTermRejectedText;
	}


	public void setPriorSchoolTermRejectedText(String priorSchoolTermRejectedText) {
		this.priorSchoolTermRejectedText = priorSchoolTermRejectedText;
	}


	public String getPriorSchoolTermRejectedText1() {
		return priorSchoolTermRejectedText1;
	}


	public void setPriorSchoolTermRejectedText1(String priorSchoolTermRejectedText1) {
		this.priorSchoolTermRejectedText1 = priorSchoolTermRejectedText1;
	}


	public String getPriorSchoolTermRejectedText2() {
		return priorSchoolTermRejectedText2;
	}


	public void setPriorSchoolTermRejectedText2(String priorSchoolTermRejectedText2) {
		this.priorSchoolTermRejectedText2 = priorSchoolTermRejectedText2;
	}


	public String getPriorSchoolTermRejectedText3() {
		return priorSchoolTermRejectedText3;
	}


	public void setPriorSchoolTermRejectedText3(String priorSchoolTermRejectedText3) {
		this.priorSchoolTermRejectedText3 = priorSchoolTermRejectedText3;
	}


	public String getPriorSchoolTermRejectedText4() {
		return priorSchoolTermRejectedText4;
	}


	public void setPriorSchoolTermRejectedText4(String priorSchoolTermRejectedText4) {
		this.priorSchoolTermRejectedText4 = priorSchoolTermRejectedText4;
	}


	public boolean isPriorSchoolTermRejected() {
		return priorSchoolTermRejected;
	}


	public void setPriorSchoolTermRejected(boolean priorSchoolTermRejected) {
		this.priorSchoolTermRejected = priorSchoolTermRejected;
	}  
}
