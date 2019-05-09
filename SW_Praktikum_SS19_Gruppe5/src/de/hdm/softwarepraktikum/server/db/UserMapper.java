package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.*;
import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Mapper Klasse für </code>User</code> Objekte. Diese umfasst Methoden um User
 * Objekte zu erstellen, zu suchen, zu modifizieren und zu löschen. Das Mapping
 * funktioniert dabei bidirektional. Es können Objekte in DB-Strukturen und
 * DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author LeoniFriedrich
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
	 * @return Gibt den User Mapper zurück.
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
	 * @return Gibt eine Liste aller Kontakte des Users zurueck.
	 */
	public ArrayList<User> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<User> users = new ArrayList<User>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT User_ID, Gmail, Name" + " FROM User");

			while (rs.next()) {

				User user = new User();
				// Inhalt einfügen
				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return users;
	}

	/**
	 * User mittels Id finden
	 * 
	 * @param id: Die id wird uebergeben, um daran den User zu finden.
	 * @return Der User der mittels der Id gefunden wurde, wird zurückgegeben.
	 */
	public User findById(int id) {
		// DB Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// leeres SQL Statement anlegen
			Statement stmt = con.createStatement();
			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT User_ID, Gmail, Name" + " FROM User" + " WHERE User_Id = " + id);

			if (rs.next()) {

				User user = new User();
				// Inhalt einfuegen!!
				return user;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	
	/**
	 * User mittels des User-Namens finden.
	 * 
	 * @param name: Der Name des Users wird uebergeben.
	 * @return Der User der ueber dessen Namen gefunden wurde, wird zurueckgegeben
	 * 
	 */
	public ArrayList<User> findByName(String name) {
		
		Connection con = DBConnection.connection();
		
		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT User_ID, Name" + " FROM User" + " WHERE Name ='" + name + "'");

			if (rs.next()) {

				User user = new User();
				// Inhalt einfügen!
				return user;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
		
	}
	
	/**
	 * Member mittels der Gmail Adresse finden.
	 * 
	 * @param gmail: Die gmail des Users wird uebergeben.
	 * @return Der User der ueber dessen GMail gefunden wurde, wird zurueckgegeben
	 * 
	 */
	public ArrayList<User> findByGMail(String gmail) {

		Connection con = DBConnection.connection();
		
		//Inhalt

		return null;
	}

	
	/**
	 * Delete Methode: um User-Datensatz aus der DB entfernen
	 * 
	 * @param user: Der User wird uebergeben.
	 */

	public void delete(User user) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM User WHERE User_ID =" // hier fehlt noch die ID des BO
			);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert Methode, um eine neue Entitaet der Datenbank hinzuzufügen.
	 *
	 * @param user: Der eingeloggte User wird uebergeben.
	 * @return Der user wird zurueckgegeben.
	 */
	public User insert(User user) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(User_ID) AS maxid " + "FROM User ");

			if (rs.next()) {
			}

			PreparedStatement stmt2 = con.prepareStatement("INSERT INTO User (User_ID, Gmail, Name) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			// vervollständigen!

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return user;

	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 *
	 * @param user Der User wird uebergeben.
	 * @return Gibt den aktualisierten User zurueck.
	 */
	public User update(User user) {
		Connection con = DBConnection.connection();

		try {
			PreparedStatement stmt = con.prepareStatement("UPDATE User SET GMail= ?, Name= ? WHERE User_ID = ?");
			//vervollständigen
		} catch (SQLException e) {
			e.printStackTrace();
			}
		return user;
	}
	
	public User getGroupMemberOf(Listitem listitem) {
	
		Connection con = DBConnection.connection();
		User groupMember = new User();
		
		try {
			
		} catch (SQLException e) {
			e.printStackTrace();
			}
		return groupMember;
	}
}



