package de.hdm.softwarepraktikum.client;

import com.google.gwt.core.client.GWT;

import de.hdm.softwarepraktikum.shared.CommonSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministration;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;

public class ClientsideSettings extends CommonSettings{

	private static ShoppinglistAdministrationAsync shoppinglistAdministration = null;
	
	public static ShoppinglistAdministrationAsync getShoppinglistAdministration() {
		if (shoppinglistAdministration == null) {
			shoppinglistAdministration = GWT.create(ShoppinglistAdministration.class);
		}
		return shoppinglistAdministration;

	}

}
