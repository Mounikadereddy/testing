package gov.va.vba.rbps.rulesengine.child;

import gov.va.vba.rbps.rulesengine.engine.DecisionVariables;

import java.util.Date;

public class ChildDecisionVariables implements DecisionVariables {

    private boolean completed;
    private boolean priorSchoolTermValid = false;
    private boolean conditionSatisfied = false;
    private boolean priorConditionSatisfied = false;

    private int childAgeOnEffectiveDate;
    private int childAgeOnDateOfClaim;

    private Date schoolAllowableDate;
    private Date ratingPlusOneYear;
    private Date child18BirthDay;
    private Date ratingPlusOneYear30Days;
    private Date child19BirthDay;
    private Date dobPlusOneYear;
    private Date ratingPlusOneYearSevenDays;
    private final Date today = new Date();

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(final boolean completed) {
        this.completed = completed;
    }

    public boolean isPriorSchoolTermValid() {
        return priorSchoolTermValid;
    }

    public void setPriorSchoolTermValid(final boolean priorSchoolTermValid) {
        this.priorSchoolTermValid = priorSchoolTermValid;
    }

    public boolean isConditionSatisfied() {
        return conditionSatisfied;
    }

    public void setConditionSatisfied(final boolean conditionSatisfied) {
        this.conditionSatisfied = conditionSatisfied;
    }

    public boolean isPriorConditionSatisfied() {
        return priorConditionSatisfied;
    }

    public void setPriorConditionSatisfied(final boolean priorConditionSatisfied) {
        this.priorConditionSatisfied = priorConditionSatisfied;
    }

    public int getChildAgeOnEffectiveDate() {
        return childAgeOnEffectiveDate;
    }

    public void setChildAgeOnEffectiveDate(final int childAgeOnEffectiveDate) {
        this.childAgeOnEffectiveDate = childAgeOnEffectiveDate;
    }

    public int getChildAgeOnDateOfClaim() {
        return childAgeOnDateOfClaim;
    }

    public void setChildAgeOnDateOfClaim(final int childAgeOnDateOfClaim) {
        this.childAgeOnDateOfClaim = childAgeOnDateOfClaim;
    }

    public Date getSchoolAllowableDate() {
        return schoolAllowableDate;
    }

    public void setSchoolAllowableDate(final Date schoolAllowableDate) {
        this.schoolAllowableDate = schoolAllowableDate;
    }

    public Date getRatingPlusOneYear() {
        return ratingPlusOneYear;
    }

    public void setRatingPlusOneYear(final Date ratingPlusOneYear) {
        this.ratingPlusOneYear = ratingPlusOneYear;
    }

    public Date getChild18BirthDay() {
        return child18BirthDay;
    }

    public void setChild18BirthDay(final Date child18BirthDay) {
        this.child18BirthDay = child18BirthDay;
    }

    public Date getRatingPlusOneYear30Days() {
        return ratingPlusOneYear30Days;
    }

    public void setRatingPlusOneYear30Days(final Date ratingPlusOneYear30Days) {
        this.ratingPlusOneYear30Days = ratingPlusOneYear30Days;
    }

    public Date getChild19BirthDay() {
        return child19BirthDay;
    }

    public void setChild19BirthDay(final Date child19BirthDay) {
        this.child19BirthDay = child19BirthDay;
    }

    public Date getDobPlusOneYear() {
        return dobPlusOneYear;
    }

    public void setDobPlusOneYear(final Date dobPlusOneYear) {
        this.dobPlusOneYear = dobPlusOneYear;
    }

    public Date getRatingPlusOneYearSevenDays() {
        return ratingPlusOneYearSevenDays;
    }

    public void setRatingPlusOneYearSevenDays(final Date ratingPlusOneYearSevenDays) {
        this.ratingPlusOneYearSevenDays = ratingPlusOneYearSevenDays;
    }

    public Date getToday() {
        return today;
    }
}
