package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.ArrayList;
import java.util.Date;

/**
 * Group object for testing purposes
 * 
 * @author JonasWagenknecht, ElinaEisele
 */
	
public class Group extends NamedBusinessObject {
	
	private ArrayList<ShoppinglistDD> shoppinglists = new ArrayList<ShoppinglistDD>();

	public Group(String name, int id, Date creationDate) {
		super(name, id, creationDate);
	}
	
	public void addShoppinglist(ShoppinglistDD s) {
		this.shoppinglists.add(s);
	}

}
