package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.shared.dummydata.Group;
import de.hdm.softwarepraktikum.shared.dummydata.ListItem;
import de.hdm.softwarepraktikum.shared.dummydata.Product;
import de.hdm.softwarepraktikum.shared.dummydata.Retailer;
import de.hdm.softwarepraktikum.shared.dummydata.Shoppinglist;
import de.hdm.softwarepraktikum.shared.dummydata.Unit;
import de.hdm.softwarepraktikum.shared.dummydata.User;

public class ShoppinglistCelltable extends VerticalPanel {

	ArrayList<Group> group;
	ArrayList<Product> product;
	ArrayList<Retailer> retailer;
	ArrayList<Shoppinglist> shoppinglist;
	ArrayList<Unit> unit;
	ArrayList<User> user;
	ArrayList<ListItem> listitem;

	public void onLoad() {
		super.onLoad();

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

		CellTable<ListItem> table = new CellTable<>();

		table.setStyleName("table");
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		Column<ListItem, Boolean> checkColumn = new Column<ListItem, Boolean>(new CheckboxCell(true, false)) {

			@Override
			public Boolean getValue(ListItem arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		checkColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		checkColumn.setCellStyleNames("columncheck");
		table.addColumn(checkColumn, "Check");
		// table.setColumnWidth(checkColumn, 40, Unit.PX);

		TextColumn<ListItem> productNameColumn = new TextColumn<ListItem>() {
			@Override
			public String getValue(ListItem object) {
				return object.getProductName();
			}
		};
		table.addColumn(productNameColumn, "Produkt");

		NumberCell amountCell = new NumberCell();
		Column<ListItem, Number> listItemAmountColumn = new Column<ListItem, Number>(amountCell) {
			@Override
			public Number getValue(ListItem object) {
				return object.getAmount();
			}
		};
		table.addColumn(listItemAmountColumn, "Menge");

		TextColumn<ListItem> unitNameColumn = new TextColumn<ListItem>() {
			@Override
			public String getValue(ListItem object) {
				return object.getUnitName();
			}
		};
		table.addColumn(unitNameColumn, "Einheit");

		TextColumn<ListItem> retailerNameColumn = new TextColumn<ListItem>() {
			@Override
			public String getValue(ListItem object) {
				return object.getRetailerName();
			}
		};
		table.addColumn(retailerNameColumn, "Haendler");

		TextColumn<ListItem> userNameColumn = new TextColumn<ListItem>() {
			@Override
			public String getValue(ListItem object) {
				return object.getUserName();
			}
		};
		table.addColumn(userNameColumn, "Verantwortlicher");

		// Set the total row count. This isn't strictly necessary,
		// but it affects paging calculations, so its good habit to
		// keep the row count up to date.
		table.setRowCount(listitem.size(), true);

		// Push the data into the widget.
		table.setRowData(0, listitem);

		this.add(table);
	}

}
