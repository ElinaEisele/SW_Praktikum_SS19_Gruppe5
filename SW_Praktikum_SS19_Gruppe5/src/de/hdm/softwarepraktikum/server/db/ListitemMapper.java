package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.server.bo.Listitem;


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

	
	
	

}
