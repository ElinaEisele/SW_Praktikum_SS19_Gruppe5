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
 * Mapper Klasse fuer </code>Listitem</code> Objekte. Diese umfasst Methoden um
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
			
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE listitems SET amount = " + listitem.getAmount() 
			+ ", isStandard = " + listitem.isStandard()
			+ ", isArchived = " + listitem.isArchived()
			+ ", unit_id = " + listitem.getListitemUnitID()
			+ ", retailer_id = " + listitem.getRetailerID()
			+ " WHERE id = " + listitem.getId());

				
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
	 * Alle Listeneintraege einer Gruppe finden
	 * 
	 * @param group
	 * @return ArrayList<Listitem>
	 */
	public ArrayList<Listitem> getListitemsOf(Group group) {

		Connection con = DBConnection.connection();
		ArrayList<Listitem> listitems = new ArrayList<Listitem>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * "
					+ "FROM listitems "
					+ "WHERE usergroup_id = " + group.getId()
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
 	 * Methode, um alle Daten von Listitem-Objekten für den CellTable auszugeben.
	 * 
	 * @param shoppinglist
	 * @return ArrayList<String>
	 */
	public Map<Listitem, ArrayList<String>> getListitemData(Shoppinglist shoppinglist){
		Connection con = DBConnection.connection();
		Map<Listitem, ArrayList<String>> listitemMap = new HashMap<Listitem, ArrayList<String>>();

 		ArrayList<Listitem> listitemArrayList = new ArrayList<Listitem>();

 		try {
			Statement stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM listitems WHERE listitems.shoppinglist_id = " 
			+ shoppinglist.getId() + " AND isArchived = " + false);

 			while(rs1.next()) {
				Listitem l = new Listitem();
				l.setId(rs1.getInt("id"));
				l.setCreationDate(rs1.getDate("creationDate"));
				l.setAmount(rs1.getFloat("amount"));
				l.setStandard(rs1.getBoolean("isStandard"));
				l.setProductID(rs1.getInt("product_id"));
				l.setShoppinglistID(rs1.getInt("shoppinglist_id"));
				l.setListitemUnitID(rs1.getInt("unit_id"));
				l.setGroupID(rs1.getInt("usergroup_id"));
				l.setRetailerID(rs1.getInt("retailer_id"));
				l.setArchived(rs1.getBoolean("isArchived"));
				listitemArrayList.add(l);
			}

 			Statement stmt2 = con.createStatement();

 			for(Listitem l : listitemArrayList) {
 				
 				ArrayList<String> stringArrayList = new ArrayList<String>();
				
 				ResultSet rs2 = stmt2.executeQuery("SELECT name FROM products WHERE id = " + l.getProductID());	

 				if(rs2.next()) {
 					
 					Product p = new Product();
					p.setName(rs2.getString("name"));
					stringArrayList.add(p.getName());		

 				}	

 				ResultSet rs3 = stmt2.executeQuery("SELECT name FROM retailers WHERE id = " + l.getRetailerID());

 				if(rs3.next()) {

 				Retailer r = new Retailer();
				r.setName(rs3.getString("name"));
				stringArrayList.add(r.getName());

 				}

 				ResultSet rs4 = stmt2.executeQuery("SELECT name FROM units WHERE id = " + l.getListitemUnitID());

 				if(rs4.next()) {
 					
 				ListitemUnit u = new ListitemUnit();
				u.setName(rs4.getString("name"));
				stringArrayList.add(u.getName());
				
 				}
 				
 				ResultSet rs5 = stmt2.executeQuery("SELECT amount FROM listitems WHERE id = " + l.getId());

 				if(rs5.next()) {
 				Float f = rs5.getFloat("amount");
 				stringArrayList.add(f.toString());
 
 				}
 				
 				listitemMap.put(l, stringArrayList);
 			}				

 		} catch (SQLException e) {
			e.printStackTrace();
		}
 		
		return listitemMap;
		
	}
	
	/**
	 * 
	 * Methode, um Eintraege nach Produktnamen in einer Shoppingliste zu suchen.
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
	 * Methode, um die Standarteintraege einer Gruppe zu finden.
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
					+ "FROM listitems INNER JOIN usergroups " 
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
	public Map<Listitem, ArrayList<String>> filterShoppinglistByUser(int shoppinglistId,  int userId){
		
		Connection con = DBConnection.connection();
		Map<Listitem, ArrayList<String>> listitemMap = new HashMap<Listitem, ArrayList<String>>();

 		ArrayList<Listitem> listitemArrayList = new ArrayList<Listitem>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * "
					+ "FROM responsibilities INNER JOIN listitems "
					+ "ON responsibilities.retailer_id = listitems.retailer_id "
					+ "WHERE listitems.shoppinglist_id = " + shoppinglistId 
					+ " AND responsibilities.shoppinglist_id = " + shoppinglistId
					+ " AND responsibilities.user_id = " + userId
					+ " AND listitems.isArchived = " + false);

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
					listitemArrayList.add(li);	   
			}

			Statement stmt2 = con.createStatement();

 			for(Listitem l : listitemArrayList) {
 				
 				ArrayList<String> stringArrayList = new ArrayList<String>();
				
 				ResultSet rs2 = stmt2.executeQuery("SELECT name FROM products WHERE id = " + l.getProductID());	

 				if(rs2.next()) {
 					
 					Product p = new Product();
					p.setName(rs2.getString("name"));
					stringArrayList.add(p.getName());		

 				}	

 				ResultSet rs3 = stmt2.executeQuery("SELECT name FROM retailers WHERE id = " + l.getRetailerID());

 				if(rs3.next()) {

 				Retailer r = new Retailer();
				r.setName(rs3.getString("name"));
				stringArrayList.add(r.getName());

 				}

 				ResultSet rs4 = stmt2.executeQuery("SELECT name FROM units WHERE id = " + l.getListitemUnitID());

 				if(rs4.next()) {
 					
 				ListitemUnit u = new ListitemUnit();
				u.setName(rs4.getString("name"));
				stringArrayList.add(u.getName());
				
 				}
 				
 				ResultSet rs5 = stmt2.executeQuery("SELECT amount FROM listitems WHERE id = " + l.getId());

 				if(rs5.next()) {
 				Float f = rs5.getFloat("amount");
 				stringArrayList.add(f.toString());
 
 				}
 				
 				listitemMap.put(l, stringArrayList);
 			}				
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listitemMap;
	}
	
	/**
	 * Eine Shoppingliste anhand eines Retailers filtern.
	 * 
	 * @param shoppinglistId
	 * @param retailerId
	 * @return ArrayList<Listitem>
	 */

	public Map<Listitem, ArrayList<String>> filterShoppinglistByRetailer(int shoppinglistId, int retailerId){

		Connection con = DBConnection.connection();
		Map<Listitem, ArrayList<String>> listitemMap = new HashMap<Listitem, ArrayList<String>>();

 		ArrayList<Listitem> listitemArrayList = new ArrayList<Listitem>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * "
					+ "FROM listitems INNER JOIN retailers "
					+ "ON listitems.retailer_id = retailers.id "
					+ "WHERE listitems.shoppinglist_id = " + shoppinglistId 
					+ " AND retailers.id = " + retailerId
					+ " AND listitems.isArchived = " + false);

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
					listitemArrayList.add(li);
			}

			Statement stmt2 = con.createStatement();

 			for(Listitem l : listitemArrayList) {
 				
 				ArrayList<String> stringArrayList = new ArrayList<String>();
				
 				ResultSet rs2 = stmt2.executeQuery("SELECT name FROM products WHERE id = " + l.getProductID());	

 				if(rs2.next()) {
 					
 					Product p = new Product();
					p.setName(rs2.getString("name"));
					stringArrayList.add(p.getName());		

 				}	

 				ResultSet rs3 = stmt2.executeQuery("SELECT name FROM retailers WHERE id = " + l.getRetailerID());

 				if(rs3.next()) {

 				Retailer r = new Retailer();
				r.setName(rs3.getString("name"));
				stringArrayList.add(r.getName());

 				}

 				ResultSet rs4 = stmt2.executeQuery("SELECT name FROM units WHERE id = " + l.getListitemUnitID());

 				if(rs4.next()) {
 					
 				ListitemUnit u = new ListitemUnit();
				u.setName(rs4.getString("name"));
				stringArrayList.add(u.getName());
				
 				}
 				
 				ResultSet rs5 = stmt2.executeQuery("SELECT amount FROM listitems WHERE id = " + l.getId());

 				if(rs5.next()) {
 				Float f = rs5.getFloat("amount");
 				stringArrayList.add(f.toString());
 
 				}
 				
 				listitemMap.put(l, stringArrayList);
 			}				
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listitemMap;
	}

}
