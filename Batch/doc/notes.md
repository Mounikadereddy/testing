# Notes for RBPS

## General

only go by the promulgation date because they don't store the notification letter date.

### Stuff to hand off

1. We’re creating a branch so we can start coding for the first 2.0 release.
This will give Karma and Raja more background on some of the bookkeeping
tasks in Dimensions, including how to make a baseline.
2. I have had Karma make some particular code changes and made him simulate
the release process for those changes so that he sees what’s involved in
making a release in preparation for getting something deployed.
3. We are moving to Win 7 so we are reviewing all of our tool setup.
4. I will be reviewing a set of CQ items with Charlie, Karma, and Raja.
I’d prefer to include Pam/Sweta, but they are on other tasks right now
because of funding.  This will let us cover the issues we’ve been dealing
with in the past few months to make sure they are aware of those issues
and will be aware of “how we got here”.
5. I want Raja to review all the CQ items that involve rules changes so
that he can refresh himself on the rules and be aware of what is on
Justin and Danielle’s mind.
6. We will be trying to meet with Linda to go over the 0% rating issue.
So that we’ll be able to code it.
7. Karma and I have been reviewing the code base to take note of changes
we’d like to make to improve the maintainability of the code base, so
he’s fairly caught up on the java and rules code base.   He’s aware of
what I expect in terms of code quality.
8. Karma has been involved in our push to increase our test coverage
and how we can go about improving that.  He knows that we hope to make use of
the new SHARE web services to create a veteran so that we can write integration
and functional tests in the future.  But that will involve Cory answering questions
so that we can figure out how to create a veteran, dependents, a claim, CEST it, etc.
This testing we hope to automate can hopefully make RBPS more reliable.
9. We had been doing some review of the 150/155 requirements so Karma is as familiar with that as I am.
Charlie seems to be doing a good job with that and is very detail oriented, so I think that will work out.
1. We’ve been reviewing the Phase 1 System Design Document to compare what actually was implemented
to the design, so Karma is familiar with that.


### To run the remote connection thingy  
mstsc

### To run our unit tests

`jruby -S buildr clean test junit:report emma:html 2>&1 | tee out`

### How to compile just the client project

`jruby -S buildr clean rbps:client:compile 2>&1 | tee out`

### To run the build continuously

`jruby -S buildr cc`

This should be run in a separate console window.  It will rerun the portions of the build
that are affected when you save java files.  This is only a best guess, but it's a good
start.

### How to run the ant build

1. `cd rbps-build-and-deploy`
1. `ant -f rbps-build.xml && done -t build`

### Junit notes

* PoaPopulatorTest - should be converted to use a response.xml
* GeneratePdfTest  - should look at testing the generated html to make sure the stuff is there that
should be there.
* EP130ClaimPostProcessorTest - need to mock out the claims processor helper
* RbpsValidatorTest - need to add more tests, was gutted.

### Interesting facts about omnibussing awards

* SCHATTB is omnibussed except if it starts on the 18th birthday
* SCHATTT is omnibussed, but other termination dates aren't.

### Stuff to tell Ashok/Nain for the next release

* Added properties

* Modified property

* Deleted property

* Now calling modified WS, must be installed in webtest


### good procids for devl
* 8744
* 1141
* 1241
* 2222
* 2341 - tests flexible SOJ
* 1041 errors, but still useful.

### Environment variables
Var             | Value
----------------|-------
ANT_HOME        | C:\Documents and Settings\vafsccorbit\My Documents\codeDownloads\ant-1.6.5
GROOVY_HOME     | H:\Groovy-1.8.0
JAVA_HOME       | c:\dev\jdk1.6.0_23
JRUBY_HOME      | c:\dev\jruby-1.6.4
Prompt          | $P $_ $D $T $G $S
RUBYLIB         | c:\dev\ruby\lib
JRUBY_OPTS      | --1.9


### Things to change in the code base

* Get rid of the damn ClaimValidatorFactory
* FindDependencyDecisionByAwardPopulator needs to be split in two
* FindDependentOnAwardWSHandler doesn't seem to be used.
* DependentOnAward needs splitting
* Fix the filtering in the pre processor to accumulate errors.
* get the corporate id earlier - in th pre processor - cq 233
* get rid of the validation factory
* get rid of any unused interface
* Get the rest of the xom utils java code into java code and out of rules strings.
* ~~in the client project, fix the packages so they don't confict with packages in other projects.~~
* The FindMailingAddressWSHandler doesn't seem to be used.
* Move the "evaluateDepentOnAwardStatus" call up higher and move the call to the child
type filter back into the RbpsFilter.
* We have a lot of duplicate code that needs to be reviewed that deals with
dependency decisions, denied/removed/onAward, etc.
* UpdateBenefitClaimDependentsWSHandler - don't send a request if there are
no dependents to add.

