package gov.va.vba.rbps.claimprocessor.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	//task 009232 use new datasource jdbc/wbrbps/CorpDB
    //private static String DEFAULT_DATA_SOURCE = "jdbc/vbms/CorpDB";
	 private static String DEFAULT_DATA_SOURCE = "jdbc/wbrbps/CorpDB";

    public static Connection getConnection(String dataSource) throws NamingException, SQLException {
        Context initContext = new InitialContext();
        DataSource ds = (DataSource) initContext.lookup(dataSource);
        return ds.getConnection();

    }

    public static Connection getConnection() throws NamingException, SQLException {
        Context initContext = new InitialContext();
        DataSource ds = (DataSource) initContext.lookup(DEFAULT_DATA_SOURCE);
        return ds.getConnection();
    }
}
