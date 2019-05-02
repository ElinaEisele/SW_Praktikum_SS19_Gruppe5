package de.hdm.softwarepraktikum.server.report;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Die Klasse <code>ReportGeneratorImpl</code> implementiert das Interface
 * ReportGenerator. In der Klasse ist neben ShoppinglistAdministrationImpl sämtliche
 * Applikationslogik vorhanden.
 */

@SuppressWarnings("serial")

public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {
	
	/**
	 * Der ReportGenerator benötigt Zugriff auf die ContactAdministation,
	 * da dort wichtige Methoden für die Koexistenz von Datenobjekten enthalten sind.
	 */
	private ShoppinglistAdministration administration = null;

}
