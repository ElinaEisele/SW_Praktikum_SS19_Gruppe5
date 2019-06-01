package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.ListitemUnit;
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
	private String unitName = new String();
	private Label retailerName = new Label("Uff");
	private Label retailerName2 = new Label("Uff2");

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

	// muss noch Ã¼ber Navigator gesetzt werden

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

		shoppinglistAdministration.getListitemsOf(selectedShoppinglist, new AsyncCallback<ArrayList<Listitem>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Listitems konnten nicht geladen werden");
			}

			@Override
			public void onSuccess(ArrayList<Listitem> result) {
				listitems = result;
				Notification.show("Listitems erfolgreich geladen");
			}
		});

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
		ListitemUnit u1 = new ListitemUnit();

		Listitem listitem1 = new Listitem(2.0f, u1);
		Listitem listitem2 = new Listitem(2.0f, u1);
		Listitem listitem3 = new Listitem(2.0f, u1);
//		Listitem listitem2 = new Listitem(0.5f, ListitemUnit.Kg, retailer2, product1);	
//		Listitem listitem3 = new Listitem(5.0f, ListitemUnit.St, retailer2, product2);	
//		Listitem listitem4 = new Listitem(1.0f, ListitemUnit.L, retailer1, product2);	
		listitems.add(listitem1);
		listitems.add(listitem2);
		listitems.add(listitem3);
//		listitems.add(listitem4);

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
		table.setColumnWidth(checkColumn, 40, Unit.PX);


	

		/**
		 * Retailer Name Column DoTo: Colum creation in on Success machen, arraylist mit
		 * allen listitem objekten und dann for schleife geändert: gstvm zeile 147
		 * RootPanel.get("main").add(shoppinglist/groupShowForm); shoppinglistcontent
		 * z30 auskommentiert
		 */

		for (int i = 0; i < listitems.size(); i++) {

			
			/**
			 * Unit Name Column
			 * 
			 */
			shoppinglistAdministration.getListitemUnitOf(listitems.get(i), new AsyncCallback<ListitemUnit>() {

				@Override
				public void onFailure(Throwable caught) {
					

				}

				@Override
				public void onSuccess(ListitemUnit result) {
					Column<Listitem, String> unitNameColumn = new Column<Listitem, String>((new EditTextCell())) {
						@Override
						public String getValue(Listitem object) {
							
							return unitName;
						}
					};
					table.addColumn(unitNameColumn, "Einheit");
				}

			});
		
			/**
			 * Amount Column
			 * 
			 */
			shoppinglistAdministration.getAmountOf(listitems.get(i), new AsyncCallback<Float>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(Float result) {
					
					NumberCell amountCell = new NumberCell();
					Column<Listitem, Number> listItemAmountColumn = new Column<Listitem, Number>(amountCell) {
						@Override
						public Number getValue(Listitem object) {
							return object.getAmount();
						}
					};
					table.addColumn(listItemAmountColumn, "Menge");

				}

			});

			/**
			 * Product Name Column
			 * 
			 */
			shoppinglistAdministration.getProductnameOf(listitems.get(i), new AsyncCallback<String>() {

				@Override
				public void onFailure(Throwable caught) {
					productName = "setzen fehlgeschlagen";

				}

				@Override
				public void onSuccess(String result) {
					productName = result;

					Column<Listitem, String> productNameColumn = new Column<Listitem, String>((new TextCell())) {
						public String getValue(Listitem object) {

							return productName;
						}
					};
					table.addColumn(productNameColumn, "Produkt");

				}

			});

			/**
			 * Retailer Name Column
			 * 
			 */
			shoppinglistAdministration.getRetailerOf(listitems.get(i), new AsyncCallback<Retailer>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(Retailer result) {

					retailerName.setText(retailerName2.getText());

					Column<Listitem, String> retailerNameColumn = new Column<Listitem, String>((new TextCell())) {
						@Override
						public String getValue(Listitem object) {

							return retailerName.getText();
						}
					};
					table.addColumn(retailerNameColumn, "Haendler");

					if (table.getColumn(1).toString() == "Haendler") {
						table.removeColumn(1);

					} else {
						// table.addColumn(retailerNameColumn, "Haendler");
					}

				}

			});

		}

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
					p1.add(table);

					Window.alert("clicked");
				}
			}
		};

		table.addColumn(imageColumn, "Edit");

		// Set the total row count
		table.setRowCount(listitems.size(), true);
		// Push the data into the widget.
		table.setRowData(0, listitems);

		p1.add(table);
		this.add(p1);
	}

//	/**
//	 * Diese Methode dient zum Aktualisieren der gesamten Shoppingliste.
//	 */
//	public void refresh() {
//		listDataProvider.getList().clear();
//		this.shoppinglistAdministration.getListitemsOf(selectedShoppinglist, new AsyncCallback<ArrayList<Listitem>>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
////				Notification.show(caught.toString());				
//			}
//
//			@Override
//			public void onSuccess(ArrayList<Listitem> listitems) {
//				for (Listitem l : listitems) {
//					listDataProvider.getList().add(l);
//				}
//			}
//
//		});
//
//	}

//	public void showSearchResult(Map<Shoppinglist, ArrayList<Listitem>> resultMap) {
//
//		this.getProvider().getList().clear();
//
//		if (resultMap.isEmpty() == false) {
//
//			for (Shoppinglist s : resultMap.keySet()) {
////				this.getProvider().getList().add(s);
//				listitemDataProviders = new HashMap<Shoppinglist, ListDataProvider<Listitem>>();
//
//				for (Listitem l : resultMap.get(s)) {
//
////					shoppinglistAdministration.getListitemsNameMapBy(l, new AsyncCallback<Map<Listitem, String>>){
////					// hier fehlt override
////					}
//				}
//			}
//		}
//	}

}
