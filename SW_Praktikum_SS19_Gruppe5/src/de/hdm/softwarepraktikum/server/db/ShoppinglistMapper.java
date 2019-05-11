package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;
import javafx.scene.Group;

/**
 * Mapper Klasse für </code>Shoppinglist</code> Objekte. Diese umfasst Methoden
 * um Shoppinglist Objekte zu erstellen zu suchen, zu modifizieren und zu
 * loeschen. Das Mapping funktioniert dabei bidirektional. Es koennen Objekte in
 * DB-Strukturen und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author LeoniFriedrich
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
	 * @return Gibt den Shoppinglist Mapper zurück.
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
	 * @return gibt eine Liste aller Shoppinglist Objekte zurück.
	 */
	public ArrayList<Shoppinglist> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, name, creationDate, usergroup_id FROM shoppinglists");

			while (rs.next()) {

				Shoppinglist shoppinglist = new Shoppinglist();
				shoppinglist.setId(rs.getInt("id"));
				shoppinglist.setName(rs.getString("name"));
				shoppinglist.setCreationDate(rs.getDate("creationDate"));
				// shoppinglist.set ("usergroup_id");

				shoppinglists.add(shoppinglist);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return shoppinglists;

	}

	/**
	 * Methode um Shoppinglist mittels Id zu finden.
	 * 
	 * @param id: die Id wird uebergeben.
	 * @return Die Shoppinglist mit der jeweiligen id wird zurueckgegeben.
	 */
	public Shoppinglist findById(int id) {
		Connection con = DBConnection.connection();

		Shoppinglist shoppinglist = new Shoppinglist();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT id, name, creationDate, usergroup_id FROM shoppinglists WHERE id = " + id);
			if (rs.next()) {
				shoppinglist.setId(rs.getInt("id"));
				shoppinglist.setName(rs.getString("name"));
				shoppinglist.setCreationDate(rs.getDate("creationDate"));
				// usergroup_id
				return shoppinglist;
			}
		} catch (

		SQLException e) {
			e.printStackTrace();
			return null;
		}

		return shoppinglist;
	}

	/**
	 * Shoppinglist mittels Shoppinglist Namen finden
	 * 
	 * @param name: Uebergabe des Namens einer Shoppinglist in Form eines Strings
	 * 
	 * @return Shoppinglist(n) mit dem entsprechenden Namen
	 */
	public ArrayList<Shoppinglist> findByName(String name) {

		Connection con = DBConnection.connection();
		ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT id, name, creationDate, usergroup_id FROM retailers WHERE name = " + name);

			while (rs.next()) {

				Shoppinglist shoppinglist = new Shoppinglist();
				shoppinglist.setId(rs.getInt("id"));
				shoppinglist.setName(rs.getString("name"));
				shoppinglist.setCreationDate(rs.getDate("creationDate"));
				shoppinglist.setGroupId(rs.getInt("usergroup_id"));

				shoppinglists.add(shoppinglist);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return shoppinglists;

	}

	/**
	 * Insert Methode um der Datenbank eine neue Entitaet hinzuzufuegen.
	 * 
	 * @param shoppinglist: Die Shoppinglist wird uebergeben.
	 * @return Die aktualisierte Shoppinglist wird zurueckgegeben.
	 */

	public Shoppinglist insert(Shoppinglist shoppinglist) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("MAX(id) AS maxid FROM shoppinglists ");

			if (rs.next()) {
				shoppinglist.setId(rs.getInt("maxid") + 1);
			}
			PreparedStatement pstmt = con.prepareStatement(
					"INSERT INTO shoppinglists (id, creationDate, name, usergroup_id) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

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
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param shoppinglist: die Shoppinglist wird uebergeben.
	 * @return Die aktualisierte Shoppinglist wird zurueckgegeben.
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
	 * Loeschen einer Shoppinglist aus der Datenbank.
	 * 
	 * @param shoppinglist: Die Shoppinglist wird uebergeben
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

	public Shoppinglist getShoppinglistOf(Listitem listitem) {

		Connection con = DBConnection.connection();
		Shoppinglist shoppinglist = new Shoppinglist();

		try {

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shoppinglist;
	}

	public ArrayList<Shoppinglist> getShoppinglistsOf(Group group) {

		Connection con = DBConnection.connection();
		ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();

		try {

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shoppinglists;
	}

	public ArrayList<Shoppinglist> getShoppinglistsOf(User user) {

		Connection con = DBConnection.connection();
		ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();

		try {

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shoppinglists;
	}
}
