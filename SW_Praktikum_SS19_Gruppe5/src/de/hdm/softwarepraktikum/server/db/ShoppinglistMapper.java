package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Mapper-Klasse, die <code>Shoppinglist</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * @author LeoniFriedrich & CarlaHofmann
 */

public class ShoppinglistMapper {

	/**
	 * Die Klasse ListitemMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	 * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 */
	private static ShoppinglistMapper shoppinglistMapper = null;

	/**
	 * Geschuetzter Konstruktor verhindert weitere Instanziierungen dieser Klasse.
	 */
	protected ShoppinglistMapper() {

	}

	/**
     * Diese statische Methode kann aufgrufen werden durch
     * <code>ShoppinglistMapper.ShoppinglistMapper()</code>. Sie stellt die
     * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
     * Instanz von <code>ShoppinglistMapper</code> existiert.
	 *
	 * @return shoppinglistMapper
	 */

	public static ShoppinglistMapper shoppinglistMapper() {
		if (shoppinglistMapper == null) {
			shoppinglistMapper = new ShoppinglistMapper();
		}
		return shoppinglistMapper;
	}

	/**
	 * Auslesen aller <code>Shoppinglist<code>-Objekte.
	 *
	 * @return ArrayList<Shoppinglist>
	 */
	public ArrayList<Shoppinglist> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM shoppinglists");

			while (rs.next()) {
				Shoppinglist sl = new Shoppinglist();
				sl.setId(rs.getInt("id"));
				sl.setCreationDate(rs.getDate("creationDate"));
				sl.setName(rs.getString("name"));
				sl.setId(rs.getInt("usergroup_id"));
				shoppinglists.add(sl);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return shoppinglists;
	}

