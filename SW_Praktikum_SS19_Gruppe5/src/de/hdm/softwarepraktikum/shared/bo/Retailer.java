package de.hdm.softwarepraktikum.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung der Klasse Retailer, welche einen Händler darstellt. Dieser ist für alle Nutzer
 * des Systems sichtbar und kann einzelnen Einträgen hinzugefügt werden.
 * 
 * @author Felix Rapp
 */

public class Retailer extends NamedBusinessObject implements IsSerializable{
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Default-Konstruktor
	 */
	public Retailer() {
		super();
	}
	
	/**
	 * Konstruktor zum Setzen des Namens des Händlers.
	 */
	public Retailer(String retailername) {
		super(retailername);
	}
}
