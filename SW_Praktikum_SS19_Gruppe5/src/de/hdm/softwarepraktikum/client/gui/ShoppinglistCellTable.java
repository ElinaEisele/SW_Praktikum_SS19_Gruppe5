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
import de.hdm.softwarepraktikum.shared.bo.ListitemUnit;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Celltable with example data
 * 
 * @author JonasWagenknecht, ElinaEisele
 */

public class ShoppinglistCellTable extends VerticalPanel {

	ArrayList<Product> products;
	ArrayList<Retailer> retailer;
	ArrayList<Listitem> listitems;

	ShoppinglistContent shoppinglistContent;
	ShoppinglistSearchBar shoppinglistSearchBar;
	//ShoppinglistCell shoppinglistCell;
	VerticalPanel p1;
	
	CellTable<Listitem> table;
	ListDataProvider<Listitem> provider;

	public void onLoad() {
		super.onLoad();
		p1 = new VerticalPanel();
		shoppinglistSearchBar = new ShoppinglistSearchBar();
		//shoppinglistCell = new ShoppinglistCell();
		shoppinglistContent = new ShoppinglistContent();

		listitems = new ArrayList<>();
		products = new ArrayList<>();
		retailer = new ArrayList<>();

		/**
		 * Testdata
		 * 
		 */
		Product product1 = new Product("Tomate");
		Product product2 = new Product("Apfel");
		products.add(product1);
		products.add(product2);

		Retailer retailer1 = new Retailer("Aldi");
		Retailer retailer2 = new Retailer("Kaufland");
		retailer.add(retailer1);
		retailer.add(retailer2);

		Listitem listitem1 = new Listitem(2.0f, ListitemUnit.St, retailer1, product1);
		Listitem listitem2 = new Listitem(0.5f, ListitemUnit.Kg, retailer2, product1);
		Listitem listitem3 = new Listitem(5.0f, ListitemUnit.St, retailer2, product2);
		Listitem listitem4 = new Listitem(1.0f, ListitemUnit.L, retailer1, product2);
		listitems.add(listitem1);
		listitems.add(listitem2);
		listitems.add(listitem3);
		listitems.add(listitem4);
		
		//User user1 = this.shoppinglistAdministrationImpl.createUser("Tim", "Tim@gmail.com");
//		Group group1 = this.shoppinglistAdministrationImpl.createGroupFor(user1, "Gruppe1");
//		Product product1 = this.shoppinglistAdministrationImpl.createProduct("Banana");
//		Shoppinglist shoppinglist1 = this.shoppinglistAdministrationImpl.createShoppinglistFor(group1,
//				"Shoppinglist11");
//
//		Listitem listitem1 = this.shoppinglistAdministrationImpl.createListitem(shoppinglist1, "", 2.0f,
//				ListitemUnit.Kg);
//
//		listitems.add(listitem1);

		table = new CellTable<Listitem>();

		table.setStyleName("shoppinglist-CellTable");
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		provider = (ListDataProvider<Listitem>) table.getKeyProvider();

		final MultiSelectionModel<Listitem> selectionModel = new MultiSelectionModel<Listitem>(Listitem.KEY_PROVIDER);
		table.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Listitem>createCheckboxManager());

		Column<Listitem, Boolean> checkColumn = new Column<Listitem, Boolean>(new CheckboxCell(true, false)) {

			@Override
			public Boolean getValue(Listitem object) {
				return null;

			}
		};

		checkColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		checkColumn.setCellStyleNames("columncheck");
		table.addColumn(checkColumn, "Check");
		// table.setColumnWidth(checkColumn, 40, Unit.PX);

		/**
		 * Product Name Column
		 * 
		 */
		TextColumn<Listitem> productNameColumn = new TextColumn<Listitem>() {
			public String getValue(Listitem object) {
				return object.getProduct().getName();
			}
		};
		table.addColumn(productNameColumn, "Produkt");

		/**
		 * Amount Column
		 * 
		 */
		NumberCell amountCell = new NumberCell();
		Column<Listitem, Number> listItemAmountColumn = new Column<Listitem, Number>(amountCell) {
			@Override
			public Number getValue(Listitem object) {
				return object.getAmount();
			}
		};
		table.addColumn(listItemAmountColumn, "Menge");

		/**
		 * Unit Name Column
		 * 
		 */
		TextColumn<Listitem> unitNameColumn = new TextColumn<Listitem>() {
			@Override
			public String getValue(Listitem object) {
				return object.getUnit().toString();
			}
		};
		table.addColumn(unitNameColumn, "Einheit");

		/**
		 * Retailer Name Column
		 * 
		 */
		NumberCell retailerNameCell = new NumberCell();
		Column<Listitem, Number> retailerNameColumn = new Column<Listitem, Number>(retailerNameCell) {
			@Override
			public Number getValue(Listitem object) {
				return object.getRetailerID();
			}
		};
		table.addColumn(retailerNameColumn, "Haendler ID");

		/**
		 * Clickable edit button containing an image
		 * 
		 */
		Column<Listitem, String> imageColumn = new Column<Listitem, String>(new ClickableTextCell() {
			public void render(Context context, SafeHtml value, SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<img width=\"20\" src=\"images/" + value.asString() + "\">");
			}

		})

		{
			@Override
			public String getValue(Listitem object) {
				return "edit.png";
			}

			public void onBrowserEvent(Context context, Element elem, Listitem object, NativeEvent event) {
				super.onBrowserEvent(context, elem, object, event);
				if ("click".equals(event.getType())) {

					p1.clear();
				//	p1.add(shoppinglistCell);

					Window.alert("clicked");
				}
			}
		};

		table.addColumn(imageColumn, "");

		// Set the total row count
		table.setRowCount(listitems.size(), true);
		// Push the data into the widget.
		table.setRowData(0, listitems);

		p1.add(table);
		this.add(p1);
	}

}
