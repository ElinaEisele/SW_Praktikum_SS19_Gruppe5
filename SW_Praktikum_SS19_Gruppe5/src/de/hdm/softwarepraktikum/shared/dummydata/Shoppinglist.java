package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.Date;

public class Shoppinglist {
	private String shoppinglistName;
	private int id;
	private Date creationDate;

	public Shoppinglist(String shoppinglistName, int id, Date creationDate) {
		this.shoppinglistName = shoppinglistName;
		this.id = id;
		this.creationDate = creationDate;
	}

	public String getShoppinglistName() {
		return shoppinglistName;
	}

	public void setShoppinglistName(String shoppinglistName) {
		this.shoppinglistName = shoppinglistName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
