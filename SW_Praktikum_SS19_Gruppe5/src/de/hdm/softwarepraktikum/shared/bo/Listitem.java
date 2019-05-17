package de.hdm.softwarepraktikum.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.view.client.ProvidesKey;

/**
 * Realisierung der Klasse Listitem, welche einen Eintrag in einer Shoppingliste darstellt.
 * 
 * @author Felix Rapp
 */

public class Listitem extends BusinessObject implements IsSerializable{
	
	private static final long serialVersionUID = 1L;

	public static final ProvidesKey<Listitem> KEY_PROVIDER = null;
	
	/**
	 * Angabe zur Menge des zu einzukaufenden Produkts.
	 */
	private float amount;
	
	/**
	 * Fremdschluesselbeziehung zur Mengeneinheit des Eintrags.
	 */
	private int ListitemUnitID;
	
	/**
	 * Fremdschluesselbeziehung zum Produkt des Eintrags.
	 */
	private int productID;
	
	/**
	 * Fremdschluesselbeziehung zur Einkaufsliste des Eintrags.
	 */
	private int shoppinglistID;
	
	/**
	 * Fremdschluesselbeziehung zum Hï¿½ndler des Eintrags.
	 */
	private int retailerID;
	
	/*
	 * Default-Konstruktor
	 */
	public Listitem() {
	}
	
	/**
	 * Konstruktor zum Setzen der Menge und der Einheit.
	 */
	public Listitem (float amount, ListitemUnit listitemUnit) {
		this.setAmount(amount);
		this.setListitemUnitID(listitemUnit.getId());
	}
	
	/**
	 * Konstruktor zum Setzen der Menge, der Einheit und des Einzelhändlers.
	 */
	public Listitem (float amount, ListitemUnit listitemUnit, Retailer retailer) {
		this.setAmount(amount);
		this.setListitemUnitID(listitemUnit.getId());
		this.setRetailerID(retailer.getId());
		
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

	/*
	 * Ausgeben der ListitemUnit ID
	 */
	public int getListitemUnitID() {
		return ListitemUnitID;
	}

	/*
	 * Setzen der ListitemUnit ID
	 */
	public void setListitemUnitID(int listitemUnitID) {
		ListitemUnitID = listitemUnitID;
	}
}
