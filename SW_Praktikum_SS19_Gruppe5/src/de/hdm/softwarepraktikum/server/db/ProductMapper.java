package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Product;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import javafx.scene.Group;



/**
 * Mapper Klasse für </code>Product</code> Objekte. Diese umfasst Methoden um
 * Produkt-Objekte zu erstellen, zu suchen, zu modifizieren und zu loeschen.
 * Das Mapping funktioniert dabei bidirektional. Es koennen Objekte in
 * DB-Strukturen und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author CarlaHofmann
 */

public class ProductMapper {
	
	/**
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	private static ProductMapper productMapper = null;

	/**
	 * Geschuetzter Konstruktor verhindert weitere Instanzierungen von ProductMapper.
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

			ResultSet rs = stmt.executeQuery("SELECT product_id, name, creationDate FROM products");

			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("product_id"));
				product.setName(rs.getString("name"));
				product.setCreationDate(rs.getString("creationDate"));
				
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
	 * @param id: Die id wird uebergeben,um mithilfe dieser ein Produkt-Objekt zu finden.
	 * @return Das Produkt-Objekt, welches anhand der id gefunden wurde, wird zurueckgegeben.
	 */
	public Product findById(int id) {
		
		Product product = new Product();
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT product_id, name, creationDate FROM products WHERE product_id = " + id);

			if (rs.next()) {

				Product product = new Product();
				product.setId(rs.getInt("product_id"));
				product.setName(rs.getString("name"));
				product.setCreationDate(rs.getString("creationDate"));
				return product;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return product;
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

			ResultSet rs = stmt.executeQuery("SELECT product_id, name, creationDate FROM products WHERE name = " + name);

			while (rs.next()) {

				Product product = new Product();
				product.setId(rs.getInt("product_id"));
				product.setName(rs.getString("name"));
				product.setCreationDate(rs.getString("creationDate"));
				
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
			ResultSet rs = stmt.executeQuery("");

			if (rs.next()) {

			}

			PreparedStatement stmt2 = con.prepareStatement(
					"INSERT INTO Product (id, creationDate,name) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			stmt2.setInt(1, product.getBOid());
			stmt2.setDate(2, product.getCreationDate());
			stmt2.setString(3, product.getName());
			stmt2.executeUpdate();

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


		} catch (SQLException e) {
			e.printStackTrace();
		}

		return product;
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
			stmt.executeUpdate("DELETE FROM Product WHERE Groups.id =" + product.getBOId());

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
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {

				Product product = new Product();
				product.setBOid(rs.getInt("id"));
				product.setCreationDate(rs.getString("CreationDate"));
				product.setName(rs.getString("Name"));
				return product;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	

}
