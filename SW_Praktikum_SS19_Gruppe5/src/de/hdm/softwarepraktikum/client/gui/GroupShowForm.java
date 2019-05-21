package de.hdm.softwarepraktikum.client.gui;

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
	
	private Group selectedGroup;
	
	public void onLoad() {
		super.onLoad();
		
		groupHeader = new GroupHeader();
		groupContent = new GroupContent();
		
		groupHeader.setGroupShowForm(GroupShowForm.this);
		
		groupHeader.setSelected(selectedGroup);
		
		groupHeader.setStylePrimaryName("groupHeader");
		groupContent.setStylePrimaryName("groupContent");
		
		this.add(groupHeader);
		this.add(groupContent);
	}

	public void setSelected(Group g) {
		selectedGroup = g;
	}

}
