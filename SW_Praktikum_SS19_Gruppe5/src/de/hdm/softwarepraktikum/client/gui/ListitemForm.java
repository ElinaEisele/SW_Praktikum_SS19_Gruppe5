package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
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
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.ListitemUnit;
import de.hdm.softwarepraktikum.shared.bo.Product;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Formular zur Darstellung des zu ändernden <code>Listitem</code>-Objekt
 * 
 * @author ElinaEisele, JonasWagenknecht
 */

public class ListitemForm extends VerticalPanel {
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private GroupShoppinglistTreeViewModel gstvm = null;
	private ShoppinglistHeader shoppinglistHeader = null;
	private ListitemHeader listitemHeader = null;
	private ListitemShowForm listitemShowForm = null;

	private Shoppinglist selectedShoppinglist = null;
	private Retailer selectedRetailer = null;
	private Listitem selectedListitem = null;
	private Group selectedGroup = null;
	private User selectedUser = null;
	private Product selectedProduct = null;
	private ListitemUnit selectedListitemUnit = new ListitemUnit();
	private Retailer retailerToDisplay = new Retailer();

	private ArrayList<Retailer> retailerArrayList = null;
	private ArrayList<ListitemUnit> listitemUnitArrayList = null;

	private NumberFormat decimalFormatter = NumberFormat.getDecimalFormat();

	private VerticalPanel mainPanel = new VerticalPanel();
	private Grid shoppinglistGrid = null;

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */
	private TextBox productNameTextBox = new TextBox();
	private TextBox amountTextBox = new TextBox();
	private ListBox unitNameListBox = new ListBox();
	private ListBox retailerNameListBox = new ListBox();

	private Button newRetailerButton = new Button("Neuer Einzelhändler hinzufügen");
	private Button saveButton = new Button("Speichern");
	private Button discardButton = new Button("Abbrechen");

	/*
	 * Beim Anzeigen werden die anderen Widgets erzeugt. Alle werden in einem Raster
	 * angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen Widgets
	 * bestimmt.
	 */
	public ListitemForm() {

		/**
		 * Das Grid-Widget erlaubt die Anordnung anderer Widgets in einem Gitter.
		 */
		shoppinglistGrid = new Grid(6, 3);

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
		
		
		newRetailerButton.setStyleName("NavButton");
		newRetailerButton.addClickHandler(new NewRetailerClickhandler());
		retailerNameListBox.addChangeHandler(new RetailerNameListBoxChangeHandler());

		HorizontalPanel actionButtonsPanel = new HorizontalPanel();
		shoppinglistGrid.setWidget(5, 1, actionButtonsPanel);

		saveButton.setStyleName("NavButton");
		saveButton.addClickHandler(new UpdateListitemClickHandler());
		saveButton.setEnabled(true);
		actionButtonsPanel.add(saveButton);

		discardButton.setStyleName("NavButton");
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
		/**
		 * Zum Befüllen der TextBox mit dem Produktname.
		 */
		shoppinglistAdministration.getProductOf(selectedListitem, new GetProductNameCallback());

		/**
		 * Zum Befüllen der TextBox mit der Menge.
		 */
		shoppinglistAdministration.getAmountOf(selectedListitem, new GetAmountCallback());

		/**
		 * Zum Befüllen der TextBox mit der Einheit.
		 */
		shoppinglistAdministration.getListitemUnitOf(selectedListitem, new GetListitemUnitCallback());

		/**
		 * Zum Befüllen der TextBox mit dem Händlername.
		 */
		shoppinglistAdministration.getRetailerOf(selectedListitem, new GetRetailerCallback());

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

	public Shoppinglist getSelectedShoppinglist() {
		return selectedShoppinglist;
	}

	public void setSelectedShoppinglist(Shoppinglist selectedShoppinglist) {
		this.selectedShoppinglist = selectedShoppinglist;
	}

	public Listitem getSelectedListitem() {
		return selectedListitem;
	}

	public void setSelectedListitem(Listitem selectedListitem) {
		this.selectedListitem = selectedListitem;
	}

	public ListitemHeader getListitemHeader() {
		return listitemHeader;
	}

	public void setListitemHeader(ListitemHeader listitemHeader) {
		this.listitemHeader = listitemHeader;
	}

	public Group getselectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public ListitemShowForm getListitemShowForm() {
		return listitemShowForm;
	}

	public void setListitemShowForm(ListitemShowForm listitemShowForm) {
		this.listitemShowForm = listitemShowForm;
	}

	public Retailer getSelectedRetailer() {
		return selectedRetailer;
	}

	public void setSelectedRetailer(Retailer selectedRetailer) {
		this.selectedRetailer = selectedRetailer;
		selectedUser = null;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
		selectedRetailer = null;
	}

	/**
	 * ***************************************************************************
	 * Abschnitt der ClickHandler
	 * ***************************************************************************
	 */

	/**
	 * Nach erfolgreichem Anlegen wird die ListitemSHowForm erneut geöffnet
	 * 
	 */
	private class NewRetailerClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedShoppinglist != null) {
				RootPanel.get("main").clear();

