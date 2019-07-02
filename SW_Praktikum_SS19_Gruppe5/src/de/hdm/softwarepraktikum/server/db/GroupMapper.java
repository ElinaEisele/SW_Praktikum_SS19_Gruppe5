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
 * Mapper-Klasse, die <code>Group</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * @author CarlaHofmann
 */

public class GroupMapper {

	  /**
	   * Die Klasse GroupMapper wird nur einmal instantiiert. Man spricht hierbei
	   * von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	   * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   */
	private static GroupMapper groupMapper = null;

	/**
	 * Geschuetzter Konstruktor verhindert weitere Instanziierungen dieser Klasse.
	 */
	protected GroupMapper() {
	}

	/**
     * Diese statische Methode kann aufgrufen werden durch
     * <code>GroupMapper.groupMapper()</code>. Sie stellt die
     * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
     * Instanz von <code>GroupMapper</code> existiert.
	 *
	 * @return groupMapper
	 */
	public static GroupMapper groupMapper() {
		if (groupMapper == null) {
			groupMapper = new GroupMapper();
		}

		return groupMapper;
	}

	/**
	 * Auslesen aller Gruppen.
	 *
	 * @return ArrayList<Group>
	 */
	public ArrayList<Group> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<Group> groups = new ArrayList<Group>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM usergroups");

			while (rs.next()) {
				Group g = new Group();
				g.setId(rs.getInt("id"));
				g.setCreationDate(rs.getDate("creationDate"));
				g.setName(rs.getString("name"));
				groups.add(g);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return groups;
	}

	/**
	 * Suchen einer Gruppe mit vorgegebener Id. Da diese eindeutig ist,
	 * wird genau ein Objekt zurueckgegeben.
	 * 
	 * @param id
	 * @return Group-Objekt
	 */
	public Group findById(int id) {

		Connection con = DBConnection.connection();
		Group g = new Group();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM usergroups WHERE id = " + id);

			if (rs.next()) {

				g.setId(rs.getInt("id"));
				g.setCreationDate(rs.getDate("creationDate"));
				g.setName(rs.getString("name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return g;
	}

	/**
	 * Einfügen eines <code>Group</code>-Objekts in die Datenbank. Dabei wird
	 * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	 * berichtigt.
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

			PreparedStatement pstmt = con.prepareStatement("INSERT INTO usergroups (id, creationDate, name) "
					+ "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, group.getId());
			java.sql.Date sqlDate = new java.sql.Date(group.getCreationDate().getTime());
			pstmt.setDate(2, sqlDate);
			pstmt.setString(3, group.getName());
			pstmt.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return group;
		
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
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

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return group;
		
	}

	/**
	 * Löschen der Daten eines <code>Group</code>-Objekts aus der Datenbank.
	 *
	 * @param group
	 */
	public void delete(Group group) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM usergroups WHERE id = " + group.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Einfügen einer Membershpbeziehung in die Datenbank.
	 * 
	 * @param user_id
	 * @param usergroup_id
	 */
	public void insertMembership(int userId, int usergroupId) {
		
		Connection con = DBConnection.connection();

		try {

			PreparedStatement pstmt = con.prepareStatement("INSERT INTO memberships (user_id, usergroup_id) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, userId);
			pstmt.setInt(2, usergroupId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Löschen einer Membershpbeziehung aus der Datenbank.
	 * 
	 * @param userId
	 * @param groupId
	 */
	public void deleteMembership(int userId, int groupId) {
		
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM memberships WHERE user_id = " + userId + " and usergroup_id = " + groupId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * Löschen aller Membershpbeziehung einer Gruppe aus der Datenbank.
	 * 
	 * @param usergroup_id
	 */
	public void deleteMemberships(int groupId) {
		
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM memberships WHERE usergroup_id = " + groupId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Auslesen der zugehörigen <code>Group</code>-Objekte eines 
	 * gegebenen <code>User<code>-Objekts.
	 * 
	 * @param user
	 * @return Group-Objekt
	 */
	public ArrayList<Group> getGroupsOf(User user) {

		Connection con = DBConnection.connection();
		ArrayList<Group> groups = new ArrayList<Group>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT memberships.user_id as user_id, "
					+ "memberships.usergroup_id as usergroup_id, "
					+ "usergroups.creationDate as usergroup_creationDate, "
					+ "usergroups.name as usergroup_name "
					+ "FROM memberships INNER JOIN usergroups "
					+ "ON memberships.usergroup_id = usergroups.id "
					+ "WHERE memberships.user_id = " + user.getId());

			while (rs.next()) {
				Group g = new Group();
				g.setId(rs.getInt("usergroup_id"));
				g.setCreationDate(rs.getDate("usergroup_creationDate"));
				g.setName(rs.getString("usergroup_name"));
				groups.add(g);
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return groups;
	}
	

}
