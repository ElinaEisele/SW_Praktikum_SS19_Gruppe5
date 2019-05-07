package de.hdm.softwarepraktikum.shared.bo;

public class Product extends NamedBusinessObject{
	
	private static final long serialVersionUID = 1L;
	
	public Product(String productname) {
		this.setName(productname);
	}

}