				NewRetailerForm nrf = new NewRetailerForm();
				nrf.setSelectedShoppinglist(selectedShoppinglist);
				nrf.setListitemHeader(listitemHeader);
				nrf.setSelectedListitem(selectedListitem);
				nrf.setSelectedRetailer(selectedRetailer);
				nrf.setSelectedUser(selectedUser);
				nrf.setSelectedGroup(selectedGroup);

				ListitemShowForm lsf = new ListitemShowForm(listitemHeader, nrf);
				lsf.setSelected(selectedListitem);
				lsf.setSelectedShoppinglist(selectedShoppinglist);

				RootPanel.get("main").add(lsf);
			} else {
				Notification.show("Es wurde keine Einkaufsliste ausgewählt.");
			}
		}

	}

	/**
	 * Clickhandler zum verwerfen der Eingaben und zur R�ckkehr zum Shoppinglist
	 * CellTable
	 * 
	 */
	private class DiscardClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			RootPanel.get("main").clear();

			if (selectedShoppinglist != null && selectedUser != null) {

				FilteredShoppinglistCellTable fsct = new FilteredShoppinglistCellTable();

				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
				ssf.setFilteredshoppinglistCellTable(fsct);
				ssf.setSelected(selectedShoppinglist);
				ssf.setSelectedGroup(selectedGroup);
				ssf.setSelectedUser(selectedUser);

				RootPanel.get("main").add(ssf);

			} else if (selectedShoppinglist != null && selectedRetailer != null) {
				FilteredShoppinglistCellTable fsct = new FilteredShoppinglistCellTable();

				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
				ssf.setFilteredshoppinglistCellTable(fsct);
				ssf.setSelected(selectedShoppinglist);
				ssf.setSelectedGroup(selectedGroup);
				ssf.setSelectedRetailer(selectedRetailer);

				RootPanel.get("main").add(ssf);

			} else {
				RootPanel.get("main").clear();
				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
				ssf.setSelected(selectedShoppinglist);
				ssf.setSelectedGroup(selectedGroup);
				RootPanel.get("main").add(ssf);
			}

		}

	}

	/**
	 * Clickhandler zum Aktualisieren des Listitem Objekts
	 * 
	 */
	private class UpdateListitemClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedShoppinglist != null) {

				selectedProduct = new Product();

				shoppinglistAdministration.getProductOf(selectedListitem, new AsyncCallback<Product>() {

					@Override
					public void onFailure(Throwable caught) {
						Notification.show(caught.toString());
					}

					@Override
					public void onSuccess(Product result) {
						selectedProduct = result;
						selectedProduct.setName(productNameTextBox.getText());
						shoppinglistAdministration.save(selectedProduct, new UpdateProductCallback());
					}

				});

				float amount = 0.0F;
				try {
					amount = (float) decimalFormatter.parse(amountTextBox.getText());
				} catch (NumberFormatException nfe) {
					Notification.show("Ungültiger Wert!");
					return;
				}
				ListitemUnit listitemUnit = selectedListitemUnit;
				Retailer retailer = retailerToDisplay;

				selectedListitem.setAmount(amount);
				selectedListitem.setListitemUnitID(listitemUnit.getId());
				selectedListitem.setRetailerID(retailer.getId());

				shoppinglistAdministration.save(selectedListitem, new UpdateListitemCallback());

			} else {
				RootPanel.get("main").clear();

			}
		}
	}

	/**
	 * ***************************************************************************
	 * Abschnitt der Callbacks
	 * ***************************************************************************
	 */

	/**
	 * Zum Befüllen der Dropdown-Liste mit <code>Unit</code>-Objekten.
	 */
	private class GetAllListitemUnitsCallback implements AsyncCallback<ArrayList<ListitemUnit>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<ListitemUnit> result) {
			listitemUnitArrayList = result;
			for (int i = 0; i < result.size(); i++) {
				unitNameListBox.addItem(result.get(i).getName());
				selectedListitemUnit = result.get(0);

			}
		}

	}

	/**
	 * Befüllen der Dropdown-Liste mit <code>Retailer</code>-Objekten.
	 */
	private class GetAllRetailersCallback implements AsyncCallback<ArrayList<Retailer>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Retailer> result) {
			retailerArrayList = result;
			for (int i = 0; i < result.size(); i++) {
				retailerNameListBox.addItem(result.get(i).getName());
				retailerToDisplay = result.get(0);
			}
		}
	}

	/**
	 * ChangeHandler zum erkennen welches <code>Unit</code>-Objekt der
	 * Dropdown-Liste ausgewählt wurde und dieses selectedListitemUnit zuordnen .
	 */
	private class UnitNameListBoxChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {

			int item = unitNameListBox.getSelectedIndex();
			selectedListitemUnit = listitemUnitArrayList.get(item);

		}
	}

	/**
	 * ChangeHandler zum erkennen welches <code>Retailer</code> Objekt der
	 * Dropdown-Liste ausgew�hlt wurde und dieses selectedRetailer zuordnen .
	 */
	private class RetailerNameListBoxChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {

			int item = retailerNameListBox.getSelectedIndex();
			retailerToDisplay = retailerArrayList.get(item);

		}
	}

	/**
	 * Zum Befüllen der TextBox mit dem Produktname.
	 */
	private class GetProductNameCallback implements AsyncCallback<Product> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Product result) {
			productNameTextBox.setText(result.getName());

		}

	}

	/**
	 * Zum Befüllen der TextBox mit der Menge.
	 */
	private class GetAmountCallback implements AsyncCallback<Float> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

		}

		@Override
		public void onSuccess(Float result) {
			amountTextBox.setText(result.toString());
		}

	}

	/**
	 * Zum Bef�llen der TextBox mit der Einheit.
	 */

	private class GetListitemUnitCallback implements AsyncCallback<ListitemUnit> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

		}

		@Override
		public void onSuccess(ListitemUnit result) {
			for (int i = 0; i < listitemUnitArrayList.size(); i++) {
				if (listitemUnitArrayList.get(i).getName() == result.getName()) {
					unitNameListBox.setItemSelected(i, true);
					selectedListitemUnit = listitemUnitArrayList.get(i);
				}
			}

		}

	}

	/**
	 * Zum Befüllen der TextBox mit dem Haendlername.
	 */
	private class GetRetailerCallback implements AsyncCallback<Retailer> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

		}

		@Override
		public void onSuccess(Retailer result) {
			for (int i = 0; i < retailerArrayList.size(); i++) {
				if (retailerArrayList.get(i).getName() == result.getName()) {
					retailerNameListBox.setItemSelected(i, true);
					retailerToDisplay = retailerArrayList.get(i);

				}
			}
		}

	}

	/**
	 * Dieser CallBack ist leer, da die Funktiknalität bereits im
	 * UpdateListitemCallback realisiert wird
	 * 
	 */
	private class UpdateProductCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

		}

		@Override
		public void onSuccess(Void result) {

		}

	}

	/**
	 * Nach dem erfolgreichen Erstellen wird das Formular geschlossen und die
	 * aktuell ausgewählte Shoppinglist erneut geöffnet.
	 * 
	 */
	private class UpdateListitemCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

		}

		@Override
		public void onSuccess(Void result) {
			RootPanel.get("main").clear();

			if (selectedShoppinglist != null && selectedUser != null) {

				FilteredShoppinglistCellTable fsct = new FilteredShoppinglistCellTable();

				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
				ssf.setFilteredshoppinglistCellTable(fsct);
				ssf.setSelected(selectedShoppinglist);
				ssf.setSelectedGroup(selectedGroup);
				ssf.setSelectedUser(selectedUser);

				RootPanel.get("main").add(ssf);

			} else if (selectedShoppinglist != null && selectedRetailer != null) {
				FilteredShoppinglistCellTable fsct = new FilteredShoppinglistCellTable();

				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
				ssf.setFilteredshoppinglistCellTable(fsct);
				ssf.setSelected(selectedShoppinglist);
				ssf.setSelectedGroup(selectedGroup);
				ssf.setSelectedRetailer(selectedRetailer);

				RootPanel.get("main").add(ssf);

			} else {

				RootPanel.get("main").clear();
				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
				ssf.setSelected(selectedShoppinglist);
				ssf.setSelectedGroup(selectedGroup);
				RootPanel.get("main").add(ssf);
			}
			
			shoppinglistAdministration.setLatestEdit(selectedShoppinglist, result, new LatestEditCallback());


		}

	}
	
	/**
	 * Zum Festlegen der letzten Änderung einer <code>Shoppinglist</code>.
	 */
	private class LatestEditCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
