package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse zur Darstellung eines Formulars, um einer Gruppe einen
 * neune User hinzuzufuegen und um schon hinzugefuegte User anzuzeigen.
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
	private Label infoLabel = new Label("Neues Gruppenmitglied hinzufügen:");
	private Label emailLabel = new Label("Gmail-Adresse: ");
	private SuggestBox emailSuggestBox = null;
	private MultiWordSuggestOracle allUserMails = new MultiWordSuggestOracle();

	
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button saveButton = new Button("Speichern");
	private Button cancelButton = new Button("Abbrechen");
	
	private FlexTable userFlexTable = new FlexTable();
	
	public AddUserToGroupForm() {
		
		emailSuggestBox = new SuggestBox(allUserMails);

		userFlexTable.setText(0, 0, "Name");
		userFlexTable.setText(0, 1, "G-Mail-Adresse");
		
		buttonPanel.add(emailLabel);
		buttonPanel.add(emailSuggestBox);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		
		mainPanel.add(infoLabel);
		mainPanel.add(userFlexTable);
		mainPanel.add(buttonPanel);
	}
	
	/**
	 * Beim Anzeigen werden alle schon in der Gruppe existierende Nutzer
	 * in die FlexTable geschrieben und die im Konstruktor angeordneten 
	 * weiteren Widgets geladen.
	 */
	public void onLoad() {

		infoLabel.setStyleName("Header");
		userFlexTable.setStyleName("FlexTable");
		
		shoppinglistAdministration.getAllUsers(new AllUsersCallback());
		
		emailSuggestBox.addKeyDownHandler(new EnterKeyDownHandler());
		emailSuggestBox.getElement().setPropertyString("default", "Suchbegriff eingeben...");
		emailSuggestBox.setSize("300px", "30px");
		
		saveButton.addClickHandler(new SaveClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
		shoppinglistAdministration.getUsersOf(selectedGroup, new UsersCallback());
				
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

	/*
	 * Click handlers und abhängige AsyncCallback Klassen.
	 */


	private class CancelClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("main").clear();
			GroupShowForm gsf = new GroupShowForm();
			gsf.setSelected(selectedGroup);
			gsf.setGstvm(gstvm);
			gstvm.setGroupShowForm(gsf);
			RootPanel.get("main").add(gsf);
		}
		
	}
	
	private class SaveClickHandler implements ClickHandler{

		public void onClick(ClickEvent event) {
			if (selectedGroup != null) {
				shoppinglistAdministration.getUserByMail(emailSuggestBox.getValue(), new UserCallback());
				emailSuggestBox.setText("");

			} else {
				Notification.show("Es wurde keine Gruppe ausgewählt.");
			}
		}
		
	}
	
//	private class GetUserCallback implements AsyncCallback<User>{
//
//		@Override
//		public void onFailure(Throwable caught) {
//			Notification.show("Folgender Fehler ist aufgetreten: " + caught.toString());
//		}
//
//		@Override
//		public void onSuccess(User result) {
//			newGroupMember = result;
//			shoppinglistAdministration.addUserToGroup(newGroupMember, selectedGroup, new AddUserCallback());
//			int row = userFlexTable.getRowCount();
//			userFlexTable.setText(row, 0, result.getName());
//			userFlexTable.setText(row, 1, result.getGmailAddress());
//		}
//		
//	}
	
	private class AddUserCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Es ist folgender Fehler aufgetreten: " + caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			Notification.show("Gruppenmitglied wurde hinzugefügt.");
		}
		
	}
	
	private class UsersCallback implements AsyncCallback<ArrayList<User>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(ArrayList<User> result) {
			
			for (int i = 0; i<result.size(); i++) {
				userFlexTable.setText(i+1, 0, result.get(i).getName());
				userFlexTable.setText(i+1, 1, result.get(i).getGmailAddress());
			}
		}
		
	}
	
	private class AllUsersCallback implements AsyncCallback<ArrayList<User>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(ArrayList<User> result) {

			for (User u : result) {
				allUserMails.add(u.getGmailAddress());
			}
		}
		
	}
	
	private class EnterKeyDownHandler implements KeyDownHandler{

		@Override
		public void onKeyDown(KeyDownEvent event) {

			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				
				if (emailSuggestBox.getValue().isEmpty()==false) {
					shoppinglistAdministration.getUserByMail(emailSuggestBox.getValue(), new UserCallback());
					emailSuggestBox.setText("");
				}
			}
		}
		
	}
	
	private class UserCallback implements AsyncCallback<User>{

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Folgender Fehler ist aufgetreten: " + caught.toString());
		}

		@Override
		public void onSuccess(User result) {
			for (int i = 0; i < userFlexTable.getRowCount(); i++) {
				if (userFlexTable.getText(i, 1) == result.getGmailAddress()) {
					return;
				}
			}
			newGroupMember = result;
			shoppinglistAdministration.addUserToGroup(newGroupMember, selectedGroup, new AddUserCallback());
			int row = userFlexTable.getRowCount();
			userFlexTable.setText(row, 0, result.getName());
			userFlexTable.setText(row, 1, result.getGmailAddress());
		}
		
	}

}
