package de.hdm.softwarepraktikum.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung der Klasse Product, welche ein Produkt darstellt, welches einem Eintrag (Listitem)
 * einer Shoppingliste zugeordnet ist.
 * 
 * @author Felix Rapp, TimBeutelspacher
 */

public class Product extends NamedBusinessObject implements IsSerializable{
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Fremdschlüsselbeziehung zum zugeordneten Listitem-Objekt
	 */
	private int listitemID;
	
	/*
	 * Konstruktor, in welchem der Name gesetzt wird.
	 */
	public Product(String productname) {
		super(productname);
	}

	/*
	 * Ausgeben der ID, des zugeordneten Listitem-Objekts
	 */
	public int getListitemID() {
		return listitemID;
	}

	/*
	 *setzen der ID, des zugeordneten Listitem-Objekts 
	 */
	public void setListitemID(int listitemID) {
		this.listitemID = listitemID;
	}
}
