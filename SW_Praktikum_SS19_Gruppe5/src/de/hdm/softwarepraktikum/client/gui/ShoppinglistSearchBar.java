package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

//import de.hdm.softwarePraktikum.client.gui.Notification;
import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse, die Such- und Filterfunktionen einer Shoppingliste zusammenfasst.
 * 
 * @author ElinaEisele, JonasWagenknecht
 */

public class ShoppinglistSearchBar extends VerticalPanel{
		
	private Grid searchGrid = new Grid(1, 3);
	private MultiWordSuggestOracle searchbar = new MultiWordSuggestOracle();
	private SuggestBox searchSuggestBox = new SuggestBox(searchbar);
	private Label searchLabel = new Label("Suche");
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	
	// muss noch im Navigator gesetzt werden
	private Shoppinglist selectedShoppinglist = null;
	private ShoppinglistHeader shoppinglistHeader = null;
//	private ShoppinglistCellTable shoppinglistCellTable;
//	private ListDataProvider<Listitem> dataProvider = new ListDataProvider<>();
//	private CellList<Listitem> cellList = new CellList<Listitem>(new ListitemCell());
	
//	public ShoppinglistCellTable getShoppinglistCellTable() {
//		return shoppinglistCellTable;
//	}
//
//	public void setShoppinglistCellTable(ShoppinglistCellTable shoppinglistCellTable) {
//		this.shoppinglistCellTable = shoppinglistCellTable;
//	}

	
	
	public Shoppinglist getSelectedShoppinglist() {
		return selectedShoppinglist;
	}

	public ShoppinglistHeader getShoppinglistHeader() {
		return shoppinglistHeader;
	}

	public void setShoppinglistHeader(ShoppinglistHeader shoppinglistHeader) {
		this.shoppinglistHeader = shoppinglistHeader;
	}

	public void setSelectedShoppinglist(Shoppinglist selectedShoppinglist) {
		this.selectedShoppinglist = selectedShoppinglist;
	}

	public void onLoad() {
		
		searchSuggestBox.addKeyDownHandler(new EnterKeyDownHandler());
//		searchSuggestBox.getValueBox().addClickHandler(new RefreshClickHandler());
		
		searchSuggestBox.setSize("450px",  "30px");
		searchSuggestBox.getElement().setPropertyString("default", "Suchbegriff eingeben...");
		
		searchGrid.setWidget(0, 0, searchLabel);
		searchGrid.setWidget(0, 1, searchSuggestBox);
		
	//	shoppinglistAdministration.getListitemsNameMapBy(selectedShoppinglist, new AllListitemsCallback());
		
		this.add(searchGrid);
	}
	
	/**
	 * Implementierung des RefreshClickHandler.In diesem wird die SearchBar aktualisiert, 
	 * sobald  in das Textfeld geklickt wird.
	 */
//	private class RefreshClickHandler implements ClickHandler {
//
//		@Override
//		public void onClick(ClickEvent event) {
//			shoppinglistAdministration.getListitemsNameMapBy(selectedShoppinglist, new AllListitemsCallback());
//		}
//
//		
//	}
	
	/**
	 * Implementierung des CancleClickHandlers.
	 */
//	private class CancelClickHandler implements ClickHandler {
//
//		@Override
//		public void onClick(ClickEvent event) {
//			searchSuggestBox.setValue("");
////			shoppinglistCellTable.refresh();
//		}
//		
//	}
	
	/**
	 * Implementierung des KeyDownHandler Events. In diesem wird nach dem Betätigen der ENTER Taste 
	 * der Suchvorgang gestartet.
	 */
	private class EnterKeyDownHandler implements KeyDownHandler{

		@Override
		public void onKeyDown(KeyDownEvent event) {
			
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				
				if (searchSuggestBox.getValue().isEmpty()==false) {
					shoppinglistAdministration.getListitemsNameMapBy(selectedShoppinglist, searchSuggestBox.getValue(), new ListitemsCallback());
					
				}
			}
		}
		
	}
	
	/**
	 * Implementierung der SearchListitemCallBack Klasse. In dieser findet der
	 * RPC-Callback statt, der für die Suche nach Listitems dient.
	 */
//	private class GetListitemMapCallback implements AsyncCallback<ArrayList<Listitem>>{
//
//		@Override
//		public void onFailure(Throwable caught) {
////			Notification.show(caught.toString());
//		}
//
//		@Override
//		public void onSuccess(ArrayList<Listitem> result) {
//			for (Listitem l : result) {
//				
//			}
//		}
//		
//	}
	
	
	private class ListitemsCallback implements AsyncCallback<Map<Listitem, ArrayList<String>>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Map<Listitem, ArrayList<String>> result) {
			if (!result.isEmpty()) {
				RootPanel.get("main").clear();

				shoppinglistHeader.setShoppinglistToDisplay(selectedShoppinglist);
				
				FilteredShoppinglistCellTable fsct = new FilteredShoppinglistCellTable();

				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
				ssf.setShoppinglistHeader(shoppinglistHeader);
				ssf.setFilteredshoppinglistCellTable(fsct);
				ssf.setSelected(selectedShoppinglist);
				fsct.setListitemData(result);


				RootPanel.get("main").add(ssf);
			}
		}
		
	}
	
//	private class AllListitemsCallback implements AsyncCallback<Map<Listitem, String>>{
//
//		@Override
//		public void onFailure(Throwable caught) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onSuccess(Map<Listitem, String> result) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}


}
