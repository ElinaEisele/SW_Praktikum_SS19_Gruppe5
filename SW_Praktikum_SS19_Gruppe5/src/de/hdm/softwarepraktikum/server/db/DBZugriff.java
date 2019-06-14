package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.hdm.softwarepraktikum.shared.bo.User;

public class DBZugriff {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Connection con;
		
		try {
			con = DriverManager.getConnection("jdbc:google:mysql://swpraktikum:main-mechanism-242607:europe-west3:swpraktikum?user=root&password=swpraktikum");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}

			
		User u = new User("Carla Hofmann", "carla@web.de");
		
		System.out.println(u.getId());
		

		
	}

}
