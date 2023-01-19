package gov.va.vba.rbps.rulesengine.spouse;

import java.util.Date;

import gov.va.vba.rbps.rulesengine.engine.DecisionVariables;

public class SpouseDecisionVariables implements DecisionVariables {
	
	private Date ratingDatePlus1year;
	private Date ratingPlus1yearPlus300Days;
	private Date marragePlus1year;
	private boolean exceptionGenerated;
	private Date ratingPlus1yearPlus7days;


	public Date getRatingDatePlus1year() {
		return ratingDatePlus1year;
	}

	public void setRatingDatePlus1year(Date ratingDatePlus1year) {
		this.ratingDatePlus1year = ratingDatePlus1year;
	}

	public Date getRatingPlus1yearPlus300Days() {
		return ratingPlus1yearPlus300Days;
	}

	public void setRatingPlus1yearPlus300Days(Date ratingPlus1yearPlus300Days) {
		this.ratingPlus1yearPlus300Days = ratingPlus1yearPlus300Days;
	}

	public Date getMarragePlus1year() {
		return marragePlus1year;
	}

	public void setMarragePlus1year(Date marragePlus1year) {
		this.marragePlus1year = marragePlus1year;
	}

	public boolean isExceptionGenerated() {
		return exceptionGenerated;
	}

	public void setExceptionGenerated(boolean exceptionGenerated) {
		this.exceptionGenerated = exceptionGenerated;
	}

	public Date getRatingPlus1yearPlus7days() {
		return ratingPlus1yearPlus7days;
	}

	public void setRatingPlus1yearPlus7days(Date ratingPlus1yearPlus7days) {
		this.ratingPlus1yearPlus7days = ratingPlus1yearPlus7days;
	}
}
