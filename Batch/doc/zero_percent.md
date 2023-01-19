# The 0% rejection fix algorithm

## A typical response from the rating comparison web service

```
    anyRatingsWithinRangeFlag                : String >N<
    differencesFound                         : String >N<
    multipleRatingsFlag                      : String >N<
    prfilDt                                  : XMLGregorianCalendarImpl >2012-03-01T14:02:23-06:00<
    promulgationDt                           : XMLGregorianCalendarImpl >2010-08-15T14:15:09-05:00<
    ptcpntVetID                              : String >9100777117<
    rejectReason                             : ><
    ratingDetails                            : String >
        effective date                           : XMLGregorianCalendarImpl >2011-01-01T00:00:00-06:00<
        service connected percent                : Integer >30<
        ========================================<
```

## The proposed steps
1. Call rating comparison to get the changed data.
1. Check for multiple promulgated ratings within one year by checking
    the multipleRatingsFlag.  If yes, reject
1. Find the earliest rating less than or equal to 395 days before the claim date.
This is the FCDR.
1. Call the new RatingInformation web service to get more information.
1. Look for smc, service connected, IU disabilities.
1. Linda says to get ratingIssues.rbaIssueId to identify the any
decisions that were changed by this rating ("at issue")
1. Once we have the ratingIssues identified, search for smcDecisions, etc.
with a matching rbaIssueId
1. Do something with percents and converted date (convBeginDt)
I can't find any percentages in the xml response from the new
web service.  Ah, ratingAwardDetails has a scPercent, which is
what we're looking for, I think.  It looks like callId links
the ratingsIssues and the smc stuff and the rating award stuff.
Maybe, anyway.
