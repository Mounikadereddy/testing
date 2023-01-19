package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.rulesengine.child.ChildBaseRuleSet;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildMessages;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.Rule;
import gov.va.vba.rbps.rulesengine.engine.RuleEngineException;


public class MinorToSchoolChildRuleSet extends ChildBaseRuleSet {

    private Logger logger = Logger.getLogger(this.getClass());

    public MinorToSchoolChildRuleSet(Child child, ChildResponse childResponse, ChildDecisionVariables childDecisionVariables) {
        super(child, childResponse, childDecisionVariables);
    }

    @Override
    public void run() throws RuleEngineException {
        super.run();
    }

    /**
     * Rule: CP0145-01
     *
     * if
     *  all of the following conditions are true :
     * 		- the current term of 'the Child' is present
     * 		- the course start date of ( the current term of 'the Child' ) is present
     * 		- the course start date of ( the current term of 'the Child'  ) is before or the same as ('childs 18th birth day' ) plus 5 months ,
     * then
     * 	set the event date of 'the minorSchoolChildAward' to 'childs 18th birth day' ;
     */
    @Rule
    public void eventDateDetermination_CP0145_01() {
        if (
            RbpsXomUtil.isPresent(child.getCurrentTerm())
            && RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate())
            && child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, childDecisionVariables.getChild18BirthDay())) <= 0
        ) {
            childResponse.getMinorSchoolChildAward().setEventDate(childDecisionVariables.getChild18BirthDay());
            logger.debug("eventDateDetermination_CP0145_01: Event date of the award set to the 18th birthday of the child: " + childDecisionVariables.getChild18BirthDay());
        }
    }


    /**
     * Rule: CP0145-02
     *
     * if
     *     all of the following conditions are true :
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - the course start date of ( the current term of 'the Child' ) is after ( 'childs 18th birth day' ) plus 5 months ,
     * then
     *     set the event date of 'the minorSchoolChildAward' to the course start date of ( the current term of 'the Child' ) ;
     */
    @Rule
    public void eventDateDetermination_CP0145_02() {
        if (
                RbpsXomUtil.isPresent(child.getCurrentTerm())
                && RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate())
                && child.getCurrentTerm().getCourseStudentStartDate().after(RbpsXomUtil.addMonthsToDate(5, childDecisionVariables.getChild18BirthDay()))
        ) {
            childResponse.getMinorSchoolChildAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("eventDateDetermination_CP0145_02: Event date of the award set to the start date of the current term of the child: " + child.getCurrentTerm().getCourseStudentStartDate());
        }
    }






}
