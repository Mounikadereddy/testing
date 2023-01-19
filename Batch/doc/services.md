## The web services used by RBPS

The first entry is the url used in development.  Subsequent bullets are the individual parts
of the web service that are used.  For example, the new signature web service was added to
an already existing service end point.

### Awards

* http://bepdev.vba.va.gov/AmendAwardDependencyEJB/AmendAwardDependencyEJBService

* ProcessAwardDependent

### Military Pay

* http://bepdev.vba.va.gov/ReadMilitaryPayEJB/ReadMilitaryPayService

* FindMilitaryPay

### Award State

* http://bepdev.vba.va.gov/AwardStateEJB/AwardStateEJBService

* FindAwardState

### Dependency decisions for the Veteran

* http://bepdev.vba.va.gov/DependencyDecisionEJB/DependencyDecisionEJBService

* FindDependencyDecisionByAward

### User Info - finds the next claim to process

* http://bepdev.vba.va.gov/UserInformationWebServiceBean/UserInformationService

* FindByDataSupplied

### Update benefit claim label

* http://bepdev.vba.va.gov/BenefitClaimServiceBean/BenefitClaimWebService

* UpdateBenefitClaim

* FindBenefitClaim ( get a list of claims that match a file number, to convert to corporate claim id )

### Claim status (Manual, Started, Complete)

* http://bepdev.vba.va.gov/VnpProcWebServiceBean/VnpProcService

* VnpProcUpdate

### POA, Dependents, Flashes

* http://bepdev.vba.va.gov/ClaimantServiceBean/ClaimantWebService

* FindPOA

* FindDependents

* FindFlashes

* FindFiduciary

### Rating Comparison

* http://bepdev.vba.va.gov/RatingComparisonEJB/RatingComparisonEJBService

* CompareByDateRange

### Regional offices list

* http://bepdev.vba.va.gov/ShareStandardDataServiceBean/ShareStandardDataWebService

* FindRegionalOffices

### Signature

* http://bepdev.vba.va.gov/StandardDataWebServiceBean/StandardDataWebService

* FindSignaturesByStationNumber

* FindRegionalOffices

### Notes

* http://bepdev.vba.va.gov/DevelopmentNotesService/DevelopmentNotesService

* CreateNote

