package gov.va.vba.rbps.services.client;

import java.rmi.RemoteException;

public class RbpsClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RbpsWSProxy rbps;
		try {
			if (args[0] == null || args[0].length() == 0) {
				// rbps = new RbpsWSProxy();
				System.out.println("Please pass the endpoint URL as an argument");
				System.exit(1);
			}
			
			System.out.println("url : " + args[0]);
			System.out.println("currentProcess : " + args[1]);
			System.out.println("totalProcessCount : " + args[2]);
			
			rbps = new RbpsWSProxy(args[0]);
			String response = rbps.processRbpsAmendDependency(args[1], args[2]);
			System.out.println(response);
			// System.out.println("Response : "+rbps.processRbpsAmendDependency());
		} catch (Throwable message) {
			message.printStackTrace();
		}
	}
}
