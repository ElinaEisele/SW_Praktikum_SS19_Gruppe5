package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.Date;

/**
 * Product object for testing purposes
 * 
 * @author JonasWagenknecht, ElinaEisele
 */

public class Product extends NamedBusinessObject {

	public Product(String name, int id, Date creationDate) {
		super(name, id, creationDate);
	}
}
