package gov.va.vba.rbps.services.ws.client.mapping.awardDoc.service;

import java.util.Comparator;

/**
 * Incremental Sort on FullName and AwardEffectiveDate
 * @author VBACOShahP
 *
 */
public class DependencyDecisionVOComparator implements Comparator<DependencyDecisionVO> {

	@Override
	public int compare(DependencyDecisionVO dependencyDecisionVO1, DependencyDecisionVO dependencyDecisionVO2) {
		if(dependencyDecisionVO1.getFullName().equals(dependencyDecisionVO2.getFullName())) 
			return dependencyDecisionVO1.getAwardEffectiveDate().compare(dependencyDecisionVO2.getAwardEffectiveDate());
		else
			return dependencyDecisionVO1.getFullName().compareTo(dependencyDecisionVO2.getFullName());
	}

}
