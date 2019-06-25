package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
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
	private Grid grid = new Grid(1, 2);
	private Label nameLabel = new Label("Name");
	private TextBox nameTextBox = new TextBox();

	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button saveButton = new Button("Speichern");
	private Button cancelButten = new Button("Abbrechen");

	public NewGroupForm() {

		nameTextBox.setFocus(true);

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

	public Group getOldSelectedGroup() {
		return oldSelectedGroup;
	}

	public void setOldSelectedGroup(Group oldSelectedGroup) {
		this.oldSelectedGroup = oldSelectedGroup;
	}

	private class SaveClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (u != null) {
				if (nameTextBox.getValue() == "") {
					Window.alert("Niemand hat die Absicht eine Gruppe ohne Namen anzulegen");
				} else if (nameTextBox.getValue().length() <= 20) {
					groupShowForm = new GroupShowForm();
					shoppinglistAdministration.createGroupFor(u, nameTextBox.getValue(), new NewGroupAsyncCallback());
				} else {
					Window.alert("Bitte gib eine kürzeren Namen ein");
				}
			}
		}

	}

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

	private class NewGroupAsyncCallback implements AsyncCallback<Group> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppenerstellung ist fehlgeschlagen.");
		}

		@Override
		public void onSuccess(Group result) {
			RootPanel.get("main").clear();
			RootPanel.get("aside").clear();
			NavigatorPanel np = new NavigatorPanel();
			RootPanel.get("aside").add(np);

			newGroup = result;
			groupShowForm.setSelected(newGroup);
			groupShowForm.getGroupHeader().setSelected(newGroup);
			RootPanel.get("main").add(groupShowForm);
			gstvm.addGroup(newGroup);

		}

	}

}
