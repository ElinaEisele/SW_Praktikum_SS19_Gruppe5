package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Mapper Klasse fuer </code>User</code> Objekte. Diese umfasst Methoden um User
 * Objekte zu erstellen, zu suchen, zu modifizieren und zu loeschen. Das Mapping
 * funktioniert dabei bidirektional. Es koennen Objekte in DB-Strukturen und
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
	 * @return ArrayList<User>
	 */
	public ArrayList<User> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<User> users = new ArrayList<User>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users ORDER BY id");

			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setCreationDate(rs.getDate("creationDate"));
				u.setName(rs.getString("name"));
				u.setGmailAddress(rs.getString("gMail"));
				users.add(u);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return users;

	}

	/**
	 * User mithilfe Id finden.
	 * 
	 * @param id
	 * @return User-Objekte
	 */
	public User findById(int id) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = " + id);

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
	 * User mithilfe des Namens finden.
	 * 
	 * @param name
	 * @return ArrayList<User>
	 * 
	 */
	public ArrayList<User> findByName(String name) {

		Connection con = DBConnection.connection();
		ArrayList<User> users = new ArrayList<User>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE Name = '" + name + "'");

			if (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setCreationDate(rs.getDate("creationDate"));
				u.setName(rs.getString("name"));
				u.setGmailAddress(rs.getString("gMail"));
				users.add(u);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return users;

	}

	/**
	 * User mithilfe der Gmail Adresse finden.
	 * 
	 * @param gmail
	 * @return User-Objekt
	 * 
	 */
	public User findByGMail(String gmail) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE gMail = '" + gmail + "'");

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
	 * Insert Methode, um eine neue Entitaet der Datenbank hinzuzufuegen.
	 *
	 * @param user
	 * @return User-Objekt
	 */
	public User insert(User user) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM users ");

			if (rs.next()) {
				user.setId(rs.getInt("maxid") + 1);
			}

			PreparedStatement pstmt = con.prepareStatement("INSERT INTO users (id, creationDate, name, gMail) "
					+ "VALUES (?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, user.getId());
			pstmt.setDate(2, (Date) user.getCreationDate());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getGmailAddress());
			pstmt.executeUpdate();
			return user;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 *
	 * @param user 
	 * @return User-Objekt
	 */
	
	public User update(User user) {
		
		Connection con = DBConnection.connection();

		try {
			
			PreparedStatement pstmt = con.prepareStatement("UPDATE users SET Name = ?, gMail = ? WHERE User_ID = ?");
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getGmailAddress());
			pstmt.setInt(3, user.getId());
			pstmt.executeUpdate();
			return user;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

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
			stmt.executeUpdate("DELETE FROM users WHERE id = " + user.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Zuweisung loeschen.
	 * 
	 * @param userId
	 */
	public void deleteResponsibilities(int userId) {
		
		Connection con = DBConnection.connection();

		try {
			
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM responsibilities WHERE user_id = " + userId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 
	 * Eine Membershipbeziehung loeschen.
	 * 
	 * @param usergroup_id
	 */
	public void deleteMemberships(int userId) {
		
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM memberships WHERE user_id = " + userId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * User einer Gruppe ausgeben
	 * 
	 * @param group
	 * @return ArrayList<User>
	 */
	
	public ArrayList<User> getUsersOf(Group group){
		
		Connection con = DBConnection.connection();
		ArrayList<User> users = new ArrayList<User>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM memberships INNER JOIN users "
					+ "ON memberships.user_id = users.id "
					+ "WHERE memberships.usergroup_id = " + group.getId());

			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setCreationDate(rs.getDate("creationDate"));
				u.setName(rs.getString("name"));
				u.setGmailAddress(rs.getString("gMail"));
				users.add(u);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
		
	}

	/**
	 * 
	 * User finden, welcher dem uebergebenen Listitem zugeordnet ist.
	 * 
	 * @param listitem
	 * @return User-Objekt
	 */
  
	public User getGroupMemberOf(Listitem listitem){

		Connection con = DBConnection.connection();

		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM listitems INNER JOIN responsibilities "
					+ "ON listitems.retailer_id = responsibilities.retailer_id "
					+ "WHERE listitems.id = " + listitem.getId());
			
			while(rs.next()) {
				int r_id = rs.getInt("retailer_id");
			
				ResultSet rs2 = stmt.executeQuery("SELECT * FROM respinsibilities INNER JOIN users "
						+ "ON responsibilities.user_id = users.id "
						+ "WHERE responsibilities.retailer_id = " + r_id);
	
				while (rs2.next()) {
					User u = new User();
					u.setId(rs2.getInt("id"));
					u.setCreationDate(rs2.getDate("creationDate"));
					u.setName(rs2.getString("name"));
					u.setGmailAddress(rs2.getString("gMail"));
					return u;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		
		return null;

	}
}
