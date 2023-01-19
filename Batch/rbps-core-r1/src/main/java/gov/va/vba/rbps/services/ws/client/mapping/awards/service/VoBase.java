
package gov.va.vba.rbps.services.ws.client.mapping.awards.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for voBase complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="voBase">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "voBase")
@XmlSeeAlso({
    RecipientEventVO.class,
    AwardClaimantVO.class,
    EftAddressVO.class,
    ParticipantAddressVO.class,
    AwardClaimRelationshipVO.class,
    DevelopmentActionGroupVO.class,
    AwardLineVO.class,
    AwardClaimRelationshipDetailVO.class,
    AwardHistoryVO.class,
    BaseEligibilityDecisionVO.class,
    AwardLineRecipientVO.class,
    AwardRecipientVO.class,
    AuthorizationEventVO.class,
    MessageVO.class,
    AllotmentDecisionVO.class,
    MilitaryServicePeriodVO.class,
    AwardProfileVO.class,
    AwardAddressDisplayVO.class,
    AvailableClaimVO.class,
    GaoWorksheetVO.class,
    AwardReasonVO.class,
    DataVO.class,
    AwardEventVO.class,
    AwardVO.class
})
public abstract class VoBase {


}
