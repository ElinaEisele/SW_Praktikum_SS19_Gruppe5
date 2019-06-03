package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse, die das Formular zur Anzeige einer <code>Group</code> darstellt.
 * Hier werden der <code>GroupHeader</code> mit der Möglichkeit die <code>Group</code>
 * zu bearbeiten und der <code>GroupContent</code> mit den Shoppinglisten zusammen
 * dargestellt.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class GroupShowForm extends VerticalPanel{
	
	private GroupHeader groupHeader = null;
	private GroupContent groupContent = null;
	private NewShoppinglistForm newShoppinglistForm = null;
	private VerticalPanel mainPanel = new VerticalPanel();
	
	private Group selectedGroup = null;
	private GroupShoppinglistTreeViewModel gstvm = new GroupShoppinglistTreeViewModel();
	
	public GroupShowForm(GroupHeader gh, NewShoppinglistForm nsf) {
		groupHeader = gh;
		mainPanel.add(nsf);
	
	}
	
	public GroupShowForm(GroupHeader gh, AddUserToGroupForm autgf) {
		groupHeader = gh;
		mainPanel.add(autgf);
	}
	
	public GroupShowForm(GroupHeader gh, EditGroupNameForm egnf) {
		groupHeader = gh;
		gh.setGstvm(gstvm);
		egnf.setGstvm(gstvm);
		mainPanel.add(egnf);
	}
	
	public GroupShowForm(GroupHeader gh) {
		groupHeader = gh;
		mainPanel.add(new GroupContent());
		// GroupContent noch eine Gruppe zuweisen!
	}
	
	public GroupShowForm() {
		groupHeader = new GroupHeader();
		groupContent = new GroupContent();
		
		groupHeader.setStylePrimaryName("groupHeader");
		mainPanel.setStylePrimaryName("groupShowFormMainPanel");
		
		mainPanel.add(groupContent);
		
	}
	
	public void onLoad() {
		
		
	
		this.add(groupHeader);
		this.add(mainPanel);
		
		groupHeader.setGroupShowForm(GroupShowForm.this);
		groupHeader.setSelected(selectedGroup);
		
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


}
