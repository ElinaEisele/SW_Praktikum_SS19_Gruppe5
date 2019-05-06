package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.Date;

/**
 * ListItem object for testing purposes
 * 
 * @author JonasWagenknecht, ElinaEisele
 */


public class ListItemDD extends BusinessObjectDD {

	private int amount;
	Group group;
	ProductDD product;
	RetailerDD retailer;
	ShoppinglistDD shoppinglist;
	UnitDD unit;
	UserDD user;

	public ListItemDD(int id, Date creationDate, int amount, Group group, ProductDD product, RetailerDD retailer,
			ShoppinglistDD shoppinglist, UnitDD unit, UserDD user) {

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

	public ProductDD getProduct() {
		return product;
	}

	public void setProduct(ProductDD product) {
		this.product = product;
	}

	public RetailerDD getRetailer() {
		return retailer;
	}

	public void setRetailer(RetailerDD retailer) {
		this.retailer = retailer;
	}

	public ShoppinglistDD getShoppinglist() {
		return shoppinglist;
	}

	public void setShoppinglist(ShoppinglistDD shoppinglist) {
		this.shoppinglist = shoppinglist;
	}

	public UnitDD getUnit() {
		return unit;
	}

	public void setUnit(UnitDD unit) {
		this.unit = unit;
	}

	public UserDD getUser() {
		return user;
	}

	public void setUser(UserDD user) {
		this.user = user;
	}

}
