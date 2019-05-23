package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
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
 * Klasse zur Darstellung eines Formulars, um eine neue Gruppe zu erstellen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class NewGroupForm extends VerticalPanel{
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	private User u = CurrentUser.getUser();
	
	private GroupShoppinglistTreeViewModel gstvm = null;
	private GroupShowForm groupShowForm = null;
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private Label infoLabel = new Label("Neue Gruppe erstellen");
	private Grid grid = new Grid(1,2);
	private Label nameLabel = new Label("Name");
	private TextBox nameTextBox = new TextBox();
	
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button saveButton = new Button("Speicher");
	private Button cancelButten = new Button("Abbrechen");
	
	public NewGroupForm() {
		
		grid.setWidget(0, 0, nameLabel);
		grid.setWidget(0, 1, nameTextBox);
		
		saveButton.addClickHandler(new SaveClickHandler());
		cancelButten.addClickHandler(new CancelClickHandler());
		
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButten);
		
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
	
	private class SaveClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if (u != null) {
				shoppinglistAdministration.createGroupFor(u, nameTextBox.getValue(), new NewGroupAsyncCallback());
				RootPanel.get("main").clear();
				RootPanel.get("main").add(groupShowForm);
			}
		}
		
	}
	
	private class CancelClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if (u != null) {
				RootPanel.get("main").clear();
			}
		}
		
	}
	
	private class NewGroupAsyncCallback implements AsyncCallback<Group>{

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppenerstellung ist fehlgeschlagen.");
		}

		@Override
		public void onSuccess(Group result) {
			gstvm.addGroup(result);
			groupShowForm.setSelected(result);
		
		}
		
	}
	

}
