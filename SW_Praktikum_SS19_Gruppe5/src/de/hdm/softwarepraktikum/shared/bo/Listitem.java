package de.hdm.softwarepraktikum.shared.bo;

public class Listitem extends BusinessObject{
	
	private static final long serialVersionUID = 1L;
	private float amount;
	
	public Listitem (String productname, float amount, Unit unit) {
		Product p = new Product(productname);
		this.amount = amount;
		//Speicher für Unit fehlt
	}
	public long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public float getAmount() {
		return amount;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
}
