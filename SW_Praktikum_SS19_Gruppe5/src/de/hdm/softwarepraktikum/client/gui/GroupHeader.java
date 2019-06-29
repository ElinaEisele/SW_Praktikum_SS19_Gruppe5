package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;

/**
 * Klasse fuer die Anordnung der Buttons im <code>GroupHeader</code>, der in der
 * <code>GroupShowForm</code> angezeigt wird.
 * 
 * @author ElinaEisele, JonasWagenknecht, LeoniFriedrich
 */

public class GroupHeader extends HorizontalPanel {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();
	private Group selectedGroup = null;
	private GroupShoppinglistTreeViewModel gstvm = null;
	private GroupShowForm groupShowForm = null;

	private Label groupHeaderLabel = new Label();
	private String groupName = null;

	private Button newShoppinglist = new Button();
	private Button addUser = new Button();
	private Button leaveGroup = new Button();
	private Button editGroupName = new Button();
	private Button deleteGroup = new Button();
	private Button showRetailers = new Button();

	public GroupHeader() {

		groupHeaderLabel.setStyleName("GroupLabel");

		Image newShoppinglistImg = new Image();
		newShoppinglistImg.setUrl("images/clipboard.png");
		newShoppinglistImg.setSize("32px", "32px");
		newShoppinglist.setStyleName("ShoppinglistHeaderButton");
		newShoppinglist.getElement().appendChild(newShoppinglistImg.getElement());
		newShoppinglist.addClickHandler(new NewShoppinglistClickHandler());

		Image addUserImg = new Image();
		addUserImg.setUrl("images/add-user.png");
		addUserImg.setSize("32px", "32px");
		addUser.setStyleName("GroupHeaderButton");
		addUser.getElement().appendChild(addUserImg.getElement());
		addUser.addClickHandler(new AddUserClickHandler());

		Image leaveGroupImg = new Image();
		leaveGroupImg.setUrl("images/logout.png");
		leaveGroupImg.setSize("32px", "32px");
		leaveGroup.setStyleName("GroupHeaderButton");
		leaveGroup.getElement().appendChild(leaveGroupImg.getElement());
		leaveGroup.addClickHandler(new LeaveGroupClickHandler());

		Image editGroupImg = new Image();
		editGroupImg.setUrl("images/edit.png");
		editGroupImg.setSize("32px", "32px");
		editGroupName.setStyleName("GroupHeaderButton");
		editGroupName.getElement().appendChild(editGroupImg.getElement());
		editGroupName.addClickHandler(new EditClickHandler());

		Image deleteImg = new Image();
		deleteImg.setUrl("images/delete.png");
		deleteImg.setSize("32px", "32px");
		deleteGroup.setStyleName("GroupHeaderButton");
		deleteGroup.getElement().appendChild(deleteImg.getElement());
		deleteGroup.addClickHandler(new DeleteGroupClickHandler());

		Image showRetailersImg = new Image();
		showRetailersImg.setUrl("images/shop.png");
		showRetailersImg.setSize("32px", "32px");
		showRetailers.setStyleName("GroupHeaderButton");
		showRetailers.getElement().appendChild(showRetailersImg.getElement());
		showRetailers.addClickHandler(new ShowRetailersClickHandler());

	}

	/**
	 * Beim Anzeigen werden alle Widgets geladen und angeordnet.
	 */
	public void onLoad() {

		this.add(groupHeaderLabel);
		this.add(newShoppinglist);
		this.add(addUser);
		this.add(leaveGroup);
		this.add(editGroupName);
		this.add(deleteGroup);
		this.add(showRetailers);

	}

	public GroupShowForm getGroupShowForm() {
		return groupShowForm;
	}

