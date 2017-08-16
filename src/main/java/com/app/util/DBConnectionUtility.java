package com.app.util;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;

public class DBConnectionUtility {

	public static Connection getDBConnection() {

		String DATASOURCE_CONTEXT = "java:comp/env/jdbc/crudDB";

		Connection result = null;
		try {
			Context initialContext = new InitialContext();
			DataSource datasource = (DataSource) initialContext.lookup(DATASOURCE_CONTEXT);
			if (datasource != null) {
				result = datasource.getConnection();
			} else {
				log("Failed to lookup datasource.");
			}
		} catch (NamingException ex) {
			log("Cannot get connection: " + ex);
		} catch (SQLException ex) {
			log("Cannot get connection: " + ex);
		} catch (Throwable te) {
			te.printStackTrace();
		}
		return result;
	}

	private static void log(Object aObject) {
		System.out.println(aObject);
	}
}
