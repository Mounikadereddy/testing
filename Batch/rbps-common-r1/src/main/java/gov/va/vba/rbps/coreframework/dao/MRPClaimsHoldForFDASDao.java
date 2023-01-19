/*
 * SR 1205913
 * RBPS process modification to “hold” MRP claims for DFAS Processing time
 * created by: Robert Dong
 * created at: 10/29/2020
 */
package gov.va.vba.rbps.coreframework.dao;

import java.sql.Connection;

import gov.va.vba.rbps.coreframework.dao.exception.NoResultsException;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;

public interface MRPClaimsHoldForFDASDao {
	
    public int findFDASForHoldtime (Connection conn)throws NoResultsException;

}
