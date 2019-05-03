package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.server.bo.Listitem;
import de.hdm.softwarepraktikum.server.bo.Shoppinglist;
import de.hdm.softwarepraktikum.server.bo.User;
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
	 * @param name: Uebergabe des Namens einer Shoppinglist in Form eines Strings
	 * 
	 * @return Gruppe(n) mit dem entsprechenden Namen
	 */
	public ArrayList<Shoppinglist> findByName(String name) {

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
	 * Insert Methode um der Datenbank eine neue Entitaet hinzuzufuegen.
	 * 
	 * @param shoppinglist: Die Shoppinglist wird uebergeben.
	 * @return Die aktualisierte Shoppinglist wird zurueckgegeben.
	 */

	public Shoppinglist insert(Shoppinglist shoppinglist) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT ... ");

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
		return shoppinglist;

	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param shoppinglist: die Shoppinglist wird uebergeben.
	 * @return Die aktualisierte Shoppinglist wird zurueckgegeben.
	 */
	public Shoppinglist update(Shoppinglist shoppinglist) {
		Connection con = DBConnection.connection();

		try {

			// Setzt den AutoCommit auf false, um das sichere Schreiben in die Datenbank zu
			// gewährleisten.
			con.setAutoCommit(false);

			PreparedStatement stmt = con.prepareStatement("UPDATE ...");

			// vervollständigen

			// Wenn alle Statements fehlerfrei ausgeführt wurden, wird commited.
			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return shoppinglist;
	}

	/**
	 * Loeschen einer Shoppinglist aus der Datenbank.
	 * 
	 * @param shoppinglist: Die Shoppinglist wird uebergeben 
	 */
	public void delete(Shoppinglist shoppinglist) {

		Connection con = DBConnection.connection();
	

		try {
			//Setzt den AutoCommit auf false, um das sichere Schreiben in die Datenbank zu gewährleisten.
			con.setAutoCommit(false);
			
			Statement stmt = con.createStatement();
			stmt.executeUpdate("");

			//vervollständigen
			
			//Wenn alle Statements fehlerfrei ausgeführt wurden, wird commited.
			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Shoppinglist getShoppinglistOf(Listitem listitem) {

		Connection con = DBConnection.connection();
		Shoppinglist shoppinglist = new Shoppinglist();

		try {

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shoppinglist;
	}
	
	public ArrayList<Shoppinglist> getShoppinglistsOf(Group group) {
		
		Connection con = DBConnection.connection();
		ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();
		
		try {
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return shoppinglists;
	}
	
	public ArrayList<Shoppinglist> getShoppinglistsOf(User user){
		
		Connection con = DBConnection.connection();
		ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();
		
		try {
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return shoppinglists;
	}
}
