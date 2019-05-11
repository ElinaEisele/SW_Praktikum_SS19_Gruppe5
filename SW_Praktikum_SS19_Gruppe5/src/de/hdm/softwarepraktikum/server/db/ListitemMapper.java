package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.*;


/**
 * Mapper Klasse für </code>Listitem</code> Objekte. Diese umfasst Methoden um
 * Listitem Objekte zu erstellen, zu suchen, zu modifizieren und zu loeschen.
 * Das Mapping funktioniert dabei bidirektional. Es koennen Objekte in
 * DB-Strukturen und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author CarlaHofmann
 */

public class ListitemMapper {

	/**
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	private static ListitemMapper listitemMapper = null;

	/**
	 * Geschuetzter Konstruktor verhindert weitere Instanzierungen von ListitemMapper.
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

			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, amount, product_id, shoppinglist_id, unit_id, group_id, retailer_id FROM listitems");

			while (rs.next()) {

				Listitem listitem = new Listitem();
				listitem.setId(rs.getInt("id"));
				listitem.setCreationDate(rs.getDate("creationDate"));
				listitem.setAmount(rs.getFloat("amount"));
				//die IDs 
				
				listitems.add(listitem);
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
	 * @param id: Die id wird uebergeben,um daran das entsprechende Listitem zu
	 *        finden.
	 * @return Das Listitem, welches anhand der id gefunden wurde, wird
	 *         zurueckgegeben.
	 */
	public Listitem findById(int id) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, amount, product_id, shoppinglist_id, unit_id, group_id, retailer_id FROM listitems WHERE id= " + id);

			if (rs.next()) {

				Listitem listitem = new Listitem();
				listitem.setId(rs.getInt("id"));
				listitem.setCreationDate(rs.getDate("creationDate"));
				listitem.setAmount(rs.getFloat("amount"));
				//die IDs 
				
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
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM retailers ");

			if (rs.next()) {
				
				listitem.setId(rs.getInt("maxid") + 1);
			}

			PreparedStatement pstmt = con.prepareStatement(
					"INSERT INTO Listitem (id, creationDate, amount) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, listitem.getId());
			pstmt.setDate(2, (Date) listitem.getCreationDate());
			pstmt.setFloat(3, listitem.getAmount());
			pstmt.executeUpdate();

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
			PreparedStatement pstmt = con.prepareStatement("UPDATE listitems SET amount = ? WHERE id = ?");

			pstmt.setFloat(1, listitem.getAmount());
			pstmt.setInt(2, listitem.getId());
			pstmt.executeUpdate();

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
			stmt.executeUpdate("DELETE FROM listitems WHERE id =" + listitem.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode, um alle Listitems einer Shoppingliste auszugeben.
	 * 
	 * @param shoppinglist: Shoppingliste, von welcher die Listitems gesucht werden.
	 * @return Listitems der Shoppinglist
	 */
	public ArrayList<Listitem> getListitemsOf(Shoppinglist shoppinglist) {

		Connection con = DBConnection.connection();
		ArrayList<Listitem> listitems = new ArrayList<Listitem>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("...");

			if (rs.next()) {

				Listitem listitem = new Listitem();
				listitem.setId(rs.getInt("id"));
				listitem.setCreationDate(rs.getDate("CreationDate"));
				return listitem;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Methode, um alle Listitems eines Retailers zu finden.
	 * 
	 * @param retailer: Retailer, von welchem alle Listitems gefunden werden sollen.
	 * @return Listitems eines Händlers.
	 */
	public ArrayList<Listitem> getListitemsOf(Retailer retailer) {

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
		}

		return listitems;

	}

}
