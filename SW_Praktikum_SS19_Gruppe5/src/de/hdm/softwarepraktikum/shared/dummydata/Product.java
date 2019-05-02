package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.Date;

public class Product {

	private String productName;
	private int id;
	private Date creationDate;

	public Product(String productName, int id, Date creationDate) {
		this.productName = productName;
		this.id = id;
		this.creationDate = creationDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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
