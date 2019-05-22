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
	 * Fremdschluesselbeziehung zum Haendler des Eintrags.
	 */
	private int retailerID;
	
	/**
	 * Fremdschluesselbeziehung zur Gruppe des Eintrags.
	 */
	private int groupID;
	/**
	 * Attribut, welches wiedergibt, ob ein Listitem ein Standard-Listitem innerhalb einer Gruppe ist.
	 */
	private boolean isStandard;
	
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
	 * Ausgeben der ListitemUnit ID.
	 */
	public int getListitemUnitID() {
		return ListitemUnitID;
	}

	/*
	 * Setzen der ListitemUnit ID.
	 */
	public void setListitemUnitID(int listitemUnitID) {
		ListitemUnitID = listitemUnitID;
	}

	/*
	 * Ausgeben des boolean-Werts isStandard.
	 */
	public boolean isStandard() {
		return isStandard;
	}

	/*
	 * Setzen des isStandard Attributs.
	 */
	public void setStandard(boolean isStandard) {
		this.isStandard = isStandard;
	}

	/*
	 * Ausgeben GruppenID
	 */
	public int getGroupID() {
		return groupID;
	}

	/*
	 * Setzen der GruppenID
	 */
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
}
