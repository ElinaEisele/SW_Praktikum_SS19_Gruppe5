package de.hdm.softwarepraktikum.server.db;

import java.sql.*;

public class DBZugriff {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection con;
		Statement stmt;
		ResultSet rs;
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?user=root&password=&serverTimezone=UTC");
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * from users");
			
			while(rs.next()){
				System.out.println(rs.getString("name"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
