package com.app.util;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

public class DBConnectionUtility {

	private DBConnectionUtility(){
		throw new IllegalStateException("Utility class");
	}

    private static Logger log = LogManager.getRootLogger();

	public static Connection getDBConnection() {

		final String data_source = "java:comp/env/jdbc/crudDB";

		Connection result = null;
		try {
			Context initialContext = new InitialContext();
			DataSource datasource = (DataSource) initialContext.lookup(data_source);
			if (datasource != null) {
				result = datasource.getConnection();
			} else {
				log.error("Failed to lookup datasource.");
			}
		} catch (NamingException | SQLException ex) {
			log.error("Cannot get connection: " + ex);
		} catch (RuntimeException te) {
			log.error("runtime exception from DBConnectionUtility class: " + te);
		}
		return result;
	}


}
