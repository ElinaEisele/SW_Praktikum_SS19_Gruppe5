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
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.ListitemUnit;
import de.hdm.softwarepraktikum.shared.bo.Product;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Formular zur Darstellung des zu aendernden Listitem
 * 
 * @author ElinaEisele, JonasWagenknecht
 */

public class ListitemForm extends VerticalPanel {
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private GroupShoppinglistTreeViewModel gstvm = null;
	private ShoppinglistHeader shoppinglistHeader = null;
	private ListitemHeader listitemHeader = null;
	private Shoppinglist shoppinglistToDisplay = null;
	private ListitemUnit selectedListitemUnit = new ListitemUnit();
	private Retailer selectedRetailer = new Retailer();
	private Listitem selectedListitem = null;
	private ListitemShowForm listitemShowForm;
	private Group groupToDisplay = null;
	Listitem oldListitem = new Listitem();
	private String oldProductname = new String();

	private ArrayList<Retailer> retailerArrayList;
	private ArrayList<ListitemUnit> listitemUnitArrayList;

	private NumberFormat decimalFormatter = NumberFormat.getDecimalFormat();
	private Product selectedProduct;
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
	 * angeordnet, dessen Gr��e sich aus dem Platzbedarf der enthaltenen Widgets
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
		newRetailerButton.addClickHandler(new NewRetailerClickhandler());
		retailerNameListBox.addChangeHandler(new RetailerNameListBoxChangeHandler());

		HorizontalPanel actionButtonsPanel = new HorizontalPanel();
		shoppinglistGrid.setWidget(5, 1, actionButtonsPanel);

		saveButton.addClickHandler(new UpdateListitemClickHandler());
		saveButton.setEnabled(true);
		actionButtonsPanel.add(saveButton);

		discardButton.addClickHandler(new DiscardClickhandler());
		discardButton.setEnabled(true);
		actionButtonsPanel.add(discardButton);

		mainPanel.add(shoppinglistGrid);

		/**
		 * Zum Bef�llen der Dropdown-Liste mit <code>Unit</code>.
		 */
		shoppinglistAdministration.getAllListitemUnits(new GetAllListitemUnitsCallback());

