package com.manduScheduler.main.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Value;

public class JDBCTemplate {
	
//	@Value("${jdbc.deriver}")
//	private static String jdbcDeriver;
//	@Value("${jdbc.url}")
//	private static String jdbcUrl;
//	@Value("${jdbc.user.name}")
//	private static String jdbcUserName;
//	@Value("${jdbc.user.password}")
//	private static String jdbcPasswrod;
	
	private static String jdbcDeriver = "oracle.jdbc.driver.OracleDriver";
	
	private static String jdbcUrl = "jdbc:oracle:thin:@132.226.234.210:1521:xe";
	
	private static String jdbcUserName = "mdmin";
	
	private static String jdbcPasswrod = "1234";
	
	public Connection getConnection() {
		
		Connection conn = null;
		
		try {
			Class.forName(jdbcDeriver);
			conn = DriverManager.getConnection(jdbcUrl,
												jdbcUserName,
												jdbcPasswrod);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	
	
	public static void close(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
