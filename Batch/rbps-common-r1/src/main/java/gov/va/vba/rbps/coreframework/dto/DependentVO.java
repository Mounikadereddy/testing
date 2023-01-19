package gov.va.vba.rbps.coreframework.dto;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

public class DependentVO {
	private Long personID;
	private String fullName;

	public DependentVO() {
		super();
	}

	public DependentVO(Long personID, String fullName) {
		super();
		this.personID = personID;
		
		if (StringUtils.isBlank(fullName)) 
			this.fullName = "";
		else
			this.fullName = WordUtils.capitalize(fullName.toLowerCase());
	}

	public Long getPersonID() {
		return personID;
	}

	public String getFullName() {
		return fullName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((personID == null) ? 0 : personID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DependentVO other = (DependentVO) obj;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (personID == null) {
			if (other.personID != null)
				return false;
		} else if (!personID.equals(other.personID))
			return false;
		return true;
	}
}