		/**
		 * Bef�llen der Dropdown-Liste mit <code>Retailer</code>.
		 */
		shoppinglistAdministration.getAllRetailers(new GetAllRetailersCallback());
	}

	public void onLoad() {
		/**
		 * Zum Bef�llen der TextBox mit dem Produktname.
		 */
		shoppinglistAdministration.getProductOf(selectedListitem, new GetProductNameCallback());

		/**
		 * Zum Bef�llen der TextBox mit der Menge.
		 */
		shoppinglistAdministration.getAmountOf(selectedListitem, new GetAmountCallback());

		/**
		 * Zum Bef�llen der TextBox mit der Einheit.
		 */
		shoppinglistAdministration.getListitemUnitOf(selectedListitem, new GetListitemUnitCallback());

		/**
		 * Zum Bef�llen der TextBox mit dem Haendlername.
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

	public Shoppinglist getShoppinglistToDisplay() {
		return shoppinglistToDisplay;
	}

	public void setShoppinglistToDisplay(Shoppinglist shoppinglistToDisplay) {
		this.shoppinglistToDisplay = shoppinglistToDisplay;
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

	public Group getGroupToDisplay() {
		return groupToDisplay;
	}

	public void setGroupToDisplay(Group groupToDisplay) {
		this.groupToDisplay = groupToDisplay;
	}

	public ListitemShowForm getListitemShowForm() {
		return listitemShowForm;
	}

	public void setListitemShowForm(ListitemShowForm listitemShowForm) {
		this.listitemShowForm = listitemShowForm;
	}

	/**
	 * Zum Bef�llen der Dropdown-Liste mit <code>Unit</code>.
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
				selectedListitemUnit = result.get(0);
				
			}
		}

	}

	/**
	 * Bef�llen der Dropdown-Liste mit <code>Retailer</code>.
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
	 * Dropdown-Liste ausgew�hlt wurde und dieses selectedListitemUnit zuordnen .
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
			selectedRetailer = retailerArrayList.get(item);
			
		}
	}
	
	/**
	 * Zum Befuellen der TextBox mit dem Produktname.
	 */
	private class GetProductNameCallback implements AsyncCallback<Product> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Product result) {
			productNameTextBox.setText(result.getName());

		}

	}

	/**
	 * Zum Bef�llen der TextBox mit der Menge.
	 */
	private class GetAmountCallback implements AsyncCallback<Float> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

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
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ListitemUnit result) {
			for (int i = 0; i < listitemUnitArrayList.size(); i++) {
				if (listitemUnitArrayList.get(i).getName() == result.getName()) {
					unitNameListBox.setItemSelected(i, true);
					selectedListitemUnit =listitemUnitArrayList.get(i);
				}
			}

		}

	}

	/**
	 * Zum Bef�llen der TextBox mit dem Haendlername.
	 */
	private class GetRetailerCallback implements AsyncCallback<Retailer> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Retailer result) {
			for (int i = 0; i < retailerArrayList.size(); i++) {
				if (retailerArrayList.get(i).getName() == result.getName()) {
					retailerNameListBox.setItemSelected(i, true);
					selectedRetailer = retailerArrayList.get(i);
					
				}
			}
		}

	}

	

	

	private class NewRetailerClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (shoppinglistToDisplay != null) {
				RootPanel.get("main").clear();

				listitemHeader = new ListitemHeader();
				listitemHeader.setShoppinglistToDisplay(shoppinglistToDisplay);
				listitemHeader.setListitemToDisplay(selectedListitem);
				NewRetailerForm nrf = new NewRetailerForm();
				nrf.setSelectedShoppinglist(shoppinglistToDisplay);
				nrf.setListitemHeader(listitemHeader);
				nrf.setSelectedListitem(selectedListitem);

				ListitemShowForm lsf = new ListitemShowForm(listitemHeader, nrf);
				lsf.setSelected(selectedListitem);
				lsf.setSelectedShoppinglist(shoppinglistToDisplay);

				RootPanel.get("main").add(lsf);
			} else {
				Notification.show("Es wurde keine Shoppinglist ausgewaehlt.");
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
			if (shoppinglistToDisplay != null) {
				RootPanel.get("main").clear();
				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
				ssf.setSelected(shoppinglistToDisplay);
				ssf.setSelectedGroup(groupToDisplay);
				RootPanel.get("main").add(ssf);
			} else {
				Notification.show("Keine Einkaufsliste ausgewaehlt");

			}
		}

	}

	/**
	 * Clickhandler zum erstellen des Listitem Objekts
	 * 
	 */
	private class UpdateListitemClickHandler implements ClickHandler {

		
		
		@Override
		public void onClick(ClickEvent event) {
			if (shoppinglistToDisplay != null) {
				
				selectedProduct = new Product();
				
				shoppinglistAdministration.getProductOf(selectedListitem, new AsyncCallback<Product>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
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
					Window.alert("ungueltiger Wert!");
					return;
				}
				ListitemUnit listitemUnit = selectedListitemUnit;
				Retailer retailer = selectedRetailer;
								
				
				oldListitem.setAmount(selectedListitem.getAmount());
				oldListitem.setListitemUnitID(selectedListitem.getListitemUnitID());
				oldListitem.setRetailerID(selectedListitem.getRetailerID());
				
				
				selectedListitem.setAmount(amount);
				selectedListitem.setListitemUnitID(listitemUnit.getId());
				selectedListitem.setRetailerID(retailer.getId());

				// Falls das geaenderte Listitem ein Standard-Listitem ist, wird dieses auch in den anderen Shoppinglists angepasst.
				if(selectedListitem.isStandard()) {
					shoppinglistAdministration.saveStandardListitem(oldListitem, selectedListitem, new UpdateListitemCallback());
				}
				else {
					shoppinglistAdministration.save(selectedListitem, new UpdateListitemCallback());
				}

				
			} else {
				RootPanel.get("main").clear();

			}
		}
	}

	private class UpdateProductCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
//			RootPanel.get("main").clear();
//			ShoppinglistShowForm ssf = new ShoppinglistShowForm();
//			ssf.setSelected(shoppinglistToDisplay);
//			ssf.setSelectedGroup(groupToDisplay);
//			RootPanel.get("main").add(ssf);
//			Notification.show("Keine Shoppinglist ausgewaehlt");
			
		}
		
	}
	
	/**
	 * Nach dem erfolgreichen Erstellen wird das Formular geschlossen und die
	 * aktuell ausgew�hlte Shoppinglist erneut ge�ffnet.
	 * 
	 */
	private class UpdateListitemCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Das Aktualisieren des Listitems ist fehlgeschlagen!");

		}

		@Override
		public void onSuccess(Void result) {
			RootPanel.get("main").clear();
			ShoppinglistShowForm ssf = new ShoppinglistShowForm();
			ssf.setSelected(shoppinglistToDisplay);
			ssf.setSelectedGroup(groupToDisplay);
			RootPanel.get("main").add(ssf);
			
		}

	}
}