### cut and paste in gvim on windows

If you happen to be using gvim for Windows and want to copy
or cut into the Windows clipboard, press Ctrl+Insert
to copy, or Shift+Delete to cut. To paste from the Windows
clipboard, press Shift+Insert.

### JRuby gems to install

jruby -S gem install [gem names]

* active_support
* awesome_print
* buildr
* colorize
* flying_saucer
* hirb
* wirble
* redcarpet
* rspec
* savon
* slop

### Ruby gems to install

gem intall [gem names]

* active_support
* awesome_print
* buildr
* colorize
* hirb
* wirble
* redcarpet
* rspec
* ruby_gntp
* savon
* slop
* win32console

### bugs proposed to fix in next release
* 211 - child under 2 with no ssn
* 232 - get num dependents in what we decided based on
    all on award.
* 233 - get corporate id earlier
* 235 - handle already on award/denied/CQ 266/674

### Contacts

 Name              | Type       | Phone
:------------------|:-----------|----------------
Ashok Sahoo        | Work       | 708-681-6970
Danielle Gervallis | Work       | 202-461-9077
Howard Moy         | Office     | 708-681-6995
Justin Holloway    | Work       | 202-461-1463
Kris               | Blackberry | 512-820-7733
Kris               | Cell       | 512-663-8915
Linda Ciston       | Work       | 813-367-3382
Pam                | Cell       | 512-868-7002
SIO                | VANTS      | 18499
Tom Corbin         | Work       | 512-460-5637



### Definitions
 Term   | Definition
--------|------
A&A     | __A__id and __A__ttendance Benefits
ALF     | __A__ssisted __L__iving __F__acility
BDN     | __B__enefits __D__elivery __N__etwork - legacy system vetsnet is replacing
CEST    | __C__laims __EST__ablishment
DEA     | __D__ependents __E__ducation Assistance
EP      | __E__nd __P__roduct (code)
EVR     | __E__ligibility __V__erification __R__eport
FNOD    | __F__irst __N__otice __O__f __D__eath
FTS     | __F__older __T__ransfer __S__ystem - when folders need to be moved from one RO to another.  Sometimes that happens when one RO is very busy and another isn't.
GAO     | __G__enerate __A__ward __O__verride
LGY     | __L__oan __G__uarant__ee__
MAPR    | __M__aximum __A__nnual __P__ension __R__ate
MER     | __M__edical __E__xpense __R__eport
NH      | __N__ursing __H__ome
OFO     | __O__ffice of __F__ield __O__perations
PMAS    | __P__rogram __M__anagement __A__ccountability __S__ystem
RFAC    | __R__equest __f__or __A__rchitecture __C__hange
RO      | __R__egional __O__ffice
shrinqf | __SH__a__R__e __INQ__uiry f
SOJ     | __S__tation __O__f __J__urisdiction
SMIB    | __S__upplementary __M__edical __I__nsurance __B__enefit
VBBGS   | __VB__A __B__enefits __G__ateway __S__ervice
VonAPP  | __V__eteran's __On__line __App__lication for Benefits
WEAMS   | __W__eb __E__nabled __A__ccreditation __M__anagement __S__ystem


### People
Name                   | Location             |   Job                                      | Organization
-----------------------|----------------------|--------------------------------------------|---------------
Ashok Sahoo            | Hines, IL            | Installs                                   |
Barbara Jones-Odell    |                      | Ashok, Nain's boss.                        |
Cory Easley            | AITC                 | Share GUI/Web Services                     | Target Programming Team 4
Danielle Gervallis     | D.C.                 |                                            |
Jeff Ivy               | St. Pete             |                                            |
John Dell              |                      |                                            | VETSNET Mapd
John Maser             | St. Pete             | Helps Linda                                | VETSNET Oracle Team
Justin Holloway        | D.C.                 | OBPI RBPS Project Lead                     |
Kathy Hudson           |                      |                                            | ORM
Lance Lundquist        |                      |                                            | VDC
Linda Ciston           | St. Pete             |                                            | VETSNET Oracle Team
Linda Heusack          |                      | Product Development IT Specialist          | VETSNET Product Support Team 2
Rob Benson             | AITC                 | Share GUI/Web Services                     | Target Programming Team 4
Sara Wear              |                      |                                            | OSP

