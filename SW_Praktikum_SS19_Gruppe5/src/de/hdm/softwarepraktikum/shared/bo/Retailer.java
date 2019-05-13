package de.hdm.softwarepraktikum.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung der Klasse Retailer, welche einen H�ndler darstellt. Dieser ist f�r alle Nutzer
 * des Systems sichtbar und kann einzelnen Eintr�gen hinzugef�gt werden.
 * 
 * @author Felix Rapp
 */

public class Retailer extends NamedBusinessObject implements IsSerializable{
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Default-Konstruktor
	 */
	public Retailer() {
		
	}
	
	/**
	 * Konstruktor zum Setzen des Namens des H�ndlers.
	 */
	public Retailer(String retailername) {
		super(retailername);
	}
}
