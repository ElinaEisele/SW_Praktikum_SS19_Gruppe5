package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.shared.bo.Group;

/**
 * Klasse, die das Formular zur Anzeige einer <code>Group</code> darstellt. Hier
 * werden der <code>GroupHeader</code> mit der Möglichkeit die
 * <code>Group</code> zu bearbeiten und der <code>GroupContent</code> mit den
 * Shoppinglisten zusammen dargestellt.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class GroupShowForm extends VerticalPanel {

	private GroupHeader groupHeader = null;
	private GroupCellTable groupCellTable = null;
	private NewShoppinglistForm newShoppinglistForm = null;
	private NewGroupForm newGroupForm = null;
	private VerticalPanel mainPanel = new VerticalPanel();

	private Group selectedGroup = null;
	private GroupShoppinglistTreeViewModel gstvm = null;

	public GroupShowForm(GroupHeader gh, NewShoppinglistForm nsf) {
		groupHeader = gh;
		mainPanel.add(groupHeader);
		mainPanel.add(nsf);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);
	}

	public GroupShowForm(GroupHeader gh, AddUserToGroupForm autgf) {
		groupHeader = gh;
		mainPanel.add(groupHeader);
		mainPanel.add(autgf);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);
	}

	public GroupShowForm(GroupHeader gh, EditGroupNameForm egnf) {
		groupHeader = gh;
		mainPanel.add(egnf);
		mainPanel.add(groupHeader);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);
	}

	public GroupShowForm(GroupHeader gh) {
		groupHeader = gh;

	}

	public GroupShowForm(GroupHeader gh, GroupCellTable gct) {
		groupHeader = gh;
		groupCellTable = gct;
		mainPanel.add(gct);
		mainPanel.add(groupHeader);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);
	}

	public GroupShowForm(NewGroupForm newGroupForm) {
		mainPanel.add(newGroupForm);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);
	}

	public GroupShowForm(GroupHeader gh, RetailersForm srf) {
		groupHeader = gh;
		mainPanel.add(srf);
		mainPanel.add(groupHeader);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);
	}

	public GroupShowForm() {

		groupHeader = new GroupHeader();
		groupCellTable = new GroupCellTable();
		groupCellTable.setGstvm(gstvm);

		groupHeader.setStylePrimaryName("groupHeader");
		mainPanel.setStylePrimaryName("groupShowFormMainPanel");

		mainPanel.add(groupCellTable);

	}

	/**
	 * Beim Anzeigen werden die Widgets geladen und angeordnet. Unter einer
	 * Buttonleiste mit dem entsprechenden Gruppennamen wird eine Tabelle mit
	 * dazugehörigen Einkaufslisten angezeigt.
	 */
	public void onLoad() {

		groupCellTable.setSelected(selectedGroup);
		groupCellTable.setGstvm(gstvm);
		groupCellTable.setGroupShowForm(GroupShowForm.this);

		groupHeader.setSelectedGroup(selectedGroup);
		groupHeader.setGstvm(gstvm);
		groupHeader.setGroupShowForm(GroupShowForm.this);

		this.add(groupHeader);
		this.add(mainPanel);

	}

	public void setSelected(Group g) {
		selectedGroup = g;
	}

	public Group getSelected() {
		return selectedGroup;
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

}
