
package gov.va.vba.rbps.services.ws.client.mapping.awards.service;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for awardLineVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="awardLineVO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://gov.va.vba.benefits.awards.ws/services}voBase">
 *       &lt;sequence>
 *         &lt;element name="AA_SPOUSE_RATE_AMT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="ADDTNL_CHILD_RATE_AMT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="BASIC_RATE_AMT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="FST_CHD_NO_SP_RATE_AMT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="FST_CHD_SP_RATE_AMT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="PARENT_RATE_AMT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="SCHOOL_CHILD_RATE_AMT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="SPOUSE_RATE_AMT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="awardBasis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="awardEventID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="awardLineReportID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="awardLineType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="awardLineTypeDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="awardOverrideIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="awardType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beneficiaryID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="childIncomeForVAPurposesText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="childStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clothingAllowanceEligibilityYear" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="combatRelatedSpecialCompensationAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="concurrentRetirementDisabilityPayAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="currentFamilyMaximumAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="currentPrimaryInsuranceAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="dicSpouseChildRateAdjustmentIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="disabilityPercent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="disabilitySeveranceRemainingBalanceAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="drillPayWithholdingAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="effectiveDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="effectiveDateOverrideIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entitlementType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="grossAdjustmentAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="grossAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="halfRateIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="incomeForVAPurposesAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="individualUnemploymentIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="institutionalizationWithholdingAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="lumpSumActualAdjustmentAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="maximumAnnualPensionRateAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="militaryServicePeriodType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedAction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="modifiedLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedProcess" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monthlyActualAdjustmentAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="netAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="numberOfChildren" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numberOfDependents" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numberOfHelplessChildren" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numberOfMinorChildren" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numberOfParents" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numberOfSchoolChildren" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numberOfUnclaimedDICChilldren" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="otherManualAdjustmentIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="overrideEventID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="parentMaritalStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priors" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="protectedIncomeForVAPurposesAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="recaActualAdjustmentAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="recaRemainingBalanceAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="retiredPayActualAdjustmentAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="separationPayActualAdjustmentAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="separationPayRemainingBalanceAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="serverancePayActualAdjustmentAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="specialMonthlyCompensationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specialMonthlyCompensationTypeDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specialMonthlyEntitlementText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specialMonthlyPensionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specialMonthlyPensionTypeDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spouseIsPartOfAward" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tortActualAdjustmentAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="tortRemainingBalanceAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="totalWithholdingAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="veteranID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="withholdingAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="workersCompensationActualAdjustmentAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="workersCompensationRemainingBalanceAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "awardLineVO", propOrder = {
    "aaspouserateamt",
    "addtnlchildrateamt",
    "basicrateamt",
    "fstchdnosprateamt",
    "fstchdsprateamt",
    "parentrateamt",
    "schoolchildrateamt",
    "spouserateamt",
    "awardBasis",
    "awardEventID",
    "awardLineReportID",
    "awardLineType",
    "awardLineTypeDesc",
    "awardOverrideIndicator",
    "awardType",
    "beneficiaryID",
    "childIncomeForVAPurposesText",
    "childStatus",
    "clothingAllowanceEligibilityYear",
    "combatRelatedSpecialCompensationAmount",
    "concurrentRetirementDisabilityPayAmount",
    "currentFamilyMaximumAmount",
    "currentPrimaryInsuranceAmount",
    "dicSpouseChildRateAdjustmentIndicator",
    "disabilityPercent",
    "disabilitySeveranceRemainingBalanceAmount",
    "drillPayWithholdingAmount",
    "effectiveDate",
    "effectiveDateOverrideIndicator",
    "entitlementType",
    "grossAdjustmentAmount",
    "grossAmount",
    "halfRateIndicator",
    "incomeForVAPurposesAmount",
    "individualUnemploymentIndicator",
    "institutionalizationWithholdingAmount",
    "lumpSumActualAdjustmentAmount",
    "maximumAnnualPensionRateAmount",
    "militaryServicePeriodType",
    "modifiedAction",
    "modifiedBy",
    "modifiedDate",
    "modifiedLocation",
    "modifiedProcess",
    "monthlyActualAdjustmentAmount",
    "netAmount",
    "numberOfChildren",
    "numberOfDependents",
    "numberOfHelplessChildren",
    "numberOfMinorChildren",
    "numberOfParents",
    "numberOfSchoolChildren",
    "numberOfUnclaimedDICChilldren",
    "otherManualAdjustmentIndicator",
    "overrideEventID",
    "parentMaritalStatus",
    "priors",
    "protectedIncomeForVAPurposesAmount",
    "recaActualAdjustmentAmount",
    "recaRemainingBalanceAmount",
    "retiredPayActualAdjustmentAmount",
    "separationPayActualAdjustmentAmount",
    "separationPayRemainingBalanceAmount",
    "serverancePayActualAdjustmentAmount",
    "specialMonthlyCompensationType",
    "specialMonthlyCompensationTypeDesc",
    "specialMonthlyEntitlementText",
    "specialMonthlyPensionType",
    "specialMonthlyPensionTypeDesc",
    "spouseIsPartOfAward",
    "tortActualAdjustmentAmount",
    "tortRemainingBalanceAmount",
    "totalWithholdingAmount",
    "veteranID",
    "withholdingAmount",
    "workersCompensationActualAdjustmentAmount",
    "workersCompensationRemainingBalanceAmount"
})
@XmlSeeAlso({
    HAwardLineVO.class
})
public class AwardLineVO
    extends VoBase
{

    @XmlElement(name = "AA_SPOUSE_RATE_AMT")
    protected BigDecimal aaspouserateamt;
    @XmlElement(name = "ADDTNL_CHILD_RATE_AMT")
    protected BigDecimal addtnlchildrateamt;
    @XmlElement(name = "BASIC_RATE_AMT")
    protected BigDecimal basicrateamt;
    @XmlElement(name = "FST_CHD_NO_SP_RATE_AMT")
    protected BigDecimal fstchdnosprateamt;
    @XmlElement(name = "FST_CHD_SP_RATE_AMT")
    protected BigDecimal fstchdsprateamt;
    @XmlElement(name = "PARENT_RATE_AMT")
    protected BigDecimal parentrateamt;
    @XmlElement(name = "SCHOOL_CHILD_RATE_AMT")
    protected BigDecimal schoolchildrateamt;
    @XmlElement(name = "SPOUSE_RATE_AMT")
    protected BigDecimal spouserateamt;
    protected String awardBasis;
    protected Long awardEventID;
    protected Long awardLineReportID;
    protected String awardLineType;
    protected String awardLineTypeDesc;
    protected String awardOverrideIndicator;
    protected String awardType;
    protected Long beneficiaryID;
    protected String childIncomeForVAPurposesText;
    protected String childStatus;
    protected String clothingAllowanceEligibilityYear;
    protected BigDecimal combatRelatedSpecialCompensationAmount;
    protected BigDecimal concurrentRetirementDisabilityPayAmount;
    protected BigDecimal currentFamilyMaximumAmount;
    protected BigDecimal currentPrimaryInsuranceAmount;
    protected String dicSpouseChildRateAdjustmentIndicator;
    protected String disabilityPercent;
    protected BigDecimal disabilitySeveranceRemainingBalanceAmount;
    protected BigDecimal drillPayWithholdingAmount;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectiveDate;
    protected String effectiveDateOverrideIndicator;
    protected String entitlementType;
    protected BigDecimal grossAdjustmentAmount;
    protected BigDecimal grossAmount;
    protected String halfRateIndicator;
    protected BigDecimal incomeForVAPurposesAmount;
    protected String individualUnemploymentIndicator;
    protected BigDecimal institutionalizationWithholdingAmount;
    protected BigDecimal lumpSumActualAdjustmentAmount;
    protected BigDecimal maximumAnnualPensionRateAmount;
    protected String militaryServicePeriodType;
    protected String modifiedAction;
    protected String modifiedBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    protected String modifiedLocation;
    protected String modifiedProcess;
    protected BigDecimal monthlyActualAdjustmentAmount;
    protected BigDecimal netAmount;
    protected Integer numberOfChildren;
    protected Integer numberOfDependents;
    protected Integer numberOfHelplessChildren;
    protected Integer numberOfMinorChildren;
    protected Integer numberOfParents;
    protected Integer numberOfSchoolChildren;
    protected Integer numberOfUnclaimedDICChilldren;
    protected String otherManualAdjustmentIndicator;
    protected Long overrideEventID;
    protected String parentMaritalStatus;
    protected String priors;
    protected BigDecimal protectedIncomeForVAPurposesAmount;
    protected BigDecimal recaActualAdjustmentAmount;
    protected BigDecimal recaRemainingBalanceAmount;
    protected BigDecimal retiredPayActualAdjustmentAmount;
    protected BigDecimal separationPayActualAdjustmentAmount;
    protected BigDecimal separationPayRemainingBalanceAmount;
    protected BigDecimal serverancePayActualAdjustmentAmount;
    protected String specialMonthlyCompensationType;
    protected String specialMonthlyCompensationTypeDesc;
    protected String specialMonthlyEntitlementText;
    protected String specialMonthlyPensionType;
    protected String specialMonthlyPensionTypeDesc;
    protected String spouseIsPartOfAward;
    protected BigDecimal tortActualAdjustmentAmount;
    protected BigDecimal tortRemainingBalanceAmount;
    protected BigDecimal totalWithholdingAmount;
    protected Long veteranID;
    protected BigDecimal withholdingAmount;
    protected BigDecimal workersCompensationActualAdjustmentAmount;
    protected BigDecimal workersCompensationRemainingBalanceAmount;

    /**
     * Gets the value of the aaspouserateamt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAASPOUSERATEAMT() {
        return aaspouserateamt;
    }

    /**
     * Sets the value of the aaspouserateamt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAASPOUSERATEAMT(BigDecimal value) {
        this.aaspouserateamt = value;
    }

    /**
     * Gets the value of the addtnlchildrateamt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getADDTNLCHILDRATEAMT() {
        return addtnlchildrateamt;
    }

    /**
     * Sets the value of the addtnlchildrateamt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setADDTNLCHILDRATEAMT(BigDecimal value) {
        this.addtnlchildrateamt = value;
    }

    /**
     * Gets the value of the basicrateamt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBASICRATEAMT() {
        return basicrateamt;
    }

    /**
     * Sets the value of the basicrateamt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBASICRATEAMT(BigDecimal value) {
        this.basicrateamt = value;
    }

    /**
     * Gets the value of the fstchdnosprateamt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFSTCHDNOSPRATEAMT() {
        return fstchdnosprateamt;
    }

    /**
     * Sets the value of the fstchdnosprateamt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFSTCHDNOSPRATEAMT(BigDecimal value) {
        this.fstchdnosprateamt = value;
    }

    /**
     * Gets the value of the fstchdsprateamt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFSTCHDSPRATEAMT() {
        return fstchdsprateamt;
    }

    /**
     * Sets the value of the fstchdsprateamt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFSTCHDSPRATEAMT(BigDecimal value) {
        this.fstchdsprateamt = value;
    }

    /**
     * Gets the value of the parentrateamt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPARENTRATEAMT() {
        return parentrateamt;
    }

    /**
     * Sets the value of the parentrateamt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPARENTRATEAMT(BigDecimal value) {
        this.parentrateamt = value;
    }

    /**
     * Gets the value of the schoolchildrateamt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSCHOOLCHILDRATEAMT() {
        return schoolchildrateamt;
    }

    /**
     * Sets the value of the schoolchildrateamt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSCHOOLCHILDRATEAMT(BigDecimal value) {
        this.schoolchildrateamt = value;
    }

    /**
     * Gets the value of the spouserateamt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSPOUSERATEAMT() {
        return spouserateamt;
    }

    /**
     * Sets the value of the spouserateamt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSPOUSERATEAMT(BigDecimal value) {
        this.spouserateamt = value;
    }

    /**
     * Gets the value of the awardBasis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwardBasis() {
        return awardBasis;
    }

    /**
     * Sets the value of the awardBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwardBasis(String value) {
        this.awardBasis = value;
    }

    /**
     * Gets the value of the awardEventID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAwardEventID() {
        return awardEventID;
    }

    /**
     * Sets the value of the awardEventID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAwardEventID(Long value) {
        this.awardEventID = value;
    }

    /**
     * Gets the value of the awardLineReportID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAwardLineReportID() {
        return awardLineReportID;
    }

    /**
     * Sets the value of the awardLineReportID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAwardLineReportID(Long value) {
        this.awardLineReportID = value;
    }

    /**
     * Gets the value of the awardLineType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwardLineType() {
        return awardLineType;
    }

    /**
     * Sets the value of the awardLineType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwardLineType(String value) {
        this.awardLineType = value;
    }

    /**
     * Gets the value of the awardLineTypeDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwardLineTypeDesc() {
        return awardLineTypeDesc;
    }

    /**
     * Sets the value of the awardLineTypeDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwardLineTypeDesc(String value) {
        this.awardLineTypeDesc = value;
    }

    /**
     * Gets the value of the awardOverrideIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwardOverrideIndicator() {
        return awardOverrideIndicator;
    }

    /**
     * Sets the value of the awardOverrideIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwardOverrideIndicator(String value) {
        this.awardOverrideIndicator = value;
    }

    /**
     * Gets the value of the awardType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwardType() {
        return awardType;
    }

    /**
     * Sets the value of the awardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwardType(String value) {
        this.awardType = value;
    }

    /**
     * Gets the value of the beneficiaryID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBeneficiaryID() {
        return beneficiaryID;
    }

    /**
     * Sets the value of the beneficiaryID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBeneficiaryID(Long value) {
        this.beneficiaryID = value;
    }

    /**
     * Gets the value of the childIncomeForVAPurposesText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChildIncomeForVAPurposesText() {
        return childIncomeForVAPurposesText;
    }

    /**
     * Sets the value of the childIncomeForVAPurposesText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChildIncomeForVAPurposesText(String value) {
        this.childIncomeForVAPurposesText = value;
    }

    /**
     * Gets the value of the childStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChildStatus() {
        return childStatus;
    }

    /**
     * Sets the value of the childStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChildStatus(String value) {
        this.childStatus = value;
    }

    /**
     * Gets the value of the clothingAllowanceEligibilityYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClothingAllowanceEligibilityYear() {
        return clothingAllowanceEligibilityYear;
    }

    /**
     * Sets the value of the clothingAllowanceEligibilityYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClothingAllowanceEligibilityYear(String value) {
        this.clothingAllowanceEligibilityYear = value;
    }

    /**
     * Gets the value of the combatRelatedSpecialCompensationAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCombatRelatedSpecialCompensationAmount() {
        return combatRelatedSpecialCompensationAmount;
    }

    /**
     * Sets the value of the combatRelatedSpecialCompensationAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCombatRelatedSpecialCompensationAmount(BigDecimal value) {
        this.combatRelatedSpecialCompensationAmount = value;
    }

    /**
     * Gets the value of the concurrentRetirementDisabilityPayAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getConcurrentRetirementDisabilityPayAmount() {
        return concurrentRetirementDisabilityPayAmount;
    }

    /**
     * Sets the value of the concurrentRetirementDisabilityPayAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setConcurrentRetirementDisabilityPayAmount(BigDecimal value) {
        this.concurrentRetirementDisabilityPayAmount = value;
    }

    /**
     * Gets the value of the currentFamilyMaximumAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCurrentFamilyMaximumAmount() {
        return currentFamilyMaximumAmount;
    }

    /**
     * Sets the value of the currentFamilyMaximumAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCurrentFamilyMaximumAmount(BigDecimal value) {
        this.currentFamilyMaximumAmount = value;
    }

    /**
     * Gets the value of the currentPrimaryInsuranceAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCurrentPrimaryInsuranceAmount() {
        return currentPrimaryInsuranceAmount;
    }

    /**
     * Sets the value of the currentPrimaryInsuranceAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCurrentPrimaryInsuranceAmount(BigDecimal value) {
        this.currentPrimaryInsuranceAmount = value;
    }

    /**
     * Gets the value of the dicSpouseChildRateAdjustmentIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDicSpouseChildRateAdjustmentIndicator() {
        return dicSpouseChildRateAdjustmentIndicator;
    }

    /**
     * Sets the value of the dicSpouseChildRateAdjustmentIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDicSpouseChildRateAdjustmentIndicator(String value) {
        this.dicSpouseChildRateAdjustmentIndicator = value;
    }

    /**
     * Gets the value of the disabilityPercent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisabilityPercent() {
        return disabilityPercent;
    }

    /**
     * Sets the value of the disabilityPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisabilityPercent(String value) {
        this.disabilityPercent = value;
    }

    /**
     * Gets the value of the disabilitySeveranceRemainingBalanceAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDisabilitySeveranceRemainingBalanceAmount() {
        return disabilitySeveranceRemainingBalanceAmount;
    }

    /**
     * Sets the value of the disabilitySeveranceRemainingBalanceAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDisabilitySeveranceRemainingBalanceAmount(BigDecimal value) {
        this.disabilitySeveranceRemainingBalanceAmount = value;
    }

    /**
     * Gets the value of the drillPayWithholdingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDrillPayWithholdingAmount() {
        return drillPayWithholdingAmount;
    }

    /**
     * Sets the value of the drillPayWithholdingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDrillPayWithholdingAmount(BigDecimal value) {
        this.drillPayWithholdingAmount = value;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the effectiveDateOverrideIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffectiveDateOverrideIndicator() {
        return effectiveDateOverrideIndicator;
    }

    /**
     * Sets the value of the effectiveDateOverrideIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffectiveDateOverrideIndicator(String value) {
        this.effectiveDateOverrideIndicator = value;
    }

    /**
     * Gets the value of the entitlementType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntitlementType() {
        return entitlementType;
    }

    /**
     * Sets the value of the entitlementType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntitlementType(String value) {
        this.entitlementType = value;
    }

    /**
     * Gets the value of the grossAdjustmentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getGrossAdjustmentAmount() {
        return grossAdjustmentAmount;
    }

    /**
     * Sets the value of the grossAdjustmentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setGrossAdjustmentAmount(BigDecimal value) {
        this.grossAdjustmentAmount = value;
    }

    /**
     * Gets the value of the grossAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    /**
     * Sets the value of the grossAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setGrossAmount(BigDecimal value) {
        this.grossAmount = value;
    }

    /**
     * Gets the value of the halfRateIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHalfRateIndicator() {
        return halfRateIndicator;
    }

    /**
     * Sets the value of the halfRateIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHalfRateIndicator(String value) {
        this.halfRateIndicator = value;
    }

    /**
     * Gets the value of the incomeForVAPurposesAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIncomeForVAPurposesAmount() {
        return incomeForVAPurposesAmount;
    }

    /**
     * Sets the value of the incomeForVAPurposesAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIncomeForVAPurposesAmount(BigDecimal value) {
        this.incomeForVAPurposesAmount = value;
    }

    /**
     * Gets the value of the individualUnemploymentIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndividualUnemploymentIndicator() {
        return individualUnemploymentIndicator;
    }

    /**
     * Sets the value of the individualUnemploymentIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndividualUnemploymentIndicator(String value) {
        this.individualUnemploymentIndicator = value;
    }

    /**
     * Gets the value of the institutionalizationWithholdingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getInstitutionalizationWithholdingAmount() {
        return institutionalizationWithholdingAmount;
    }

    /**
     * Sets the value of the institutionalizationWithholdingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setInstitutionalizationWithholdingAmount(BigDecimal value) {
        this.institutionalizationWithholdingAmount = value;
    }

    /**
     * Gets the value of the lumpSumActualAdjustmentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLumpSumActualAdjustmentAmount() {
        return lumpSumActualAdjustmentAmount;
    }

    /**
     * Sets the value of the lumpSumActualAdjustmentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLumpSumActualAdjustmentAmount(BigDecimal value) {
        this.lumpSumActualAdjustmentAmount = value;
    }

    /**
     * Gets the value of the maximumAnnualPensionRateAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaximumAnnualPensionRateAmount() {
        return maximumAnnualPensionRateAmount;
    }

    /**
     * Sets the value of the maximumAnnualPensionRateAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaximumAnnualPensionRateAmount(BigDecimal value) {
        this.maximumAnnualPensionRateAmount = value;
    }

    /**
     * Gets the value of the militaryServicePeriodType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMilitaryServicePeriodType() {
        return militaryServicePeriodType;
    }

    /**
     * Sets the value of the militaryServicePeriodType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMilitaryServicePeriodType(String value) {
        this.militaryServicePeriodType = value;
    }

    /**
     * Gets the value of the modifiedAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedAction() {
        return modifiedAction;
    }

    /**
     * Sets the value of the modifiedAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedAction(String value) {
        this.modifiedAction = value;
    }

    /**
     * Gets the value of the modifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the value of the modifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedBy(String value) {
        this.modifiedBy = value;
    }

    /**
     * Gets the value of the modifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the value of the modifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedDate(XMLGregorianCalendar value) {
        this.modifiedDate = value;
    }

    /**
     * Gets the value of the modifiedLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedLocation() {
        return modifiedLocation;
    }

    /**
     * Sets the value of the modifiedLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedLocation(String value) {
        this.modifiedLocation = value;
    }

    /**
     * Gets the value of the modifiedProcess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedProcess() {
        return modifiedProcess;
    }

    /**
     * Sets the value of the modifiedProcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedProcess(String value) {
        this.modifiedProcess = value;
    }

    /**
     * Gets the value of the monthlyActualAdjustmentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMonthlyActualAdjustmentAmount() {
        return monthlyActualAdjustmentAmount;
    }

    /**
     * Sets the value of the monthlyActualAdjustmentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMonthlyActualAdjustmentAmount(BigDecimal value) {
        this.monthlyActualAdjustmentAmount = value;
    }

    /**
     * Gets the value of the netAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNetAmount() {
        return netAmount;
    }

    /**
     * Sets the value of the netAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNetAmount(BigDecimal value) {
        this.netAmount = value;
    }

    /**
     * Gets the value of the numberOfChildren property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfChildren() {
        return numberOfChildren;
    }

    /**
     * Sets the value of the numberOfChildren property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfChildren(Integer value) {
        this.numberOfChildren = value;
    }

    /**
     * Gets the value of the numberOfDependents property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfDependents() {
        return numberOfDependents;
    }

    /**
     * Sets the value of the numberOfDependents property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfDependents(Integer value) {
        this.numberOfDependents = value;
    }

    /**
     * Gets the value of the numberOfHelplessChildren property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfHelplessChildren() {
        return numberOfHelplessChildren;
    }

    /**
     * Sets the value of the numberOfHelplessChildren property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfHelplessChildren(Integer value) {
        this.numberOfHelplessChildren = value;
    }

    /**
     * Gets the value of the numberOfMinorChildren property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfMinorChildren() {
        return numberOfMinorChildren;
    }

    /**
     * Sets the value of the numberOfMinorChildren property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfMinorChildren(Integer value) {
        this.numberOfMinorChildren = value;
    }

    /**
     * Gets the value of the numberOfParents property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfParents() {
        return numberOfParents;
    }

    /**
     * Sets the value of the numberOfParents property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfParents(Integer value) {
        this.numberOfParents = value;
    }

    /**
     * Gets the value of the numberOfSchoolChildren property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfSchoolChildren() {
        return numberOfSchoolChildren;
    }

    /**
     * Sets the value of the numberOfSchoolChildren property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfSchoolChildren(Integer value) {
        this.numberOfSchoolChildren = value;
    }

    /**
     * Gets the value of the numberOfUnclaimedDICChilldren property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfUnclaimedDICChilldren() {
        return numberOfUnclaimedDICChilldren;
    }

    /**
     * Sets the value of the numberOfUnclaimedDICChilldren property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfUnclaimedDICChilldren(Integer value) {
        this.numberOfUnclaimedDICChilldren = value;
    }

    /**
     * Gets the value of the otherManualAdjustmentIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherManualAdjustmentIndicator() {
        return otherManualAdjustmentIndicator;
    }

    /**
     * Sets the value of the otherManualAdjustmentIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherManualAdjustmentIndicator(String value) {
        this.otherManualAdjustmentIndicator = value;
    }

    /**
     * Gets the value of the overrideEventID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOverrideEventID() {
        return overrideEventID;
    }

    /**
     * Sets the value of the overrideEventID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOverrideEventID(Long value) {
        this.overrideEventID = value;
    }

    /**
     * Gets the value of the parentMaritalStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentMaritalStatus() {
        return parentMaritalStatus;
    }

    /**
     * Sets the value of the parentMaritalStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentMaritalStatus(String value) {
        this.parentMaritalStatus = value;
    }

    /**
     * Gets the value of the priors property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriors() {
        return priors;
    }

    /**
     * Sets the value of the priors property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriors(String value) {
        this.priors = value;
    }

    /**
     * Gets the value of the protectedIncomeForVAPurposesAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getProtectedIncomeForVAPurposesAmount() {
        return protectedIncomeForVAPurposesAmount;
    }

    /**
     * Sets the value of the protectedIncomeForVAPurposesAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setProtectedIncomeForVAPurposesAmount(BigDecimal value) {
        this.protectedIncomeForVAPurposesAmount = value;
    }

    /**
     * Gets the value of the recaActualAdjustmentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRecaActualAdjustmentAmount() {
        return recaActualAdjustmentAmount;
    }

    /**
     * Sets the value of the recaActualAdjustmentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRecaActualAdjustmentAmount(BigDecimal value) {
        this.recaActualAdjustmentAmount = value;
    }

    /**
     * Gets the value of the recaRemainingBalanceAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRecaRemainingBalanceAmount() {
        return recaRemainingBalanceAmount;
    }

    /**
     * Sets the value of the recaRemainingBalanceAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRecaRemainingBalanceAmount(BigDecimal value) {
        this.recaRemainingBalanceAmount = value;
    }

    /**
     * Gets the value of the retiredPayActualAdjustmentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRetiredPayActualAdjustmentAmount() {
        return retiredPayActualAdjustmentAmount;
    }

    /**
     * Sets the value of the retiredPayActualAdjustmentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRetiredPayActualAdjustmentAmount(BigDecimal value) {
        this.retiredPayActualAdjustmentAmount = value;
    }

    /**
     * Gets the value of the separationPayActualAdjustmentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSeparationPayActualAdjustmentAmount() {
        return separationPayActualAdjustmentAmount;
    }

    /**
     * Sets the value of the separationPayActualAdjustmentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSeparationPayActualAdjustmentAmount(BigDecimal value) {
        this.separationPayActualAdjustmentAmount = value;
    }

    /**
     * Gets the value of the separationPayRemainingBalanceAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSeparationPayRemainingBalanceAmount() {
        return separationPayRemainingBalanceAmount;
    }

    /**
     * Sets the value of the separationPayRemainingBalanceAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSeparationPayRemainingBalanceAmount(BigDecimal value) {
        this.separationPayRemainingBalanceAmount = value;
    }

    /**
     * Gets the value of the serverancePayActualAdjustmentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getServerancePayActualAdjustmentAmount() {
        return serverancePayActualAdjustmentAmount;
    }

    /**
     * Sets the value of the serverancePayActualAdjustmentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setServerancePayActualAdjustmentAmount(BigDecimal value) {
        this.serverancePayActualAdjustmentAmount = value;
    }

    /**
     * Gets the value of the specialMonthlyCompensationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialMonthlyCompensationType() {
        return specialMonthlyCompensationType;
    }

    /**
     * Sets the value of the specialMonthlyCompensationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialMonthlyCompensationType(String value) {
        this.specialMonthlyCompensationType = value;
    }

    /**
     * Gets the value of the specialMonthlyCompensationTypeDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialMonthlyCompensationTypeDesc() {
        return specialMonthlyCompensationTypeDesc;
    }

    /**
     * Sets the value of the specialMonthlyCompensationTypeDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialMonthlyCompensationTypeDesc(String value) {
        this.specialMonthlyCompensationTypeDesc = value;
    }

    /**
     * Gets the value of the specialMonthlyEntitlementText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialMonthlyEntitlementText() {
        return specialMonthlyEntitlementText;
    }

    /**
     * Sets the value of the specialMonthlyEntitlementText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialMonthlyEntitlementText(String value) {
        this.specialMonthlyEntitlementText = value;
    }

    /**
     * Gets the value of the specialMonthlyPensionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialMonthlyPensionType() {
        return specialMonthlyPensionType;
    }

    /**
     * Sets the value of the specialMonthlyPensionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialMonthlyPensionType(String value) {
        this.specialMonthlyPensionType = value;
    }

    /**
     * Gets the value of the specialMonthlyPensionTypeDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialMonthlyPensionTypeDesc() {
        return specialMonthlyPensionTypeDesc;
    }

    /**
     * Sets the value of the specialMonthlyPensionTypeDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialMonthlyPensionTypeDesc(String value) {
        this.specialMonthlyPensionTypeDesc = value;
    }

    /**
     * Gets the value of the spouseIsPartOfAward property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpouseIsPartOfAward() {
        return spouseIsPartOfAward;
    }

    /**
     * Sets the value of the spouseIsPartOfAward property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpouseIsPartOfAward(String value) {
        this.spouseIsPartOfAward = value;
    }

    /**
     * Gets the value of the tortActualAdjustmentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTortActualAdjustmentAmount() {
        return tortActualAdjustmentAmount;
    }

    /**
     * Sets the value of the tortActualAdjustmentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTortActualAdjustmentAmount(BigDecimal value) {
        this.tortActualAdjustmentAmount = value;
    }

    /**
     * Gets the value of the tortRemainingBalanceAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTortRemainingBalanceAmount() {
        return tortRemainingBalanceAmount;
    }

    /**
     * Sets the value of the tortRemainingBalanceAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTortRemainingBalanceAmount(BigDecimal value) {
        this.tortRemainingBalanceAmount = value;
    }

    /**
     * Gets the value of the totalWithholdingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalWithholdingAmount() {
        return totalWithholdingAmount;
    }

    /**
     * Sets the value of the totalWithholdingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalWithholdingAmount(BigDecimal value) {
        this.totalWithholdingAmount = value;
    }

    /**
     * Gets the value of the veteranID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVeteranID() {
        return veteranID;
    }

    /**
     * Sets the value of the veteranID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVeteranID(Long value) {
        this.veteranID = value;
    }

    /**
     * Gets the value of the withholdingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWithholdingAmount() {
        return withholdingAmount;
    }

    /**
     * Sets the value of the withholdingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWithholdingAmount(BigDecimal value) {
        this.withholdingAmount = value;
    }

    /**
     * Gets the value of the workersCompensationActualAdjustmentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWorkersCompensationActualAdjustmentAmount() {
        return workersCompensationActualAdjustmentAmount;
    }

    /**
     * Sets the value of the workersCompensationActualAdjustmentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWorkersCompensationActualAdjustmentAmount(BigDecimal value) {
        this.workersCompensationActualAdjustmentAmount = value;
    }

    /**
     * Gets the value of the workersCompensationRemainingBalanceAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWorkersCompensationRemainingBalanceAmount() {
        return workersCompensationRemainingBalanceAmount;
    }

    /**
     * Sets the value of the workersCompensationRemainingBalanceAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWorkersCompensationRemainingBalanceAmount(BigDecimal value) {
        this.workersCompensationRemainingBalanceAmount = value;
    }

}
