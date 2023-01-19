package gov.va.vba.rbps.services.ws;


/**
 * RbpsWsReturnCode.java
 * 
 * Holds return codes for RbpsWS
 * 
 * @author Omar.Gaye
 * @since 06/23/2011
 * @version 1.0
 * 
 * @see <code>RbpsWs</code>
 * 
 */
public enum RbpsWsReturnCode {
	
	SUCCSESS(0, "RbpsWS completed successfuly."),
	VONAPP_FAILURE(1, "VONAPP2 WS Client Failure."),
	AWARDS_FAILURE(2, "AWARDS WS Client Failure."),
	MAPD_FAILURE(3, "MAP-D WS Client Failure."),
	VIRTUALVA_FAILURE(4, "VIRTUAL VA WS Client Failure."),
	SHARE_FAILURE(5, "SHARE WS Client Failure."),
	RBPS_FAILURE(6, "RBPS system failure!!!");
	
	/**
	 * Private Constructor
	 */
	private RbpsWsReturnCode(int code, String message){
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}

	private int code;
	private String message;

}
