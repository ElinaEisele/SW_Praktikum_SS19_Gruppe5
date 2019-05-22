package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.ListitemUnit;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse zum Anzeigen eines Formulars, um ein neues Listitem anzulegen .
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class NewListitemForm extends HorizontalPanel {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private Shoppinglist shoppinglistToDisplay = null;
	private ListitemUnit selectedlistitemUnit = null;
	private Retailer selectedRetailer = null;

	private ArrayList<Retailer> retailerArrayList;
	private ArrayList<ListitemUnit> listitemUnitArrayList;

	private NumberFormat decimalFormatter = NumberFormat.getDecimalFormat();

	private Grid shoppinglistGrid;
	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */
	private TextBox productNameTextBox = new TextBox();
	private TextBox amountTextBox = new TextBox();
	private ListBox unitNameListBox = new ListBox();
	private ListBox retailerNameListBox = new ListBox();

	private Button newRetailerButton = new Button("Neu");
	private Button saveButton = new Button("Speichern");
	private Button discardButton = new Button("verwerfen und zurueck");

	/*
	 * Beim Anzeigen werden die anderen Widgets erzeugt. Alle werden in einem Raster
	 * angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen Widgets
	 * bestimmt.
	 */
	public NewListitemForm() {

		/**
		 * Das Grid-Widget erlaubt die Anordnung anderer Widgets in einem Gitter.
		 */
		shoppinglistGrid = new Grid(5, 3);

		Label productNameLabel = new Label("Produkt Name: ");
		shoppinglistGrid.setWidget(0, 0, productNameLabel);
		shoppinglistGrid.setWidget(0, 1, productNameTextBox);

		Label amountLabel = new Label("Menge: ");
		shoppinglistGrid.setWidget(1, 0, amountLabel);
		shoppinglistGrid.setWidget(1, 1, amountTextBox);

		Label unitNameLabel = new Label("Einheit: ");
		shoppinglistGrid.setWidget(2, 0, unitNameLabel);
		shoppinglistGrid.setWidget(2, 1, unitNameListBox);
		unitNameListBox.addChangeHandler(new UnitNameListBoxChangeHandler());

		Label retailerNameLabel = new Label("Händler: ");
		shoppinglistGrid.setWidget(3, 0, retailerNameLabel);
		shoppinglistGrid.setWidget(3, 1, retailerNameListBox);
		shoppinglistGrid.setWidget(3, 2, newRetailerButton);
		retailerNameListBox.addChangeHandler(new RetailerNameListBoxChangeHandler());

		HorizontalPanel actionButtonsPanel = new HorizontalPanel();
		shoppinglistGrid.setWidget(4, 1, actionButtonsPanel);

		saveButton.addClickHandler(new NewListitemClickHandler());
		saveButton.setEnabled(true);
		actionButtonsPanel.add(saveButton);

		discardButton.addClickHandler(new DiscardClickhandler());
		discardButton.setEnabled(true);
		actionButtonsPanel.add(discardButton);

		/**
		 * Zum Befüllen der Dropdown-Liste mit <code>Unit</code>.
		 */
		shoppinglistAdministration.getAllListitemUnits(new GetAllListitemUnitsCallback());

		/**
		 * Befüllen der Dropdown-Liste mit <code>Retailer</code>.
		 */
		shoppinglistAdministration.getAllRetailers(new GetAllRetailersCallback());

	}

	public void onLoad() {
		this.add(shoppinglistGrid);
	}

	/**
	 * Zum Befüllen der Dropdown-Liste mit <code>Unit</code>.
	 */
	private class GetAllListitemUnitsCallback implements AsyncCallback<ArrayList<ListitemUnit>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<ListitemUnit> listitemUnitArrayList) {
			for (int i = 0; i < listitemUnitArrayList.size(); i++) {
				unitNameListBox.addItem(listitemUnitArrayList.get(i).getName());
				selectedlistitemUnit = listitemUnitArrayList.get(0);
			}
		}

	}

	/**
	 * Befüllen der Dropdown-Liste mit <code>Retailer</code>.
	 */
	private class GetAllRetailersCallback implements AsyncCallback<ArrayList<Retailer>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Retailer> retailerArrayList) {
			for (int i = 0; i < retailerArrayList.size(); i++) {
				retailerNameListBox.addItem(retailerArrayList.get(i).getName());
				selectedRetailer = retailerArrayList.get(0);
			}
		}
	}

	/**
	 * ChangeHandler zum erkennen welches <code>Unit</code> Objekt der
	 * Dropdown-Liste ausgewählt wurde und dieses selectedListitemUnit zuordnen .
	 */
	private class UnitNameListBoxChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			int item = unitNameListBox.getSelectedIndex();
			selectedlistitemUnit = listitemUnitArrayList.get(item);
		}
	}

	/**
	 * ChangeHandler zum erkennen welches <code>Retailer</code> Objekt der
	 * Dropdown-Liste ausgewählt wurde und dieses selectedRetailer zuordnen .
	 */
	private class RetailerNameListBoxChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			int item = retailerNameListBox.getSelectedIndex();
			selectedRetailer = retailerArrayList.get(item);
		}
	}

	/**
	 * Clickhandler zum verwerfen der Eingaben und zur Rückkehr zum Shoppinglist
	 * CellTable
	 * 
	 */
	private class DiscardClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			// tbd: Ansicht schließen und zurück zu Shoppinglist CellTable
			// selectedShoppinglist
		}

	}

	/**
	 * Clickhandler zum erstellen des Listitem Objekts
	 * 
	 */
	private class NewListitemClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (shoppinglistToDisplay != null) {
				String productName = productNameTextBox.getText();
				float amount = 0.0F;
				try {
					amount = (float) decimalFormatter.parse(amountTextBox.getText());
				} catch (NumberFormatException nfe) {
					Window.alert("ungültiger Wert!");
					return;
				}
				ListitemUnit listitemUnit = selectedlistitemUnit;
				Retailer retailer = selectedRetailer;

				shoppinglistAdministration.createListitem(shoppinglistToDisplay, productName, amount, listitemUnit,
						retailer, new CreateListitemCallback());
			} else {
				Notification.show("Keine Shoppinglist ausgewaehlt");
			}
		}
	}

	/**
	 * Nach dem erfolgreichen Erstellen wird das Formular geschlossen und die
	 * aktuell ausgewählte Shoppinglist erneut geöffnet.
	 * 
	 */
	private class CreateListitemCallback implements AsyncCallback<Listitem> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Das Anlegen eines neuen Eintrags ist fehlgeschlagen!");
		}

		@Override
		public void onSuccess(Listitem result) {
			if (result != null) {

				RootPanel.get("main").clear();
				// RootPanel.get().add(w);
				// RootPanel.get().add(w);
			}
		}
	}

}