### Regional Offices

|Station Id |   Name             | Station Id | Name         |
|-----------|--------------------|------------|--------------|
|301        | Boston, MA         | 331        | St. Louis, MO |
|304        | Providence, RI     | 333        | Des Moines, IA |
|306        | New York, NY       | 334        | Lincoln, NE |
|307        | Buffalo, NY        | 335        | St. Paul, MN |
|308        | Hartford           | 339        | Lakewood/Denver, CO |
|309        | Newark, NJ         | 340        | Albuquerque, NM |
|311        | Pittsburgh, PA     | 341        | Salt Lake City, UT |
|313        | Baltimore, MD      | 343        | Oakland/San Francisco, CA |
|314        | Roanoke, VA        | 344        | Los Angeles, CA |
|315        | Huntington, WV     | 345        | Phoenix, AZ |
|316        | Atlanta, GA        | 346        | Seattle, WA |
|317        | St. Petersburg, FL | 347        | Boise, ID |
|318        | Winston-Salem, NC  | 348        | Portland, OR |
|319        | Columbia, SE       | 349        | Waco, TX |
|320        | Nashville, TN      | 350        | Little Rock, AR |
|321        | New Orleans, LA    | 351        | Muskogee, OK |
|322        | Montgomery, Al     | 354        | Reno, NV |
|323        | Jackson, MS        | 355        | Hoto Rey/San Juan, PR |
|325        | Cleveland, OH      | 358        | Manila, Philippines |
|326        | Indianapolis, IN   | 362        | Houston, TX |
|327        | Louisville, KY     | 372        | Washington, DC |
|328        | Chicago, IL        | 373        | Manchester, NH |
|329        | Detroit, MI        | 377        | San Diego, CA |
|330        | Milwaukee, WI      |            |               |
|REGIONAL OFFICE | AND | INSURANCE | CENTER                         |
|310        | Philadelphia, PA   |            |                |
|FOREIGN OFFICE | OUTPATIENT | CLINIC & RO                         |
|358        | Manila, Philippines | 358        | Manila, Philippines |
|           |                     | 463        | Anchorage, AK      |
|REGIONAL OFFICE | AND | MEDICAL | CENTERS                            |
|402        | Togus, ME           | 442        | Cheyenne, WY |
|405        | White River Junction, VT | 452   | Wichita, KS |
|436        | Fort Harrison, MT   | 459        | Honolulu, HI |
|437        | Fargo, ND           | 460        | Wilmington, DE |
|438        | Sioux Falls, SD     |            |                 |



### Standalone 674 from share

is broken because the following is missing:
SSN
birthday
spouse DOB



### Setting up eclipse for a new RBPS version/project
1. Open the “Serena” perspective.
2. Open “Eclipse Project Containers”
3. Find “RBPS:RBPS-R1[rbps-r1]"
4. Open it
5. RMB click on “RBPS:RBPS-R1.3”
6. Choose “add to workspace as”
7. Add as project
8. In Package Explorer, open the new project’s properties
9. Convert to a faceted project (in the project facets part)
1. It should choose for it to be a java project.
1. In the java build path, add the source folders as in the screenshot
1. In the java build path, add all the libraries from the ear sub project
1. I added log4j-1.2.15.jar
1. I added axis.jar and jaxrpc.jar from the rpc-client project to the jar files


### ITA process
1. goes to obipi
1. assigns priority
1. goes to tina edwards (BA)
1. she does BA portion
1. goes to scrumm meeting
1. they do development and testing apportion points assignment

shortest turnaround is 6 week sprint for service


## Release 1.2

### Interesting sql for the signer's name
> SELECT *  
> FROM stn_prfil_detail d  
> WHERE  
>        d.letter_signtr_title_txt LIKE '%Service Center%'  
>     OR d.letter_signtr_title_txt LIKE '%SERVICE CENTER%'  
>     OR d.letter_signtr_title_txt LIKE '%VSCM%';  


### Outstanding Questions

1. VDC putting COMP instead of CPL for pgm_type_cd  

    Sent to Lance Thursday@10am  
    Again Monday@3:30pm  
    It's hard coded, Justin's substitute will deal with it.

1. Why isn't Fargo showing up in list of Regional Offices

    The web service has a bug, they are fixing it.

1. findSignaturesByStationNumber appears to have been in webtest
    on Friday, but not this week.

    Sent to Ashok/Jerry Palma Mon@Noon  
    They seem to be working on it.  
    Fixed.

