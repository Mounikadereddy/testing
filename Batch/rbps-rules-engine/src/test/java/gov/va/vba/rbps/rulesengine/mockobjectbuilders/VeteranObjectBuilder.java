package gov.va.vba.rbps.rulesengine.mockobjectbuilders;

import gov.va.vba.rbps.coreframework.xom.*;

import java.util.ArrayList;
import java.util.List;

public class VeteranObjectBuilder {

    public static Veteran createVeteran() {
        Veteran veteran = new Veteran();

        setPersonObject(veteran);

        veteran.setReceivingMilitaryRetirePay(false);
        veteran.setHasPOA(false);
        veteran.setServiceConnectedDisabilityRating(40);
        veteran.setFileNumber("990915708");
        veteran.setRatingDate(DateUtils.convertDate("01/01/2017"));
        veteran.setRatingEffectiveDate(DateUtils.convertDate("02/01/2017"));
        veteran.setSalutation(SalutationType.MR);
        veteran.setAwardStatus(AwardStatusObjectBuilder.createAwardStatus());
        veteran.setClaim(ClaimObjectBuilder.createClaim());
        veteran.setMaritalStatus(MaritalStatusType.MARRIED);
        veteran.setDayTimePhone(PhoneObjectBuilder.createPhone());
        veteran.setHasMilitaryPay(false);
        veteran.setCurrentMonthlyAmount(0);
        veteran.setTotalNumberOfDependents(2);
        veteran.setAllowableDate(DateUtils.convertDate("01/01/2018"));
        veteran.setPriorSchoolTermRejected(false);



        return veteran;
    }

    public static Marriage createPreviousSpouse() {
        Award award =  AwardObjectBuilder.createAward(
                DependentDecisionType.MARRIAGE_TERMINATED,
                DependentStatusType.SPOUSE,
                DateUtils.convertDate("01/01/2013"));

        award.setEndDate(DateUtils.convertDate("01/01/2014"));

        Spouse previousSpouse = SpouseObjectBuilder.createSpouse(
                "990915709",
                false,
                true,
                 award);

        Marriage previousMarriage = MarriageObjectBuilder.
            createMarriage(DateUtils.convertDate("01/01/2012"),
                MarriageTerminationType.DIVORCE,
                previousSpouse);

        previousMarriage.setEndDate(DateUtils.convertDate("01/01/2014"));


        return previousMarriage;
    }

    public static Marriage createCurrentSpouse() {
        Award award =  AwardObjectBuilder.createAward(
                DependentDecisionType.MARRIAGE,
                DependentStatusType.SPOUSE,
                DateUtils.convertDate("01/01/2016"));

        Spouse currentSpouse = SpouseObjectBuilder.createSpouse(
                "990915710",
                true,
                true,
                award);

        return MarriageObjectBuilder.
                createMarriage(DateUtils.convertDate("01/01/2016"),
                        MarriageTerminationType.DIVORCE,
                        currentSpouse);
    }

    public static List<Child> createChildren(Veteran veteran) {
        List<Child> children = new ArrayList<>();

        children.add(createChild(ChildType.BIOLOGICAL_CHILD, veteran));
        children.add(createChild(ChildType.STEPCHILD, veteran));

        return children;
    }

    private static Child createChild(ChildType childType, Veteran veteran) {
        return ChildObjectBuilder.createChild(childType, veteran);
    }

    private static void setPersonObject(Veteran veteran) {
        veteran.setAlive(true);
        veteran.setSsn("990915708");
        veteran.setFirstName("Link");
        veteran.setLastName("Nintendo");
        veteran.setMiddleName("Hyrule");

        Address address = AddressObjectBuilder.createAddress("101 Hyrule Ln", "Largo", "USA", "FL", "33756");
        veteran.setMailingAddress(address);
        veteran.setPermanentAddress(address);
        veteran.setResidenceAddress(address);

        veteran.setGender(GenderType.MALE);
        veteran.setSsnVerification(SSNVerificationType.VERIFIED_BY_VBA);
    }
}
