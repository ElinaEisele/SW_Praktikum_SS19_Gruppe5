package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
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
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse zur Darstellung eines Formulars, um einen neuen Händler anzulegen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class NewRetailerForm extends VerticalPanel {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private ShoppinglistHeader shoppinglistHeader = null;
	private ListitemHeader listitemHeader = null;
	private GroupShoppinglistTreeViewModel gstvm = null;

	private Shoppinglist selectedShoppinglist = null;
	private Listitem selectedListitem = null;
	private Group selectedGroup = null;
	private Retailer selectedRetailer = null;
	private User selectedUser = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel actionButtonsPanel = new HorizontalPanel();
	private TextBox retailerNameTextBox = new TextBox();
	private Button confirmButton = new Button("Bestätigen");
	private Button cancelButton = new Button("Abbrechen");
	private Label descriptionLabel = new Label("Neuen Einzelhaendler anlegen");

	public NewRetailerForm() {
		
		retailerNameTextBox.setText("Name eingeben...");
		retailerNameTextBox.addClickHandler(new NameTextBoxClickHandler());
		retailerNameTextBox.setWidth("200px");
		
		cancelButton.setStyleName("NavButton");
		cancelButton.addClickHandler(new CancelClickHandler());

		confirmButton.setStyleName("NavButton");
		confirmButton.addClickHandler(new ConfirmClickHandler());
		
		actionButtonsPanel.add(confirmButton);
		actionButtonsPanel.add(cancelButton);
		
		mainPanel.add(descriptionLabel);
		mainPanel.add(retailerNameTextBox);
		mainPanel.add(actionButtonsPanel);

	}

	/**
	 * In dieser Methode werden die Widgets der Form hinzugefügt.
	 * 
	 */
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

	public Retailer getSelectedRetailer() {
		return selectedRetailer;
	}

	public void setSelectedRetailer(Retailer selectedRetailer) {
		this.selectedRetailer = selectedRetailer;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	/**
	 * ***************************************************************************
	 * Abschnitt der ClickHandler
	 * ***************************************************************************
	 */

	/**
	 * ClickHandler zum schließen der DialogBox.
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			retailerNameTextBox.setText("");
			RootPanel.get("main").clear();

			if (selectedShoppinglist != null && selectedUser != null) {

				ListitemShowForm lsf = new ListitemShowForm();
				lsf.setSelected(selectedListitem);
				lsf.setSelectedShoppinglist(selectedShoppinglist);
				lsf.setSelectedUser(selectedUser);
				lsf.setSelectedGroup(selectedGroup);
				RootPanel.get("main").add(lsf);

			} else if (selectedShoppinglist != null && selectedRetailer != null) {

				ListitemShowForm lsf = new ListitemShowForm();
				lsf.setSelected(selectedListitem);
				lsf.setSelectedShoppinglist(selectedShoppinglist);
				lsf.setSelectedRetailer(selectedRetailer);
				lsf.setSelectedGroup(selectedGroup);
				RootPanel.get("main").add(lsf);

			} else if (selectedGroup != null && selectedShoppinglist != null && selectedRetailer == null
					&& selectedUser == null && selectedListitem == null) {

				NewListitemForm nlf = new NewListitemForm();
				nlf.setSelectedShoppinglist(getSelectedShoppinglist());
				nlf.setSelectedShoppinglist(selectedShoppinglist);

				ShoppinglistShowForm ssf = new ShoppinglistShowForm(shoppinglistHeader, nlf);
				ssf.setSelected(selectedShoppinglist);
				ssf.setSelectedGroup(selectedGroup);

				RootPanel.get("main").add(nlf);

			} else if (selectedListitem != null) {

				ListitemShowForm lsf = new ListitemShowForm();
				lsf.setSelected(selectedListitem);
				lsf.setSelectedShoppinglist(selectedShoppinglist);
				lsf.setSelectedGroup(selectedGroup);
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
					Notification.show("Bitte gib einen Namen für den neuen Einzelhändler an.");
				}

			} else {
				Notification.show("Es wurde keine Shoppinglist ausgewählt.");
			}
		}
	}
	
	/**
	 * Beim Klick in das Text Feld wird dieses geleert.
	 *
	 */
	private class NameTextBoxClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			retailerNameTextBox.setText("");
		}
		
	}

	/**
	 * ***************************************************************************
	 * Abschnitt der Callbacks
	 * ***************************************************************************
	 */

	/**
	 * Textbox inhalt zuruecksetzen und ShoppinglistShowForm aktualisieren
	 */
	private class CreateRetailerCallback implements AsyncCallback<Retailer> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Retailer result) {

			retailerNameTextBox.setText("");
			RootPanel.get("main").clear();

			if (selectedShoppinglist != null && selectedUser != null) {

				ListitemShowForm lsf = new ListitemShowForm();
				lsf.setSelected(selectedListitem);
				lsf.setSelectedShoppinglist(selectedShoppinglist);
				lsf.setSelectedUser(selectedUser);
				lsf.setSelectedGroup(selectedGroup);
				RootPanel.get("main").add(lsf);

			} else if (selectedShoppinglist != null && selectedRetailer != null) {
				ListitemShowForm lsf = new ListitemShowForm();
				lsf.setSelected(selectedListitem);
				lsf.setSelectedShoppinglist(selectedShoppinglist);
				lsf.setSelectedRetailer(selectedRetailer);
				lsf.setSelectedGroup(selectedGroup);
				RootPanel.get("main").add(lsf);

			} else if (selectedGroup != null && selectedShoppinglist != null && selectedRetailer == null
					&& selectedUser == null && selectedListitem == null) {

				NewListitemForm nlf = new NewListitemForm();
				nlf.setSelectedGroup(selectedGroup);
				nlf.setSelectedShoppinglist(selectedShoppinglist);

				ShoppinglistShowForm ssf = new ShoppinglistShowForm(shoppinglistHeader, nlf);
				ssf.setSelected(selectedShoppinglist);
				ssf.setSelectedGroup(selectedGroup);

				RootPanel.get("main").add(nlf);

			} else if (selectedListitem != null) {
				ListitemShowForm lsf = new ListitemShowForm();
				lsf.setSelected(selectedListitem);
				lsf.setSelectedShoppinglist(selectedShoppinglist);
				lsf.setSelectedGroup(selectedGroup);

				RootPanel.get("main").add(lsf);

			}
		}

	}

}
