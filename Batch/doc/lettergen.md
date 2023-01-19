# Letter Generation

Letter Generation is a process of transforming the data from awards into data structures that make generating
a letter easy - the data structures match what needs to be printed.

1. Create a map between the dependentId and the _DependencyDecisionResultVO_, which
is the grant/denial entry for each dependent.  Usually these can be related
to an award line summary by the _alReasonSequenceNumber_.

    During this process, we make sure that the denials are assigned
    a sequence number.  If they don't have one, they are assigned
    the _DENIAL_SEQUENCE_NUMBER_.

    Also the _DependencyDecisionResultVO_ objects are sorted
    so that they are ordered by:
    * event date
    * the sequence number
    * first name
    * grants before denials

1. Create a fake AwardLineSummaryVO summary to accumulate denials.

    This allows us to attach denials to this summary which do not belong
    to any other summary.   Sometimes denials don't have an _alReasonSequenceNumber_.
    Since this number is used to tie a grant or denial to an award line reason,
    and hence to a denial, a denial w/o one needs this fake summary
    so that it can be processed later.   It won't get lost this way.
    * This fake summary is assigned the _DENIAL_SEQUENCE_NUMBER_.
    * The effective date of the fake summary is the claim received date.
    * A fake _AwardReasonSeqNbrVO_ is provided (empty).
    * The gross amount is set to -1.

1. If any AwardLineSummaryVO is prior to the _firstChangeDate_, drop them on the floor.
    * A _firstChangeDate_ is not always provided.
    When a _firstChangeDate_ isn't provided, then all AwardLineSummaryVO are
    processed.
    * If a AwardLineSummaryVO is a denial, then it is not checked
    for being prior to the _firstChangeDate_

1. An _AwardSummaryBuilder_ is used to convert each _AwardLineSummaryVO_
to an _AwardSummary_, whish is an RBPS class, as opposed to a class provided
by the response from the amend awards web service.  The _AwardSummaryBuilder_
is provided the _AwardLineSummaryVO_ and the list of _DependencyDecisionResultVO_
that is retrieved from the map created above.

----

1. The _AwardSummaryBuilder_ creates an empty _AwardSummary_ object.

1. The following fields are copied from the _AwardLineSummaryVO_ to the
_AwardSummary_.
    * net amount
    * payment change date (copied from the effective date)

1. A list of reasons is constructed from the _AwardLineSummaryVO_ and
the list of decisions.

1. For each _AwardReasonSeqNbrVO_ we get a list of _DependencyDecisionResultVO_ with
a matching _alReasonSequenceNumber_.  If there is no matching dependent, we create
a fake one.

1. For each _DependencyDecisionResultVO_ in this list, we create an _AwardReason_
that is added to the _AwardSummary_.  The _AwardReason_ and _AwardSummary_ are
classes which match the data that is needed to generate the pdf letter. The following
info is set on the new _AwardReason_:
    * award line reason type
    * award line reason type description
    * reason sequence number
    * dependent name
    * dependent decision type
    * dependent decision type description
    * first name
    * full name
    * isChild
    * approval type ( GRANT/DENIAL )
    * claim id
    * corporate participant id for the dependent
    * a _ReasonTranslator_ is added to the new _AwardReason_

    A _ReasonTranslator_ is used to translate from __RATNHEL__
    to _%s was reported as being 23 years of age or older and not reported as being seriously disabled._
    The _%s_ is substituted with the dependent's name.

1. The one award summary is split into a list of grant summaries and denial summaries, if any.

----

1. Letter Generation uses _Velocity_ a java templating engine
to produce html, which is later translated to a pdf file.  Velocity takes
a map of names -> objects which can be used to fill in values in the
template.  Here is an sample taken and simplified from the Letter Generation
template.

        We are paying you as a Veteran with $letterFields.totalDependents dependent(s).
        Your payment includes an additional amount for your spouse $letterFields.approvalSpouseName.


    You can see that the string _$letterFields_ is used in the template in a number
    of places.  This is the _LetterFields_ object that is passed into Velocity
    via the map that Velocity takes.  The _totalDependents_ and _approvalSpouseName_
    are fields on the java class _LetterFields_.

1. _LetterFields_ is created with the _RbpsRepository_ object and the
list of _AwardSummaries_ produced by the _AwardSummaryBuilder_.

1. An _AwardSummaryFilter_ is used to process the list of _AwardSummary_
objects.

1. The _AwardSummaryFilter_  filters the total list of _AwardReason_
objects from all _AwardSummary_ objects into a list of grant/removals
and denial reasons.  During this process, if a denial contains
a previous grant, then it is changed from a denial to a removal and
added to the list of grant/removals.

1. The _AwardSummaryFilter_ splits the list of _AwardSummary_ objects
into a list of grants/removals, and denials.  Also, each summary
that has a _AwardReason_ with no dependents has that reason removed
from it's list of reasons.

1. Any empty _AwardSummary_ objects are then removed from the
list of all summaries, the list of grant/removals, and the
list of denials.

1. _approval names_ are accumulated.  This is the process
of converting the spouse and children
names to have an initial capital letter
and building up the child names into a string with the
appropriate English for 1 child, 2 children, or 3 or
more children.  This list only contains the
names of children that had grants.

    The names are made unique in case a dependent had two grants.
    The uniqueness is determined by corporate participant id.

1. Velocity is called with the _LetterFields_ object so as
to produce html into a String.

1. The html is used to produce a pdf file.

1. A csv file is generated for transmittal to the VVA.