	/**
	 * Suchen eines <code>Shoppinglist<code>-Objekts mit vorgegebener Id. 
	 * Da diese eindeutig ist, wird genau ein Objekt zurueckgegeben.
	 *
	 * @param id
	 * @return Shoppinglist-Objekt
	 */
	public Shoppinglist findById(int id) {
		
		Connection con = DBConnection.connection();
		Shoppinglist sl = new Shoppinglist();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM shoppinglists WHERE id = " + id);
			
			if (rs.next()) {
				sl.setId(rs.getInt("id"));
				sl.setCreationDate(rs.getDate("creationDate"));
				sl.setName(rs.getString("name"));
				sl.setId(rs.getInt("usergroup_id"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return sl;
		
	}

	/**
	 * Suchen eines <code>Shoppinglist<code>-Objekts mit vorgegebenem Namen.
	 * 
	 * @param name
	 * @return ArrayList<Shoppinglist>
	 */
	public ArrayList<Shoppinglist> findByName(String name) {

		Connection con = DBConnection.connection();
		ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name, usergroup_id "
					+ "FROM retailers WHERE name = '" + name + "'");

			while (rs.next()) {
				Shoppinglist sl = new Shoppinglist();
				sl.setId(rs.getInt("id"));
				sl.setCreationDate(rs.getDate("creationDate"));
				sl.setName(rs.getString("name"));
				sl.setGroupId(rs.getInt("usergroup_id"));
				shoppinglists.add(sl);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return shoppinglists;

	}

	/**
	 * Einfügen eines <code>Shoppinglist</code>-Objekts in die Datenbank. 
	 * Dabei wird auch der Primaerschlüssel des übergebenen Objekts 
	 * geprüft und ggf. berichtigt.
	 *
	 * @param shoppinglist
	 * @return Shoppinglist-Objekt
	 */

	public Shoppinglist insert(Shoppinglist shoppinglist) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM shoppinglists");

			if (rs.next()) {
				shoppinglist.setId(rs.getInt("maxid") + 1);
			}
			
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO shoppinglists (id, creationDate, name, usergroup_id) "
					+ "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, shoppinglist.getId());
			pstmt.setDate(2, (Date) shoppinglist.getCreationDate());
			pstmt.setString(3, shoppinglist.getName());
			pstmt.setInt(4, shoppinglist.getGroupId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();	
		}
		
		return shoppinglist;
	}

	/**
	 * Wiederholtes Schreiben eines <code>Shoppinglist<code>-Objekts in die Datenbank.
	 *
	 * @param shoppinglist
	 * @return Shoppinglist-Objekt
	 */
	public Shoppinglist update(Shoppinglist shoppinglist) {
		Connection con = DBConnection.connection();

		try {
			PreparedStatement pstmt = con.prepareStatement("UPDATE shoppinglists SET name = ? WHERE id = ?");

			pstmt.setString(1, shoppinglist.getName());
			pstmt.setInt(2, shoppinglist.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();	
		}
		
		return shoppinglist;

	}

	/**
	 * Loeschen der Daten eines 
	 * <code>Shoppinglist</code>-Objekts aus der Datenbank.
	 *
	 * @param shoppinglist
	 */
	public void delete(Shoppinglist shoppinglist) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM shoppinglists WHERE id = " + shoppinglist.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Hinzufuegen einer neuen Verantwortlichkeit eines Users einer Shoppingliste 
	 * bezogen auf einen bestimmten Haendler anhand den jeweiligen Ids.
	 * 
	 * @param retailerId
	 * @param userId
	 * @param shoppinglistId
	 */
	public void insertResponsibility(int retailerId, int userId, int shoppinglistId) {
		
		Connection con = DBConnection.connection();

		try {
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO responsibilities (retailer_id, user_id, shoppinglist_id) " 
					+ "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		
			pstmt.setInt(1, retailerId);
			pstmt.setInt(2 , userId);
			pstmt.setInt(3 , shoppinglistId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Loeschen einer Verantwortlichkeit anhand der Id eines <code>Shoppinglist<code>-Objekts.
	 * 
	 * @param shoppinglistId
	 */
	public void deleteResponsibilities(int shoppinglistId) {
		
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM responsibilities WHERE shoppinglist_id = " + shoppinglistId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Auslesen des zugehoerigen <code>Shoppinglist<code>-Objekts eines
	 * gegebenen <code>Listitem<code>-Objekts.	
	 *
	 * @param listitem
	 * @return Shoppinglist-Objekt
	 */
	
	public Shoppinglist getShoppinglistOf(Listitem listitem) {

		Connection con = DBConnection.connection();
		Shoppinglist sl = new Shoppinglist();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT shoppinglists.id AS shoppinglist_id, "
					+ "shoppinglists.creationDate AS shoppinglist_creationDate, "
					+ "shoppinglists.name AS shoppinglist_name "
					+ "FROM listitems INNER JOIN shoppinglists "
					+ "ON listitems.shoppinglist_id = shoppinglists.id "
					+ "WHERE listitems.id = " + listitem.getId());
			
			while (rs.next()) {
				sl.setId(rs.getInt("shoppinglist_id"));
				sl.setCreationDate(rs.getDate("shoppinglist_creationDate"));
				sl.setName(rs.getString("shoppinglist_name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return sl;

	}

	/**
	 * Auslesen aller <code>Shoppinglist<code>-Objekte eines
	 * gegebenen <code>Group<code>-Objekts.	
	 *
	 * @param group
	 * @return ArrayList<Shoppinglist>
	 */
	
	public ArrayList<Shoppinglist> getShoppinglistsOf(Group group) {

		Connection con = DBConnection.connection();
		ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();

		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT shoppinglists.id AS shoppinglist_id, "
					+ "shoppinglists.creationDate AS shoppinglist_creationDate, "
					+ "shoppinglists.name AS shoppinglist_name, "
					+ "shoppinglists.usergroup_id AS shoppinglist_usergroup_id "
					+ "FROM shoppinglists INNER JOIN usergroups "
					+ "ON shoppinglists.usergroup_id = usergroups.id "
					+ "WHERE usergroups.id = " + group.getId());
			
			while (rs.next()) {
				Shoppinglist sl = new Shoppinglist();
				sl.setId(rs.getInt("shoppinglist_id"));
				sl.setCreationDate(rs.getDate("shoppinglist_creationDate"));
				sl.setName(rs.getString("shoppinglist_name"));
				sl.setGroupId(rs.getInt("shoppinglist_usergroup_id"));
				shoppinglists.add(sl);
			}

		} catch (SQLException e) {
			e.printStackTrace();	
		}
		
		return shoppinglists;
		
	}

	/**
	 * Auslesen aller <code>Shoppinglist<code>-Objekte eines
	 * gegebenen <code>User<code>-Objekts.	
	 *
	 * @param user
	 * @return ArrayList<Shoppinglist>
	 */
	
	public ArrayList<Shoppinglist> getShoppinglistsOf(User user) {

		Connection con = DBConnection.connection();
		ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();

		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT shoppinglists.id AS shoppinglist_id, "
					+ "shoppinglists.creationDate AS shoppinglist_creationDate, "
					+ "shoppinglists.name AS shoppinglist_name "
					+ "FROM shoppinglists INNER JOIN users "
					+ "ON shoppinglists.user_id = users.id "
					+ "WHERE users.id = " + user.getId());
			
			while (rs.next()) {
				Shoppinglist sl = new Shoppinglist();
				sl.setId(rs.getInt("shoppinglist_id"));
				sl.setCreationDate(rs.getDate("shoppinglist_creationDate"));
				sl.setName(rs.getString("shoppinglist_name"));
				shoppinglists.add(sl);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return shoppinglists;
		
	}
	
	
}
