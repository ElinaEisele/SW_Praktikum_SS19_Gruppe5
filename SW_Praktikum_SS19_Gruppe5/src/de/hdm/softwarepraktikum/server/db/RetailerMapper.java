package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Mapper Klasse fuer </code>Retailer</code> Objekte. Diese umfasst Methoden um
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
	 * Geschuetzter Konstrukter verhindert weitere Instanzierungen von RetailerMapper-Objekten
	 */
	protected RetailerMapper() {
	}

	/**
	 * Sicherstellung der Singleton Eigenschaft der Mapperklasse
	 * 
	 * @return Retailermapper
	 */

	public static RetailerMapper retailerMapper() {
		if (retailerMapper == null) {
			retailerMapper = new RetailerMapper();
		}
		return retailerMapper;
	}

	/**
	 * Ausgabe einer Liste aller Retailer-Objekte
	 * 
	 * @return ArrayList<Retailer>
	 */
	public ArrayList<Retailer> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<Retailer> retailers = new ArrayList<Retailer>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM retailers ORDER BY id");

			while (rs.next()) {
				Retailer r = new Retailer();
				r.setId(rs.getInt("id"));
				r.setCreationDate(rs.getDate("creationDate"));
				r.setName(rs.getString("name"));
				retailers.add(r);
			}
			return retailers;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Methode um einen Retailer mithilfe seiner Id zu finden
	 * 
	 * @param id
	 * @return Retailer-Objekt
	 */
	public Retailer findById(int id) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM retailers WHERE id = " + id);

			if (rs.next()) {
				Retailer r = new Retailer();
				r.setId(rs.getInt("id"));
				r.setCreationDate(rs.getDate("creationDate"));
				r.setName(rs.getString("name"));
				return r;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Retailer mithilfe ihrer Namen finden
	 * 
	 * @param name
	 * @return ArrayList<Retailer> 
	 * 
	 */
	public ArrayList<Retailer> findByName(String name) {

		Connection con = DBConnection.connection();
		ArrayList<Retailer> retailers = new ArrayList<Retailer>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM retailers WHERE name = '" + name + "'");

			while (rs.next()) {
				Retailer r = new Retailer();
				r.setId(rs.getInt("id"));
				r.setCreationDate(rs.getDate("creationDate"));
				r.setName(rs.getString("name"));
				retailers.add(r);
			}

			return retailers;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Insert Methode um der Datenbank eine neue Entitaet hinzuzufuegen
	 * 
	 * @param retailer
	 * @return Retailer-Objekt
	 */

	public Retailer insert(Retailer retailer) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM retailers ");

			if (rs.next()) {
				retailer.setId(rs.getInt("maxid") + 1);
			}

			PreparedStatement pstmt = con.prepareStatement("INSERT INTO retailers (id, creationDate, name) VALUES (?, ?, ?) ", 
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, retailer.getId());
			pstmt.setDate(2, (Date) retailer.getCreationDate());
			pstmt.setString(3, retailer.getName());
			pstmt.executeUpdate();
			return retailer;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank
	 * 
	 * @param retailer
	 * @return Retailer-Objekt
	 */
	public Retailer update(Retailer retailer) {

		Connection con = DBConnection.connection();

		try {

			PreparedStatement pstmt = con.prepareStatement("UPDATE retailers SET name = ? WHERE id = ?");

			pstmt.setString(1, retailer.getName());
			pstmt.setInt(2, retailer.getId());
			pstmt.executeUpdate();
			return retailer;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Loeschen eines Retailers aus der Datenbank
	 * 
	 * @param retailer
	 */
	public void delete(Retailer retailer) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM retailers WHERE id = " + retailer.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * Eine neue Zuweisung erstellen
	 * 
	 * @param retailer_id
	 * @param user_id
	 * @param shoppinglist_id
	 */
	public void insertResponsibility(int retailerId, int userId, int shoppinglistId) {
		
		Connection con = DBConnection.connection();

		try {

			PreparedStatement pstmt = con.prepareStatement("INSERT INTO responsibilities (retailer_id, user_id, shoppinglist_id) "
					+ "VALUES (?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, retailerId);
			System.out.println("1: "+retailerId);
			pstmt.setInt(2, userId);
			System.out.println("2: "+userId);
			pstmt.setInt(3, shoppinglistId);
			System.out.println("3: "+shoppinglistId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Finden des Retailers eines Listitems
	 * 
	 * @param listitem
	 * @return Retailer
	 */

	public Retailer getRetailerOf(Listitem listitem) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT retailers.id, retailers.creationDate, retailers.name "
							+ "FROM listitems INNER JOIN retailers "
							+ "ON listitems.retailer_Id = retailers.id "
							+ "WHERE listitem_id = " + listitem.getId());

			if (rs.next()) {
				Retailer r = new Retailer();
				r.setId(rs.getInt("id"));
				r.setCreationDate(rs.getDate("creationDate"));
				r.setName(rs.getString("name"));
				return r;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * Finden der zugeordneten Retailer eines Users
	 * 
	 * @param user
	 * @return ArrayList<Retailer>
	 */

	public ArrayList<Retailer> getRetailersOf(User user) {

		Connection con = DBConnection.connection();
		ArrayList<Retailer> retailers = new ArrayList<Retailer>();

		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT retailers.id, retailers.creationDate, retailers.name "
					+ "FROM responsibilities INNER JOIN retailers "
					+ "ON responsibilities.retailer_id = retailers.id "
					+ "WHERE user_id = " + user.getId());

			while (rs.next()) {
				Retailer r = new Retailer();
				r.setId(rs.getInt("id"));
				r.setCreationDate(rs.getDate("creationDate"));
				r.setName(rs.getString("name"));
				retailers.add(r);
			}	
			return retailers;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	/**
	 * 
	 * Retailer, welche in einer Shoppingliste verwendet werden, finden
	 * 
	 * @param shoppinglist
	 * @return ArrayList<Retailer>
	 */
	public ArrayList<Retailer> getRetailersOf(Shoppinglist shoppinglist){
	
		Connection con = DBConnection.connection();
		ArrayList<Retailer> retailers = new ArrayList<Retailer>();

		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM listitems INNER JOIN retailers "
					+ "ON listitems.retailer_id = retailers.id "
					+ "WHERE shoppinglist_id = " + shoppinglist.getId());

			while (rs.next()) {
				Retailer r = new Retailer();
				r.getId();
				r.getCreationDate();
				r.getName();
				retailers.add(r);
			}	
			return retailers;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	/**
	 * 
	 * Alle Retailer einer Shoppingliste finden, welche einem User zugeordnet sind
	 * 
	 * @param shoppinglist
	 * @param user
	 * @return ArrayList<Retailer>
	 */
	
	public ArrayList<Retailer> getAssignedRetailersOf(Shoppinglist shoppinglist, User user){
		
		Connection con = DBConnection.connection();
		ArrayList<Retailer> retailers = new ArrayList<Retailer>();

		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM responsibilities INNER JOIN retailers "
					+ "ON responsibilities.retailer_id = retailers.id "
					+ "WHERE shoppinglist_id = " + shoppinglist.getId() + " and user_id = " + user.getId());
			

			while (rs.next()) {
				Retailer r = new Retailer();
				r.setId(rs.getInt("id"));
				r.setCreationDate(rs.getDate("creationDate"));
				r.setName(rs.getString("name"));
				retailers.add(r);
			}
			
			return retailers;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
