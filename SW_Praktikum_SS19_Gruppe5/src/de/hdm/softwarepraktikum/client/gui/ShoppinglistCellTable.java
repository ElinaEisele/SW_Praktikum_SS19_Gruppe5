package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Product;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.Unit;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Celltable with example data
 * 
 * @author JonasWagenknecht, ElinaEisele
 */

public class ShoppinglistCellTable extends VerticalPanel {

	ArrayList<Group> group;
	ArrayList<Product> product;
	ArrayList<Retailer> retailer;
	ArrayList<Shoppinglist> shoppinglist;
	ArrayList<User> user;
	ArrayList<Listitem> listitem;
	ArrayList<ShoppinglistObject> shoppinglistobject;

	ShoppinglistContent shoppinglistContent;
	ShoppinglistSearchBar shoppinglistSearchBar;
	ShoppinglistCell shoppinglistCell;
	VerticalPanel p1;
	CellTable<ShoppinglistObject> table;
	ListDataProvider<ShoppinglistObject> provider;
	
	public void onLoad() {
		super.onLoad();
		p1 = new VerticalPanel();
		shoppinglistSearchBar = new ShoppinglistSearchBar();
		shoppinglistCell = new ShoppinglistCell();
		shoppinglistContent = new ShoppinglistContent();

		group = new ArrayList<>();
		listitem = new ArrayList<>();
		product = new ArrayList<>();
		retailer = new ArrayList<>();
		shoppinglist = new ArrayList<>();
		user = new ArrayList<>();
		shoppinglistobject = new ArrayList<>();

		Group group1 = new Group("Tims Geburtstag");
		Group group2 = new Group("Tims Familie");
		group.add(group1);
		group.add(group2);

		Product product1 = new Product("Tomate");
		Product product2 = new Product("Apfel");
		product.add(product1);
		product.add(product2);

		Retailer retailer1 = new Retailer("Aldi");
		Retailer retailer2 = new Retailer("Kaufland");
		retailer.add(retailer1);
		retailer.add(retailer2);

		Shoppinglist shoppinglist1 = new Shoppinglist("Party");
		Shoppinglist shoppinglist2 = new Shoppinglist("Familieneinkauf");
		shoppinglist.add(shoppinglist1);
		shoppinglist.add(shoppinglist2);

		User user1 = new User("Tim");
		User user2 = new User("Tims Papa");
		User user3 = new User("Felix");
		User user4 = new User("Jonas");
		user.add(user1);
		user.add(user2);
		user.add(user3);
		user.add(user4);

		Listitem listitem1 = new Listitem(2.0f, Unit.St);
		Listitem listitem2 = new Listitem(0.5f, Unit.Kg);
		Listitem listitem3 = new Listitem(5.0f, Unit.St);
		Listitem listitem4 = new Listitem(1.0f, Unit.L);
		listitem.add(listitem1);
		listitem.add(listitem2);
		listitem.add(listitem3);
		listitem.add(listitem4);

		ShoppinglistObject shoppinglistobject1 = new ShoppinglistObject(product1, listitem1, retailer1);
		shoppinglistobject.add(shoppinglistobject1);

		table = new CellTable<ShoppinglistObject>();

		
		table.setStyleName("shoppinglist-CellTable");
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		provider = (ListDataProvider<ShoppinglistObject>)table.getKeyProvider();
		
		final MultiSelectionModel<ShoppinglistObject> selectionModel = new MultiSelectionModel<ShoppinglistObject>(
				ShoppinglistObject.KEY_PROVIDER);
		table.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<ShoppinglistObject>createCheckboxManager());

		Column<ShoppinglistObject, Boolean> checkColumn = new Column<ShoppinglistObject, Boolean>(
				new CheckboxCell(true, false)) {

			@Override
			public Boolean getValue(ShoppinglistObject object) {
				return null;

			}
		};

		checkColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		checkColumn.setCellStyleNames("columncheck");
		table.addColumn(checkColumn, "Check");

		// table.setColumnWidth(checkColumn, 40, Unit.PX);

		TextColumn<ShoppinglistObject> productNameColumn = new TextColumn<ShoppinglistObject>() {

			public String getValue(ShoppinglistObject object) {
				return object.getProduct().getName();
			}

		};

		table.addColumn(productNameColumn, "Produkt");

		NumberCell amountCell = new NumberCell();
		Column<ShoppinglistObject, Number> listItemAmountColumn = new Column<ShoppinglistObject, Number>(amountCell) {
			@Override
			public Number getValue(ShoppinglistObject object) {
				return object.getListitem().getAmount();
			}
		};
		table.addColumn(listItemAmountColumn, "Menge");

		TextColumn<ShoppinglistObject> unitNameColumn = new TextColumn<ShoppinglistObject>() {
			@Override
			public String getValue(ShoppinglistObject object) {
				return object.getListitem().getUnit().toString();
			}
		};
		table.addColumn(unitNameColumn, "Einheit");

		TextColumn<ShoppinglistObject> retailerNameColumn = new TextColumn<ShoppinglistObject>() {
			@Override
			public String getValue(ShoppinglistObject object) {
				return object.getRetailer().getName();
			}
		};
		table.addColumn(retailerNameColumn, "Haendler");

		Column<ShoppinglistObject, String> imageColumn = new Column<ShoppinglistObject, String>(
				new ClickableTextCell() {
					public void render(Context context, SafeHtml value, SafeHtmlBuilder sb) {
						sb.appendHtmlConstant("<img width=\"20\" src=\"images/" + value.asString() + "\">");
					}

				})

		{
			@Override
			public String getValue(ShoppinglistObject object) {
				return "edit.png";
			}

			public void onBrowserEvent(Context context, Element elem, ShoppinglistObject object, NativeEvent event) {
				super.onBrowserEvent(context, elem, object, event);
				if ("click".equals(event.getType())) {
					
					p1.clear();
					p1.add(shoppinglistCell);

					Window.alert("clicked");
				}
			}
		};

		table.addColumn(imageColumn, "");

		// Set the total row count
		table.setRowCount(shoppinglistobject.size(), true);
		// Push the data into the widget.
		table.setRowData(0, shoppinglistobject);
		
		p1.add(table);
		this.add(p1);
	}

}
