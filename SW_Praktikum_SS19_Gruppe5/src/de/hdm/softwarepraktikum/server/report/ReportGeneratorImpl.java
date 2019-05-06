package de.hdm.softwarepraktikum.server.report;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.softwarepraktikum.server.ShoppinglistAdministrationImpl;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministration;

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
	
	 /**
     * <p>
     * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
     * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu
     * ist ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines anderen
     * Konstruktors ist durch die Client-seitige Instantiierung durch
     * <code>GWT.create(Klassenname.class)</code> nach derzeitigem Stand nicht
     * möglich.
     * </p>
     * <p>
     * Es bietet sich also an, eine separate Instanzenmethode zu erstellen, die
     * Client-seitig direkt nach <code>GWT.create(Klassenname.class)</code>
     * aufgerufen wird, um eine Initialisierung der Instanz vorzunehmen.
     * </p>
     */
    public ReportGeneratorImpl() throws IllegalArgumentException {

    }

    /**
     * Initialsierungsmethode. Siehe dazu Anmerkungen zum No-Argument-Konstruktor.
     * 
     * @see #ReportGeneratorImpl()
     */
    public void init() throws IllegalArgumentException{
        
        /*
         * Ein ReportGeneratorImpl-Objekt instantiiert für seinen Eigenbedarf eine
         * ContactAdministrationImpl-Instanz.
         */
    	 ShoppinglistAdministrationImpl a = new ShoppinglistAdministrationImpl();
    	    a.init();
    	    this.administration = a;
    }
}