package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.ClickListener;

//import de.hdm.softwarePraktikum.client.gui.Notification;
import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse, die Such- und Filterfunktionen einer Shoppingliste zusammenfasst.
 * 
 * @author ElinaEisele, JonasWagenknecht
 */

@SuppressWarnings("deprecation")
public class ShoppinglistSearchBar extends HorizontalPanel{
		
	private Grid searchGrid = new Grid(1, 3);
	private MultiWordSuggestOracle searchbar = new MultiWordSuggestOracle();
	private SuggestBox searchSuggestBox = new SuggestBox(searchbar);
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	private Button cancelButton = new Button("\u2716");
	
	// muss noch im Navigator gesetzt werden
	private Shoppinglist selectedShoppinglist;
	private ShoppinglistCellTable shoppinglistCellTable;
	
	
	public ShoppinglistCellTable getShoppinglistCellTable() {
		return shoppinglistCellTable;
	}

	public void setShoppinglistCellTable(ShoppinglistCellTable shoppinglistCellTable) {
		this.shoppinglistCellTable = shoppinglistCellTable;
	}


	public Shoppinglist getSelectedShoppinglist() {
		return selectedShoppinglist;
	}

	public void setSelectedShoppinglist(Shoppinglist selectedShoppinglist) {
		this.selectedShoppinglist = selectedShoppinglist;
	}

	public void onLoad() {
		
		cancelButton.addClickHandler(new CancelClickHandler());
		searchSuggestBox.addKeyDownHandler(new EnterKeyDownHandler());
		searchSuggestBox.addClickListener(new RefreshClickHandler());
		
		cancelButton.setPixelSize(30,  30);
		cancelButton.setStylePrimaryName("cancelSearchButton");
		
		searchSuggestBox.setSize("450px",  "30px");
		searchSuggestBox.getElement().setPropertyString("default", "Suchbegriff eingeben");
		
		searchGrid.setWidget(0, 0, searchSuggestBox);
		searchGrid.setWidget(0, 1, cancelButton);
		
		shoppinglistAdministration.getListitemsOf(selectedShoppinglist, new GetListitemCallback());
		
		this.add(searchGrid);
	}
	
	/**
	 * Implementierung des RefreshClickHandler.In diesem wird die SearchBar aktualisiert, 
	 * sobald  in das Textfeld geklickt wird.
	 */
	@SuppressWarnings("deprecation")
	private class RefreshClickHandler implements ClickListener {

		@Override
		public void onClick(Widget sender) {
			shoppinglistAdministration.getListitemsOf(selectedShoppinglist, new GetListitemCallback());
		}
		
	}
	
	/**
	 * Implementierung des CancleClickHandlers.
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			searchSuggestBox.setValue("");
//			shoppinglistCellTable.refresh();
		}
		
	}
	
	/**
	 * Implementierung des KeyDownHandler Events. In diesem wird nach dem Betätigen der ENTER Taste 
	 * der Suchvorgang gestartet.
	 */
	private class EnterKeyDownHandler implements KeyDownHandler{

		@Override
		public void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				if (searchSuggestBox.getValue().isEmpty()==false) {
//					shoppinglistAdministration.getListitemMapBy(searchSuggestBox.getValue(), selectedShoppinglist, new GetListitemMapCallback();
				}
			}
		}
		
	}
	
	/**
	 * Implementierung der SearchListitemCallBack Klasse. In dieser findet der
	 * RPC-Callback statt, der für die Suche nach Listitems dient.
	 */
	private class GetListitemMapCallback implements AsyncCallback<Map<Shoppinglist, ArrayList<Listitem>>>{

		@Override
		public void onFailure(Throwable caught) {
//			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Map<Shoppinglist, ArrayList<Listitem>> resultMap) {
//			shoppinglistCellTable.showSearchResult(resultMap);
		}
		
	}
	
	private class GetListitemCallback implements AsyncCallback<ArrayList<Listitem>>{

		@Override
		public void onFailure(Throwable caught) {
//			Notification.show(caught.toString());			
		}

		@Override
		public void onSuccess(ArrayList<Listitem> result) {
			for (Listitem l : result) {
//				searchbar.add(l.getProduct().getName());
				
			}
		}
		
	}


}
