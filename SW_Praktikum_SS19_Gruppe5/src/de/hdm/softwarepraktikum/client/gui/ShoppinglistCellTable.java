package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Product;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Celltable with example data
 * 
 * @author JonasWagenknecht, ElinaEisele
 */

public class ShoppinglistCellTable extends VerticalPanel {
	private String productName = new String();
	ArrayList<Product> products;
	ArrayList<Retailer> retailer;
	ArrayList<Listitem> listitems;

	ShoppinglistContent shoppinglistContent;
	ShoppinglistSearchBar shoppinglistSearchBar;
	ShoppinglistCell shoppinglistCell;
	VerticalPanel p1;

	CellTable<Listitem> table;
	ListDataProvider<Listitem> listDataProvider;

	private ShoppinglistAdministrationAsync shoppinglistAdministration = null;
	private Shoppinglist selectedShoppinglist;
	private Map<Shoppinglist, ListDataProvider<Listitem>> listitemDataProviders = null;

	// muss noch über Navigator gesetzt werden

	public Shoppinglist getSelectedShoppinglist() {
		return selectedShoppinglist;
	}

	public ListDataProvider<Listitem> getProvider() {
		return listDataProvider;
	}

	public void setProvider(ListDataProvider<Listitem> provider) {
		this.listDataProvider = provider;
	}

	public void setSelectedShoppinglist(Shoppinglist selectedShoppinglist) {
		this.selectedShoppinglist = selectedShoppinglist;
	}

	public void onLoad() {

		shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();

		listitemDataProviders = new HashMap<Shoppinglist, ListDataProvider<Listitem>>();

		p1 = new VerticalPanel();
		shoppinglistSearchBar = new ShoppinglistSearchBar();
		shoppinglistCell = new ShoppinglistCell();
		shoppinglistContent = new ShoppinglistContent();

		listitems = new ArrayList<>();
		products = new ArrayList<>();
		retailer = new ArrayList<>();

		table = new CellTable<Listitem>();

		table.setStyleName("shoppinglist-CellTable");
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		listDataProvider = (ListDataProvider<Listitem>) table.getKeyProvider();

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
		Column<Listitem, String> productNameColumn = new Column<Listitem, String>((new EditTextCell())) {
			public String getValue(Listitem object) {

				shoppinglistAdministration.getProductnameOf(object, new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(String result) {
						productName = result;

					}

				});
				return productName;
			}
		};
		table.addColumn(productNameColumn, "Produkt");

//		/**
//		 * Amount Column
//		 * 
//		 */
//		NumberCell amountCell = new NumberCell();
//		Column<Listitem, Number> listItemAmountColumn = new Column<Listitem, Number>(amountCell) {
//			@Override
//			public Number getValue(Listitem object) {
//				return object.getAmount();
//			}
//		};
//		table.addColumn(listItemAmountColumn, "Menge");
//
//		/**
//		 * Unit Name Column
//		 * 
//		 */
//		TextColumn<Listitem> unitNameColumn = new TextColumn<Listitem>() {
//			@Override
//			public String getValue(Listitem object) {
//				return object.getUnit().toString();
//			}
//		};
//		table.addColumn(unitNameColumn, "Einheit");
//
//		/**
//		 * Retailer Name Column
//		 * 
//		 */
//		NumberCell retailerNameCell = new NumberCell();
//		Column<Listitem, Number> retailerNameColumn = new Column<Listitem, Number>(retailerNameCell) {
//			@Override
//			public Number getValue(Listitem object) {
//				return object.getRetailerID();
//			}
//		};
//		table.addColumn(retailerNameColumn, "Haendler ID");
//
//		/**
//		 * Clickable edit button containing an image
//		 * 
//		 */
//		Column<Listitem, String> imageColumn = new Column<Listitem, String>(new ClickableTextCell() {
//			public void render(Context context, SafeHtml value, SafeHtmlBuilder sb) {
//				sb.appendHtmlConstant("<img width=\"20\" src=\"images/" + value.asString() + "\">");
//			}
//
//		})
//
//		{
//			@Override
//			public String getValue(Listitem object) {
//				return "edit.png";
//			}
//
//			public void onBrowserEvent(Context context, Element elem, Listitem object, NativeEvent event) {
//				super.onBrowserEvent(context, elem, object, event);
//				if ("click".equals(event.getType())) {
//
//					p1.clear();
//					p1.add(shoppinglistCell);
//
//					Window.alert("clicked");
//				}
//			}
//		};
//
//		table.addColumn(imageColumn, "");
//
		// Set the total row count
		table.setRowCount(listitems.size(), true);
		// Push the data into the widget.
		table.setRowData(0, listitems);

		p1.add(table);
		this.add(p1);
	}

	/**
	 * Diese Methode dient zum Aktualisieren der gesamten Shoppingliste.
	 */
	public void refresh() {
		listDataProvider.getList().clear();
		this.shoppinglistAdministration.getListitemsOf(selectedShoppinglist, new AsyncCallback<ArrayList<Listitem>>() {

			@Override
			public void onFailure(Throwable caught) {
//				Notification.show(caught.toString());				
			}

			@Override
			public void onSuccess(ArrayList<Listitem> listitems) {
				for (Listitem l : listitems) {
					listDataProvider.getList().add(l);
				}
			}

		});

	}

	public void showSearchResult(Map<Shoppinglist, ArrayList<Listitem>> resultMap) {

		this.getProvider().getList().clear();

		if (resultMap.isEmpty() == false) {

			for (Shoppinglist s : resultMap.keySet()) {
//				this.getProvider().getList().add(s);
				listitemDataProviders = new HashMap<Shoppinglist, ListDataProvider<Listitem>>();

				for (Listitem l : resultMap.get(s)) {

//					shoppinglistAdministration.getListitemsNameMapBy(l, new AsyncCallback<Map<Listitem, String>>){
//					// hier fehlt override
//					}
				}
			}
		}
	}

}
