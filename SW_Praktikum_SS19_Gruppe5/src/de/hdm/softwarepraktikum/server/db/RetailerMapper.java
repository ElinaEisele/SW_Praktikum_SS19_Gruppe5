package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Mapper Klasse für </code>Retailer</code> Objekte. Diese umfasst Methoden um
 * RetailerMapper Objekte zu erstellen, zu suchen, zu modifizieren und zu
 * loeschen. Das Mapping funktioniert dabei bidirektional. Es koennen Objekte in
 * DB-Strukturen und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author Leoni Friedrich
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

			ResultSet rs = stmt.executeQuery("SELECT retailer_id, name, creationDate FROM retailers");

			while (rs.next()) {

				Retailer retailer = new Retailer();
				retailer.setId(rs.getInt("retailer_id"));
				retailer.setName(rs.getString("name"));
				retailer.setCreationDate(rs.getString("creationDate"));
				
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

		Retailer retailer = new Retailer();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT retailer_id, name, creationDate FROM retailers WHERE retailer_id = \" + id);");
			if (rs.next()) {

				Retailer retailer = new Retailer();
				retailer.setId(rs.getInt("retailer_id"));
				retailer.setName(rs.getString("name"));
				retailer.setCreationDate(rs.getString("creationDate"));
				return retailer;
			}
		} catch (

		SQLException e) {
			e.printStackTrace();
			return null;
		}

		return retailer;
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

			ResultSet rs = stmt.executeQuery("SELECT retailer_id, name, creationDate FROM retailers WHERE name = " + name);

			while (rs.next()) {

				Retailer retailer = new Retailer();
				retailer.setId(rs.getInt("retailer_id"));
				retailer.setName(rs.getString("name"));
				retailer.setCreationDate(rs.getString("creationDate"));
				
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

			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM retailers ");

			if (rs.next()) {

			}
			// Setzt den AutoCommit auf false, um das sichere Schreiben in die Datenbank zu
			// gewährleisten.
			con.setAutoCommit(false);

			PreparedStatement stmt2 = con.prepareStatement("INSERT INTO ...", Statement.RETURN_GENERATED_KEYS);

			// vervollstaendigen

			stmt2.executeUpdate();

			PreparedStatement stmt3 = con.prepareStatement("INSERT INTO ... ", Statement.RETURN_GENERATED_KEYS);

			stmt3.executeUpdate();

			// Wenn alle Statements fehlerfrei ausgefuehrt wurden, wird commited.
			con.commit();

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

			// Setzt den AutoCommit auf false, um das sichere Schreiben in die Datenbank zu
			// gewaehrleisten.
			con.setAutoCommit(false);

			PreparedStatement stmt = con.prepareStatement("UPDATE ...");

			// vervollständigen

			// Wenn alle Statements fehlerfrei ausgefuehrt wurden, wird commited.
			con.commit();

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
			// Setzt den AutoCommit auf false, um das sichere Schreiben in die Datenbank zu
			// gewaehrleisten.
			con.setAutoCommit(false);

			Statement stmt = con.createStatement();
			stmt.executeUpdate("");

			// vervollstaendigen

			// Wenn alle Statements fehlerfrei ausgefuehrt wurden, wird commited.
			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Retailer getRetailerOf(Listitem listitem) {

		Connection con = DBConnection.connection();
		Retailer retailer = new Retailer();

		try {

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retailer;
	}

	public ArrayList<Retailer> getRetailersOf(User user) {
		Connection con = DBConnection.connection();
		ArrayList<Retailer> retailers = new ArrayList<Retailer>();

		try {

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retailers;
	}
}
