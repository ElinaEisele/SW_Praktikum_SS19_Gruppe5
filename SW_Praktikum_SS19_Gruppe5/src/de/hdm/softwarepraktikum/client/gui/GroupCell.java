package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.softwarepraktikum.shared.bo.Group;

/**
 * Klasse zur Darstellung von Group-Objekten im Navigator.
 * Erweiterungen von <code>AbstractCell<T></code> dienen zur
 * Erzeugung von HTML-Code fuer benutzerdefinierte Objekte.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class GroupCell extends AbstractCell<Group> {

	@Override
	public void render(Context context, Group value, SafeHtmlBuilder sb) {
		if (value == null) {
			return;		
		}
		
		sb.appendHtmlConstant("<div>");
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant("</div>");
	}

}
