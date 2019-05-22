package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

public class DBZugriff {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				
		
		try {
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/swpraktikum?user=root&password=&serverTimezone=UTC");
			
			String pn = ListitemMapper.listitemMapper().getProductnameOf(1);
			
			System.out.println(pn.toString());
		
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
