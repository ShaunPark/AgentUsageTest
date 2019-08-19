package com.coolage.usagetest.job;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseCaller extends Job {	
	static HikariDataSource datasource = new HikariDataSource(new HikariConfig());

	public static void init() {
		HikariConfig config = new HikariConfig(System.getenv("mysql.config"));
		datasource = new HikariDataSource(config);
	}
	
	@Override
	public int execute() {
		try {
			if( datasource == null ) {
				init();
			}
			try( Connection con=datasource.getConnection();
				 PreparedStatement pstmt = con.prepareStatement(message);			
					){  
				
				try {
					pstmt.execute();
					con.commit();
	
				} catch(Exception e){ 
					con.rollback();
					System.out.println(e);
				}
			}finally {
				
			}
			return 0;
		} catch(Exception e) {
			return -1;
		} 
	}
}
