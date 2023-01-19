package gov.va.vba.rbps.coreframework.dao;

import gov.va.vba.rbps.coreframework.dao.exception.NoResultsException;
import gov.va.vba.rbps.coreframework.dto.VetsNetSystem;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;

import java.sql.SQLException;


public interface VetsNetSystemServiceDao {
    public VetsNetSystem findByFieldName (String fieldName) throws RbpsRuntimeException, NoResultsException;
    public boolean isProcessingPensions () throws RbpsRuntimeException, NoResultsException;
    public String isRbpsOn() throws RbpsRuntimeException;
    public String turnOnOffRbps(String onOff) throws RbpsRuntimeException;
}
