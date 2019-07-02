package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Mapper-Klasse, die <code>Retailer</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * @author LeoniFriedrich & CarlaHofmann
 *
 */

public class RetailerMapper {

	/**
	 * Die Klasse ListitemMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	 * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 */
	private static RetailerMapper retailerMapper = null;

	/**
	 * Geschuetzter Konstruktor verhindert weitere Instanziierungen dieser Klasse.
	 */
	protected RetailerMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
     * <code>RetailerMapper.retailerMapper()</code>. Sie stellt die
     * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
     * Instanz von <code>RetailerMapper</code> existiert.
	 * 
	 * @return retailerMapper
	 */

	public static RetailerMapper retailerMapper() {
		if (retailerMapper == null) {
			retailerMapper = new RetailerMapper();
		}
		return retailerMapper;
	}

	/**
	 * Auslesen aller <code>Retailer<code>-Objekte.
	 * 
	 * @return ArrayList<Retailer>
	 */
	public ArrayList<Retailer> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<Retailer> retailers = new ArrayList<Retailer>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM retailers");

			while (rs.next()) {
				Retailer r = new Retailer();
				r.setId(rs.getInt("id"));
				r.setCreationDate(rs.getDate("creationDate"));
				r.setName(rs.getString("name"));
				retailers.add(r);
			}

		} catch (SQLException e) {
			e.printStackTrace();	
		}
		
		return retailers;
	}

	/**
	 * Suchen eines <code>Retailer<code>-Objekts mit vorgegebener Id. 
	 * Da diese eindeutig ist, wird genau ein Objekt zurueckgegeben.
	 *
	 * @param id
	 * @return Retailer-Objekt
	 */
	public Retailer findById(int id) {
		
		Connection con = DBConnection.connection();
		Retailer r = new Retailer();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM retailers WHERE id = " + id);

			if (rs.next()) {
				r.setId(rs.getInt("id"));
				r.setCreationDate(rs.getDate("creationDate"));
				r.setName(rs.getString("name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return r;

	}

	/**
	 * Suchen aller <code>Retailer<code>-Objekte mit vorgegebenem Namen. 
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

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retailers;
	}

	/**
	 * Einfügen eines <code>Retailer</code>-Objekts in die Datenbank. 
	 * Dabei wird auch der Primärschlüssel des übergebenen Objekts 
	 * geprüft und ggf. berichtigt.
	 *
	 * @param retailer
	 * @return Retailer-Objekt
	 */

	public Retailer insert(Retailer retailer) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM retailers");

			if (rs.next()) {
				retailer.setId(rs.getInt("maxid") + 1);
			}

			PreparedStatement pstmt = con.prepareStatement("INSERT INTO retailers (id, creationDate, name) "
					+ "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

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
	 * Wiederholtes Schreiben eines <code>Retailer<code>-Objekts in die Datenbank.
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

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retailer;
	}

	/**
	 * Loeschen der Daten eines 
	 * <code>Retailer</code>-Objekts aus der Datenbank.
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
	 * Hinzufuegen einer neuen Verantwortlichkeit eines Users einer Shoppingliste 
	 * bezogen auf einen bestimmten Händler anhand den jeweiligen Ids.
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
			pstmt.setInt(2, userId);
			pstmt.setInt(3, shoppinglistId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loeschen einer Verantwortlichkeit anhand eines gegebenen <code>Retailer<code>-Objekts
	 * und eines gegebenen <code>Shoppinglist<code>-Objekts.
	 * 
	 * @param retailerId 
	 * @param userId 
	 * @param shoppinglistId 
	 */
	public void deleteResponsibility(Retailer retailer, Shoppinglist shoppinglist) {
		
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM responsibilities WHERE retailer_id = " + retailer.getId() 
					+ " AND shoppinglist_id = " + shoppinglist.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Auslesen des zugehoerigen <code>Retailer<code>-Objekts eines
	 * gegebenen <code>Listitem<code>-Objekts.	
	 *
	 * @param listitem
	 * @return Retailer
	 */

	public Retailer getRetailerOf(Listitem listitem) {

		Connection con = DBConnection.connection();
		Retailer r = new Retailer();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT retailers.id AS retailer_id, "
					+ "retailers.creationDate AS retailer_creationDate, "
					+ "retailers.name AS retailer_name "
					+ "FROM listitems INNER JOIN retailers "
					+ "ON listitems.retailer_Id = retailers.id "
					+ "WHERE listitems.id = " + listitem.getId());

			if (rs.next()) {
				r.setId(rs.getInt("retailer_id"));
				r.setCreationDate(rs.getDate("retailer_creationDate"));
				r.setName(rs.getString("retailer_name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return r;
	}

	/**
	 * Auslesen des verantwortlichen <code>User<code>-Objekts 
	 * eines <code>Retailer<code>-Objekts.
	 * 
	 * 
	 * @param user
	 * @return ArrayList<Retailer>
	 */

	public ArrayList<Retailer> getRetailersOf(User user) {

		Connection con = DBConnection.connection();
		ArrayList<Retailer> retailers = new ArrayList<Retailer>();

		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT retailers.id AS retailer_id, "
					+ "retailers.creationDate AS retailer_creationDate, "
					+ "retailers.name AS retailer_name "
					+ "FROM responsibilities INNER JOIN retailers "
					+ "ON responsibilities.retailer_id = retailers.id "
					+ "WHERE user_id = " + user.getId());

			while (rs.next()) {
				Retailer r = new Retailer();
				r.setId(rs.getInt("retailer_id"));
				r.setCreationDate(rs.getDate("retailer_creationDate"));
				r.setName(rs.getString("retailer_name"));
				retailers.add(r);
			}	

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retailers;
	}
	
	
	/**
	 * Auslesen des zugehörigen <code>Retailer<code>-Objekts eines
	 * gegebenen <code>Shoppinglist<code>-Objekts.	
	 *
	 * @param shoppinglist
	 * @return ArrayList<Retailer>
	 */
	public ArrayList<Retailer> getRetailersOf(Shoppinglist shoppinglist){
	
		Connection con = DBConnection.connection();
		ArrayList<Retailer> retailers = new ArrayList<Retailer>();

		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * "
					+ "FROM retailers INNER JOIN listitems "
					+ "ON listitems.retailer_id = retailers.id "
					+ "WHERE shoppinglist_id = " + shoppinglist.getId());

			while (rs.next()) {
				Retailer r = new Retailer();
				r.setId(rs.getInt("id"));;
				r.setCreationDate(rs.getDate("creationDate"));
				r.setName(rs.getString("name"));

				if (!retailers.contains(r)) {
					retailers.add(r);
				}
			}	


		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retailers;
	}
	
	/**
	 * Auslesen aller <code>Retailer<code>-Objekte eines 
	 * <code>Shoppinglist<code>-Objekts mit der Zuordnung eines Verantwortlichen.
	 * 
	 * @param shoppinglist
	 * @return ArrayList<Retailer>
	 */
	public ArrayList<Retailer> getAssignedRetailersOf(Shoppinglist shoppinglist){
		
		Connection con = DBConnection.connection();
		ArrayList<Retailer> retailers = new ArrayList<Retailer>();

		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * "
					+ "FROM responsibilities INNER JOIN retailers "
					+ "ON responsibilities.retailer_id = retailers.id "
					+ "WHERE shoppinglist_id = " + shoppinglist.getId());

			while (rs.next()) {
				Retailer r = new Retailer();
				r.setId(rs.getInt("id"));
				r.setCreationDate(rs.getDate("creationDate"));
				r.setName(rs.getString("name"));
				retailers.add(r);
			}	

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retailers;
	}
	
	/**
	 * Auslesen aller <code>User<code>-Objekte eines <code>Shoppinglist<code>-Objekts 
	 * mit einer zugeordneten Verantwortlichkeit zu einem gegebenen <code>Retailer<code>-Objekt.
	 * 
	 * @param shoppinglist
	 * @param retailer
	 * @return User-Objekt
	 */
	public User getAssigndUserOf(Shoppinglist shoppinglist, Retailer retailer) {
		
		Connection con = DBConnection.connection();
		User u = new User();
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM responsibilities INNER JOIN users "
					+"ON responsibilities.user_id = users.id "
					+"WHERE responsibilities.retailer_id = " + retailer.getId()
					+" AND responsibilities.shoppinglist_id = " + shoppinglist.getId());
			
			if(rs.next()) {
				u.setId(rs.getInt("id"));
				u.setCreationDate(rs.getDate("creationDate"));
				u.setName(rs.getString("name"));
				u.setGmailAddress(rs.getString("gMail"));
			}

		} catch (SQLException e){
			e.printStackTrace();
	
		}
		
		return u;
	}
	
	/**
	 * Auslesen aller <code>User<code>-Objekte eines <code>Shoppinglist<code>-Objekts 
	 * mit einer zugeordneten Verantwortlichkeit.
	 * 
	 * @param shoppinglist
	 * @return ArrayList<User>
	 */
	public ArrayList<User> getAssigndUsersOf(Shoppinglist shoppinglist){
		
		Connection con = DBConnection.connection();
		ArrayList<User> users = new ArrayList<User>();
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM responsibilities INNER JOIN users "
					+ "ON responsibilities.user_id = users.id "
					+ "WHERE responsibilities.shoppinglist_id = " + shoppinglist.getId());
			
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setCreationDate(rs.getDate("creationDate"));
				u.setName(rs.getString("name"));
				u.setGmailAddress(rs.getString("gMail"));
				
				if (!users.contains(u)) {
					users.add(u);
				}
			}
			
		}catch(SQLException e) {
			e.getStackTrace();
		}
		return users;
	}
	
	
	/**
	 * Auslesen aller Verantwortlichkeiten eines <code>Shoppinglist<code>-Objekts.
	 * 
	 * @param shoppinglist
	 * @return Map<String, String>
	 */
	public Map<String, String> getAllocationsOf(Shoppinglist shoppinglist){
				
		Connection con = DBConnection.connection();
		Map<String, String> allocations = new HashMap<String, String>();
		ArrayList<Retailer> retailers = new ArrayList<Retailer>();
		
		try {
			Statement stmt1 = con.createStatement();
			ResultSet rs1 = stmt1.executeQuery("SELECT * FROM responsibilities INNER JOIN retailers "
					+ "ON responsibilities.retailer_id = retailers.id "
					+ "WHERE responsibilities.shoppinglist_id = " + shoppinglist.getId());
		
			while (rs1.next()) {
				
				Retailer r = new Retailer();
				r.setId(rs1.getInt("id"));
				r.setCreationDate(rs1.getDate("creationDate"));
				r.setName(rs1.getString("name"));
				retailers.add(r);
				
			
			}
			
			Statement stmt2 = con.createStatement();
			
			for (Retailer r : retailers) {
				ResultSet rs2 = stmt2.executeQuery("SELECT * FROM responsibilities INNER JOIN users "
						+ "ON responsibilities.user_id = users.id "
						+ "WHERE responsibilities.shoppinglist_id = " + shoppinglist.getId()
						+ " AND responsibilities.retailer_id = " + r.getId());
								
				if (rs2.next()) {
					User u = new User();
					u.setId(rs2.getInt("id"));
					u.setCreationDate(rs2.getDate("creationDate"));
					u.setName(rs2.getString("name"));
					u.setGmailAddress(rs2.getString("gMail"));
					
					
					allocations.put(r.getName(), u.getName());
				}
			}
			
		} catch (SQLException e) {
			e.getStackTrace();
		}
	
		return allocations;
	}
	
	/**
	 * Auslesen aller <code>Retailer<code>-Objekte eines <code>Shoppinglist<code>-Objekts
	 * mit einer zugeordneten Verantwortlichkeit.
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
			ResultSet rs = stmt.executeQuery("SELECT * "
					+ "FROM responsibilities INNER JOIN retailers "
					+ "ON responsibilities.retailer_id = retailers.id "
					+ "WHERE shoppinglist_id = " + shoppinglist.getId() 
					+ " AND user_id = " + user.getId());
			
			while (rs.next()) {
				Retailer r = new Retailer();
				r.setId(rs.getInt("id"));
				r.setCreationDate(rs.getDate("creationDate"));
				r.setName(rs.getString("name"));
				retailers.add(r);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
		return retailers;
	}
}
