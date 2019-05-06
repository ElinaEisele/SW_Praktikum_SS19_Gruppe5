package de.hdm.softwarepraktikum.shared.bo;

public class Listitem extends BusinessObject{
	
	private static final long serialVersionUID = 1L;
	private float amount;
	private String unit;
	private Product product;
	
	public Listitem (String productname, float amount, Unit unit) {
		Product p = new Product(productname);
		this.setProduct(p);
		this.setAmount(amount);
		this.setUnit(unit.toString());
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
}
