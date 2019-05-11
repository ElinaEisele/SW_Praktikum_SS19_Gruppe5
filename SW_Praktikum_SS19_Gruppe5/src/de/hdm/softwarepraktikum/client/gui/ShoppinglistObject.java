package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwarepraktikum.shared.bo.BusinessObject;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Product;
import de.hdm.softwarepraktikum.shared.bo.Retailer;

public class ShoppinglistObject extends BusinessObject{

	/**
	 * Objekt zum zum befüllen des Cell Tables
	 */
	private static final long serialVersionUID = 1L;
	
	public static final ProvidesKey<ShoppinglistObject> KEY_PROVIDER = null;
	private Product product;
	private Listitem listitem;
	private Retailer retailer;
	

	public ShoppinglistObject(Product product, Listitem listitem, Retailer retailer) {
		this.product = product;
		this.listitem = listitem;
		this.retailer=retailer;
		
	}

	
	


	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Listitem getListitem() {
		return listitem;
	}

	public void setListitem(Listitem listitem) {
		this.listitem = listitem;
	}

	public Retailer getRetailer() {
		return retailer;
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}
	
	
}
