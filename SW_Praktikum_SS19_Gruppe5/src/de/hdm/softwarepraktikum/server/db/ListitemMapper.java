package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.server.bo.Listitem;
import javafx.scene.Group;


/**
 * Mapper Klasse für </code>Listitem</code> Objekte. Diese umfasst Methoden um
 * Listitem Objekte zu erstellen, zu suchen, zu modifizieren und zu loeschen. Das
 * Mapping funktioniert dabei bidirektional. Es koennen Objekte in DB-Strukturen
 * und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author CarlaHofmann
 */

public class ListitemMapper {
	
	/**
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	private static ListitemMapper listitemMapper = null;

	/**
	 * Geschuetzter Konstruktor verhindert weitere Instanzierungen von UserMapper.
	 */
	protected ListitemMapper() {
	}

	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse.
	 *
	 * @return Gibt den Listitemmapper zurueck.
	 */
	public static ListitemMapper listitemMapper() {
		if (listitemMapper == null) {
			listitemMapper = new ListitemMapper();
		}

		return listitemMapper;
	}
	
	/**
	 * Ausgabe einer Liste aller Listitems.
	 *
	 * @return Gibt eine Liste aller Listitems zurueck.
	 */
	public ArrayList<Listitem> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<Listitem> listitems = new ArrayList<Listitem>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT ...");

			while (rs.next()) {

				Listitem listitem = new Listitem();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return listitems;

	}

	/**
	 * Listitem mittels id finden.
	 *
	 * @param id: Die id wird uebergeben,um daran das entsprechende Listitem zu finden.
	 * @return Das Listitem, welches anhand der id gefunden wurde, wird zurueckgegeben.
	 */
	public Listitem findById(int id) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, CreationDate, Name FROM Listitem WHERE Listitem.id = " + id);

			if (rs.next()) {

				Listitem listitem = new Listitem();
				listitem.setBOid(rs.getInt("id"));
				listitem.setCreationDate(rs.getString("CreationDate"));
				listitem.setName(rs.getString("Name"));
				return listitem;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * Insert Methode, um eine neue Entitaet der Datenbank hinzuzufuegen.
	 *
	 * @param listitem: Das gewaehlte Listitem wird uebergeben
	 * @return Das Listitem wird zurueckgegeben.
	 */
	public Listitem insert(Listitem listitem) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("");

			if (rs.next()) {

			}

			PreparedStatement stmt2 = con.prepareStatement(
					"INSERT INTO Listitem (id, creationDate,name) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			stmt2.setInt(1, listitem.getBOid());
			stmt2.setDate(2, listitem.getCreationDate());
			stmt2.setString(3, listitem.getName());
			stmt2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return listitem;

	}

	/**
	 * Wiederholtes Schreiben / Aendern eines Objekts in die/der Datenbank.
	 *
	 * @param listitem: Das Listitem wird uebergeben.
	 * @return Gibt das akutalisierte Listitem zurueck.
	 */
	public Listitem update(Listitem listitem) {

		Connection con = DBConnection.connection();

		try {

			

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listitem;
	}

	/**
	 * Delete Methode, um ein Listitem-Objekt aus der Datenbank zu entfernen.
	 *
	 * @param listitem: Das Listitem wird uebergeben.
	 */
	public void delete(Listitem listitem) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Listitem WHERE Listitem.id =" + listitem.getBOId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	
	

}
