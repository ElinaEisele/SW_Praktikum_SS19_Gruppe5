package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse zur Darstellung von <code>Shoppinglist</code>-Objekte. Erweiterungen von
 * <code>AbstractCell<T></code> dienen zur Erzeugung von HTML-Code fuer
 * benutzerdefinierte Objekte.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class ShoppinglistCell extends AbstractCell<Shoppinglist> {


	@Override
	public void render(Context context, Shoppinglist value, SafeHtmlBuilder sb) {
		if (value == null) {
			return;
		}
		sb.appendHtmlConstant("<div>");
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant("</div>");
	}

}