1. Has the RTF to PDF license been installed?

    Yes.


## Phase 2

The project team needs updating!

* EP 150 - Medical Expenses (MER)
* EP 155 - EVR - Eligibility Verfication Report Processing (EVR)
* The St. Paul VARO serves nearly one-half million veterans in geographic
Minnesota with over $500 million in VA benefits annually from compensation,
pension, and vocational rehabilitation programs.
* One RO will get 30,000 EVR between Jan and Feb each year.

### In scope

* Retrieving EP150 and EP155 claim types from the corporate database.
* Enhancing automated processing for EP150 and EP155 by applying a set of rules.
* Handling exceptions
* Making award determinations based on the following types of information.
    + Service-related Disability(ies) Information
    + Non-Service Connected Pension Information
    + Veterans Information
    + Active Duty Service Information
    + Marital and Dependency Information
    + Dependent Child(ren) Information
    + Other VA Benefits Information
    + Military Retired/Severance Pay Information
    + Wage and Income Information
    + Net worth Information
    + Medical, legal and other expenses Information
    + Nursing Home/Medicaid Information
    + Aid and Attendance/Housebound Information
    + Award Information
    + Apportionment Information
* Interfacing with Social Security Administration using current VBA interface to retrieve and verify SSI, SSDI, RSDI income for EVR. 
* Intefacing with VBA Corporate Database Claim and Award tables
* Interfacing with VETSNET suite of applications (SHARE, eBenefits, VonApp, Virtual VA, MAP-D, EVR)
* Recording all claim outcomes and provide decision information to the Awards service.
* Generating correspondence for all actions requiring development or actions resulting in award or denial.
* Interfacing with Virtual VA document repository.

### Out of scope

* Modifying the existing VBA source systems

### Constraints

* VonApp cannot provide Medical Expense Types in standard electronic format

### Project Assumptions

* VBA will be responsible for Certification & Accreditation of the system.
* All information from eBenefits electronic submissions and SHARE will match paper claim data.
* VBA will review all business rules for adherence to applicable laws and guidance.
* VBA will conduct traceability on each business rule to applicable laws and guidance.
* VBA will provide the necessary technical specifications and resources.
* VBA will set up the development, testing, and production environments.
* VBA will have the necessary interfaces properly be in place (SHARE, VonApp, Awards, MAPD, SSA, EVR).

### System Assumptions

UC-10 - probably shouldn't metion Work List

EP290 is fugitive felon match, social security prison match and BOP prison match


#### Reused:

* User info – not sure if there will be changes needed or not.
* Award state
* Find military pay
* Update benefit claim label
* Find benefit claim (to translate the vonapp claim id to a corporate claim id)
* VnpProcUpdate to set the claim status
* ClaimantWebService
    + findPoa?
    + findFiduciary?
    + findFlashes (to see if there is an attorney fee agreement)
    + findDependents
* Mapd – to create a note
* StandardDataWebService
    + Find regional offices?
    + Find signatures?
* 




### Questions
1. When would the claimant not be the veteran?
1. Are we validating the SSN format?
1. Why would we get an EP 154 to reject? Won't that be filtered by User Info? (EP 150 UC-01)
1. What is EP600 for?
1. Will we need to filter for EP290/600/155 or will User Info do it?
1. Will User Info include the form 21-8416 info?
1. Does User Info validate that the first/last name on the form match
what's in corporate for the file number or will RBPS.
1. What's an EVR diary?
1. (150 UC-02) What if they chose "member of household" in eligible
dropdown?  We've not handled that before.
1. (150 UC-03) What is the $90 medicaid rate and why is it only
for some periods.
1. (150 UC-03) What is a period of entitlement to compensation during the MER period?
1. (150 UC-03) What is SMIB? SSA?
1. (150 UC-03) Won't the mileage reimbursement change over time?  Is this something
we can get from a web service?
1. (150 UC-03) What is ALF, NH, A/A/HB?
    + ALF - Assisted Living Facility
    + NH - Nursing Home
