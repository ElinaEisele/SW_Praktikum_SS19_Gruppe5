package de.hdm.softwarepraktikum.dummydata;

public class Shoppinglist {
	private String shoppinglistName;
	private int id;
	private int creationDate;

	public Shoppinglist(String shoppinglistName, int id, int creationDate) {
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

	public int getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(int creationDate) {
		this.creationDate = creationDate;
	}

}
