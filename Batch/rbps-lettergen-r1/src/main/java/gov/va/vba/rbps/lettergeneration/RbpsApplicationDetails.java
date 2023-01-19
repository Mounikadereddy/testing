package gov.va.vba.rbps.lettergeneration;





public class RbpsApplicationDetails {

	
    private String			username;
    private String			password;
    private String			clientmachine;
    private String			appname;
    private String 			stationid;
    private String			serverName;
    private String          prefix;
    
    
    
    public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getUsername() {
        return username;
    }
    public void setUsername(final String username) {
        this.username = username;
    }
    
 
    public String getPassword() {
        return password;
    }
    public void setPassword(final String password) {
        this.password = password;
    }

    
    public String getClientmachine() {
        return clientmachine;
    }
    public void setClientmachine(final String clientmachine) {
        this.clientmachine = clientmachine;
    }

    
    public String getAppname() {
        return appname;
    }
    public void setAppname(final String appname) {
        this.appname = appname;
    }

    
    public String getStationid() {
        return stationid;
    }
    public void setStationid(final String stationid) {
        this.stationid = stationid;
    }
    
    
    public void setServerName( final String serverName ) {

        this.serverName = serverName;
    }
    public String getServerName() {

        return serverName;
    }

    
}
