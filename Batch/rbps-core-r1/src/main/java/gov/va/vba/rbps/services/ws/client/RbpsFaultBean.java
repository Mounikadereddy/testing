package gov.va.vba.rbps.services.ws.client;


/**
 *
 * RbpsFaultBean.java
 *
 * @see <code>RbpsWsReturnCode</code>
 * @see <code>RbpsWsException</code>
 * @see <code>RbpsWs</code>
 *
 * @author Omar.Gaye
 * @since 06/22/2011
 * @version 1.0
 *
 **/
public class RbpsFaultBean {

	private String message;

	public RbpsFaultBean(){

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}
}
