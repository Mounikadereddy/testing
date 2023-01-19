package gov.va.vba.rbps.builder;

import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.*;

import java.util.Date;

public class XomBuilder {
    public static Veteran createVeteran(String ssn, String firstName, String lastName) {
        Veteran veteran = new Veteran();

        veteran.setAlive(true);
        veteran.setSsn(ssn);
        veteran.setFirstName(firstName);
        veteran.setLastName(lastName);
        veteran.setMiddleName("Hyrule");

        Address address = createAddress("101 Hyrule Ln", "Largo", "USA", "FL", "33756");
        veteran.setMailingAddress(address);
        veteran.setPermanentAddress(address);
        veteran.setResidenceAddress(address);

        veteran.setGender(GenderType.MALE);
        veteran.setSsnVerification(SSNVerificationType.VERIFIED_BY_VBA);

        veteran.setReceivingMilitaryRetirePay(false);
        veteran.setHasPOA(false);
        veteran.setServiceConnectedDisabilityRating(40);
        veteran.setFileNumber(ssn);
        veteran.setRatingDate(SimpleDateUtils.parseInputDate("01/01/2017"));
        veteran.setRatingEffectiveDate(SimpleDateUtils.parseInputDate("02/01/2017"));
        veteran.setSalutation(SalutationType.MR);
        veteran.setAwardStatus(createAwardStatus());
        veteran.setClaim(createClaim());
        veteran.setMaritalStatus(MaritalStatusType.MARRIED);
        veteran.setDayTimePhone(createPhone());
        veteran.setHasMilitaryPay(false);
        veteran.setCurrentMonthlyAmount(0);
        veteran.setAllowableDate(SimpleDateUtils.parseInputDate("01/01/2018"));
        veteran.setPriorSchoolTermRejected(false);



        return veteran;
    }

    public static AwardStatus createAwardStatus() {
        AwardStatus awardStatus = new AwardStatus();

        awardStatus.setChangesMade(false);
        awardStatus.setIsSuspended(false);
        awardStatus.setGAOUsed(false);
        awardStatus.setHasAttorneyFeeAgreement(false);
        awardStatus.setRunningAward(true);
        awardStatus.setProposed(false);

        return awardStatus;
    }


    public static Claim createClaim() {
        Claim claim = new Claim();

        claim.setHasAttachments(false);
        claim.setNew(false);
        claim.setClaimId(999999999);
        claim.setReceivedDate(SimpleDateUtils.parseInputDate("01/01/2018"));
        claim.setEndProductCode(EndProductType.COMPENSATION_EP130);

        return claim;
    }

    public static Phone createPhone() {
        Phone phone = new Phone();

        phone.setAreaCode(999);
        phone.setCountryCode(1);
        phone.setPhoneNumber(999999);
        phone.setPhoneType(PhoneType.DAY);

        return phone;
    }


    public static Spouse createSpouse(String fileNumber, boolean isOnCurrentAward, boolean isEverOnAward, Award award) {
        Spouse spouse = new Spouse();

        spouse.setFileNumber(fileNumber);
        spouse.setOnCurrentAward(isOnCurrentAward);
        spouse.setIsEverOnAward(isEverOnAward);
        spouse.setAward(award);

        return spouse;
    }



    public static Marriage createMarriage(Date startDate, MarriageTerminationType termType, Spouse spouse) {
        Marriage marriage = new Marriage();

        marriage.setStartDate(startDate);
        marriage.setTerminationType(termType);
        marriage.setMarriedTo(spouse);

        return marriage;
    }


    public static Child createChild(ChildType childType, Veteran veteran) {
        Child child = new Child();

        child.setChildType(childType);
        child.setLivingWith(veteran);

        return child;
    }

    public static Award createAward(DependentDecisionType dependentDecisionType, DependentStatusType dependentStatusType, Date eventDate) {
        Award award = new Award();

        award.setDependencyDecisionType(dependentDecisionType);
        award.setDependencyStatusType(dependentStatusType);
        award.setEventDate(eventDate);

        return award;
    }


    public static Address createAddress(String lineOne, String city, String country, String state, String zip) {
        Address address = new Address();

        address.setLine1(lineOne);
        address.setCity(city);
        address.setCountry(country);
        address.setState(state);
        address.setZipPrefix(zip);

        return address;
    }
}
