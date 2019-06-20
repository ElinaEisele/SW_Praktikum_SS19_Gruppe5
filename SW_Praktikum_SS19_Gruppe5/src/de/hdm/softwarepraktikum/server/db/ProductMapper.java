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
 * Mapper Klasse fuer </code>Product</code> Objekte. Diese umfasst Methoden um
 * Produkt-Objekte zu erstellen, zu suchen, zu modifizieren und zu loeschen. Das
 * Mapping funktioniert dabei bidirektional. Es koennen Objekte in DB-Strukturen
 * und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author CarlaHofmann
 */

public class ProductMapper {

	/**
	 * Speicherung der Instanz dieser Mapperklasse
	 */
	private static ProductMapper productMapper = null;

	/**
	 * Geschuetzter Konstruktor verhindert weitere Instanzierungen von
	 * ProductMapper
	 */
	protected ProductMapper() {
	}

	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse
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
	 * Ausgabe einer Liste aller Produkte
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
	 * Produkt mithilfe seiner id finden
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
	 * Produkt mithilfe des Produktnamens finden
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
	 * Insert Methode, um eine neue Entitaet der Datenbank hinzuzufuegen
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
				pstmt.setDate(2, (Date) product.getCreationDate());
				pstmt.setString(3, product.getName());
				pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return product;
	}

	/**
	 * Wiederholtes Schreiben / Aendern eines Objekts in die/der Datenbank
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
	 * Delete Methode, um ein Produkt-Objekt aus der Datenbank zu entfernen
	 *
	 * @param product
	 */
	
	public void delete(int id) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM products WHERE id = " + id);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode, um das Produkt eines Listitems zu finden
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
