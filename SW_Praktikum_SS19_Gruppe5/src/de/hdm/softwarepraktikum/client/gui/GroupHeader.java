package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse für die Anordnung der Buttons im <code>GroupHeader</code>, der in der
 * <code>GroupShowForm</code> angezeigt wird.
 * 
 * @author ElinaEisele, JonasWagenknecht
 */

public class GroupHeader extends HorizontalPanel {
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	private Group groupToDisplay = null;
//	private GroupShoppinglistTreeViewModel gstvm = null;
	
	private User u = CurrentUser.getUser();
	
	private Label groupHeaderLabel = new Label("Gruppe");

	private Button newShoppinglist = new Button();
	private Button addUser = new Button();
	private Button leaveGroup = new Button();
	private Button editGroupName = new Button();
	private Button deleteGroup = new Button();
	private Button showUsers = new Button();
	private Button ShowRetailers = new Button();
	
	public GroupHeader() {
		
		Image newShoppinglistImg = new Image();
		newShoppinglistImg.setUrl("images/clipboard.png");
		newShoppinglistImg.setSize("32px", "32px");
		newShoppinglist.getElement().appendChild(newShoppinglistImg.getElement());
		newShoppinglist.addClickHandler(new NewShoppinglistClickHandler());
		
		Image addUserImg = new Image();
		addUserImg.setUrl("images/add-user.png");
		addUserImg.setSize("32px", "32px");
		addUser.getElement().appendChild(addUserImg.getElement());
		addUser.addClickHandler(new AddUserClickHandler());

		Image leaveGroupImg = new Image();
		leaveGroupImg.setUrl("images/logout.png");
		leaveGroupImg.setSize("32px", "32px");
		leaveGroup.getElement().appendChild(leaveGroupImg.getElement());
		leaveGroup.addClickHandler(new LeaveGroupClickHandler());
		//leaveGroup.setStyleName("leaveGroupButton");
		
		Image editGroupImg = new Image();
		editGroupImg.setUrl("images/edit.png");
		editGroupImg.setSize("32px", "32px");
		editGroupName.getElement().appendChild(editGroupImg.getElement());
		editGroupName.addClickHandler(new EditClickHandler());

		Image deleteImg = new Image();
		deleteImg.setUrl("images/delete.png");
		deleteImg.setSize("32px", "32px");
		deleteGroup.getElement().appendChild(deleteImg.getElement());
		deleteGroup.addClickHandler(new DeleteGroupClickHandler());
		
		Image showUsersImg = new Image();
		showUsersImg.setUrl("images/user.png");
		showUsersImg.setSize("32px", "32px");
		showUsers.getElement().appendChild(showUsersImg.getElement());
		showUsers.addClickHandler(new ShowUsersClickHandler());
		
		
		
	}

	public void onLoad() {

		this.add(groupHeaderLabel);
		this.add(newShoppinglist);
		this.add(addUser);
		this.add(leaveGroup);
		this.add(editGroupName);
		this.add(deleteGroup);


	}
	
	/**
	 * Sobald eine <code>Group</code> ausgewählt wird, werden die "Edit" Buttons daktiviert
	 * und das Label mit den entsprechenden Informationen befüllt.
	 * 
	 * @param cl das zu setztende <code>ContactList</code> Objekt.
	 */
	public void setSelected(Group g) {
		if (g != null) {
			groupToDisplay = g;
			groupHeaderLabel.setText(groupToDisplay.getName());
			
		} else {
			this.clear();
		}
	}
	

	
	/**
	 * ***************************************************************************
	 * ABSCHNITT der ClickHandler
	 * ***************************************************************************
	 */
	private class NewShoppinglistClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if (groupToDisplay != null) {
				NewShoppinglistForm nsf = new NewShoppinglistForm(groupToDisplay);
//				nsf.setGstvm(GroupHeader.this.gstvm);
				// lieber Dialogbox zum Name erstellen? 
				// dann dahin leiten? bzw Shoppinglisten Übersicht zeigen
			}
		}
		
	}
	
	private class AddUserClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if (groupToDisplay != null) {
				AddUserToGroupDialogBox audb = new AddUserToGroupDialogBox();
//				audb.setGstvm(GroupHeader.this.gstvm);
				audb.show();
			} else {
				Notification.show("Es wurde keine Gruppe ausgewählt.");
				// macht das Sinn?
			}
		}
		
	}
	
	private class LeaveGroupClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if (groupToDisplay != null) {
				LeaveGroupDialogBox ldb = new LeaveGroupDialogBox();
//				ldb.setGstvm(GroupHeader.this.gstvm);
				ldb.show();
			} else {
				Notification.show("Es wurde keine Gruppe ausgewählt.");
			}
		}
		
	}
	
	/**
	 * ClickHandler dient dem erzeugen einer <code>DeleteGroupDialogBox</code> Instanz.
	 */
	private class DeleteGroupClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			DeleteGroupDialogBox ddb = new DeleteGroupDialogBox();
			ddb.setSelectedGroup(groupToDisplay);
		}
		
	}
	
	private class EditClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if (groupToDisplay != null) {
				EditGroupNameDialogBox edb = new EditGroupNameDialogBox();
//				edb.setGstvm(GroupHeader.this.gstvm);
				edb.show();
			} else {
				Notification.show("Es wurde keine Gruppe ausgewählt.");
			}
		}
		
	}
	
	private class ShowUsersClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	

}
