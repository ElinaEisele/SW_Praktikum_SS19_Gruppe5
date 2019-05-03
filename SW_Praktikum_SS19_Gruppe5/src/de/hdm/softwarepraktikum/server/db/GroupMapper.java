package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.server.bo.Shoppinglist;
import de.hdm.softwarepraktikum.server.bo.User;
import javafx.scene.Group;

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

			ResultSet rs = stmt.executeQuery("SELECT ...");

			while (rs.next()) {

				Group group = new Group();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return groups;

	}

	/**
	 * Gruppe mithilfe des Gruppennamen finden.
	 * 
	 * @param name Uebergabe des Namens einer Gruppe in Form eines Strings
	 * @return Gruppe(n) mit dem entsprechenden Namen
	 */
	public ArrayList<Group> findByName(String name) {

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
			ResultSet rs = stmt.executeQuery("");

			if (rs.next()) {

			}

			PreparedStatement stmt2 = con.prepareStatement(
					"INSERT INTO Groups (id, creationDate,name) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			stmt2.setInt(1, group.getBOid());
			stmt2.setDate(2, group.getCreationDate());
			stmt2.setString(3, group.getName());
			stmt2.executeUpdate();

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

			/**
			 * PreparedStatement stmt = con.prepareStatement("UPDATE Groups SET
			 * CreationDate= ?, Name= ? WHERE ID = ?");
			 * 
			 * stmt.setString(1, group.get()); stmt.setString(2, group.getName());
			 * stmt.setInt(3, group.getBO_ID()); stmt.executeUpdate();
			 */

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
			stmt.executeUpdate("DELETE FROM Groups WHERE Groups.id =" + group.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
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

			ResultSet rs = stmt.executeQuery("SELECT id, CreationDate, Name FROM Groups WHERE Group.id = " + id);

			if (rs.next()) {

				Group group = new Group();
				group.setBOid(rs.getInt("id"));
				group.setCreationDate(rs.getString("CreationDate"));
				group.setName(rs.getString("Name"));
				return group;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Methode, um die Gruppenzugehörigkeit einer Shoppingliste festzustellen.
	 * 
	 * @param shoppinglist: Shoppingliste, von welcher die Gruppe abgefragt wird.
	 * @return Gruppe der Shoppingliste
	 */
	public Group getGroupOf(Shoppinglist shoppinglist) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {

				Group group = new Group();
				group.setBOid(rs.getInt("id"));
				group.setCreationDate(rs.getString("CreationDate"));
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
