package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
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
 * Klasse zur Darstellung einer Dialogbox, um einer Gruppe einen
 * neune User hinzuzufuegen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class AddUserToGroupForm extends VerticalPanel{
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	
	private User u = CurrentUser.getUser();
	private User newGroupMember = null;
	private GroupHeader groupHeader = null;
	private Group selectedGroup = null;
	private GroupShoppinglistTreeViewModel gstvm = null;
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private Label infoLabel = new Label("Neues Gruppenmitglied hinzufügen.");
	private Grid grid = new Grid(1, 2);
	private Label emailLabel = new Label("Gmail-Adresse: ");
	private TextBox emailTextBox = new TextBox();
	
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button saveButton = new Button("Speichern");
	private Button cancelButton = new Button("Abbrechen");
	
	public AddUserToGroupForm() {
		
		grid.setWidget(0, 0, emailLabel);
		grid.setWidget(0, 1, emailTextBox);
		
		saveButton.addClickHandler(new SaveClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
		
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		
		mainPanel.add(infoLabel);
		mainPanel.add(grid);
		mainPanel.add(buttonPanel);
	}
	
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



	private class CancelClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("main").clear();
			GroupShowForm gsf = new GroupShowForm();
			gsf.setSelected(selectedGroup);
			RootPanel.get("main").add(gsf);
		}
		
	}
	
	private class SaveClickHandler implements ClickHandler{

		public void onClick(ClickEvent event) {
			if (selectedGroup != null) {
				shoppinglistAdministration.getUserByMail(emailTextBox.getValue(), new GetUserCallback());
				// neues Gruppenobjekt zurück geben?
				GroupShowForm gsf = new GroupShowForm();
				gsf.setSelected(selectedGroup);
				RootPanel.get("main").clear();
				RootPanel.get("main").add(gsf);
			} else {
				Notification.show("Es wurde keine Gruppe ausgewählt.");
			}
		}
		
	}
	
	private class GetUserCallback implements AsyncCallback<User>{

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("1. Folgender Fehler ist aufgetreten: " + caught.toString());
		}

		@Override
		public void onSuccess(User result) {
			newGroupMember = result;
			shoppinglistAdministration.addUserToGroup(newGroupMember, selectedGroup, new AddUserCallback());

		}
		
	}
	
	private class AddUserCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("2. Es ist folgender Fehler aufgetreten: " + caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			Notification.show("Gruppenmitglied wurde hinzugefügt.");
		}
		
	}

}
