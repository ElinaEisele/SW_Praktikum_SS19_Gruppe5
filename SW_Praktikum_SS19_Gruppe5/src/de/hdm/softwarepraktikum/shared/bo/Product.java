package de.hdm.softwarepraktikum.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung der Klasse Product, welche ein Produkt darstellt, welches einem Eintrag (Listitem)
 * einer Shoppingliste zugeordnet ist.
 * 
 * @author Felix Rapp
 */

public class Product extends NamedBusinessObject implements IsSerializable{
	
	private static final long serialVersionUID = 1L;
	private int listitemID;
	
	public Product(String productname) {
		this.setName(productname);
	}

	public int getListitemID() {
		return listitemID;
	}

	public void setListitemID(int listitemID) {
		this.listitemID = listitemID;
	}
}
