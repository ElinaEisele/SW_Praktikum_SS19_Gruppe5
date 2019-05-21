package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse zur Darstellung einer Dialogbox, wenn ein User alle Users anzeigen
 * moechte.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class ShowUsersDialogBox extends DialogBox{
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
		
	private Group selectedGroup;
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private Label infoLabel = new Label();
	private FlexTable usersFlexTable = new FlexTable();
	private Button cancelButton = new Button("Schließen");
	
	public ShowUsersDialogBox() {
		
//		shoppinglistAdministration.getUsersOf(selectedGroup, new ShowUsersCallback());
		
		this.setGlassEnabled(true);
				
		cancelButton.setStylePrimaryName("cancelButton");
		cancelButton.addClickHandler(new CancelClickHandler());
		
		infoLabel.setText("Hier seteht der Gruppenname");
		
//		infoLabel.setText("Mitglieder der Gruppe " + selectedGroup.getName() + ":");

		mainPanel.add(infoLabel);
//		mainPanel.add(usersFlexTable);
		mainPanel.add(cancelButton);
		
		this.add(mainPanel);
		this.center();
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
			ShowUsersDialogBox.this.hide();
		}
		
	}
	
	private class ShowUsersCallback implements AsyncCallback<ArrayList<User>>{

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Folgender Fehler ist aufgetreten: \n" + caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<User> result) {
			usersFlexTable.setText(0, 0, "Name");
			usersFlexTable.setText(0, 1, "Email");
			
			int i = 1;
			for (User u : result) {
				usersFlexTable.setText(i, 0, u.getName());
				usersFlexTable.setText(i, 1, u.getGmailAddress());
				i++;
			}
		}
		
	}

}
