package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Mapper Klasse für </code>User</code> Objekte. Diese umfasst Methoden um User
 * Objekte zu erstellen, zu suchen, zu modifizieren und zu löschen. Das Mapping
 * funktioniert dabei bidirektional. Es können Objekte in DB-Strukturen und
 * DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author LeoniFriedrich & CarlaHofmann
 *
 */
public class UserMapper {

	/**
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	private static UserMapper userMapper = null;

	/**
	 * Geschuetzter Konstruktor verhindert weitere Instanzierungen von UserMapper.
	 */
	protected UserMapper() {
	}

	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse.
	 *
	 * @return Usermapper
	 */
	public static UserMapper userMapper() {
		if (userMapper == null) {
			userMapper = new UserMapper();
		}

		return userMapper;
	}

	/**
	 * Ausgabe einer Liste aller User.
	 * 
	 * @return Userliste
	 */
	public ArrayList<User> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<User> users = new ArrayList<User>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name, gMail FROM users ORDER BY id");

			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setCreationDate(rs.getDate("creationDate"));
				u.setName(rs.getString("name"));
				u.setGmailAddress(rs.getString("gMail"));
				users.add(u);
			}
			
			return users;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * User mittels Id finden.
	 * 
	 * @param id
	 * @return User
	 */
	public User findById(int id) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name, gMail FROM Users WHERE id = " + id);

			if (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setCreationDate(rs.getDate("creationDate"));
				u.setName(rs.getString("name"));
				u.setGmailAddress(rs.getString("gMail"));
				return u;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * User mittels des Namens finden.
	 * 
	 * @param name
	 * @return Userliste
	 * 
	 */
	public ArrayList<User> findByName(String name) {

		Connection con = DBConnection.connection();
		ArrayList<User> users = new ArrayList<User>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name, gMail FROM users WHERE Name ='" + name + "'");

			if (rs.next()) {

				User u = new User();
				u.setId(rs.getInt("id"));
				u.setCreationDate(rs.getDate("creationDate"));
				u.setName(rs.getString("name"));
				u.setGmailAddress(rs.getString("gMail"));
				users.add(u);
			}
			
			return users;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * User mittels der Gmail Adresse finden.
	 * 
	 * @param gmail
	 * @return User
	 * 
	 */
	public User findByGMail(String gmail) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name, gMail FROM users WHERE gMail = '" + gmail + "'");

			if (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setCreationDate(rs.getDate("creationDate"));
				u.setGmailAddress(rs.getString("gMail"));
				u.setName(rs.getString("name"));
				return u;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Insert Methode, um eine neue Entitaet der Datenbank hinzuzufügen.
	 *
	 * @param user
	 * @return User
	 */
	public User insert(User user) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM Users ");

			if (rs.next()) {
				user.setId(rs.getInt("maxid") + 1);
			}

			PreparedStatement pstmt = con.prepareStatement("INSERT INTO Users (id, creationDate, name, gMail)" + "VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, user.getId());
			pstmt.setDate(2, (Date) user.getCreationDate());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getGmailAddress());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;

	}

	/**
	 * Delete Methode um User-Datensatz aus der DB zu entfernen.
	 * 
	 * @param user
	 */

	public void delete(User user) {
		
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM users WHERE id =" + user.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 *
	 * @param user 
	 * @return User
	 */
	
	public User update(User user) {
		
		Connection con = DBConnection.connection();

		try {
			
			PreparedStatement pstmt = con.prepareStatement("UPDATE users SET GMail= ?, Name= ? WHERE User_ID = ?");
			pstmt.setString(1, user.getGmailAddress());
			pstmt.setString(2, user.getName());
			pstmt.setInt(2, user.getId());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}

	/**
	 * 
	 * User finden, welcher dem übergebenen Listitem zugeordnet ist.
	 * 
	 * @param listitem
	 * @return User
	 */
	
	public User getGroupMemberOf(Listitem listitem) {

		Connection con = DBConnection.connection();

		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ...");

			if (rs.next()) {
				
				User u = new User();
				//
				return u;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		
		return null;

	}
}
