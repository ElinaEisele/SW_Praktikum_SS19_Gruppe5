package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Mapper-Klasse, die <code>User</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * @author LeoniFriedrich & CarlaHofmann
 *
 */
public class UserMapper {

	/**
	 * Die Klasse ListitemMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	 * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 */
	private static UserMapper userMapper = null;

	/**
	 * Geschuetzter Konstruktor verhindert weitere Instanziierungen dieser Klasse.
	 */
	protected UserMapper() {
	}

	/**
     * Diese statische Methode kann aufgrufen werden durch
     * <code>UserMapper.userMapper()</code>. Sie stellt die
     * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
     * Instanz von <code>UserMapper</code> existiert.
	 *
	 * @return userMapper
	 */
	public static UserMapper userMapper() {
		if (userMapper == null) {
			userMapper = new UserMapper();
		}

		return userMapper;
	}

	/**
	 * Auslesen aller <code>User<code>-Objekte.
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
	 * Suchen eines <code>User<code>-Objekts mit vorgegebener Id. 
	 * Da diese eindeutig ist, wird genau ein Objekt zurueckgegeben.
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
	 * Suchen aller <code>User<code>-Objekte mit vorgegebenem Namen. 
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
	 * Suchen eines <code>User<code>-Objekts mit vorgegebener gMail-Adresse. 
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
	 * Einfügen eines <code>User</code>-Objekts in die Datenbank. 
	 * Dabei wird auch der Primaerschlüssel des übergebenen Objekts 
	 * geprüft und ggf. berichtigt.
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
	 * Wiederholtes Schreiben eines <code>User<code>-Objekts in die Datenbank.
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
	 * Loeschen der Daten eines 
	 * <code>User</code>-Objekts aus der Datenbank.
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
	 * Loeschen aller Verantwortlichkeiten eines <code>User<code>-Objekts.
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
	 * Loeschen aller Membershipbeziehungen eines <code>User<code>-Objekts.
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
	 * Auslesen aller zugehörigen <code>User<code>-Objekte eines
	 * gegebenen <code>Group<code>-Objekts.	
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
	 * Auslesen eines <code>User<code>-Objekts mit der Verantwortlichkeit 
	 * für ein gegebenes <code>Listitem<code>-Objekt.	
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
	
	/**
	 * Auslesen eines <code>User<code>-Objekts eines <code>Shoppinglist<code>-Objekts 
	 * mit einer zugeordneten Verantwortlichkeit zu einem gegebenen <code>Retailer<code>-Objekt.
	 * 
	 * @param shoppinglist
	 * @param retailer
	 * @return User-Objekt
	 */
	public User getAssignedUser(Shoppinglist shoppinglist, Retailer retailer) {
		
		Connection con = DBConnection.connection();
		User u = new User();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT users.id As user_id, "
					+ "users.creationDate AS user_creationDate, "
					+ "users.name AS user_name, "
					+ "users.gMail AS user_gMail "
					+ "FROM responsibilities INNER JOIN users "
					+ "ON responsibilities.user_id = users.id "
					+ "WHERE responsibilities.shoppinglist_id = " + shoppinglist.getId()
					+ " AND responsibilities.retailer_id = " + retailer.getId());

			while (rs.next()) {
				u.setId(rs.getInt("user_id"));
				u.setCreationDate(rs.getDate("user_creationDate"));
				u.setName(rs.getString("user_name"));
				u.setGmailAddress(rs.getString("user_gMail"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return u;
	}
}
