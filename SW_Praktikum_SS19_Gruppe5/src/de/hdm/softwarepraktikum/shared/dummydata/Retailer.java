package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.Date;

public class Retailer {
	private String retailerName;
	private int id;
	private Date creationDate;

	public Retailer(String retailerName, int id, Date creationDate) {
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
