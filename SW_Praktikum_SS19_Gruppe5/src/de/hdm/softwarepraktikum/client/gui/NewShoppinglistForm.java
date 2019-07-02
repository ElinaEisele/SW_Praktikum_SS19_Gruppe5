package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
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
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse zur Darstellung eines Formulars, um eine neue <code>Soppinglist</code>
 * anzulegen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class NewShoppinglistForm extends VerticalPanel {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private GroupShoppinglistTreeViewModel gstvm = null;
	private Group selectedGroup = null;
	private GroupHeader groupHeader = null;
	private ShoppinglistShowForm shoppinglistShowForm = new ShoppinglistShowForm();
	private Shoppinglist selectedShoppinglist = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private Label infoLabel = new Label("Neue Einkaufsliste erstellen");
	private TextBox nameTextBox = new TextBox();

	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button saveButton = new Button("Speichern");
	private Button cancelButton = new Button("Abbrechen");

	public NewShoppinglistForm() {

		nameTextBox.setText("Name eingeben...");
		nameTextBox.addClickHandler(new NameTextBoxClickHandler());

		saveButton.addClickHandler(new SaveClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
		
		saveButton.setStyleName("NavButton");
		cancelButton.setStyleName("NavButton");

		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);

		mainPanel.add(infoLabel);
		mainPanel.add(nameTextBox);
		mainPanel.add(buttonPanel);

	}

	/**
	 * Laden und Anordnen der Widgets.
	 */
	public void onLoad() {

		RootPanel.get("main").add(mainPanel);

	}

	public GroupHeader getGroupHeader() {
		return groupHeader;
	}

	public void setGroupHeader(GroupHeader groupHeader) {
		this.groupHeader = groupHeader;
	}

	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der Click-/EventHandler
	 * ***************************************************************************
	 */

	/**
	 * Bei Betätigen der Speichern-Schaltfläche wird das neue
	 * <code>Shoppinglist</code>-Objekt angelegt.
	 *
	 */
	private class SaveClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedGroup != null) {
				if (nameTextBox.getValue() == "") {
					
				} else if (nameTextBox.getValue().length() <= 23) {

					shoppinglistAdministration.createShoppinglistFor(selectedGroup, nameTextBox.getValue(),
							new NewShoppinglistAsyncCallback());

				}
			} else {
				Notification.show("Bitte gib eine kürzeren Namen ein");
			}
		}

	}

	/**
	 * Bei Betätigen der Abbrechen-Schaltfläche wird die Gruppenansicht wieder
	 * geladen.
	 *
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedGroup != null) {
				RootPanel.get("main").clear();
				GroupShowForm gsf = new GroupShowForm();
				gsf.setSelected(selectedGroup);
				gsf.setGstvm(gstvm);
		//		gstvm.setGroupShowForm(gsf);
				
				RootPanel.get("main").add(gsf);
			}
		}

	}

	/**
	 * Beim Klick in das Text Feld wird dieses geleert.
	 *
	 */
	private class NameTextBoxClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			nameTextBox.setText("");
		}

	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der Callbacks
	 * ***************************************************************************
	 */

	/**
	 * Die neue Gruppe wird im <code>CellTree</code> hinzugefügt.
	 *
	 */
	private class NewShoppinglistAsyncCallback implements AsyncCallback<Shoppinglist> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Shoppinglist result) {

			selectedShoppinglist = result;

			shoppinglistShowForm.setSelected(result);
			shoppinglistShowForm.setSelectedGroup(selectedGroup);
			RootPanel.get("main").clear();
			RootPanel.get("main").add(shoppinglistShowForm);

			gstvm.setSelectedShoppinglist(selectedShoppinglist);
			gstvm.getSelectionModel().setSelected(selectedShoppinglist, true);

		}

	}

}
