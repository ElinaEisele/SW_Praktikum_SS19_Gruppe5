package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.ArrayList;
import java.util.Date;

public class CelltableData {
	ArrayList<Group> group;
	ArrayList<ListItem> listitem;
	ArrayList<Product> product;
	ArrayList<Retailer> retailer;
	ArrayList<Shoppinglist> shoppinglist;
	ArrayList<Unit> unit;
	ArrayList<User> user;

	public void CellTableData() {

		group = new ArrayList<>();
		listitem = new ArrayList<>();
		product = new ArrayList<>();
		retailer = new ArrayList<>();
		shoppinglist = new ArrayList<>();
		unit = new ArrayList<>();
		user = new ArrayList<>();

		Group group1 = new Group("Tims Geburtstag", 187, new Date());
		Group group2 = new Group("Tims Familie", 188, new Date());
		group.add(group1);
		group.add(group2);

		Product product1 = new Product("Tomate", 191, new Date());
		Product product2 = new Product("Apfel", 192, new Date());
		product.add(product1);
		product.add(product2);

		Retailer retailer1 = new Retailer("Aldi", 193, new Date());
		Retailer retailer2 = new Retailer("Kaufland", 194, new Date());
		retailer.add(retailer1);
		retailer.add(retailer2);

		Shoppinglist shoppinglist1 = new Shoppinglist("Party", 195, new Date());
		Shoppinglist shoppinglist2 = new Shoppinglist("Familieneinkauf", 196, new Date());
		shoppinglist.add(shoppinglist1);
		shoppinglist.add(shoppinglist2);

		Unit unit1 = new Unit("Kg", 197, new Date());
		Unit unit2 = new Unit("Stk", 198, new Date());
		unit.add(unit1);
		unit.add(unit2);

		User user1 = new User("Tim", 199, new Date(), "tim@gmail.com");
		User user2 = new User("Tims Papa", 200, new Date(), "timspapa@gmail.com");
		User user3 = new User("Felix", 201, new Date(), "felix@gmail.com");
		User user4 = new User("Jonas", 202, new Date(), "jonas@gmail.com");
		user.add(user1);
		user.add(user2);
		user.add(user3);
		user.add(user4);

		ListItem listitem1 = new ListItem(2, 204, new Date(), group1, product2, retailer2, shoppinglist2, unit2, user2);
		ListItem listitem2 = new ListItem(1, 204, new Date(), group1, product2, retailer2, shoppinglist2, unit2, user2);
		ListItem listitem3 = new ListItem(1, 205, new Date(), group1, product1, retailer1, shoppinglist1, unit1, user3);
		ListItem listitem4 = new ListItem(2, 206, new Date(), group1, product2, retailer2, shoppinglist2, unit2, user4);
		listitem.add(listitem1);
		listitem.add(listitem2);
		listitem.add(listitem3);
		listitem.add(listitem4);

	}

	public ArrayList<Group> getGroup() {
		return group;
	}

	public void setGroup(ArrayList<Group> group) {
		this.group = group;
	}

	public ArrayList<ListItem> getListitem() {
		return listitem;
	}

	public void setListitem(ArrayList<ListItem> listitem) {
		this.listitem = listitem;
	}

	public ArrayList<Product> getProduct() {
		return product;
	}

	public void setProduct(ArrayList<Product> product) {
		this.product = product;
	}

	public ArrayList<Retailer> getRetailer() {
		return retailer;
	}

	public void setRetailer(ArrayList<Retailer> retailer) {
		this.retailer = retailer;
	}

	public ArrayList<Shoppinglist> getShoppinglist() {
		return shoppinglist;
	}

	public void setShoppinglist(ArrayList<Shoppinglist> shoppinglist) {
		this.shoppinglist = shoppinglist;
	}

	public ArrayList<Unit> getUnit() {
		return unit;
	}

	public void setUnit(ArrayList<Unit> unit) {
		this.unit = unit;
	}

	public ArrayList<User> getUser() {
		return user;
	}

	public void setUser(ArrayList<User> user) {
		this.user = user;
	}

}