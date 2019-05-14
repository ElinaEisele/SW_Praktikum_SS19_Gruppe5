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
	 * Liste aller Eintrï¿½ge, welche als Standard zu jeder neuen Einkaufsliste der Gruppe 
	 * hinzugefï¿½gt werden.
	 */
	private ArrayList<Listitem> standardListitems;
	
	/*
	 * Default-Konstruktor
	 */
	public Group() {
	}
	
	/**
	 * Konstruktor zum Setzen des Namens.
	 */
	public Group(String groupName) {
		super(groupName);
	}
	/**
	 * Konstruktor zum befüllen des CellTrees.
	 */
	public Group(String groupName, ArrayList <Shoppinglist> shoppinglists, ArrayList <User> users, ArrayList <Listitem> standardListitems ) {
		super(groupName);
		this.shoppinglists = shoppinglists;
		this.users=users;
		this.standardListitems= standardListitems;
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
	 * Auslesen der Liste aller Eintrï¿½ge, welche die Nutzer als Standardartikel markiert haben.
	 */
	public ArrayList<Listitem> getStandardListitem() {
		return standardListitems;
	}
	
	/**
	 * Setzen der Liste aller Eintrï¿½ge, welche die Nutzer als Standardartikel markiert haben.
	 */
	public void setStandardListitem(ArrayList<Listitem> standardListitems) {
		this.standardListitems = standardListitems;
	}
}
