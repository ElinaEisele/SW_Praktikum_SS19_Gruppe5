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
 * Mapper-Klasse, die <code>Product</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * @author CarlaHofmann
 */

public class ProductMapper {

	/**
	 * Die Klasse ListitemMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	 * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 */
	private static ProductMapper productMapper = null;

	/**
	 * Geschuetzter Konstruktor verhindert weitere Instanziierungen dieser Klasse.
	 */
	protected ProductMapper() {
	}

	/**
     * Diese statische Methode kann aufgrufen werden durch
     * <code>ProductMapper.productMapper()</code>. Sie stellt die
     * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
     * Instanz von <code>ProductMapper</code> existiert.
	 *
	 * @return Productmapper
	 */
	public static ProductMapper productMapper() {
		if (productMapper == null) {
			productMapper = new ProductMapper();
		}
		return productMapper;
	}

	/**
	 * Auslesen aller <code>Product<code>-Objekte.
	 *
	 * @return ArrayList<Product>
	 */
	public ArrayList<Product> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<Product> products = new ArrayList<Product>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM products");

			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getInt("id"));
				p.setCreationDate(rs.getDate("creationDate"));
				p.setName(rs.getString("name"));
				products.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return products;
	}

	/**
	 * Suchen eines <code>Product<code>-Objekts mit vorgegebener Id. 
	 * Da diese eindeutig ist, wird genau ein Objekt zurueckgegeben.
	 *
	 * @param id
	 * @return Product-Objekt
	 */
	public Product findById(int id) {

		Connection con = DBConnection.connection();
		Product p = new Product();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM products WHERE id = " + id);

			if (rs.next()) {
				p.setId(rs.getInt("id"));
				p.setCreationDate(rs.getDate("creationDate"));
				p.setName(rs.getString("name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return p;
	}

	/**
	 * Suchen aller <code>Product<code>-Objekte mit vorgegebenem Namen. 
	 * 
	 * @param name
	 * @return ArrayList<Product>
	 */
	public ArrayList<Product> findByName(String name) {

		Connection con = DBConnection.connection();
		ArrayList<Product> products = new ArrayList<Product>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM products WHERE name = '" + name + "'");

			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getInt("id"));
				p.setCreationDate(rs.getDate("creationDate"));
				p.setName(rs.getString("name"));
				products.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return products;
	}

	/**
	 * Einfügen eines <code>Product</code>-Objekts in die Datenbank. 
	 * Dabei wird auch der Primaerschlüssel des übergebenen Objekts 
	 * geprüft und ggf. berichtigt.
	 *
	 * @param product
	 * @return Product-Objekt
	 */
	public Product insert(Product product) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM products");

			if (rs.next()) {
				product.setId(rs.getInt("maxid") + 1);
			}

			PreparedStatement pstmt = con.prepareStatement("INSERT INTO products (id, creationDate, name) "
					+ "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

				pstmt.setInt(1, product.getId());
				java.sql.Date sqlDate = new java.sql.Date(product.getCreationDate().getTime());
				pstmt.setDate(2, sqlDate);
				pstmt.setString(3, product.getName());
				pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return product;
	}

	/**
	 * Wiederholtes Schreiben eines <code>Product<code>-Objekts in die Datenbank.
	 *
	 * @param product
	 * @return Product-Objekt
	 */
	public Product update(Product product) {

		Connection con = DBConnection.connection();

		try {
			PreparedStatement pstmt = con.prepareStatement("UPDATE products SET name = ? WHERE id = ?");
			
				pstmt.setString(1, product.getName());
				pstmt.setInt(2, product.getId());
				pstmt.executeUpdate();	
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
		return product;
	}

	/**
	 * Loeschen der Daten eines 
	 * <code>Listitem</code>-Objekts aus der Datenbank.
	 *
	 * @param product
	 */
	
	public void delete(Product product) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM products WHERE id = " + product.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Auslesen eines zugehoerigen <code>Product<code>-Objekts 
	 * eines gegebenen <code>Listitem<code>-Objekts.
	 * 	
	 * @param listitem
	 * @return Product-Objekt
	 */
	public Product getProductOf(Listitem listitem) {

		Connection con = DBConnection.connection();
		Product p = new Product();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT products.id AS product_id, "
					+ "products.creationDate AS product_creationDate, "
					+ "products.name AS product_name "
					+ "FROM listitems INNER JOIN products "
					+ "ON listitems.product_id = products.id "
					+ "WHERE listitems.id = " + listitem.getId());

			if (rs.next()) {
				p.setId(rs.getInt("product_id"));
				p.setCreationDate(rs.getDate("product_creationDate"));
				p.setName(rs.getString("product_name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	

}
