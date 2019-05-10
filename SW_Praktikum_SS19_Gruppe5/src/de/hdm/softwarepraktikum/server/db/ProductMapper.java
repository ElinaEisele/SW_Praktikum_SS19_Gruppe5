package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.*;
import javafx.scene.Group;

/**
 * Mapper Klasse für </code>Product</code> Objekte. Diese umfasst Methoden um
 * Produkt-Objekte zu erstellen, zu suchen, zu modifizieren und zu loeschen. Das
 * Mapping funktioniert dabei bidirektional. Es koennen Objekte in DB-Strukturen
 * und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author CarlaHofmann
 */

public class ProductMapper {

	/**
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	private static ProductMapper productMapper = null;

	/**
	 * Geschuetzter Konstruktor verhindert weitere Instanzierungen von
	 * ProductMapper.
	 */
	protected ProductMapper() {
	}

	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse.
	 *
	 * @return Gibt den Productmapper zurueck.
	 */
	public static ProductMapper productMapper() {
		if (productMapper == null) {
			productMapper = new ProductMapper();
		}

		return productMapper;
	}

	/**
	 * Ausgabe einer Liste aller Produkte.
	 *
	 * @return Gibt eine Liste aller Produkte zurueck.
	 */
	public ArrayList<Product> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<Product> products = new ArrayList<Product>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, name, creationDate FROM products");

			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setCreationDate(rs.getDate("creationDate"));

				products.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return products;

	}

	/**
	 * Produkt mittels id finden.
	 *
	 * @param id: Die id wird uebergeben,um mithilfe dieser ein Produkt-Objekt zu
	 *        finden.
	 * @return Das Produkt-Objekt, welches anhand der id gefunden wurde, wird
	 *         zurueckgegeben.
	 */
	public Product findById(int id) {

		Product product = new Product();
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, name, creationDate FROM products WHERE id = " + id);

			if (rs.next()) {

				Product p = new Product();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setCreationDate(rs.getDate("creationDate"));
				return p;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Produkt mithilfe des Produktnamens finden.
	 * 
	 * @param name: Uebergabe des Namens eines Produkts in Form eines Strings
	 * @return Produkt(e) mit dem entsprechenden Namen
	 */
	public ArrayList<Product> findByName(String name) {

		Connection con = DBConnection.connection();
		ArrayList<Product> products = new ArrayList<Product>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, name, creationDate FROM products WHERE name = " + name);

			while (rs.next()) {

				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setCreationDate(rs.getDate("creationDate"));

				products.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return products;

	}

	/**
	 * Insert Methode, um eine neue Entitaet der Datenbank hinzuzufuegen.
	 *
	 * @param product: Das gewaehlte Produkt wird uebergeben
	 * @return Das Produkt wird zurueckgegeben.
	 */
	public Product insert(Product product) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM products ");

			if (rs.next()) {
				product.setId(rs.getInt("maxid") + 1);
			}

			PreparedStatement pstmt = con.prepareStatement(
					"INSERT INTO products (id, creationDate,name) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

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
	 * Wiederholtes Schreiben / Aendern eines Objekts in die/der Datenbank.
	 *
	 * @param product: Das Produkt wird uebergeben.
	 * @return Gibt das akutalisierte Produkt zurueck.
	 */
	public Product update(Product product) {

		Connection con = DBConnection.connection();

		try {
			PreparedStatement pstmt = con.prepareStatement("UPDATE products SET name = ? WHERE id = ? ");
			
			pstmt.setString(1, product.getName());
			pstmt.setInt(2, product.getId());
			pstmt.executeUpdate();
			
			return product;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Delete Methode, um ein Produkt-Objekt aus der Datenbank zu entfernen.
	 *
	 * @param product: Das Produkt wird uebergeben.
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
	 * Methode, um das Produkt eines Listitems zu bekommen.
	 * 
	 * @param listitem: Listitem, von welchem das Produkt abgefragt wird.
	 * @return Produkt des Listitems
	 */
	public Product getProductOf(Listitem listitem) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT product_id FROM listitems WHERE id = " + listitem.getId());

			if (rs.next()) {

				Product p = ProductMapper.productMapper().findById(rs.getInt("id"));
				p.getId();
				p.getCreationDate();
				p.getName();
				return p;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;

	}

}
