package de.hdm.softwarepraktikum.client.gui;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse zur Darstellung eines Formulars, um eine neue SHoppingliste anzulegen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class NewShoppinglistForm {
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	
	private GroupShoppinglistTreeViewModel gstvm = null;
	private Group selectedGroup = null;
	
	public NewShoppinglistForm(Group g) {
		this.selectedGroup = g;
		
	}

	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}

}