1. (150 UC-03) EVR is apparently a year end thing, what about MER-28?
what is meant by the previous calendar year (step 8).
1. (150 UC-03) I'd like to see the provider field UI, I'm a bit confused
about the general v. specific provider.  MER-29.
1. (150 UC-03) MER-30: what does this mean: "combine unreimbursed and current
year's continuous amounts".  Why would these be combined?  Wouldn't some
continuing expenses have been reimbursed?
1. (150 UC-03) MER-31: How do we do the computation of the total amount
of continuous MER that can be applied for the next calendar year?
1. (150 UC-03) MER-31: What is the purpose field?
1. (150 UC-03) MER-31: What is a max rate claimant?
1. (150 UC-04) MER-34: What does this mean, "Verify the
SSA Interface file to the Corporate Data under the income
screen for the dollar match"?
1. (150 UC-04) MER-35: What is PCLR EP mean?
1. (150 UC-04) MER-35: What letter do we send in this situation?
1. (150 UC-05) MER-39: Will AWARDS do the omnibus calc or will RBPS?
1. (150 UC-05) MER-40: How does the SS COLA calc work?

1. (UC-10) What do we do about benefit type being
"improved pension" or "compensation"
1. (EVR-13) What is "EVR complete"?
1. (UC-12) Why are Medical Expenses related to deductions?
1. (UC-12) What is the entitlement date?
1. (MER-32) What if the Vet has a $10K increase and has already hit
the max amount?
1. (UC-12 TRule MER 10) why only check the spouse and not kids?
1. (UC-12 TRule MER 26) Why is not paying a SMIB a reason for denial?
1. (TRule MER 21 and TRule MER 24) These seem very similar except for the $90 thing
and the Max Rate.  Is that intentional?  If so, what's the intent?
1. (TRule MER 26 and TRule MER 27) These seem very similar.
Is that intentional?  If so, what's the intent?
1. (TRule MER 27 - and in a number of places) What is meant by PCLR EP?
I assume it's "pclear" that I've heard Pam and Sweta talk about, but
what is the effect of that and how does RBPS do that?
1. (TRule MER 29) What is meant by "December line"?
If SS COLA will cause a decrease (why would an SS COLA cause a decrease?)
on the 12/1 rate, do not apply until the January rate.
If the rate is less that what was paid on the previous January
send to exception.
1. (TRule MER 30) What does it mean to find the provider name in the system?
1. What is the EVR web service?  How do we find info on it?
Is it really a separate application?
1. (TRule EVR 01) What does "EVR Reporting Period to current system date"
mean?
1. Does AWARDS need to change for phase 2?




#### Need to figure out how to get this info:
1. If there are other EP 150 claims pending.
1. (UC-10) There are no EP290/EP600/EP155 claims pending.
1. (UC-10) Is the veteran living?
1. (UC-10) Is the veteran incarcerated in the last XX months? or institutionalized
1. (UC-10) Active Live Improved Pension award is current and is not suspended or terminated
1. (UC-10) Have Replace Gross Rate and Add to Gross rate been used on the corresponding current award.
1. (UC-10) is Veteran and/or Dependents receiving SSI, SSDI, RSDI income types?
1. (UC-10) active duty service info: when started, released, separation reaon, etc.
1. (UC-10) Severance Pay info. (May be part of findMilitaryPay WS we already call)
1. (UC-10) Income/Net Worth Info.  Form info?
1. Is there an EVR Diary control
1. (150 UC-03) is this an initial year issue?  Look at dates for medical expenses. Not sure
what that means.
1. (150 UC-03) is the Veteran receiving $90 Medicaid rate, and for what period?
1. (150 UC-03) is the Veteran a max rate claimant?
1. (150 UC-03) is there a period of entitlement to compensation during the MER period.
1. (150 UC-03) is there SMIB paid with SSA, and how much?  This must come from the SSA so we can
verify what's on the form.
1. (150 UC-03) what are the valid expense types?  Or a list of all MER types?
1. (150 UC-03) related to ALF/NH, is there a rated or admin decision for A/A?
What is the decision?  What is the name of the facility (provider)?  In the corporate
database?
1. (150 UC-04) How do we find the income data for the veteran?
1. (150 UC-05) MER-40 How do we find out what was paid the previous January?
1. (150 UC-05) MER-38 How do we find out if the Veteran has hardship expenses?
1. (UC-12) "total medical expenses reported year" increased by $10,000 from "last year reported"
1. (UC-12 TRule MER 4) # months SMIB was paid
1. (UC-12 TRule MER 5) whether continuous expenses are to be paid next year?
1. (UC-12 TRule MER 9) is Veteran housebound or requires aid and assistance?
1. (MER 12) if there is a period of compensation entitlement?
1. (MER 13) the medical purpose? (part of UserInfo?)
1. What is the value of the MAPR?  Needed to see if expenses are 5% of MAPR.
1. (TRule MER 30) how do we find the provider name in the system?
1. What is the EVR web service?  How do we find info on it?
Is it really a separate application?
1. What is the SSA web service?  How do we find info on it?


