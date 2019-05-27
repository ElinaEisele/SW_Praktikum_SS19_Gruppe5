package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
 * Klasse zur Darstellung einer Dialogbox, um bei einer Gruppe den Gruppennamen
 * zu aendern.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class EditGroupNameForm extends VerticalPanel{
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	private User u = CurrentUser.getUser();
	private GroupShoppinglistTreeViewModel gstvm = null;
	private GroupHeader groupHeader = new GroupHeader();
	private Group selectedGroup = null;
	private Group changedGroup = null;
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private Label infoLabel = new Label("Gruppenname ändern");
	private Grid grid = new Grid(1, 2);
	private Label newNameLabel = new Label("Neuer Gruppenname: ");
	private TextBox newNameTextBox = new TextBox();
	
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button saveButton = new Button("Änderung speichern");
	private Button cancelButton = new Button("Abbrechen");
	
	public EditGroupNameForm() {
		
		grid.setWidget(0, 0, newNameLabel);
		grid.setWidget(0, 1, newNameTextBox);
		
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
//			if (selectedGroup != null) {
				shoppinglistAdministration.changeNameOf(selectedGroup, newNameTextBox.getValue(), new ChangeNameCallback());
				GroupShowForm gsf = new GroupShowForm();
				gsf.setSelected(changedGroup);
				RootPanel.get("main").clear();
				RootPanel.get("main").add(gsf);
//			}
		}
	}
	
	private class ChangeNameCallback implements AsyncCallback<Group>{

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Namensänderung der Gruppe ist fehlgeschlagen.");
		}

		@Override
		public void onSuccess(Group result) {
			changedGroup = result;
			gstvm.updateGroup(result);
		}
		
	}
	

}
