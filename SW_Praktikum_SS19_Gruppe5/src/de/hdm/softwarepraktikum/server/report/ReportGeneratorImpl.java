package de.hdm.softwarepraktikum.server.report;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Die Klasse <code>ReportGeneratorImpl</code> implementiert das Interface
 * ReportGenerator. In der Klasse ist neben ShoppinglistAdministrationImpl s�mtliche
 * Applikationslogik vorhanden.
 */

@SuppressWarnings("serial")

public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {
	
	/**
	 * Der ReportGenerator ben�tigt Zugriff auf die ContactAdministation,
	 * da dort wichtige Methoden f�r die Koexistenz von Datenobjekten enthalten sind.
	 */
	private ShoppinglistAdministration administration = null;

}
