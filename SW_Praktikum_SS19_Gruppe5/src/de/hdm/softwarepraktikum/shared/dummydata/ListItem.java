package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.Date;

/**
 * ListItem object for testing purposes
 * 
 * @author JonasWagenknecht, ElinaEisele
 */


public class ListItem extends BusinessObject {

	private int amount;
	Group group;
	Product product;
	Retailer retailer;
	Shoppinglist shoppinglist;
	Unit unit;
	User user;

	public ListItem(int id, Date creationDate, int amount, Group group, Product product, Retailer retailer,
			Shoppinglist shoppinglist, Unit unit, User user) {

		super(id, creationDate);
		this.amount = amount;
		this.group = group;
		this.product = product;
		this.retailer = retailer;
		this.shoppinglist = shoppinglist;
		this.unit = unit;
		this.user = user;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Retailer getRetailer() {
		return retailer;
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}

	public Shoppinglist getShoppinglist() {
		return shoppinglist;
	}

	public void setShoppinglist(Shoppinglist shoppinglist) {
		this.shoppinglist = shoppinglist;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
