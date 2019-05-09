package de.hdm.softwarepraktikum.shared.bo;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung der Klasse Group, welche eine Gruppe darstellt, welche aus mehreren Mitgliedern (User)
 * und Einkaufslisten (Shoppinglist) bestehen kann.
 * 
 * @author Felix Rapp
 */

public class Group extends NamedBusinessObject implements IsSerializable{

	private static final long serialVersionUID = 1L;

	/**
	 * Liste mit allen Nutzern der Gruppe.
	 */
	private ArrayList<User> users;
	
	/**
	 * Liste aller Einkaufslisten der Gruppe.
	 */
	private ArrayList<Shoppinglist> shoppinglists;
	
	/**
	 * Liste aller Einträge, welche als Standard zu jeder neuen Einkaufsliste der Gruppe 
	 * hinzugefügt werden.
	 */
	private ArrayList<Listitem> standardListitem;
	
	/*
	 * Default-Konstruktor
	 */
	public Group() {
		super();
	}
	
	/**
	 * Konstruktor zum Setzen des Namens.
	 */
	public Group(String groupname) {
		super(groupname);
	}
	
	/**
	 * Auslesen der Nutzer einer Gruppe.
	 */
	public ArrayList<User> getUsers() {
		return users;
	}
	
	/**
	 * Setzen der Liste mit den Nutzern der Gruppe.
	 */
	public void setUser(ArrayList<User> users) {
		this.users = users;
	}
	
	/**
	 * Auslesen aller Einkauflisten der Gruppe.
	 */
	public ArrayList<Shoppinglist> getShoppinglists() {
		return shoppinglists;
	}
	
	/**
	 * Setzen der Liste aller Einkauslisten der Gruppe.
	 */
	public void setShoppinglists(ArrayList<Shoppinglist> shoppinglists) {
		this.shoppinglists = shoppinglists;
	}
	
	/**
	 * Auslesen der Liste aller Einträge, welche die Nutzer als Standardartikel markiert haben.
	 */
	public ArrayList<Listitem> getStandardListitem() {
		return standardListitem;
	}
	
	/**
	 * Setzen der Liste aller Einträge, welche die Nutzer als Standardartikel markiert haben.
	 */
	public void setStandardListitem(ArrayList<Listitem> standardListitem) {
		this.standardListitem = standardListitem;
	}
}
