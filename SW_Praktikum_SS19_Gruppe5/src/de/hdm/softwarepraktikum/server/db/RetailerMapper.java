package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Mapper Klasse für </code>Retailer</code> Objekte. Diese umfasst Methoden um
 * RetailerMapper Objekte zu erstellen, zu suchen, zu modifizieren und zu
 * loeschen. Das Mapping funktioniert dabei bidirektional. Es koennen Objekte in
 * DB-Strukturen und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author LeoniFriedrich & CarlaHofmann
 *
 */

public class RetailerMapper {

	/**
	 * Speicherung der Instanz dieser Mapperklasse
	 */
	private static RetailerMapper retailerMapper = null;

	/**
	 * Geschuetzter Konstrukter verhindert weitere Instanzierungen von
	 * RetailerMapper Objekten.
	 */
	protected RetailerMapper() {
	}

	/**
	 * Sicherstellung der Singleton Eigenschaft der Mapperklasse
	 * 
	 * @return Gibt den RetailerMapper zurueck.
	 */

	public static RetailerMapper retailerMapper() {
		if (retailerMapper == null) {
			retailerMapper = new RetailerMapper();
		}
		return retailerMapper;
	}

	/**
	 * Ausgabe einer Liste aller Retailer Objekte
	 * 
	 * @return gibt Liste aller Retailer Objekte zurück.
	 */
	public ArrayList<Retailer> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<Retailer> retailers = new ArrayList<Retailer>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, name, creationDate FROM retailers ORDER BY id");

			while (rs.next()) {

				Retailer retailer = new Retailer();
				retailer.setId(rs.getInt("id"));
				retailer.setName(rs.getString("name"));
				retailer.setCreationDate(rs.getDate("creationDate"));

				retailers.add(retailer);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return retailers;
	}

	/**
	 * Methode um Retailer mittels Id zu finden
	 * 
	 * @param id: Die Id wird uebergeben.
	 * @return Der Retailer wird mit der jeweiligen id wird zurueckgegeben.
	 */
	public Retailer findById(int id) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, name, creationDate FROM retailers WHERE id = " + id);
			if (rs.next()) {

				Retailer retailer = new Retailer();
				retailer.setId(rs.getInt("id"));
				retailer.setName(rs.getString("name"));
				retailer.setCreationDate(rs.getDate("creationDate"));
				return retailer;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Retailer mittels Namen finden
	 * 
	 * @param name: Uebergabe des Namens eines Retailers in Form eines Strings
	 * 
	 * @return Retailer(n) mit dem entsprechenden Namen
	 */
	public ArrayList<Retailer> findByName(String name) {

		Connection con = DBConnection.connection();
		ArrayList<Retailer> retailers = new ArrayList<Retailer>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, name, creationDate FROM retailers WHERE name = " + name);

			while (rs.next()) {

				Retailer retailer = new Retailer();
				retailer.setId(rs.getInt("id"));
				retailer.setName(rs.getString("name"));
				retailer.setCreationDate(rs.getDate("creationDate"));

				retailers.add(retailer);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return retailers;
	}

	/**
	 * Insert Methode um der Datenbank eine neue Entitaet hinzuzufuegen.
	 * 
	 * @param retailer: Der Retailer wird uebergeben.
	 * @return Der aktualisierte Retailer wird zurueckgegeben.
	 */

	public Retailer insert(Retailer retailer) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM retailers ");

			if (rs.next()) {

				retailer.setId(rs.getInt("maxid") + 1);
			}

			PreparedStatement pstmt = con.prepareStatement(
					"INSERT INTO retailers (id, creationDate, name) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, retailer.getId());
			pstmt.setDate(2, (Date) retailer.getCreationDate());
			pstmt.setString(3, retailer.getName());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return retailer;

	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param retailer: der Retailer wird uebergeben.
	 * @return Der aktualisierte Retailer wird zurueckgegeben.
	 */
	public Retailer update(Retailer retailer) {
		Connection con = DBConnection.connection();

		try {

			PreparedStatement pstmt = con.prepareStatement("UPDATE retailers SET name = ? WHERE id = ?");

			pstmt.setString(1, retailer.getName());
			pstmt.setInt(2, retailer.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retailer;
	}

	/**
	 * Loeschen eines Retailers aus der Datenbank.
	 * 
	 * @param retailer: Der Retailer wird uebergeben.
	 */
	public void delete(Retailer retailer) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM retailers WHERE id =" + retailer.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param listitem
	 * @return
	 */
	
	public Retailer getRetailerOf(Listitem listitem) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT retailer_id FROM listitems WHERE listitem_id = " + listitem.getId());

			if (rs.next()) {

				Retailer r = new Retailer();
				r.getId();
				r.getCreationDate();
				r.getName();
				return r;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */

	public ArrayList<Retailer> getRetailersOf(User user) {
		Connection con = DBConnection.connection();
		ArrayList<Retailer> retailers = new ArrayList<Retailer>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * retailer_id FROM responsibilities WHERE user_id =" + user.getId());

			while (rs.next()) {

				Retailer r = RetailerMapper.retailerMapper().findById(rs.getInt("id"));
				r.getId();
				r.getCreationDate();
				r.getName();
				retailers.add(r);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return retailers;
	}
}
