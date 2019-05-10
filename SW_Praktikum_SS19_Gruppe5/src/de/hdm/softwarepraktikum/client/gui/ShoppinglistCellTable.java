package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.Cell.Context;
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
import com.google.gwt.view.client.MultiSelectionModel;

import de.hdm.softwarepraktikum.shared.dummydata.GroupDD;
import de.hdm.softwarepraktikum.shared.dummydata.ListItemDD;
import de.hdm.softwarepraktikum.shared.dummydata.ProductDD;
import de.hdm.softwarepraktikum.shared.dummydata.RetailerDD;
import de.hdm.softwarepraktikum.shared.dummydata.ShoppinglistDD;
import de.hdm.softwarepraktikum.shared.dummydata.UnitDD;
import de.hdm.softwarepraktikum.shared.dummydata.UserDD;


/**
 * Celltable that contains the dummy data
 * 
 * @author JonasWagenknecht, ElinaEisele
 */

public class ShoppinglistCellTable extends VerticalPanel {

	ArrayList<GroupDD> group;
	ArrayList<ProductDD> product;
	ArrayList<RetailerDD> retailer;
	ArrayList<ShoppinglistDD> shoppinglist;
	ArrayList<UnitDD> unit;
	ArrayList<UserDD> user;
	ArrayList<ListItemDD> listitem;

	public void onLoad() {
		super.onLoad();

		group = new ArrayList<>();
		listitem = new ArrayList<>();
		product = new ArrayList<>();
		retailer = new ArrayList<>();
		shoppinglist = new ArrayList<>();
		unit = new ArrayList<>();
		user = new ArrayList<>();

		GroupDD group1 = new GroupDD("Tims Geburtstag", 187, new Date());
		GroupDD group2 = new GroupDD("Tims Familie", 188, new Date());
		group.add(group1);
		group.add(group2);

		ProductDD product1 = new ProductDD("Tomate", 191, new Date());
		ProductDD product2 = new ProductDD("Apfel", 192, new Date());
		product.add(product1);
		product.add(product2);

		RetailerDD retailer1 = new RetailerDD("Aldi", 193, new Date());
		RetailerDD retailer2 = new RetailerDD("Kaufland", 194, new Date());
		retailer.add(retailer1);
		retailer.add(retailer2);

		ShoppinglistDD shoppinglist1 = new ShoppinglistDD("Party", 195, new Date());
		ShoppinglistDD shoppinglist2 = new ShoppinglistDD("Familieneinkauf", 196, new Date());
		shoppinglist.add(shoppinglist1);
		shoppinglist.add(shoppinglist2);

		UnitDD unit1 = new UnitDD("Kg", 197, new Date());
		UnitDD unit2 = new UnitDD("Stk", 198, new Date());
		unit.add(unit1);
		unit.add(unit2);

		UserDD user1 = new UserDD("Tim", 199, new Date(), "tim@gmail.com");
		UserDD user2 = new UserDD("Tims Papa", 200, new Date(), "timspapa@gmail.com");
		UserDD user3 = new UserDD("Felix", 201, new Date(), "felix@gmail.com");
		UserDD user4 = new UserDD("Jonas", 202, new Date(), "jonas@gmail.com");
		user.add(user1);
		user.add(user2);
		user.add(user3);
		user.add(user4);

		ListItemDD listitem1 = new ListItemDD(204, new Date(), 2, group1, product2, retailer2, shoppinglist2, unit2,
				user2, true);
		ListItemDD listitem2 = new ListItemDD(207, new Date(), 1, group1, product2, retailer2, shoppinglist2, unit2,
				user2, true);
		ListItemDD listitem3 = new ListItemDD(205, new Date(), 1, group1, product1, retailer1, shoppinglist1, unit1,
				user3, false);
		ListItemDD listitem4 = new ListItemDD(206, new Date(), 2, group1, product2, retailer2, shoppinglist2, unit2,
				user4, true);
		listitem.add(listitem1);
		listitem.add(listitem2);
		listitem.add(listitem3);
		listitem.add(listitem4);

		// Button dahinter setzen, wenn gelickt wird neues bearbeiten Formular, wenn
		// speichern, setten und dann refresh zu altem table

		CellTable<ListItemDD> table = new CellTable<>();

		table.setStyleName("shoppinglist-CellTable");
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		final MultiSelectionModel<ListItemDD> selectionModel = new MultiSelectionModel<ListItemDD>(
				ListItemDD.KEY_PROVIDER);
		table.setSelectionModel(selectionModel, DefaultSelectionEventManager.<ListItemDD>createCheckboxManager());

		Column<ListItemDD, Boolean> checkColumn = new Column<ListItemDD, Boolean>(new CheckboxCell(true, false)) {

			@Override
			public Boolean getValue(ListItemDD object) {

				return object.getCheck();
			}
		};

		checkColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		checkColumn.setCellStyleNames("columncheck");
		table.addColumn(checkColumn, "Check");

		// table.setColumnWidth(checkColumn, 40, Unit.PX);

		TextColumn<ListItemDD> productNameColumn = new TextColumn<ListItemDD>() {
			@Override
			public String getValue(ListItemDD object) {
				return object.getProduct().getName();
			}
		};

		table.addColumn(productNameColumn, "Produkt");

		NumberCell amountCell = new NumberCell();
		Column<ListItemDD, Number> listItemAmountColumn = new Column<ListItemDD, Number>(amountCell) {
			@Override
			public Number getValue(ListItemDD object) {
				return object.getAmount();
			}
		};
		table.addColumn(listItemAmountColumn, "Menge");

		TextColumn<ListItemDD> unitNameColumn = new TextColumn<ListItemDD>() {
			@Override
			public String getValue(ListItemDD object) {
				return object.getUnit().getName();
			}
		};
		table.addColumn(unitNameColumn, "Einheit");

		TextColumn<ListItemDD> retailerNameColumn = new TextColumn<ListItemDD>() {
			@Override
			public String getValue(ListItemDD object) {
				return object.getRetailer().getName();
			}
		};
		table.addColumn(retailerNameColumn, "Haendler");

		TextColumn<ListItemDD> userNameColumn = new TextColumn<ListItemDD>() {
			@Override
			public String getValue(ListItemDD object) {
				return object.getUser().getName();
			}
		};
		table.addColumn(userNameColumn, "Verantwortlicher");

		
		Column<ListItemDD, String> imageColumn = 
			    new Column<ListItemDD, String>(
			        new ClickableTextCell() 
			        {
			            public void render(Context context, 
			                               SafeHtml value, 
			                               SafeHtmlBuilder sb)
			            {
			                sb.appendHtmlConstant("<img width=\"20\" src=\"images/" 
			                                       + value.asString() + "\">");
			            }
			           
			        })
			    
			        {
			            @Override
			            public String getValue(ListItemDD object) {
			                return "edit.png";
			            }
			            public void onBrowserEvent(Context context, Element elem,
			                    ListItemDD object, NativeEvent event) {
			                super.onBrowserEvent(context, elem, object, event); 
			                if ("click".equals(event.getType())) {
			                	
								Window.alert("hu");
			                }
			            }
			        };
			        
			        table.addColumn(imageColumn, "");
		
		
		
		
		// Set the total row count. This isn't strictly necessary,
		// but it affects paging calculations, so its good habit to
		// keep the row count up to date.
		table.setRowCount(listitem.size(), true);

		// Push the data into the widget.
		table.setRowData(0, listitem);

		this.add(table);
	}

}
