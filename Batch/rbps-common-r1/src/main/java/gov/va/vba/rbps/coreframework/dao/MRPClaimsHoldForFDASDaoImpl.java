/*
 * SR 1205913
 * RBPS process modification to �hold� MRP claims for DFAS Processing time
 * created by: Robert Dong
 * created at: 10/29/2020
 */

package gov.va.vba.rbps.coreframework.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
//import gov.va.vba.rbps.services.impl.RbpsServiceFacade;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dao.exception.NoResultsException;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;

public class MRPClaimsHoldForFDASDaoImpl implements MRPClaimsHoldForFDASDao {
	private static Logger logger = Logger.getLogger(MRPClaimsHoldForFDASDaoImpl.class);
	
	@Override
	public int findFDASForHoldtime(Connection conn) throws NoResultsException {
		int claimHold = 0;
		String query = "SELECT cutoff_dt FROM award_dfas_cutoff_dates " +
        			   " WHERE  to_char(cutoff_dt, 'MM-YYYY') = to_char(SYSDATE, 'MM-YYYY') " +   
        			   		   "AND pay_cd  = 'RETIRD_PAY' " +
        			   		   "AND EXISTS (SELECT NVL(max(value_txt),'Y') FROM vtsnet_system " +
                               "WHERE field_nm = 'BGS_RBPS_RETPAY_ADJ_PROCG')"; 

        try {     
            PreparedStatement getCutOffdtTime = conn.prepareStatement(query);
            ResultSet results = getCutOffdtTime.executeQuery();

            if(!results.next()) {
    			CommonUtils.log(logger, "No results returned for SQL: " + query); 
                throw new NoResultsException("No results found for cutoff query");
            }
            claimHold = calculateCutOffTime(results.getTimestamp("cutoff_dt"));
        } catch (SQLException e) {
            throw new RbpsRuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new NoResultsException("Unable to close connection: " + e.getMessage());
                }
            }
        }

		return claimHold;
	}

	private int calculateCutOffTime (Timestamp cutoffStart) {
		int cutOff = 0;
		//add one hour to cutoff Timestamp
		Timestamp cutoffEnd = new Timestamp(cutoffStart.getTime() + (1000 * 60 * 60 * 1));
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		//return -1, if it is one hour cutoff time
		if (currentTime.after(cutoffStart) && currentTime.before(cutoffEnd)) {
			cutOff = -1;
		}
		return cutOff;
	}
}
