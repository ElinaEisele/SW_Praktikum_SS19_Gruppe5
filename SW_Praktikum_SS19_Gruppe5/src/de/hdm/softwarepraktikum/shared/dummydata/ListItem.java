package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.Date;

/**
 * 
 * @author JonasWagenknecht
 * ListItem object for testing purposes 
 * 
 */

public class ListItem extends BusinessObject{
	private int amount;
	private int id;
	private Date creationDate;
	Group group;
	Product product;
	Retailer retailer;
	Shoppinglist shoppinglist;
	Unit unit;
	User user;

	public ListItem(int amount, int id, Date creationDate, Group group, Product product, Retailer retailer,
			Shoppinglist shoppinglist, Unit unit, User user) {
		this.amount = amount;
		this.id = id;
		this.creationDate = creationDate;
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

	public String getGroupName() {
		return group.getGroupName();
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getProductName() {
		return product.getProductName();
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getRetailerName() {
		return retailer.getRetailerName();
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}

	public String getShoppinglistName() {
		return shoppinglist.getShoppinglistName();
	}

	public void setShoppinglist(Shoppinglist shoppinglist) {
		this.shoppinglist = shoppinglist;
	}

	public String getUnitName() {
		return unit.getUnitName();
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public String getUserName() {
		return user.getUserName();
	}

	public void setUser(User user) {
		this.user = user;
	}

}
