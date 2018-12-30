package com.payment.dbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	private static final String dbdriver = "com.mysql.jdbc.Driver";
	private static final String dburl = "jdbc:mysql://localhost:3306/payment?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String dbuser = "root";
	private static final String dbpass = "lanshuai2468";
	
	private Connection conn = null;
	
	public DatabaseConnection() throws Exception{
		try{
			Class.forName(dbdriver);
			this.conn = DriverManager.getConnection(dburl,dbuser,dbpass);
		}catch(Exception e){
			throw e;
		}
	}
	public Connection getConnection(){
		return this.conn;
	}
	public void close() throws Exception{
		if (this.conn != null) {
			try{
				this.conn.close();
			}catch(Exception e){
				throw e;
			}
		}
	} 
}
