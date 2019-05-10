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
	 * @return Gibt eine Liste aller User zurueck.
	 */
	public ArrayList<User> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<User> users = new ArrayList<User>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM Users");

			while (rs.next()) {

				User user = new User();
				user.setId(rs.getInt("id"));
				user.setCreationDate(rs.getDate("creationDate"));
				user.setName(rs.getString("name"));
				user.setGmailAddress(rs.getString("gMail"));
				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return users;
	}

	/**
	 * User mittels Id finden.
	 * 
	 * @param id: Die id wird uebergeben, um daran den User zu finden.
	 * @return Der User der mittels der Id gefunden wurde, wird zurückgegeben.
	 */
	public User findById(int id) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name, gMail FROM Users WHERE id = " + id);

			if (rs.next()) {

				User user = new User();
				user.setId(rs.getInt("id"));
				user.setCreationDate(rs.getDate("creationDate"));
				user.setName(rs.getString("name"));
				user.setGmailAddress(rs.getString("gMail"));
				return user;

			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * User mittels des Namens finden.
	 * 
	 * @param name: Der Name des Users wird uebergeben.
	 * @return Der User der ueber dessen Namen gefunden wurde, wird zurueckgegeben
	 * 
	 */
	public ArrayList<User> findByName(String name) {

		Connection con = DBConnection.connection();
		ArrayList<User> users = new ArrayList<User>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT id, creationDate, name, gMail FROM Users WHERE Name ='" + name + "'");

			if (rs.next()) {

				User user = new User();
				user.setId(rs.getInt("id"));
				user.setCreationDate(rs.getDate("creationDate"));
				user.setName(rs.getString("name"));
				user.setGmailAddress(rs.getString("gMail"));
				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return users;

	}

	/**
	 * User mittels der Gmail Adresse finden.
	 * 
	 * @param gmail: Die gmail des Users wird uebergeben.
	 * @return Der User der ueber dessen GMail gefunden wurde, wird zurueckgegeben
	 * 
	 */
	public User findByGMail(String gmail) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT id, creationDate, name, gMail FROM Users WHERE gMail = '" + gmail + "'");

			if (rs.next()) {

				User user = new User();
				user.setId(rs.getInt("id"));
				user.setCreationDate(rs.getDate("creationDate"));
				user.setGmailAddress(rs.getString("gMail"));
				user.setName(rs.getString("name"));
				return user;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;

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
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM Users ");

			if (rs.next()) {

				user.setId(rs.getInt("maxid") + 1);

			}

			PreparedStatement pstmt = con.prepareStatement(
					"INSERT INTO Users (id, creationDate, name, gMail)" + "VALUES (?, ?, ?, ?)",
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
	 * Delete Methode: um User-Datensatz aus der DB zu entfernen
	 * 
	 * @param user: Der User wird uebergeben.
	 */

	public void delete(User user) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Users WHERE id =" + user.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
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
			// vervollständigen
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public User getGroupMemberOf(Listitem listitem) {

		Connection con = DBConnection.connection();
		User groupMember = new User();

		try {

			//

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return groupMember;
	}
}
