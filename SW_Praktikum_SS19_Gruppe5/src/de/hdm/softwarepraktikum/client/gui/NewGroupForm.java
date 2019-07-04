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
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse zur Darstellung eines Formulars, um eine neue Gruppe zu erstellen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class NewGroupForm extends VerticalPanel {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();
	private User u = CurrentUser.getUser();

	private GroupShoppinglistTreeViewModel gstvm = null;
	private GroupShowForm groupShowForm = null;
	private Group newGroup = null;
	private Group oldSelectedGroup = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private Label infoLabel = new Label("Neue Gruppe erstellen");
	private TextBox nameTextBox = new TextBox();

	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button saveButton = new Button("Speichern");
	private Button cancelButton = new Button("Abbrechen");

	public NewGroupForm() {
		
		nameTextBox.setText("Name eingeben...");
		nameTextBox.addClickHandler(new NameTextBoxClickHandler());

		saveButton.addClickHandler(new SaveClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());

		infoLabel.setStyleName("Header");
		buttonPanel.setStyleName("ButtonPanel");
		saveButton.setStyleName("NavButton");
		cancelButton.setStyleName("NavButton");
		
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		
		mainPanel.add(infoLabel);
		mainPanel.add(nameTextBox);
		mainPanel.add(buttonPanel);
	

	}

	/**
	 * Beim Anzeigen werden die Widgets geladen und angeordnet.
	 */
	public void onLoad() {

		RootPanel.get("main").add(mainPanel);
	}

	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}

	public Group getOldSelectedGroup() {
		return oldSelectedGroup;
	}

	public void setOldSelectedGroup(Group oldSelectedGroup) {
		this.oldSelectedGroup = oldSelectedGroup;
	}

	
	/**
	 * ***************************************************************************
	 * ABSCHNITT der Click-/EventHandler
	 * ***************************************************************************
	 */
	
	/**
	 * Beim Betätigen der Speichern-Schalfläche wird das neue <code>Group</code>-Objekt gespeichert.
	 *
	 */
	private class SaveClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (u != null) {
				if (nameTextBox.getValue() == "") {

					Notification.show("Bitte gib einen Namen für die neue Gruppe ein.");
				} else if (nameTextBox.getValue().length() <= 20) {
					groupShowForm = new GroupShowForm();
					shoppinglistAdministration.createGroupFor(u, nameTextBox.getValue(), new NewGroupAsyncCallback());
				} else {
					Notification.show("Bitte gib eine kürzeren Namen ein.");
				}
			}
		}

	}

	/**
	 * Bei Betätigen der Abbrechen-Schaltfläche wird die Gruppenansicht der alten 
	 * Gruppe anzuzeigen.
	 *
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			RootPanel.get("main").clear();
			if (oldSelectedGroup != null) {
				GroupShowForm gsf = new GroupShowForm();
				gsf.setSelected(oldSelectedGroup);
				RootPanel.get("main").add(gsf);
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
			nameTextBox.setText("");
		}
		
	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der Callbacks
	 * ***************************************************************************
	 */
	
	/**
	 * Die neue Gruppe wird dem <code>CellTree</code> hinzugefügt.
	 */
	private class NewGroupAsyncCallback implements AsyncCallback<Group> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Group result) {
			RootPanel.get("main").clear();

			newGroup = result;
			groupShowForm.setSelected(newGroup);
			
			groupShowForm.getGroupHeader().setSelectedGroup(newGroup);
			RootPanel.get("main").add(groupShowForm);
			gstvm.addGroup(result);

			

			
		}

	}

}
