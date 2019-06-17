package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse zur Darstellung eines Formulars, um einen neuen Haendler anzulegen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class NewRetailerForm extends VerticalPanel {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private Shoppinglist selectedShoppinglist = null;
	private Listitem selectedListitem = null;
	private Group selectedGroup = null;
	private ShoppinglistHeader shoppinglistHeader;
	private ListitemHeader listitemHeader;
	private VerticalPanel mainPanel = new VerticalPanel();
	private GroupShoppinglistTreeViewModel gstvm;
	private TextBox retailerNameTextBox = new TextBox();

	private Button confirmButton = new Button("Anlegen");
	private Button cancelButton = new Button("Abbrechen");

	private Grid newRetailerGrid;

	public NewRetailerForm() {

		/**
		 * Das Grid-Widget erlaubt die Anordnung anderer Widgets in einem Gitter.
		 */
		newRetailerGrid = new Grid(3, 2);
		mainPanel.add(newRetailerGrid);

		Label descriptionLabel = new Label("Neuen Einzelhaendler anlegen");
		newRetailerGrid.setWidget(0, 0, descriptionLabel);

		Label newRetailerLabel = new Label("Name des Einzelhaendlers: ");
		newRetailerGrid.setWidget(1, 0, newRetailerLabel);
		newRetailerGrid.setWidget(1, 1, retailerNameTextBox);

		HorizontalPanel actionButtonsPanel = new HorizontalPanel();
		newRetailerGrid.setWidget(2, 1, actionButtonsPanel);

		cancelButton.addClickHandler(new CancelClickHandler());
		confirmButton.addClickHandler(new ConfirmClickHandler());
		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");
		actionButtonsPanel.add(confirmButton);
		actionButtonsPanel.add(cancelButton);

	}

	public void onLoad() {
		this.add(mainPanel);
	}

	public void setSelectedShoppinglist(Shoppinglist selectedShoppinglist) {
		this.selectedShoppinglist = selectedShoppinglist;
	}

	public ShoppinglistHeader getShoppinglistHeader() {
		return shoppinglistHeader;
	}

	public void setShoppinglistHeader(ShoppinglistHeader shoppinglistHeader) {
		this.shoppinglistHeader = shoppinglistHeader;
	}

	public Shoppinglist getSelectedShoppinglist() {
		return selectedShoppinglist;
	}

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}

	public ListitemHeader getListitemHeader() {
		return listitemHeader;
	}

	public void setListitemHeader(ListitemHeader listitemHeader) {
		this.listitemHeader = listitemHeader;
	}

	public Listitem getSelectedListitem() {
		return selectedListitem;
	}

	public void setSelectedListitem(Listitem selectedListitem) {
		this.selectedListitem = selectedListitem;
	}

	/**
	 * ClickHandler zum schliessen der DialogBox.
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			retailerNameTextBox.setText("");
			RootPanel.get("main").clear();

			if (selectedGroup != null && selectedShoppinglist != null) {

				shoppinglistHeader = new ShoppinglistHeader();
				shoppinglistHeader.setShoppinglistToDisplay(selectedShoppinglist);
				NewListitemForm nlf = new NewListitemForm();
				nlf.setShoppinglistHeader(shoppinglistHeader);
				nlf.setShoppinglistToDisplay(selectedShoppinglist);
				nlf.setGroupToDisplay(selectedGroup);

				ShoppinglistShowForm ssf = new ShoppinglistShowForm(shoppinglistHeader, nlf);
				ssf.setSelected(selectedShoppinglist);
				ssf.setSelectedGroup(selectedGroup);

				RootPanel.get("main").add(nlf);
			} else if (selectedListitem != null) {

				ListitemShowForm lsf = new ListitemShowForm();
				lsf.setSelected(selectedListitem);
				lsf.setSelectedShoppinglist(selectedShoppinglist);

				RootPanel.get("main").add(lsf);
				
			}
		}
	}

	/**
	 * ClickHandler zum erstellen eines Retailer Objekts.
	 */
	private class ConfirmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedShoppinglist != null) {
				if (retailerNameTextBox.getText() != "") {
					shoppinglistAdministration.createRetailer(retailerNameTextBox.getText(),
							new CreateRetailerCallback());
				} else {
					Window.alert("Besser keinen Einzelhaendler anlegen als einen ohne Name anlegen");
				}

			} else {
				Notification.show("Es wurde keine Shoppinglist ausgewaehlt.");
			}
		}
	}

	/**
	 * Textbox inhalt zuruecksetzen und ShoppinglistShowForm aktualisieren
	 */
	private class CreateRetailerCallback implements AsyncCallback<Retailer> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Folgender Fehler ist aufgetreten: /n" + caught.toString());
		}

		@Override
		public void onSuccess(Retailer result) {

			retailerNameTextBox.setText("");
			RootPanel.get("main").clear();

			if (selectedGroup != null && selectedShoppinglist != null) {
				
				shoppinglistHeader = new ShoppinglistHeader();
				shoppinglistHeader.setShoppinglistToDisplay(selectedShoppinglist);
				NewListitemForm nlf = new NewListitemForm();
				nlf.setShoppinglistHeader(shoppinglistHeader);
				nlf.setShoppinglistToDisplay(selectedShoppinglist);
				nlf.setGroupToDisplay(selectedGroup);

				ShoppinglistShowForm ssf = new ShoppinglistShowForm(shoppinglistHeader, nlf);
				ssf.setSelected(selectedShoppinglist);
				ssf.setSelectedGroup(selectedGroup);

				RootPanel.get("main").add(nlf);
			} else if (selectedListitem != null) {

				ListitemShowForm lsf = new ListitemShowForm();
				lsf.setSelected(selectedListitem);
				lsf.setSelectedShoppinglist(selectedShoppinglist);

				RootPanel.get("main").add(lsf);

			}

		}

	}

}
