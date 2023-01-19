package gov.va.vba.rbps.lettergeneration;

import java.util.Date;

public class BenefitInformation {

	    private String			dependentType;
	    private String			fullName;
	  	private Date            effectiveDate;
	   
	  	public String getDependentType() {
			return dependentType;
		}
		public void setDependentType(String dependentType) {
			this.dependentType = dependentType;
		}
		 public String getFullName() {
				return fullName;
			}
			public void setFullName(String fullName) {
				this.fullName = fullName;
			}
		public Date getEffectiveDate() {
			return effectiveDate;
		}
		public void setEffectiveDate(Date effectiveDate) {
			this.effectiveDate = effectiveDate;
		}
		
}
