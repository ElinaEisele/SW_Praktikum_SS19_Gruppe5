package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Mapper Klasse für </code>Group</code> Objekte. Diese umfasst Methoden um
 * Group Objekte zu erstellen, zu suchen, zu modifizieren und zu loeschen. Das
 * Mapping funktioniert dabei bidirektional. Es koennen Objekte in DB-Strukturen
 * und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author CarlaHofmann
 */

public class GroupMapper {

	/**
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	private static GroupMapper groupMapper = null;

	/**
	 * Geschuetzter Konstruktor verhindert weitere Instanzierungen von GroupMapper.
	 */
	protected GroupMapper() {
	}

	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse.
	 *
	 * @return Groupmapper
	 */
	public static GroupMapper groupMapper() {
		if (groupMapper == null) {
			groupMapper = new GroupMapper();
		}

		return groupMapper;
	}

	/**
	 * Ausgabe einer Liste aller Gruppen
	 *
	 * @return ArrayList<Group>
	 */
	public ArrayList<Group> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<Group> groups = new ArrayList<Group>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name FROM usergroups");

			while (rs.next()) {
				Group g = new Group();
				g.setId(rs.getInt("id"));
				g.setCreationDate(rs.getDate("creationDate"));
				g.setName(rs.getString("name"));
				groups.add(g);
			}

			return groups;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Gruppe mittels id finden
	 *
	 * @param id
	 * @return Group-Objekt
	 */
	public Group findById(int id) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name FROM usergroups WHERE id = " + id);

			if (rs.next()) {

				Group g = new Group();
				g.setId(rs.getInt("id"));
				g.setCreationDate(rs.getDate("creationDate"));
				g.setName(rs.getString("name"));
				return g;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Gruppe mithilfe des Gruppennamen finden
	 * 
	 * @param name
	 * @return ArrayList<Group>
	 */
	public ArrayList<Group> findByName(String name) {

		Connection con = DBConnection.connection();
		ArrayList<Group> groups = new ArrayList<Group>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name FROM usergroups WHERE name = " + name);

			while (rs.next()) {

				Group g = new Group();
				g.setId(rs.getInt("id"));
				g.setCreationDate(rs.getDate("creationDate"));
				g.setName(rs.getString("name"));
				groups.add(g);
			}

			return groups;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Insert Methode, um eine neue Entitaet der Datenbank hinzuzufuegen
	 *
	 * @param group
	 * @return Group-Objekt
	 */
	public Group insert(Group group) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM usergroups");

			if (rs.next()) {

				group.setId(rs.getInt("maxid") + 1);
			}

			PreparedStatement pstmt = con.prepareStatement(
					"INSERT INTO usergroups (id, creationDate, name) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, group.getId());
			pstmt.setDate(2, (Date) group.getCreationDate());
			pstmt.setString(3, group.getName());
			pstmt.executeUpdate();

			return group;

		} catch (SQLException e) {
			e.printStackTrace();

			return null;

		}

	}

	/**
	 * Wiederholtes Schreiben / Aendern eines Objekts in die/der Datenbank
	 *
	 * @param group
	 * @return Group-Objekt
	 */
	public Group update(Group group) {

		Connection con = DBConnection.connection();

		try {

			PreparedStatement pstmt = con.prepareStatement("UPDATE usergroups SET name = ? WHERE id = ?");

			pstmt.setString(1, group.getName());
			pstmt.setInt(2, group.getId());
			pstmt.executeUpdate();
			
			return group;

		} catch (SQLException e) {
			e.printStackTrace();
			
			return null;
		}

	}

	/**
	 * Delete Methode, um ein Gruppen-Objekt aus der Datenbank zu entfernen
	 *
	 * @param group
	 */
	public void delete(Group group) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM usergroups WHERE id =" + group.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode, um die Gruppenzugehörigkeit einer Shoppingliste festzustellen
	 * 
	 * @param shoppinglist
	 * @return Group-Objekt
	 */
	public Group getGroupOf(Shoppinglist shoppinglist) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM shoppinglists INNER JOIN usergroups "
					+ "ON shoppinglists.usergroup_id=usergroups.id");

			if (rs.next()) {
				Group g = new Group();
				g.setId(rs.getInt("id"));
				g.setCreationDate(rs.getDate("creationDate"));
				g.setName(rs.getString("name"));
				return g;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;

	}

	/**
	 * Methode, um alle Gruppen eines Users zu finden.
	 * 
	 * @param user
	 * @return Group-Objekt
	 */
	public ArrayList<Group> getGroupsOf(User user) {

		Connection con = DBConnection.connection();
		ArrayList<Group> groups = new ArrayList<Group>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM memberships INNER JOIN usergroups "
					+ "ON memberships.usergroup_id=usergroups.id "
					+ "WHERE user_id = " + user.getId());

			while (rs.next()) {
				Group g = new Group();
				g.setId(rs.getInt("id"));
				g.setCreationDate(rs.getDate("creationDate"));
				g.setName(rs.getString("name"));
				groups.add(g);
			}
			
			return groups;

		} catch (SQLException e) {
			e.printStackTrace();
			
			return null;
		}

	}
	
	/**
	 * 
	 * @param group
	 * @return ArrayList<User>
	 */
	
	public ArrayList<User> getUsersOf(Group group){
		
		Connection con = DBConnection.connection();
		ArrayList<User> groups = new ArrayList<User>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM memberships INNER JOIN users"
					+ "ON memberships.user_id=users.id "
					+ "WHERE usergroup_id = " + group.getId());

			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setCreationDate(rs.getDate("creationDate"));
				u.setName(rs.getString("name"));
				u.setGmailAddress(rs.getString("gMail"));
				groups.add(u);
			}
			
			return groups;

		} catch (SQLException e) {
			e.printStackTrace();
			
			return null;
		}
		
	}
	
	/**
	 * 
	 * User zu einer Gruppe hinzufügen.
	 * 
	 * @param user
	 * @param group
	 */
	
	public void addUserToGroup(User user, Group group) {
		
		Connection con = DBConnection.connection();
		
		try {

			PreparedStatement pstmt = con.prepareStatement("INSERT INTO memberships (user_id, usergroups_id) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, user.getId());
			pstmt.setInt(2, group.getId());
			pstmt.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
