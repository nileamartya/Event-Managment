package com.eventmanagement.utility;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyConnection {
	
	public static Connection getConnectionObj()
	{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver Loaded...");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/event?zeroDateTimeBehavior=convertToNull","root","");
			System.out.println("Connection Established...");
			return conn;
			
		    } 
		catch (Exception e) 
		    {
			e.printStackTrace();
			}
		
		return null;
	}
	
	
	
	
	
	
	
	

}
