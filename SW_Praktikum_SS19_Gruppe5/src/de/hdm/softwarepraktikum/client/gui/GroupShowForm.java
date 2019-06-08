package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.Window;
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
	private GroupContent groupContent = null;
	private GroupCellTable groupCellTable = null;
	private NewShoppinglistForm newShoppinglistForm = null;
	private NewGroupForm newGroupForm = null;
	private VerticalPanel mainPanel = new VerticalPanel();

	private Group selectedGroup = null;
	private GroupShoppinglistTreeViewModel gstvm = new GroupShoppinglistTreeViewModel();

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
		gh.setGstvm(gstvm);
		egnf.setGstvm(gstvm);
		mainPanel.add(groupHeader);
		mainPanel.add(egnf);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);
	}

	public GroupShowForm(GroupHeader gh) {
		groupHeader = gh;
		mainPanel.add(new GroupContent());
		// GroupContent noch eine Gruppe zuweisen!
		
	}

	public GroupShowForm(GroupHeader gh, GroupCellTable gct) {
		groupHeader = gh;
		mainPanel.add(gct);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);
	}
	
	public GroupShowForm(NewGroupForm newGroupForm) {
		mainPanel.add(newGroupForm);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);
	}

	public GroupShowForm() {
		groupHeader = new GroupHeader();

		groupContent = new GroupContent();

		groupCellTable = new GroupCellTable();

		groupHeader.setStylePrimaryName("groupHeader");
		mainPanel.setStylePrimaryName("groupShowFormMainPanel");

		mainPanel.add(groupCellTable);

	}

	public void onLoad() {

		groupCellTable.setSelected(selectedGroup);
		groupCellTable.setGroupShowForm(GroupShowForm.this);
		groupHeader.setSelected(selectedGroup);
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