	public void setGroupShowForm(GroupShowForm groupShowForm) {
		this.groupShowForm = groupShowForm;
	}

	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}

	/**
	 * Sobald eine <code>Group</code> ausgewaehlt wird, wird das Label mit dem
	 * Gruppenname befuellt.
	 * 
	 * @param g das zu setzende <code>Group</code> Objekt.
	 */
	public void setSelectedGroup(Group g) {
		if (g != null) {
			selectedGroup = g;
			groupHeaderLabel.setText(selectedGroup.getName());

		} else {
			this.clear();
		}
	}

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der ClickHandler
	 * ***************************************************************************
	 */

	/**
	 * Bei Betätigen der Schaltfläche, um einen neues
	 * <code>Shoppinglist</code>-Objekt anzulegen, wird das Formular zum Anlegen
	 * eines neuen <code>Shoppinglist</code>-Objekts geladem.
	 *
	 */
	private class NewShoppinglistClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			NavigatorPanel np = new NavigatorPanel();
			RootPanel.get("aside").clear();
			RootPanel.get("aside").add(np);

			NewShoppinglistForm nsf = new NewShoppinglistForm();
			nsf.setGstvm(GroupHeader.this.gstvm);
			nsf.setGroupHeader(GroupHeader.this);
			nsf.setSelectedGroup(selectedGroup);

			GroupShowForm gsf = new GroupShowForm(GroupHeader.this, nsf);
			gsf.setSelected(selectedGroup);
			gsf.setGstvm(gstvm);
			gstvm.setGroupShowForm(gsf);

		}

	}

	/**
	 * Bei Betätigen der Schaltfläche, um eine <code>User</code>-Objekt einem
	 * <code>Group</code>-Objekt zuzuordnen, wird das Formular dazu gelade.
	 *
	 */
	private class AddUserClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedGroup != null) {

				AddUserToGroupForm autgf = new AddUserToGroupForm();

				autgf.setGstvm(GroupHeader.this.gstvm);
				autgf.setGroupHeader(GroupHeader.this);
				autgf.setSelectedGroup(selectedGroup);

				GroupShowForm gsf = new GroupShowForm(GroupHeader.this, autgf);
				gsf.setSelected(selectedGroup);

			} else {
				Notification.show("Es wurde keine Gruppe ausgewählt.");
			}
		}
	}

	/**
	 * Bei Betätigen der Schaltfläche, um eine Gruppe zu verlassen, wird ein
	 * <code>DialogBox</code>-Objekt geladen.
	 *
	 */
	private class LeaveGroupClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedGroup != null) {
				LeaveGroupDialogBox ldb = new LeaveGroupDialogBox();
				ldb.setGstvm(GroupHeader.this.gstvm);
				ldb.setSelectedGroup(selectedGroup);
				ldb.show();
			} else {
				Notification.show("Es wurde keine Gruppe ausgewählt.");
			}
		}

	}

	/**
	 * ClickHandler dient dem erzeugen einer <code>DeleteGroupDialogBox</code>
	 * Instanz.
	 */
	private class DeleteGroupClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DeleteGroupDialogBox ddb = new DeleteGroupDialogBox();
			ddb.setSelectedGroup(selectedGroup);
			ddb.show();
		}

	}

	/**
	 * Bei Betätigen der Schaltfläche, um den Namen eines <code>Group</code>-Objekts
	 * zu ändern wird das Formular dafür geladen.
	 *
	 */
	private class EditClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (selectedGroup != null) {

				EditGroupNameForm egnf = new EditGroupNameForm();
				egnf.setGstvm(GroupHeader.this.gstvm);
				egnf.setGroupHeader(GroupHeader.this);
				egnf.setSelectedGroup(selectedGroup);

				GroupShowForm gsf = new GroupShowForm(GroupHeader.this, egnf);
				gsf.setSelected(selectedGroup);

			} else {
				Notification.show("Es wurde keine Gruppe ausgewählt.");
			}
		}

	}

	/**
	 * ClickHandler zum Anzeigen des Händeler Formulars.
	 *
	 */
	private class ShowRetailersClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedGroup != null) {
				RetailersForm srdb = new RetailersForm();
				srdb.setSelectedGroup(selectedGroup);
				srdb.setGstvm(GroupHeader.this.gstvm);
				GroupShowForm gsf = new GroupShowForm(GroupHeader.this, srdb);
				gsf.setSelected(selectedGroup);

			} else {
				Notification.show("Es wurde keine Gruppe ausgewählt.");
			}

		}

	}

}
