package de.hdm.softwarepraktikum.dummydata;

public class ListItem {
	private int amount;
	private int id;
	private int creationDate;

	public ListItem(int amount, int id, int creationDate) {
		this.amount = amount;
		this.id = id;
		this.creationDate = creationDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
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
