package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Listitem;

public class ListitemCell extends AbstractCell<Listitem>{
	
	ShoppinglistAdministrationAsync shoppinglistAdministraiton = ClientsideSettings.getShoppinglistAdministration();

	@Override
	public void render(Context context, Listitem value, SafeHtmlBuilder sb) {
		if (value == null) {
			return;
		}
		sb.appendHtmlConstant("<div>");
	}

}
