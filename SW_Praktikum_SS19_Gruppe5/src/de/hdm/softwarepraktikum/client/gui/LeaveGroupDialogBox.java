package de.hdm.softwarepraktikum.client.gui;

<<<<<<< HEAD
/**
 * Klasse zur Darstellung einer Dialogbox, wenn ein User eine
 * Gruppe verlassen moechte.
 * 
 * @author ElinaEisele, JonasWagenknecht	
 *
 */
public class LeaveGroupDialogBox {
=======
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse zur Darstellung einer Dialogbox, wenn ein User eine Gruppe verlassen
 * moechte.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class LeaveGroupDialogBox extends DialogBox{
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	private User u = CurrentUser.getUser();
	private Group selectedGroup = null;
	
	private GroupShoppinglistTreeViewModel gstvm = null;
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private Label confirmationLabel = new Label("Sind Sie sicher, dass Sie diese Gruppe verlassen möchten?");
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button confirmButton = new Button("Gruppe verlassen");
	private Button cancelButton = new Button("Abbrechen");
	
	public LeaveGroupDialogBox() {
		
		this.setGlassEnabled(true);
		
		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");
		
		cancelButton.addClickHandler(new CancleClickHandler());
		confirmButton.addClickHandler(new ConfirmClickHandler());
		
		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);
		
		mainPanel.add(confirmationLabel);
		mainPanel.add(buttonPanel);
		
		this.add(mainPanel);
		this.center();
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
	
	private class CancleClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			LeaveGroupDialogBox.this.hide();
		}
		
	}
	
	
	private class ConfirmClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if (selectedGroup != null) {
				shoppinglistAdministration.removeUserFromGroup(u, selectedGroup, new RemoveUserCallback());
				LeaveGroupDialogBox.this.hide();
				
			} else {
				Notification.show("Es wurde keine Gruppe ausgewählt.");
			}
		}
		
	}
	
	private class RemoveUserCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Folgender Fehler ist aufgetreten: " + caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			setSelectedGroup(null);
			gstvm.removeGroup(selectedGroup);
			
		
			
			
		}
		
	}
	
>>>>>>> refs/heads/master

}
