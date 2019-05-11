package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.*;
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
	 * @return Gibt den Groupmapper zurueck.
	 */
	public static GroupMapper groupMapper() {
		if (groupMapper == null) {
			groupMapper = new GroupMapper();
		}

		return groupMapper;
	}

	/**
	 * Ausgabe einer Liste aller Gruppen.
	 *
	 * @return Gibt eine Liste aller Gruppen zurueck.
	 */
	public ArrayList<Group> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<Group> groups = new ArrayList<Group>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, name, creationDate FROM usergroups");

			while (rs.next()) {

				Group group = new Group();
				group.setId(rs.getInt("id"));
				group.setName(rs.getString("name"));
				group.setCreationDate(rs.getDate("creationDate"));
				groups.add(group);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return groups;

	}

	/**
	 * Gruppe mittels id finden.
	 *
	 * @param id: Die id wird uebergeben,um daran die Gruppe zu finden.
	 * @return Die Gruppe, die anhand der id gefunden wurde, wird zurueckgegeben.
	 */
	public Group findById(int id) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, name, creationDate FROM usergroups WHERE id = " + id);

			if (rs.next()) {

				Group group = new Group();
				group.setId(rs.getInt("id"));
				group.setName(rs.getString("name"));
				group.setCreationDate(rs.getDate("creationDate"));
				return group;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Gruppe mithilfe des Gruppennamen finden.
	 * 
	 * @param name: Uebergabe des Namens einer Gruppe in Form eines Strings
	 * @return Gruppe(n) mit dem entsprechenden Namen
	 */
	public ArrayList<Group> findByName(String name) {

		Connection con = DBConnection.connection();
		ArrayList<Group> groups = new ArrayList<Group>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, name, creationDate FROM usergroups WHERE name = " + name);

			while (rs.next()) {

				Group g = new Group();
				g.setId(rs.getInt("id"));
				g.setName(rs.getString("name"));
				g.setCreationDate(rs.getDate("creationDate"));
				
				groups.add(g);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return groups;

	}

	/**
	 * Insert Methode, um eine neue Entitaet der Datenbank hinzuzufuegen.
	 *
	 * @param group: Die gewaehlte Gruppe wird uebergeben
	 * @return Die group wird zurueckgegeben.
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
					"INSERT INTO usergroups (id, creationDate,name) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, group.getId());
			pstmt.setDate(2, (Date) group.getCreationDate());
			pstmt.setString(3, group.getName());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return group;

	}

	/**
	 * Wiederholtes Schreiben / Aendern eines Objekts in die/der Datenbank.
	 *
	 * @param group: Die Gruppe wird uebergeben.
	 * @return Gibt die akutalisierte Gruppe zurueck.
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
	 * Delete Methode, um ein Gruppen-Objekt aus der Datenbank zu entfernen.
	 *
	 * @param group: Die Gruppe wird uebergeben.
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
	 * Methode, um die Gruppenzugehörigkeit einer Shoppingliste festzustellen.
	 * 
	 * @param shoppinglist: Shoppingliste, von welcher die Gruppe abgefragt wird.
	 * @return Gruppe der Shoppingliste
	 */
	public Group getGroupOf (Shoppinglist shoppinglist) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ...");

			if (rs.next()) {

				Group group = new Group();
				group.setId(rs.getInt("id"));
				group.setCreationDate(rs.getDate("CreationDate"));
				group.setName(rs.getString("Name"));
				return group;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Methode, um alle Gruppen eines Users zu finden.
	 * 
	 * @param user: User, von welchem alle Gruppen gefunden werden sollen.
	 * @return Gruppen, zu welchen der User gehört.
	 */
	public ArrayList<Group> getGroupsOf(User user) {

		Connection con = DBConnection.connection();
		ArrayList<Group> groups = new ArrayList<Group>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT ...");

			while (rs.next()) {

				Group group = new Group();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return groups;

	}

}
