package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.softwarepraktikum.shared.dummydata.GroupDD;

/**
 * Klasse zur Darstellung von Group-Objekten im Navigator.
 * Erweiterungen von <code>AbstractCell<T></code> dienen zur
 * Erzeugung von HTML-Code fuer benutzerdefinierte Objekte.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class GroupCell extends AbstractCell<GroupDD> {

	@Override
	public void render(Context context, GroupDD value, SafeHtmlBuilder sb) {
		if (value == null) {
			return;		
		}
		
		sb.appendHtmlConstant("<div>");
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant("</div>");
	}

}
