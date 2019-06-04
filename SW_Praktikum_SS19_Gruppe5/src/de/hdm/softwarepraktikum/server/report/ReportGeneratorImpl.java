package de.hdm.softwarepraktikum.server.report;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.softwarepraktikum.server.ShoppinglistAdministrationImpl;
import de.hdm.softwarepraktikum.shared.ReportGenerator;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministration;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.report.AllListitemsOfGroupReport;
import de.hdm.softwarepraktikum.shared.report.Column;
import de.hdm.softwarepraktikum.shared.report.Row;

/**
 * Die Klasse stellt die vollstaendige Applikationslogik des ReportGenerators dar.
 * 
 * @author FelixRapp, TimBeutelspacher
 */

@SuppressWarnings("serial")

public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {
	
	/**
	 * Der ReportGenerator benoetigt Zugriff auf die ShoppinglistAdministration,
	 * da er diese ausgeben muss.
	 */
	private ShoppinglistAdministration administration = null;
	
	 /**
     * <p>
     * GWT benoetigt einen No-Argument Konstruktor und eine Intanziierung 
     * ohne diesen ist auch nicht moeglich. Deshalb bietet es sich an 
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
         * Fuer den Eigenbedarf erstellt ein ReportGeneratorImpl-Objekt 
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
    protected ShoppinglistAdministration getShoppinglistAdministration() throws IllegalArgumentException {
    	return this.administration;
    }
    
    /**
     * Methode zum erstellen eines AllListitemsOfGroupReport
     * 
     * @param g Gruppe, fuer welche der Report erstellt werden soll
     * @return AllListitemsOfGroupReport der vollstaendige Report
     * @throws IllegalArgumentException
     */
    public AllListitemsOfGroupReport createAllListitemsOfGroupReport(Group g, Date startdate, Date enddate) 
    		throws IllegalArgumentException {
    	
    	if (this.getShoppinglistAdministration() != null) {
    		
    		//Ausgeben aller Einkauslisten der Gruppe
    		ArrayList<Shoppinglist> shoppinglists = this.getShoppinglistAdministration().getShoppinglistsOf(g);
    		
    		//Liste mit allen Eintraegen der Gruppe
    		ArrayList<Listitem> listitems = new ArrayList<Listitem>();
    		
    		//Erstellen einer Liste mit allen Eintraegen aus allen Listen
    		for (Shoppinglist s: shoppinglists)	{
    			listitems.addAll(this.getShoppinglistAdministration().getListitemsOf(s));
    		}
    		
        	//Anlegen eines leeren Reports
        	AllListitemsOfGroupReport result = new AllListitemsOfGroupReport();
        	
        	//Erstellen eines Tabellenkopfs
        	Row tablehead = new Row();
        	tablehead.addColumn(new Column("Erstellungsdatum"));
        	tablehead.addColumn(new Column("Bezeichnung"));
        	tablehead.addColumn(new Column("Menge"));
        	tablehead.addColumn(new Column("Einheit"));
        	result.addRow(tablehead);
        	
        	//Fuer jedes Listitem wird eine Reihe mit Spalten erstellt
        	for(Listitem l : listitems) {
        		Row r = new Row();
        		r.addColumn(new Column(l.getCreationDateConvertToString()));
        		r.addColumn(new Column(this.getShoppinglistAdministration().getProductnameOf(l)));
        		r.addColumn(new Column(String.valueOf(l.getAmount())));
        		r.addColumn(new Column(this.getShoppinglistAdministration().getListitemUnitOf(l).getName()));
        		result.addRow(r);
        	}
        	
        	//Setzen des Titels
        	result.setTitle("Report der Gruppe:" + g.getName());
        	
        	//Zeitpunkt der Erstellung speichern
        	result.setCreationDate(new Date());
        	
        	return result;
        	
    	} else {
    		return null;
    	}
    }
}

