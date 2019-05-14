package de.hdm.softwarepraktikum.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung der Klasse Listitem, welche einen Eintrag in einer Shoppingliste darstellt.
 * 
 * @author Felix Rapp
 */

public class Listitem extends BusinessObject implements IsSerializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Angabe zur Menge des zu einzukaufenden Produkts.
	 */
	private float amount;
	
	/**
	 * Einheit der Menge.
	 */
	private Unit unit;
	
	/**
	 * Angabe des einzukaufenden Produkts.
	 */
	private Product product;
	
	/**
	 * Fremdschlï¿½sselbeziehung zum Produkt des Eintrags.
	 */
	private int productID;
	
	/**
	 * Fremdschlï¿½sselbeziehung zur Einkaufsliste des Eintrags.
	 */
	private int shoppinglistID;
	
	/**
	 * Fremdschlï¿½sselbeziehung zum Hï¿½ndler des Eintrags.
	 */
	private int retailerID;
	
	/*
	 * Default-Konstruktor
	 */
	public Listitem() {
	}
	
	/**
	 * Konstruktor zum Setzen des Namen, der Menge und der Einheit.
	 */
	public Listitem (float amount, Unit unit) {
		super();
		this.setAmount(amount);
		this.setUnit(unit);
	}
	
	/**
	 * Konstruktor zum Setzen des Namen, der Menge, der Einheit und des Einzelhändlers.
	 */
	public Listitem (float amount, Unit unit, Retailer retailer) {
		super();
		this.setAmount(amount);
		this.setUnit(unit);		
	}
	
	/**
	 * Konstruktor zum Setzen des Namen, der Menge, der Einheit und des Einzelhändlers.
	 */
	public Listitem (float amount, Unit unit, Retailer retailer, Product product) {
		super();
		this.setAmount(amount);
		this.setUnit(unit);		
		this.setProduct(product);
	}
	
	/**
	 * Ausgeben des zu einkaufenden Produkts.
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * Setzen des zu einkaufenden Produkts.
	 */
	private void setProduct(Product product) {
		this.product = product;		
	}

	/**
	 * Ausgeben der zu einkaufenden Menge.
	 */
	public float getAmount() {
		return amount;
	}
	
	/**
	 * Setzen der zu einkaufenden Menge.
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	/**
	 * Auslesen der Einheit zu der Menge.
	 */
	public Unit getUnit() {
		return unit;
	}
	
	/**
	 * Setzen der Einheit.
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	/**
	 * Auslesen der Produkt ID.
	 */
	public int getProductID() {
		return productID;
	}
	
	/**
	 * Setzen der Produkt ID.
	 */
	public void setProductID(int productID) {
		this.productID = productID;
	}
	
	/**
	 * Auslesen der Einkaufsliste ID.
	 */
	public int getShoppinglistID() {
		return shoppinglistID;
	}
	
	/**
	 * Setzen der Einkaufslisten ID.
	 */
	public void setShoppinglistID(int shoppinglistID) {
		this.shoppinglistID = shoppinglistID;
	}

	/**
	 * Auslesen der Retailer ID.
	 */
	public int getRetailerID() {
		return retailerID;
	}

	/**
	 * Setzen der Retailer ID.
	 */
	public void setRetailerID(int retailerID) {
		this.retailerID = retailerID;
	}
}
