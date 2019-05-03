package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.Date;

/**
 * 
 * @author JonasWagenknecht
 * Retailer object for testing purposes
 * 
 */

public class Retailer extends BusinessObject{
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
