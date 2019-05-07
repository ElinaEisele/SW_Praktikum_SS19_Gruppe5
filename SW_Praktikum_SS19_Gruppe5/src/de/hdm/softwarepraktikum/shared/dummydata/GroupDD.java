package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.ArrayList;
import java.util.Date;

/**
 * Group object for testing purposes
 * 
 * @author JonasWagenknecht, ElinaEisele
 */
	
public class GroupDD extends NamedBusinessObjectDD {
	
	private ArrayList<ShoppinglistDD> shoppinglists = new ArrayList<ShoppinglistDD>();

	public GroupDD(String name, int id, Date creationDate) {
		super(name, id, creationDate);
	}
	
	public void addShoppinglist(ShoppinglistDD s) {
		this.shoppinglists.add(s);
	}

}
