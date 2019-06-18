package de.hdm.softwarepraktikum.server.report;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.softwarepraktikum.server.ShoppinglistAdministrationImpl;
import de.hdm.softwarepraktikum.shared.ReportGenerator;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministration;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.NamedBusinessObject;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;
import de.hdm.softwarepraktikum.shared.report.AllListitemsOfGroupReport;
import de.hdm.softwarepraktikum.shared.report.Column;
import de.hdm.softwarepraktikum.shared.report.Row;
import de.hdm.softwarepraktikum.server.db.GroupMapper;
import de.hdm.softwarepraktikum.server.db.ListitemMapper;
import de.hdm.softwarepraktikum.server.db.ListitemUnitMapper;
import de.hdm.softwarepraktikum.server.db.ShoppinglistMapper;
import de.hdm.softwarepraktikum.server.db.UserMapper;

/**
 * Die Klasse stellt die vollstaendige Applikationslogik des ReportGenerators dar.
 * 
 * @author FelixRapp, TimBeutelspacher
 */


public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Der ReportGenerator benoetigt Zugriff auf die ShoppinglistAdministration,
	 * da er diese ausgeben muss.
	 */
	private ReportGenerator report = null;
	
	private GroupMapper groupMapper = null;
	
	private ShoppinglistMapper shoppinglistMapper = null;
	
	private ListitemMapper listitemMapper = null;
	
	private ListitemUnitMapper listitemUnitMapper = null;
	
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
    	
//    	ReportGeneratorImpl a = new ReportGeneratorImpl();
//    	 a.init();
//    	 this.report = a;
    	
    	this.groupMapper = GroupMapper.groupMapper();
		this.listitemMapper = ListitemMapper.listitemMapper();
		this.listitemUnitMapper = ListitemUnitMapper.listitemUnitMapper();
		this.shoppinglistMapper = ShoppinglistMapper.shoppinglistMapper();
    }
    
    /**
     * Ausgeben der Einkauslisten Verwaltung
     * @return ShoppinglistAdministrationImpl 
     */
    protected ReportGenerator getReportGenerator() throws IllegalArgumentException {
    	return this.report;
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
    	
    	if (this.getReportGenerator() != null) {
    		
    		//Ausgeben aller Einkauslisten der Gruppe
    		ArrayList<Shoppinglist> shoppinglists = this.getReportGenerator().getShoppinglistsOf(g);
    		
    		//Liste mit allen Eintraegen der Gruppe
    		ArrayList<Listitem> listitems = new ArrayList<Listitem>();
    		
    		//Erstellen einer Liste mit allen Eintraegen aus allen Listen
    		if(!shoppinglists.isEmpty()) {
    			for (Shoppinglist s: shoppinglists)	{
        			listitems.addAll(this.getReportGenerator().getListitemsOf(s));
        		}
    		}
    		
    		
        	//Anlegen eines leeren Reports
        	AllListitemsOfGroupReport result = new AllListitemsOfGroupReport();
        	
        	//Erstellen eines Tabellenkopfs
        	Row tablehead = new Row();
        	
        	tablehead.addColumn(new Column("Bezeichnung"));
        	tablehead.addColumn(new Column("Erstellungsdatum"));
        	tablehead.addColumn(new Column("Menge"));
        	tablehead.addColumn(new Column("Einheit"));
        	result.addRow(tablehead);
        	
        	//Fuer jedes Listitem wird eine Reihe mit Spalten erstellt
        	for(Listitem l : listitems) {
        		Row r = new Row();
        		r.addColumn(new Column(this.getReportGenerator().getProductnameOf(l)));
        		r.addColumn(new Column(l.getCreationDateConvertToString()));
        		r.addColumn(new Column(String.valueOf(l.getAmount())));
        		r.addColumn(new Column(this.getReportGenerator().getListitemUnitOf(l).getName()));
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

	@Override
	public ArrayList<Group> getAllGroupsOf(User u) throws IllegalArgumentException {
		
		return this.groupMapper.getGroupsOf(u);	
	}
	
	public ArrayList<Shoppinglist> getShoppinglistsOf(Group g) throws IllegalArgumentException{
		
		return this.shoppinglistMapper.getShoppinglistsOf(g);
	}

	@Override
	public ArrayList<Listitem> getListitemsOf(Shoppinglist s) throws IllegalArgumentException {
		
		return this.listitemMapper.getListitemsOf(s);
	}

	@Override
	public String getProductnameOf(Listitem l) throws IllegalArgumentException {
		
		return this.listitemMapper.getProductnameOf(l.getId());
	}

	@Override
	public NamedBusinessObject getListitemUnitOf(Listitem l) throws IllegalArgumentException {
		
		return this.listitemUnitMapper.getUnitOf(l);
	}
	
}

