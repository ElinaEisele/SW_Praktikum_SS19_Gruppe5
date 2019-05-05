package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.ArrayList;
import java.util.Date;

/**
 * Group object for testing purposes
 * 
 * @author JonasWagenknecht, ElinaEisele
 */
	
public class Group extends NamedBusinessObject {
	
	private ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();

	public Group(String name, int id, Date creationDate) {
		super(name, id, creationDate);
	}
	
	public void addShoppinglist(Shoppinglist s) {
		this.shoppinglists.add(s);
	}

}
