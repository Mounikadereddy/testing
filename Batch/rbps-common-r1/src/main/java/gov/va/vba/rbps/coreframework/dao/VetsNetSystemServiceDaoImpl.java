package gov.va.vba.rbps.coreframework.dao;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dao.exception.NoResultsException;
import gov.va.vba.rbps.coreframework.dto.VetsNetSystem;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.EmailSender;

import java.sql.*;


public class VetsNetSystemServiceDaoImpl implements VetsNetSystemServiceDao {
	private static Logger logger = Logger.getLogger(VetsNetSystemServiceDaoImpl.class);
	

    private final Connection connection;

    public VetsNetSystemServiceDaoImpl(Connection conn) {
        this.connection = conn;
    }

    @Override
    public VetsNetSystem findByFieldName(String fieldName) {
        String query = "select * from VTSNET_SYSTEM where field_nm = ?";

        try {
            PreparedStatement fetchByFieldName = connection.prepareStatement(query);
            fetchByFieldName.setString(1,fieldName);
            ResultSet results = fetchByFieldName.executeQuery();

            if(!results.next()) {
                throw new NoResultsException("No results returned");
            }

            return mapToObject(results);
        } catch (SQLException e) {
            throw new RbpsRuntimeException(e);
        }

    }



    @Override
    public boolean isProcessingPensions() throws RbpsRuntimeException {
        VetsNetSystem vetsNetSystem = findByFieldName("BGS_RBPS_PROCESS_PENSION_AWARDS");
        return vetsNetSystem.getValueText().equalsIgnoreCase("Y");
    }


    protected VetsNetSystem mapToObject(ResultSet resultSet) throws SQLException {
        VetsNetSystem vetsNetSystem = new VetsNetSystem();
        vetsNetSystem.setFieldName(resultSet.getString("field_nm"));
        vetsNetSystem.setJrnDt(resultSet.getDate("jrn_dt"));
        vetsNetSystem.setJrnLctnId(resultSet.getString("jrn_lctn_id"));
        vetsNetSystem.setJrnObjId(resultSet.getString("jrn_obj_id"));
        vetsNetSystem.setJrnStatusTypeCd(resultSet.getString("jrn_status_type_cd"));
        vetsNetSystem.setJrnUserId(resultSet.getString("jrn_user_id"));
        vetsNetSystem.setValueDate(resultSet.getDate("value_dt"));
        vetsNetSystem.setValueText(resultSet.getString("value_txt"));
        vetsNetSystem.setValueNumber(resultSet.getBigDecimal("value_nbr"));
        return vetsNetSystem;
    }
    @Override
    public String isRbpsOn() throws RbpsRuntimeException {
    	
    	try {
    	
		logger.debug("Calling ws_rbps_prc.get_rbps_run_ind with conn" + connection.toString());
		CallableStatement cs = connection
				.prepareCall("{call ws_rbps_prc.get_rbps_run_ind (?,?)}");
		cs.registerOutParameter(1, Types.VARCHAR);
		cs.registerOutParameter(2, Types.VARCHAR);
		
		logger.debug("call ws_award_data_prc.getVetWithholdingData registerOutParameter");
		logger.debug("statement = " + cs.toString());
		cs.execute();
		
		String isRbpsOn = cs.getString(1);
		String returnText = cs.getString(2);
		if(returnText.equalsIgnoreCase("Success")){
			logger.debug("Call to ws_rbps_prc.get_rbps_run_ind procedure Success  with value for isRbpsOn" + isRbpsOn);
			return isRbpsOn;
		}
		else{
			logger.debug("Call to ws_rbps_prc.get_rbps_run_ind procedure is not Success  with value for returnText" + returnText);
			throw new RbpsRuntimeException(
					"Call to ws_rbps_prc.get_rbps_run_ind failed with value for returnText" + returnText);
		}
    	} catch (SQLException e) {
			logger.debug("Call to ws_rbps_prc.get_rbps_run_ind procedure failed " + e.getStackTrace());
			throw new RbpsRuntimeException(
					"Call to ws_rbps_prc.get_rbps_run_ind failed " + e.getMessage());
		} catch (Exception e) {
			String detailedMessage = "Call to ws_rbps_prc.getVetWithholdingData procedure failed ";
			logger.debug("Call to ws_rbps_prc.getVetWithholdingData procedure failed " + e.getStackTrace());
			throw new RbpsRuntimeException(e.getMessage());
		} finally {
			if (connection != null) {
				try {
					connection.close();			
				} catch (SQLException e) {
					throw new RbpsRuntimeException("Unable to close connection: " + e.getMessage());
				}
			}
		}
    }
    @Override
    public String turnOnOffRbps(String onOff) throws RbpsRuntimeException {
    	
    	try {
    	String onOffFlag=onOff.substring(0, 1);
    	String userName=onOff.substring(2);
    	logger.debug("Calling ws_rbps_prc.update_rbps_run_ind  with conn" + connection.toString());
		CallableStatement cs = connection
				.prepareCall("{call ws_rbps_prc.update_rbps_run_ind  (?,?,?,?)}");
		cs.setString(1,userName);
		cs.setString(2,"");
		cs.setString(3,onOffFlag);
		cs.registerOutParameter(4, Types.VARCHAR);
		
		
		logger.debug("call ws_rbps_prc.update_rbps_run_ind  registerOutParameter");
		logger.debug("statement = " + cs.toString());
		cs.execute();
		
		String isRbpsOn = cs.getString(3);
		String returnText = cs.getString(4);
		if(returnText.equalsIgnoreCase("Success")){
			logger.debug("Call to ws_rbps_prc.update_rbps_run_ind  procedure Success  with value for isRbpsOn" + isRbpsOn);
			if(onOffFlag.equalsIgnoreCase("N")){
				EmailSender.addInfoMsg("Successfully turned off RBPS by "+userName+"."); 
						
				returnText="Successfully turned off RBPS.";
			}else{
				EmailSender.addInfoMsg("Successfully turned on RBPS by "+userName+"."); 
				returnText="Successfully turned on RBPS.";
			}
			return returnText;
		}
		else{
			logger.debug("Call to ws_rbps_prc.update_rbps_run_ind  procedure is not Success  with value for returnText" + returnText);
			throw new RbpsRuntimeException(
					"Call to ws_rbps_prc.update_rbps_run_ind  failed with value for returnText" + returnText);
		}
    	} catch (SQLException e) {
			logger.debug("Call to ws_rbps_prc.update_rbps_run_ind  procedure failed " + e.getStackTrace());
			throw new RbpsRuntimeException(
					"Call to ws_rbps_prc.update_rbps_run_ind  failed " + e.getMessage());
		} catch (Exception e) {
			String detailedMessage = "Call to ws_rbps_prc.gupdate_rbps_run_ind  procedure failed ";
			logger.debug("Call to ws_rbps_prc.update_rbps_run_ind  procedure failed " + e.getStackTrace());
			throw new RbpsRuntimeException(e.getMessage());
		} finally {
			if (connection != null) {
				try {
					connection.close();			
				} catch (SQLException e) {
					throw new RbpsRuntimeException("Unable to close connection: " + e.getMessage());
				}
			}
		}
    }
}
