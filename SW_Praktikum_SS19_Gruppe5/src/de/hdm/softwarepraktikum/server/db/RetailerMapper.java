package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.server.bo.Retailer;
import de.hdm.softwarepraktikum.server.bo.Shoppinglist;


/**
 * Mapper Klasse für </code>Retailer</code> Objekte.
 * Diese umfasst  Methoden um RetailerMapper Objekte zu erstellen, zu suchen, zu  modifizieren und zu loeschen.
 * Das Mapping funktioniert dabei bidirektional. Es koennen Objekte in DB-Strukturen und DB-Stukturen in Objekte umgewandelt werden.
 * @author Leoni Friedrich
 *
 */

public class RetailerMapper {

	/**
	 * Speicherung der Instanz dieser Mapperklasse
	 */
	private static RetailerMapper retailerMapper = null;

	/**
	 * Geschuetzter Konstrukter verhindert weitere Instanzierungen von RetailerMapper Objekten.
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
			retailerMapper = new RetailerMapper ();
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

			ResultSet rs = stmt.executeQuery("SELECT ...");

			while (rs.next()) {

				Retailer retailer = new Retailer();
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

			ResultSet rs = stmt.executeQuery("SELECT ... ");
			if (rs.next()) {

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

			ResultSet rs = stmt.executeQuery("SELECT ...");

			while (rs.next()) {

				Retailer retailer = new Retailer();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return retailers;

	}

}