package de.hdm.softwarepraktikum.dummydata;

public class Retailer {
	private String retailerName;
	private int id;
	private int creationDate;

	public Retailer(String retailerName, int id, int creationDate) {
		this.retailerName = retailerName;
		this.id = id;
		this.creationDate = creationDate;
	}

	public String getRetailerName() {
		return retailerName;
	}

	public void setRetailerName(String retailerName) {
		this.retailerName = retailerName;
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
