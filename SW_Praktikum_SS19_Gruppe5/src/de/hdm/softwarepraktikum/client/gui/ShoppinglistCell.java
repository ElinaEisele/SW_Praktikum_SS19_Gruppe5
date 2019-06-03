package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse zur Darstellung von Shoppinglist-Objekte. Erweiterungen von
 * <code>AbstractCell<T></code> dienen zur Erzeugung von HTML-Code fuer
 * benutzerdefinierte Objekte.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class ShoppinglistCell extends AbstractCell<Shoppinglist> {

<<<<<<< HEAD
=======

>>>>>>> refs/heads/master
	@Override
	public void render(Context context, Shoppinglist value, SafeHtmlBuilder sb) {
		if (value == null) {
			return;
		}
<<<<<<< HEAD
		
		sb.appendHtmlConstant("<div>");
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant("</div>");
	}
	
=======
		sb.appendHtmlConstant("<div>");
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant("</div>");
	}

>>>>>>> refs/heads/master
}
