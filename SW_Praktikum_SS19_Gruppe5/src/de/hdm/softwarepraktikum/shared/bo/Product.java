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
	 * Default-Konstruktor
	 */
	public Product() {

	}
	/*
	 * Konstruktor, in welchem der Name gesetzt wird.
	 */
	public Product(String productname) {
		super(productname);
	}

	
}
