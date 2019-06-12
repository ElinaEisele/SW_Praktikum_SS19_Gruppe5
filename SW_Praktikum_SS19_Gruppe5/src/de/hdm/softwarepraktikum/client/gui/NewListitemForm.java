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
import com.google.gwt.user.client.ui.VerticalPanel;

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

	private GroupShoppinglistTreeViewModel gstvm = null;
	private ShoppinglistHeader shoppinglistHeader;

	private Shoppinglist shoppinglistToDisplay = null;
	private ListitemShowForm shoppinglistShowForm;

	private ListitemUnit selectedlistitemUnit = null;
	private Retailer selectedRetailer = null;

	private ArrayList<Retailer> retailerArrayList;
	private ArrayList<ListitemUnit> listitemUnitArrayList;

	private NumberFormat decimalFormatter = NumberFormat.getDecimalFormat();

	private VerticalPanel mainPanel = new VerticalPanel();
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
		shoppinglistGrid = new Grid(6, 3);

		Label newListitemLabel = new Label("Neuen Eintrag erstellen");
		shoppinglistGrid.setWidget(0, 0, newListitemLabel);

		Label productNameLabel = new Label("Produkt Name: ");
		shoppinglistGrid.setWidget(1, 0, productNameLabel);
		shoppinglistGrid.setWidget(1, 1, productNameTextBox);

		Label amountLabel = new Label("Menge: ");
		shoppinglistGrid.setWidget(2, 0, amountLabel);
		shoppinglistGrid.setWidget(2, 1, amountTextBox);

		Label unitNameLabel = new Label("Einheit: ");
		shoppinglistGrid.setWidget(3, 0, unitNameLabel);
		shoppinglistGrid.setWidget(3, 1, unitNameListBox);
		unitNameListBox.addChangeHandler(new UnitNameListBoxChangeHandler());

		Label retailerNameLabel = new Label("Haendler: ");
		shoppinglistGrid.setWidget(4, 0, retailerNameLabel);
		shoppinglistGrid.setWidget(4, 1, retailerNameListBox);
		shoppinglistGrid.setWidget(4, 2, newRetailerButton);
		newRetailerButton.addClickHandler(new NewRetailerClickhandler());
		retailerNameListBox.addChangeHandler(new RetailerNameListBoxChangeHandler());

		HorizontalPanel actionButtonsPanel = new HorizontalPanel();
		shoppinglistGrid.setWidget(5, 1, actionButtonsPanel);

		saveButton.addClickHandler(new NewListitemClickHandler());
		saveButton.setEnabled(true);
		actionButtonsPanel.add(saveButton);

		discardButton.addClickHandler(new DiscardClickhandler());
		discardButton.setEnabled(true);
		actionButtonsPanel.add(discardButton);

		mainPanel.add(shoppinglistGrid);
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
		RootPanel.get("main").add(mainPanel);
	}

	public ShoppinglistHeader getShoppinglistHeader() {
		return shoppinglistHeader;
	}

	public void setShoppinglistHeader(ShoppinglistHeader shoppinglistHeader) {
		this.shoppinglistHeader = shoppinglistHeader;
	}

	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}

	public Shoppinglist getShoppinglistToDisplay() {
		return shoppinglistToDisplay;
	}

	public void setShoppinglistToDisplay(Shoppinglist shoppinglistToDisplay) {
		this.shoppinglistToDisplay = shoppinglistToDisplay;
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
		public void onSuccess(ArrayList<ListitemUnit> result) {
			listitemUnitArrayList = result;
			for (int i = 0; i < result.size(); i++) {
				unitNameListBox.addItem(result.get(i).getName());
				selectedlistitemUnit = result.get(0);
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
		public void onSuccess(ArrayList<Retailer> result) {
			retailerArrayList = result;
			for (int i = 0; i < result.size(); i++) {
				retailerNameListBox.addItem(result.get(i).getName());
				selectedRetailer = result.get(0);
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

	
	private class NewRetailerClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if (shoppinglistToDisplay != null) {
				RootPanel.get("main").clear();
				NewRetailerForm nrf = new NewRetailerForm();

				shoppinglistHeader = new ShoppinglistHeader();
				shoppinglistHeader.setShoppinglistToDisplay(shoppinglistToDisplay);
				nrf.setShoppinglistHeader(shoppinglistHeader);
				nrf.setSelectedShoppinglist(shoppinglistToDisplay);
				ShoppinglistShowForm ssf = new ShoppinglistShowForm(shoppinglistHeader, nrf);
				ssf.setSelected(shoppinglistToDisplay);
				RootPanel.get("main").add(ssf);
			} else {
				Notification.show("Es wurde keine Shoppinglist ausgewaehlt.");
			}
			
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
			if (shoppinglistToDisplay != null) {
				RootPanel.get("main").clear();
				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
				ssf.setSelected(shoppinglistToDisplay);
				RootPanel.get("main").add(ssf);

			}
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
				Window.alert(shoppinglistToDisplay.getName());
				Window.alert(productName);
				Window.alert(String.valueOf(amount));
				Window.alert(listitemUnit.getName());
				Window.alert(retailer.getName());
				
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
				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
				ssf.setSelected(shoppinglistToDisplay);
				RootPanel.get("main").add(ssf);

			}
		}
	}

}
