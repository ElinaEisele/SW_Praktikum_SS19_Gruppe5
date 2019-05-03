package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.server.bo.Shoppinglist;
import javafx.scene.Group;

/**
 * Mapper Klasse für </code>Shoppinglist</code> Objekte. Diese umfasst Methoden
 * um Shoppinglist Objekte zu erstellen zu suchen, zu modifizieren und zu
 * loeschen. Das Mapping funktioniert dabei bidirektional. Es koennen Objekte in
 * DB-Strukturen und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author LeoniFriedrich
 */

public class ShoppinglistMapper {

	/**
	 * Speicherung der Instanz dieser Mapper Klasse.
	 */
	private static ShoppinglistMapper shoppinglistMapper = null;

	/**
	 * Geschützter Konstruktor verhindert die Moeglichkeit, mit <code>new</code>
	 * neue Instanzen dieser Klasse zu erzeugen.
	 */

	protected ShoppinglistMapper() {

	}

	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse.
	 * 
	 * @return Gibt den Shoppinglist Mapper zurück.
	 */

	public static ShoppinglistMapper shoppinglistMapper() {
		if (shoppinglistMapper == null) {
			shoppinglistMapper = new ShoppinglistMapper();
		}
		return shoppinglistMapper;
	}

	/**
	 * Ausgabe einer Liste aller Shoppinglist Objekte
	 * 
	 * @return gibt eine Liste aller Shoppinglist Objekte zurück.
	 */
	public ArrayList<Shoppinglist> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT ...");

			while (rs.next()) {

				Shoppinglist shoppinglist = new Shoppinglist();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return shoppinglists;

	}

	/**
	 * Methode um Shoppinglist mittels Id zu finden.
	 * 
	 * @param id: die Id wird uebergeben.
	 * @return Die Shoppinglist mit der jeweiligen id wird zurueckgegeben.
	 */
	public Shoppinglist findById(int id) {
		Connection con = DBConnection.connection();

		Shoppinglist shoppinglist = new Shoppinglist();

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

		return shoppinglist;
	}
	/**
	 * Shoppinglist mittels Shoppinglist Namen finden 
	 * 
	 * @param name: Uebergabe des Namens einer Gruppe in Form eines Strings
	 * 
	 * @return Gruppe(n) mit dem entsprechenden Namen 
	 */
	public ArrayList<Shoppinglist> findByName (String name){
		
		Connection con = DBConnection.connection();
		ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT ...");

			while (rs.next()) {

				Shoppinglist shoppinglist = new Shoppinglist();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return shoppinglists;
		
	}
}
