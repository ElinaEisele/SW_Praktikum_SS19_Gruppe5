package de.hdm.softwarepraktikum.dummydata;

public class Product {

	private String productName;
	private int id;
	private int creationDate;

	public Product(String productName, int id, int creationDate) {
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

	public int getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(int creationDate) {
		this.creationDate = creationDate;
	}

}
