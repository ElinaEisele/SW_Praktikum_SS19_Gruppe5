package de.hdm.softwarepraktikum.server.report;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.softwarepraktikum.server.ShoppinglistAdministrationImpl;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministration;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.report.AllListitemsOfGroupReport;

/**
 * Die Klasse stellt die vollständige Applikationslogik des ReportGenerators dar.
 */

@SuppressWarnings("serial")

public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {
	
	/**
	 * Der ReportGenerator benötigt Zugriff auf die ShoppinglistAdministration,
	 * da er diese ausgeben muss.
	 */
	private ShoppinglistAdministration administration = null;
	
	 /**
     * <p>
     * GWT benötigt einen No-Argument Konstruktor und eine Intanziierung 
     * ohne diesen ist auch nicht möglich. Deshalb bietet es sich an 
     * eine seperate Methode zur Instanziierung zu erstellen, welche
     * gleich nach der Initialisierung aufgerufen werden muss.
     * </p>
     */
    public ReportGeneratorImpl() throws IllegalArgumentException {
    }

    /**
     * Initialsierungsmethode.
     */
    public void init() throws IllegalArgumentException{
        
        /*
         * Für den Eigenbedarf erstellt ein ReportGeneratorImpl-Objekt 
         * ein ShoppinglistAdministrationImpl-Objekt. 
         */
    	 ShoppinglistAdministrationImpl a = new ShoppinglistAdministrationImpl();
    	 a.init();
    	 this.administration = a;
    }
    
    /**
     * Ausgeben der Einkauslisten Verwaltung
     * @return ShoppinglistAdministrationImpl 
     */
    protected ShoppinglistAdministration getShoppinglistAdministration() {
    	return this.administration;
    }
    
    /**
     * Methode zum erstellen eines AllListitemsOfGroupReport
     * 
     * @param g Gruppe, für welche der Report erstellt werden soll
     * @return AllListitemsOfGroupReport der vollständige Report
     * @throws IllegalArgumentException
     */
    public AllListitemsOfGroupReport createAllListitemsOfGroupReport(Group g) throws IllegalArgumentException {
    	
    	if (this.getShoppinglistAdministration() == null) {
    		return null;
    	}
    	
    	//Anlegen eines leeren Reports
    	AllListitemsOfGroupReport result = new AllListitemsOfGroupReport();
    	
    	//Setzen des Titels
    	result.setTitle("Report der Gruppe:" + g.getName());
    	
    	//Zeitpunkt der Erstellung speichern
    	result.setCreationDate(new Date());
    }
}