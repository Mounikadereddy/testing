package gov.va.vba.rbps.rulesengine.child;

import java.util.HashMap;
import java.util.Map;

import gov.va.vba.rbps.rulesengine.engine.Response;
import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.RuleExceptionMessages;

public class ChildResponse implements Response {
	
	private final RuleExceptionMessages exceptionMessages;
    private final RuleExceptionMessages logMessage;
	  private Award award;
	  private Award minorSchoolChildAward;
	  private Award priorSchoolChild;
    private String priorSchoolTermStatus;

	public ChildResponse() {
		exceptionMessages = new RuleExceptionMessages();
		award = new Award();
		minorSchoolChildAward = new Award();
		priorSchoolChild = new Award();
		logMessage = new RuleExceptionMessages();
		priorSchoolTermStatus = "";
	}

	@Override
	public Map<String, Object> getOutputParameters() {
		
		Map<String, Object> response = new HashMap<>();
		response.put("Exceptions", exceptionMessages);
		response.put("Award", award);
		response.put("MinorSchoolChildAward", minorSchoolChildAward);
		response.put("PriorSchoolChild", priorSchoolChild);
		response.put("LogMessage", logMessage);
		response.put("PriorSchoolTermStatus", priorSchoolTermStatus);

		return response;
	}
	
    /**
     * @return Award return the award
     */
    public Award getAward() {
        return award;
    }

    /**
     * @return Award return the minorSchoolChildAward
     */
    public Award getMinorSchoolChildAward() {
        return minorSchoolChildAward;
    }

    /**
     * @return Award return the priorSchoolChild
     */
    public Award getPriorSchoolChild() {
        return priorSchoolChild;
    }

    /**
     * @return RuleExceptionMessages return the logMessage
     */
    public RuleExceptionMessages getLogMessage() {
        return logMessage;
    }

    /**
     * @return String return the priorSchoolTermStatus
     */
    public String getPriorSchoolTermStatus() {
        return priorSchoolTermStatus;
    }

    public RuleExceptionMessages getExceptionMessages() {
        return exceptionMessages;
    }

    public void setPriorSchoolTermStatus(String priorSchoolTermStatus) {
        this.priorSchoolTermStatus = priorSchoolTermStatus;
    }

    public void setAward(final Award award) {
        this.award = award;
    }

    public void setMinorSchoolChildAward(final Award award) {
        this.minorSchoolChildAward = award;
    }

    public void setPriorSchoolChild(final Award priorSchoolChild) { this.priorSchoolChild = priorSchoolChild; }

    @Override
    public String toString() {
        return "ChildResponse{" +
                "exceptionMessages=" + exceptionMessages +
                ", logMessage=" + logMessage +
                ", award=" + award +
                ", minorSchoolChildAward=" + minorSchoolChildAward +
                ", priorSchoolChild=" + priorSchoolChild +
                ", priorSchoolTermStatus='" + priorSchoolTermStatus + '\'' +
                '}';
    }
}
