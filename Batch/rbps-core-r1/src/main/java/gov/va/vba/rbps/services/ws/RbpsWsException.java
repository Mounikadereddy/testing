package gov.va.vba.rbps.services.ws;

import gov.va.vba.rbps.coreframework.exception.RbpsException;
import gov.va.vba.rbps.services.ws.client.RbpsFaultBean;

import javax.xml.ws.WebFault;

/**
 *
 * <p>
 * Title: <code>RbpsWsException</code>
 * </p>
 * <p>
 * Description: This class extends <code>RbpsRuntimeException</code>
 *  {@link RbpsRuntimeExecption}
 *  This class will handle all exceptions thrown when the RBPS WS
 *  is running
 * </p>
 *
 * @see <code>RbpsWsReturnCode</code>
 * @see <code>RbpsWsException</code>
 * @see <code>RbpsWs</code>
 */


@WebFault(
		name = "RbpsWsException",
		faultBean = "gov.va.vba.rbps.services.ws.RbpsFaultBean",
		targetNamespace = "http://ws.services.rbps.vba.va.gov"
)
public class RbpsWsException extends RbpsException {

	private static final long serialVersionUID = 1110761186402199568L;
	private RbpsFaultBean faultInfo;

	public RbpsWsException() {
		super();
	}

	public RbpsWsException(final String message, final RbpsFaultBean faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}

	public RbpsWsException(final String message, final RbpsFaultBean faultInfo,
			final Throwable cause) {
		super(message, cause);
		this.faultInfo = faultInfo;
	}

	public RbpsFaultBean getFaultInfo() {
		return faultInfo;
	}
}
