package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Mapper Klasse für </code>Shoppinglist</code> Objekte. Diese umfasst Methoden
 * um Shoppinglist Objekte zu erstellen zu suchen, zu modifizieren und zu
 * loeschen. Das Mapping funktioniert dabei bidirektional. Es koennen Objekte in
 * DB-Strukturen und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author LeoniFriedrich & CarlaHofmann
 */

public class ShoppinglistMapper {

	/**
	 * Speicherung der Instanz dieser Mapper Klasse.
	 */
	private static ShoppinglistMapper shoppinglistMapper = null;

	/**
	 * Geschützter Konstruktor verhindert die Moeglichkeit, mit <code>new</code>
	 * neue Instanzen dieser Klasse zu erzeugen.
	 */

	protected ShoppinglistMapper() {

	}

	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse.
	 * 
	 * @return Shoppinglistmapper
	 */

	public static ShoppinglistMapper shoppinglistMapper() {
		if (shoppinglistMapper == null) {
			shoppinglistMapper = new ShoppinglistMapper();
		}
		return shoppinglistMapper;
	}

	/**
	 * Ausgabe einer Liste aller Shoppinglist Objekte
	 * 
	 * @return Shoppinglist-Liste
	 */
	public ArrayList<Shoppinglist> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name, usergroup_id FROM shoppinglists");

			while (rs.next()) {
				Shoppinglist sl = new Shoppinglist();
				sl.setId(rs.getInt("id"));
				sl.setCreationDate(rs.getDate("creationDate"));
				sl.setName(rs.getString("name"));
				sl.setId(rs.getInt("usergroup_id"));
				shoppinglists.add(sl);
			}
			
			return shoppinglists;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Methode um Shoppinglist mittels Id zu finden.
	 * 
	 * @param id
	 * @return Shoppinglist
	 */
	public Shoppinglist findById(int id) {
		
		Connection con = DBConnection.connection();

		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name, usergroup_id FROM shoppinglists WHERE id = " + id);
			
			if (rs.next()) {
				Shoppinglist sl = new Shoppinglist();
				sl.setId(rs.getInt("id"));
				sl.setCreationDate(rs.getDate("creationDate"));
				sl.setName(rs.getString("name"));
				sl.setId(rs.getInt("usergroup_id"));
				return sl;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
		
	}

	/**
	 * Shoppinglist mittels Shoppinglist Namen finden
	 * 
	 * @param name
	 * 
	 * @return Shoppinglist-Liste
	 */
	public ArrayList<Shoppinglist> findByName(String name) {

		Connection con = DBConnection.connection();
		ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name, usergroup_id FROM retailers WHERE name = " + name);

			while (rs.next()) {
				Shoppinglist sl = new Shoppinglist();
				sl.setId(rs.getInt("id"));
				sl.setCreationDate(rs.getDate("creationDate"));
				sl.setName(rs.getString("name"));
				sl.setGroupId(rs.getInt("usergroup_id"));
				shoppinglists.add(sl);
			}
			
			return shoppinglists;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Insert Methode um der Datenbank eine neue Entitaet hinzuzufuegen.
	 * 
	 * @param shoppinglist
	 * @return Shoppinglist
	 */

	public Shoppinglist insert(Shoppinglist shoppinglist) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("MAX(id) AS maxid FROM shoppinglists ");

			if (rs.next()) {
				shoppinglist.setId(rs.getInt("maxid") + 1);
			}
			
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO shoppinglists (id, creationDate, name, usergroup_id) "
					+ "VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, shoppinglist.getId());
			pstmt.setDate(2, (Date) shoppinglist.getCreationDate());
			pstmt.setString(3, shoppinglist.getName());
			pstmt.setInt(4, shoppinglist.getGroupId());
			pstmt.executeUpdate();
			
			return shoppinglist;

		} catch (SQLException e) {
			e.printStackTrace();
			
			return null;
		}

	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param shoppinglist
	 * @return Shoppinglist
	 */
	public Shoppinglist update(Shoppinglist shoppinglist) {
		Connection con = DBConnection.connection();

		try {
			
			PreparedStatement pstmt = con.prepareStatement("UPDATE shoppinglists SET name = ? WHERE id = ?");

			pstmt.setString(1, shoppinglist.getName());
			pstmt.setInt(2, shoppinglist.getId());
			pstmt.executeUpdate();
			
			return shoppinglist;

		} catch (SQLException e) {
			e.printStackTrace();
			
			return null;
		}

	}

	/**
	 * Loeschen einer Shoppinglist aus der Datenbank.
	 * 
	 * @param shoppinglist
	 */
	public void delete(Shoppinglist shoppinglist) {

		Connection con = DBConnection.connection();

		try {
			
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM shoppinglists WHERE id =" + shoppinglist.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * Zuweisung löschen.
	 * 
	 * @param shoppinglistId
	 */
	public void deleteResponsibilities(int shoppinglistId) {
		
		Connection con = DBConnection.connection();

		try {
			
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM responsibilities WHERE shoppinglist_id =" + shoppinglistId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Die übergeordnete Shoppinglist eines listitems finden.
	 * 
	 * @param listitem
	 * @return Shoppinglist
	 */
	
	public Shoppinglist getShoppinglistOf(Listitem listitem) {

		Connection con = DBConnection.connection();

		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM listitems INNER JOIN shoppinglists "
					+ "ON listitems.shoppinglist_id=shoppinglists.id "
					+ "WHERE listitems.id = " + listitem.getId());
			
			while (rs.next()) {
				Shoppinglist sl = new Shoppinglist();
				sl.setId(rs.getInt("id"));
				sl.setCreationDate(rs.getDate("creationDate"));
				sl.setName(rs.getString("name"));
				
				return sl;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;

	}

	/**
	 * 
	 * @param group
	 * @return
	 */
	
	public ArrayList<Shoppinglist> getShoppinglistsOf(Group group) {

		Connection con = DBConnection.connection();
		ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();

		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM shoppinglists INNER JOIN usergroups "
					+ "ON shoppinglists.usergroup_id = usergroup.id"
					+ "WHERE usergroups.id = " + group.getId());
			
			while (rs.next()) {
				Shoppinglist sl = new Shoppinglist();
				sl.setId(rs.getInt("id"));
				sl.setCreationDate(rs.getDate("creationDate"));
				sl.setName(rs.getString("name"));
				shoppinglists.add(sl);
			}
			
			return shoppinglists;

		} catch (SQLException e) {
			e.printStackTrace();
			
			return null;
		}
		
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	
	public ArrayList<Shoppinglist> getShoppinglistsOf(User user) {

		Connection con = DBConnection.connection();
		ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();

		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM shoppinglists INNER JOIN users"
					+ "ON shoppinglists.user_id = users.id"
					+ "WHERE users.id = " + user.getId());
			
			while (rs.next()) {
				Shoppinglist sl = new Shoppinglist();
				sl.setId(rs.getInt("id"));
				sl.setCreationDate(rs.getDate("creationDate"));
				sl.setName(rs.getString("name"));
				shoppinglists.add(sl);
			}
			
			return shoppinglists;

		} catch (SQLException e) {
			e.printStackTrace();
			
			return null;
		}
		
	}
}
