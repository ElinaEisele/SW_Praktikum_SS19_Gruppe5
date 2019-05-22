package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.DialogBox;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse zur Darstellung einer Dialogbox, um einer Gruppe einen
 * neune User hinzuzufuegen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class AddUserToGroupDialogBox extends DialogBox{
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	private User u = CurrentUser.getUser();
	
	private GroupShoppinglistTreeViewModel gstvm = null;

	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}

}
