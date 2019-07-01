package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
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
 * Klasse zur Darstellung einer Dialogbox, um bei einem
 * <code>Group</code>-Objekt den Namen zu aendern.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class EditGroupNameForm extends VerticalPanel {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private User u = CurrentUser.getUser();
	private GroupShoppinglistTreeViewModel gstvm = null;
	private GroupHeader groupHeader = new GroupHeader();
	private GroupShowForm groupShowForm;
	private Group selectedGroup = null;
	private Group changedGroup = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private Label infoLabel = new Label("Gruppenname ändern");
	private TextBox newNameTextBox = new TextBox();

	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button saveButton = new Button("Speichern");
	private Button cancelButton = new Button("Abbrechen");

	public EditGroupNameForm() {
		
		newNameTextBox.setText("Neuen Namen eingeben...");
		newNameTextBox.addClickHandler(new NameTextBoxClickHandler());
		newNameTextBox.setWidth("200px");

		cancelButton.addClickHandler(new CancelClickHandler());
		cancelButton.setStyleName("NavButton");
		saveButton.addClickHandler(new SaveClickHandler());
		saveButton.setStyleName("NavButton");
		
		buttonPanel.add(saveButton);	
		buttonPanel.add(cancelButton);
		
		mainPanel.add(infoLabel);
		mainPanel.add(newNameTextBox);
		mainPanel.add(buttonPanel);

	}

	/**
	 * Beim Anzeigen werden alle Widgets geladen und angeordnet.
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

	public GroupHeader getGroupHeader() {
		return groupHeader;
	}

	public void setGroupHeader(GroupHeader groupHeader) {
		this.groupHeader = groupHeader;
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
	 * Bei Betätigen der Abbrechen-Schaltfläche wird die Gruppenansicht wieder
	 * geladen.
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("main").clear();
			GroupShowForm gsf = new GroupShowForm();
			gsf.setSelected(selectedGroup);
			gsf.setGstvm(gstvm);
			RootPanel.get("main").add(gsf);

		}

	}

	/**
	 * Bei Betätigen der Speichern-Schaltfläche wird der neue Namen des
	 * <code>Group</code>-Objekts gespeichert.
	 *
	 */
	private class SaveClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			if (selectedGroup != null && newNameTextBox.getValue().length() <= 18) {

				shoppinglistAdministration.changeNameOf(selectedGroup, newNameTextBox.getValue(),
						new ChangeNameCallback());

			} else {
				Notification.show("Bitte gib eine kürzeren Namen ein");
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
			newNameTextBox.setText("");
		}
		
	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der Callbacks
	 * ***************************************************************************
	 */

	/**
	 * Zum Ändern des Gruppennamens in der Gruppenansicht und im
	 * <code>CellTree</code> und anschließendem Laden der Gruppenansicht.
	 */
	private class ChangeNameCallback implements AsyncCallback<Group> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Group result) {
			selectedGroup = result;
			RootPanel.get("main").clear();
			RootPanel.get("aside").clear();
			GroupShowForm gsf = new GroupShowForm();
			gsf.setSelected(selectedGroup);
			gsf.setGstvm(gstvm);
			RootPanel.get("main").add(gsf);
			NavigatorPanel np = new NavigatorPanel();
			RootPanel.get("aside").add(np);
			gstvm.updateGroup(result);
		}

	}

}
