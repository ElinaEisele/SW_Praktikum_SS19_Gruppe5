package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.Date;

import com.google.gwt.view.client.ProvidesKey;

/**
 * ListItem object for testing purposes
 * 
 * @author JonasWagenknecht, ElinaEisele
 */


public class ListItemDD extends BusinessObjectDD {

	private int amount;
	GroupDD group;
	ProductDD product;
	RetailerDD retailer;
	ShoppinglistDD shoppinglist;
	UnitDD unit;
	UserDD user;
	private Boolean check = false;

	public static final ProvidesKey<ListItemDD> KEY_PROVIDER = new ProvidesKey<ListItemDD>() {
	      @Override
	      public Object getKey(ListItemDD item) {
	        return item == null ? null : item.getId();
	      }
	    };
	
	public ListItemDD(int id, Date creationDate, int amount, GroupDD group, ProductDD product, RetailerDD retailer,
			ShoppinglistDD shoppinglist, UnitDD unit, UserDD user, Boolean check) {

		super(id, creationDate);
		this.amount = amount;
		this.group = group;
		this.product = product;
		this.retailer = retailer;
		this.shoppinglist = shoppinglist;
		this.unit = unit;
		this.user = user;
		this.check=check;
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public GroupDD getGroup() {
		return group;
	}

	public void setGroup(GroupDD group) {
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
