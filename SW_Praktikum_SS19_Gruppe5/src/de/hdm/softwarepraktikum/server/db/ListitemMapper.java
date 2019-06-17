package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Mapper Klasse f�r </code>Listitem</code> Objekte. Diese umfasst Methoden um
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
	 * @return Listitemmapper
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
	 * @return ArrayList<Listitem>
	 */
	public ArrayList<Listitem> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<Listitem> listitems = new ArrayList<Listitem>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM listitems");

			while (rs.next()) {
				Listitem li = new Listitem();
				li.setId(rs.getInt("id"));
				li.setCreationDate(rs.getDate("creationDate"));
				li.setAmount(rs.getFloat("amount"));
				li.setStandard(rs.getBoolean("isStandard"));
				li.setProductID(rs.getInt("product_id"));
				li.setShoppinglistID(rs.getInt("shoppinglist_id"));
				li.setListitemUnitID(rs.getInt("unit_id"));
				li.setGroupID(rs.getInt("usergroup_id"));
				li.setRetailerID(rs.getInt("retailer_id"));
				li.setArchived(rs.getBoolean("isArchived"));
				listitems.add(li);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listitems;

	}

	/**
	 * Listitem mithilfe der Id finden.
	 *
	 * @param id
	 * @return Listitem-Objekt
	 */
	public Listitem findById(int id) {

		Connection con = DBConnection.connection();
		Listitem li = new Listitem();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM listitems WHERE id = " + id);

			if (rs.next()) {
				li.setId(rs.getInt("id"));
				li.setCreationDate(rs.getDate("creationDate"));
				li.setAmount(rs.getFloat("amount"));
				li.setStandard(rs.getBoolean("isStandard"));
				li.setProductID(rs.getInt("product_id"));
				li.setShoppinglistID(rs.getInt("shoppinglist_id"));
				li.setListitemUnitID(rs.getInt("unit_id"));
				li.setGroupID(rs.getInt("usergroup_id"));
				li.setRetailerID(rs.getInt("retailer_id"));
				li.setArchived(rs.getBoolean("isArchived"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return li;

	}

	/**
	 * Insert Methode, um eine neue Entitaet der Datenbank hinzuzufuegen.
	 *
	 * @param listitem
	 * @return Listitem-Objekt
	 */
	public Listitem insert(Listitem listitem) {
		
		Connection con = DBConnection.connection();

		try {
			
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM listitems");

			if (rs.next()) {
				listitem.setId(rs.getInt("maxid") + 1);
			}
			

			PreparedStatement pstmt = con.prepareStatement("INSERT INTO listitems "
					+ "(id, creationDate, amount, isStandard, product_id, shoppinglist_id, unit_id, usergroup_id, "
					+ "retailer_id, isArchived) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
				pstmt.setInt(1, listitem.getId());
				pstmt.setDate(2, (Date) listitem.getCreationDate());
				pstmt.setFloat(3, listitem.getAmount());
				pstmt.setBoolean(4, listitem.isStandard()); 
				pstmt.setInt(5, listitem.getProductID());
				pstmt.setInt(6, listitem.getShoppinglistID());
				pstmt.setInt(7, listitem.getListitemUnitID());
				pstmt.setInt(8, listitem.getGroupID());
				pstmt.setInt(9, listitem.getRetailerID());
				pstmt.setBoolean(10, listitem.isArchived());
				pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listitem;

	}

	/**
	 * Wiederholtes Schreiben / Aendern eines Objekts in die/der Datenbank.
	 *
	 * @param listitem
	 * @return Listitem-Objekt
	 */
	public Listitem update(Listitem listitem) {

		Connection con = DBConnection.connection();

		try {

			PreparedStatement pstmt = con.prepareStatement("UPDATE listitems SET amount = ? AND isStandard = ? "
					+ "AND isArchived = ? AND unit_id = ? AND retailer_id = ? WHERE id = ?");

				pstmt.setFloat(1, listitem.getAmount());
				pstmt.setBoolean(2, listitem.isStandard());
				pstmt.setBoolean(3, listitem.isArchived());
				pstmt.setInt(4, listitem.getListitemUnitID());
				pstmt.setInt(5, listitem.getRetailerID());
				pstmt.setInt(6, listitem.getId());
				pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listitem;

	}

	/**
	 * Delete Methode, um ein Listitem-Objekt aus der Datenbank zu entfernen.
	 *
	 * @param listitem
	 */
	public void delete(Listitem listitem) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM listitems WHERE id = " + listitem.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ausgeben des Amounts eines Listitems
	 * 
	 * @param listitem
	 * @return float amount
	 */
	@SuppressWarnings("null")
	public float getAmountOf (Listitem listitem) {
		
		Connection con = DBConnection.connection();
		float amount;

		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT amount FROM listitems WHERE id = " + listitem.getId());
			
			while(rs.next()) {
				amount = rs.getFloat("amount");
				return amount;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (Float) null; //return 0.0f;

	}
	
	/**
	 * 
	 * Produktname eines Eintrags finden.
	 * 
	 * @param listitem
	 * @return String productname
	 */
	
	public String getProductnameOf(int listitemId) {
		
		Connection con = DBConnection.connection();
		String productname;
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT listitems.id as listitem_id, "
					+ "products.name as product_name "
					+ "FROM products INNER JOIN listitems "
					+ "ON products.id = listitems.product_id "
					+ "WHERE listitems.id = " + listitemId);
			
			while(rs.next()) {
				productname = rs.getString("product_name");
				return productname;
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (String) null;
	}

	/**
	 * Methode, um alle Listitems einer Shoppingliste auszugeben.
	 * 
	 * @param shoppinglist
	 * @return ArrayList<Listitem>
	 */
	public ArrayList<Listitem> getListitemsOf(Shoppinglist shoppinglist) {
		
		Connection con = DBConnection.connection();
		ArrayList<Listitem> listitems = new ArrayList<Listitem>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM listitems "
					+ "WHERE shoppinglist_id = " + shoppinglist.getId()
					+ " AND isArchived = " + false);
			
			while(rs.next()) {

				Listitem li = new Listitem();
				li.setId(rs.getInt("id"));
				li.setCreationDate(rs.getDate("creationDate"));
				li.setAmount(rs.getFloat("amount"));
				li.setStandard(rs.getBoolean("isStandard"));
				li.setProductID(rs.getInt("product_id"));
				li.setShoppinglistID(rs.getInt("shoppinglist_id"));
				li.setListitemUnitID(rs.getInt("unit_id"));
				li.setGroupID(rs.getInt("usergroup_id"));
				li.setRetailerID(rs.getInt("retailer_id"));
				li.setArchived(rs.getBoolean("isArchived"));
				listitems.add(li);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listitems;

	}

	/**

	 * Methode, um alle archivierten Listitems einer Shoppingliste auszugeben.
	 * 
	 * @param shoppinglist
	 * @return ArrayList<Listitem>
	 */
	public ArrayList<Listitem> getArchivedListitemsOf(Shoppinglist shoppinglist) {

		Connection con = DBConnection.connection();
		ArrayList<Listitem> listitems = new ArrayList<Listitem>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * "
					+ "FROM shoppinglists INNER JOIN listitems " 
					+ "ON shoppinglists.listitem_id = listitems.id "
					+ "WHERE shoppinglists.id = " + shoppinglist.getId()
					+ " AND isArchived = " + true);

			while(rs.next()) {
				Listitem li = new Listitem();
				li.setId(rs.getInt("id"));
				li.setCreationDate(rs.getDate("creationDate"));
				li.setAmount(rs.getFloat("amount"));
				li.setStandard(rs.getBoolean("isStandard"));
				li.setProductID(rs.getInt("product_id"));
				li.setShoppinglistID(rs.getInt("shoppinglist_id"));
				li.setListitemUnitID(rs.getInt("unit_id"));
				li.setGroupID(rs.getInt("usergroup_id"));
				li.setRetailerID(rs.getInt("retailer_id"));
				li.setArchived(rs.getBoolean("isArchived"));
				listitems.add(li);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listitems;

	}
	
	/**
	 * Methode, um alle Listitems eines Retailers zu finden.
	 * 
	 * @param retailer
	 * @return ArrayList<Listitem>
	 */
	public ArrayList<Listitem> getListitemsOf(Retailer retailer) {

		Connection con = DBConnection.connection();
		ArrayList<Listitem> listitems = new ArrayList<Listitem>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * "
					+ "FROM listitems INNER JOIN retailers "
					+ "ON listitems.retailer_id = retailers.id " 
					+ "WHERE retailers.id = " + retailer.getId()
					+ " AND isArchived = " + false);

			while (rs.next()) {
				Listitem li = new Listitem();
				li.setId(rs.getInt("id"));
				li.setCreationDate(rs.getDate("creationDate"));
				li.setAmount(rs.getFloat("amount"));
				li.setStandard(rs.getBoolean("isStandard"));
				li.setProductID(rs.getInt("product_id"));
				li.setShoppinglistID(rs.getInt("shoppinglist_id"));
				li.setListitemUnitID(rs.getInt("unit_id"));
				li.setGroupID(rs.getInt("usergroup_id"));
				li.setRetailerID(rs.getInt("retailer_id"));
				li.setArchived(rs.getBoolean("isArchived"));
				listitems.add(li);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listitems;
		
	}

	/**
	 * 
	 * Methode, um Eintr�ge nach Produktnamen in einer Shoppingliste zu suchen.
	 * 
	 * @param shoppinglist
	 * @param productname
	 * @return ArrayList<Listitem>
	 */
	public ArrayList<Listitem> getListitemsByNameOf(Shoppinglist shoppinglist, String productname) {

		Connection con = DBConnection.connection();
		ArrayList<Listitem> listitems = new ArrayList<Listitem>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * "
					+ "FROM listitems INNER JOIN products "
					+ "ON listitems.product_id = products.id " 
					+ "WHERE listitems.shoppinglist_id = " + shoppinglist.getId() 
					+ " AND products.name = " + productname
					+ " AND isArchived = " + false);

			while (rs.next()) {
				Listitem li = new Listitem();
				li.setId(rs.getInt("id"));
				li.setCreationDate(rs.getDate("creationDate"));
				li.setAmount(rs.getFloat("amount"));
				li.setStandard(rs.getBoolean("isStandard"));
				li.setProductID(rs.getInt("product_id"));
				li.setShoppinglistID(rs.getInt("shoppinglist_id"));
				li.setListitemUnitID(rs.getInt("unit_id"));
				li.setGroupID(rs.getInt("usergroup_id"));
				li.setRetailerID(rs.getInt("retailer_id"));
				li.setArchived(rs.getBoolean("isArchived"));
				listitems.add(li);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listitems;
		
	}

	/**
	 * Methode, um die Standarteintr�ge einer Gruppe zu finden.
	 * 
	 * @param group
	 * @return ArrayList<Listitem>
	 */
	public ArrayList<Listitem> getStandardListitemsOf(Group group) {
		
		Connection con = DBConnection.connection();
		ArrayList<Listitem> listitems = new ArrayList<Listitem>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * "
					+ "FROM lisitems INNER JOIN usergroups " 
					+ "ON listitems.usergroup_id = usergroups.id "
					+ "WHERE usergroups.id = " + group.getId() 
					+ " AND listitems.isStandard = " + true
					+ " AND listitems.isArchived = " + false);

			while (rs.next()) {
				Listitem li = new Listitem();
				li.setId(rs.getInt("id"));
				li.setCreationDate(rs.getDate("creationDate"));
				li.setAmount(rs.getFloat("amount"));
				li.setStandard(rs.getBoolean("isStandard"));
				li.setProductID(rs.getInt("product_id"));
				li.setShoppinglistID(rs.getInt("shoppinglist_id"));
				li.setListitemUnitID(rs.getInt("unit_id"));
				li.setGroupID(rs.getInt("usergroup_id"));
				li.setRetailerID(rs.getInt("retailer_id"));
				li.setArchived(rs.getBoolean("isArchived"));
				listitems.add(li);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listitems;
	}

	/**
	 * Ein Listitem einer Gruppe als Standart setzen.
	 * 
	 * @param group
	 * @param listitem
	 */
	public void setStandardListitemOf (Group group, Listitem listitem) {
		
		Connection con = DBConnection.connection();
	
		try { 
			
			PreparedStatement pstmt = con.prepareStatement("UPDATE listitems SET isStandard = " + true
					+ " WHERE usergroup_id = ? and id = ?");

			pstmt.setFloat(1, group.getId());
			pstmt.setInt(2, listitem.getId());
			pstmt.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Eine Shoppingliste anhand eines Usernamens filtern.
	 * 
	 * @param shoppinglistId
	 * @param usernameId
	 * @return ArrayList<Listitem>
	 */
	public ArrayList<Listitem> filterShoppinglistByUser(int shoppinglistId,  int userId){
		
		Connection con = DBConnection.connection();
		ArrayList<Listitem> listitems = new ArrayList<Listitem>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * "
					+ "FROM responsibilities INNER JOIN listitems "
					+ "ON responsibilities.retailer_id = listitems.retailer_id "
					+ "WHERE listitems.shoppinglist_id = " + shoppinglistId 
					+ " AND responsibilities.user_id = " + userId
					+ " AND listitems.isArchieved = " + false);

			while (rs.next()){
			        Listitem li = new Listitem();
			        li.setId(rs.getInt("id"));
			        li.setCreationDate(rs.getDate("creationDate"));
			        li.setAmount(rs.getFloat("amount"));
			        li.setStandard(rs.getBoolean("isStandard"));
			       	li.setProductID(rs.getInt("product_id"));
					li.setShoppinglistID(rs.getInt("shoppinglist_id"));
					li.setListitemUnitID(rs.getInt("unit_id"));
					li.setGroupID(rs.getInt("usergroup_id"));
					li.setRetailerID(rs.getInt("retailer_id"));
					li.setArchived(rs.getBoolean("isArchived"));
			        listitems.add(li);		   
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listitems;
	}
	
	/**
	 * Eine Shoppingliste anhand eines Retailers filtern.
	 * 
	 * @param shoppinglist
	 * @param retailername
	 * @return ArrayList<Listitem>
	 */

	public ArrayList<Listitem> filterShoppinglistByRetailer(int shoppinglistId, int retailerId){
		
		Connection con = DBConnection.connection();
		ArrayList<Listitem> listitems = new ArrayList<Listitem>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * "
					+ "FROM listitems INNER JOIN retailers "
					+ "ON listitems.retailer_id = retailers.id "
					+ "WHERE listitems.shoppinglist_id = " + shoppinglistId 
					+ " AND retailers.retailername = " + retailerId
					+ " AND listitems.isArchieved = " + false);

			while (rs.next()) {	 
			        Listitem li = new Listitem();
			        li.setId(rs.getInt("id"));
			        li.setCreationDate (rs.getDate("creationDate"));
			        li.setAmount(rs.getFloat("amount"));
			        li.setStandard(rs.getBoolean("isStandard"));
					li.setProductID(rs.getInt("product_id"));
					li.setShoppinglistID(rs.getInt("shoppinglist_id"));
					li.setListitemUnitID(rs.getInt("unit_id"));
					li.setGroupID(rs.getInt("usergroup_id"));
					li.setRetailerID(rs.getInt("retailer_id"));
					li.setArchived(rs.getBoolean("isArchived"));
			        listitems.add(li);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listitems;
	}

}
