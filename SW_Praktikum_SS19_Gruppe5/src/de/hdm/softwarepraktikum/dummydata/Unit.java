package de.hdm.softwarepraktikum.dummydata;

public class Unit {
	private String unitName;
	private int id;
	private int creationDate;

	public Unit(String productName, int id, int creationDate) {
		this.unitName = productName;
		this.id = id;
		this.creationDate = creationDate;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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
