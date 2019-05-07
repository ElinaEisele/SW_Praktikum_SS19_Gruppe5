package de.hdm.softwarepraktikum.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung der Klasse Listitem, welche einen Eintrag in einer Shoppingliste darstellt.
 * 
 * @author Felix Rapp
 */

public class Listitem extends BusinessObject implements IsSerializable{
	
	private static final long serialVersionUID = 1L;
	private float amount;
	private Unit unit;
	private int productID;
	private int shoppinglistID;
	
	public Listitem (String productname, float amount, Unit unit) {
		Product p = new Product(productname);
		this.setProductID(p.getId());
		this.setAmount(amount);
		this.setUnit(unit);
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
	
	public Unit getUnit() {
		return unit;
	}
	
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public int getProductID() {
		return productID;
	}
	
	public void setProductID(int productID) {
		this.productID = productID;
	}
	
	public int getShoppinglistID() {
		return shoppinglistID;
	}
	
	public void setShoppinglistID(int shoppinglistID) {
		this.shoppinglistID = shoppinglistID;
	}
	
}
