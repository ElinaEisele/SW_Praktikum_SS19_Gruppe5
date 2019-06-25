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

import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse, die Such- und Filterfunktionen einer Shoppingliste zusammenfasst.
 * 
 * @author ElinaEisele, JonasWagenknecht
 */

public class ShoppinglistSearchBar extends VerticalPanel {

	private Grid searchGrid = new Grid(1, 3);
	private MultiWordSuggestOracle searchbar = new MultiWordSuggestOracle();
	private SuggestBox searchSuggestBox = null;
	private Button searchButton = new Button("Suche");
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private Shoppinglist selectedShoppinglist = null;
	private Group selectedGroup = null;
	private ShoppinglistHeader shoppinglistHeader = null;

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

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	/**
	 * Beim Anzeigen werden die Widgets geladen und angeordnet.
	 */
	public void onLoad() {

		shoppinglistAdministration.getListitemsNameMapBy(selectedShoppinglist, new AllListitemsCallback());

		searchSuggestBox = new SuggestBox(searchbar);
		searchSuggestBox.addKeyDownHandler(new EnterKeyDownHandler());
		searchSuggestBox.getValueBox().addClickHandler(new RefreshClickHandler());
		searchSuggestBox.getElement().setPropertyString("default", "Suchbegriff eingeben...");
		searchSuggestBox.setSize("450px", "30px");

		searchButton.addClickHandler(new SearchClickHandler());

		searchGrid.setWidget(0, 0, searchSuggestBox);
		searchGrid.setWidget(0, 1, searchButton);

		this.add(searchGrid);
	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der Click-/EventHandler
	 * ***************************************************************************
	 */

	/**
	 * Implementierung des RefreshClickHandler.In diesem wird die SearchBar
	 * aktualisiert, sobald in das Textfeld geklickt wird.
	 */
	private class RefreshClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			shoppinglistAdministration.getListitemsNameMapBy(selectedShoppinglist, new AllListitemsCallback());
		}

	}

	/**
	 * Implementierung des CancleClickHandlers.
	 */
	private class SearchClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			shoppinglistAdministration.getListitemsNameMapBy(selectedShoppinglist, searchSuggestBox.getValue(),
					new ListitemsCallback());

		}

	}

	/**
	 * Implementierung des KeyDownHandler Events. In diesem wird nach dem Betätigen
	 * der ENTER Taste der Suchvorgang gestartet.
	 */
	private class EnterKeyDownHandler implements KeyDownHandler {

		@Override
		public void onKeyDown(KeyDownEvent event) {

			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {

				if (searchSuggestBox.getValue().isEmpty() == false) {
					shoppinglistAdministration.getListitemsNameMapBy(selectedShoppinglist, searchSuggestBox.getValue(),
							new ListitemsCallback());

				}
			}
		}

	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der Callbacks
	 * ***************************************************************************
	 */

	/**
	 * Die gefilterte <code>Shoppinglist</code> wird geladen.
	 *
	 */
	private class ListitemsCallback implements AsyncCallback<Map<Listitem, ArrayList<String>>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Map<Listitem, ArrayList<String>> result) {
			if (!result.isEmpty()) {
				RootPanel.get("main").clear();

				shoppinglistHeader.setSelectedShoppinglist(selectedShoppinglist);

				FilteredShoppinglistCellTable fsct = new FilteredShoppinglistCellTable();

				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
				ssf.setShoppinglistHeader(shoppinglistHeader);
				ssf.setFilteredshoppinglistCellTable(fsct);
				ssf.setSelected(selectedShoppinglist);
				ssf.setSelectedGroup(selectedGroup);
				fsct.setListitemData(result);

				RootPanel.get("main").add(ssf);
			}
		}

	}

	/**
	 * Alle Einträge einer <code>Shoppinglist</code> werden dem
	 * <code>MultiWordSuggestOracle</code>-Objekt als Vorschläge für die Suche
	 * hinzugefügt.
	 *
	 */
	private class AllListitemsCallback implements AsyncCallback<Map<Listitem, String>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Map<Listitem, String> result) {
			for (Listitem key : result.keySet()) {
				searchbar.add(result.get(key));
			}
		}

	}

}
