package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.DialogBox;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse zur Darstellung einer Dialogbox, um bei einer Gruppe den Gruppennamen
 * zu aendern.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class EditGroupNameDialogBox extends DialogBox{
	
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
