package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Mapper Klasse für </code>Group</code> Objekte. Diese umfasst Methoden
 * um Group Objekte zu erstellen, zu suchen, zu modifizieren und zu
 * löschen. Das Mapping funktioniert dabei bidirektional. Es können Objekte in
 * DB-Strukturen und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @autor CarlaHofmann
 */

public class GroupMapper {

	/**
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	private static GroupMapper groupMapper = null;

	/**
	 * Geschützter Konstruktor verhindert weitere Instanzierungen von UserMapper.
	 */
	protected GroupMapper() {
	}

	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse.
	 *
	 * @return Gibt den Groupmapper zurück.
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
	 * @return Gibt eine Liste aller Gruppen zurück.
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
	 * @param Übergabe des Namens einer Gruppe in Form eines Strings
	 * @return Gruppe(n) mit dem entsprechenden Namen
	 */
	public ArrayList<Group> findByName (String name){
		
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

}
