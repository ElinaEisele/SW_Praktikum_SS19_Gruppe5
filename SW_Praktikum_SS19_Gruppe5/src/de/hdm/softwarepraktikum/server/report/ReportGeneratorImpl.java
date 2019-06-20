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
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;
import de.hdm.softwarepraktikum.shared.report.AllListitemsOfGroupReport;
import de.hdm.softwarepraktikum.shared.report.Column;
import de.hdm.softwarepraktikum.shared.report.Row;
import de.hdm.softwarepraktikum.server.db.GroupMapper;
import de.hdm.softwarepraktikum.server.db.ListitemMapper;
import de.hdm.softwarepraktikum.server.db.ListitemUnitMapper;
import de.hdm.softwarepraktikum.server.db.RetailerMapper;
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
	private ShoppinglistAdministrationImpl shoppinglistAdministration = null;
	
//	private ReportGenerator report = null;
	
	private GroupMapper groupMapper = null;
	
	private ShoppinglistMapper shoppinglistMapper = null;
	
	private ListitemMapper listitemMapper = null;
	
	private ListitemUnitMapper listitemUnitMapper = null;
	
	private RetailerMapper retailerMapper = null;
	
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
    	    	
    	this.groupMapper = GroupMapper.groupMapper();
		this.listitemMapper = ListitemMapper.listitemMapper();
		this.listitemUnitMapper = ListitemUnitMapper.listitemUnitMapper();
		this.shoppinglistMapper = ShoppinglistMapper.shoppinglistMapper();
		this.retailerMapper = RetailerMapper.retailerMapper();
    }
    
    /**
     * Ausgeben der Einkauslisten Verwaltung
     * @return ShoppinglistAdministrationImpl 
     */
//    protected ReportGenerator getReportGenerator() throws IllegalArgumentException {
//    	return this.report;
//    }
    
    /**
     * 
     * @param g
     * @param r
     * @return
     * @throws IllegalArgumentException
     */
    public AllListitemsOfGroupReport createAllListitemsOfGroupReport(Group g, Retailer r) throws IllegalArgumentException{
    	
    	//Anlegen eines leeren Reports
    	AllListitemsOfGroupReport result = new AllListitemsOfGroupReport();
    	
    	//Setzen des Titels
    	result.setTitle("Report der Gruppe: " + g.getName());
    	
    	//Zeitpunkt der Erstellung speichern
    	result.setCreationDate(new Date());
    		
		//Ausgeben aller Einkauslisten der Gruppe
		ArrayList<Shoppinglist> shoppinglists = this.shoppinglistMapper.getShoppinglistsOf(g);
		
		//Liste mit allen Eintraegen der Gruppe
		ArrayList<Listitem> listitems = new ArrayList<Listitem>();
		
		//Liste mit allen relevanten Eintraegen der Gruppe
		ArrayList<Listitem> relevantListitems = new ArrayList<Listitem>();
		
		//Erstellen einer Liste mit allen Eintraegen aus allen Listen
		if(!shoppinglists.isEmpty()) {
			for (Shoppinglist s: shoppinglists)	{
    			listitems.addAll(this.listitemMapper.getListitemsOf(s));
    		}
			for (Listitem l : listitems) {
				if(l.getRetailerID() == r.getId()) {
					relevantListitems.add(l);	
				}
			}    			
		}	
        	
    	//Erstellen eines Tabellenkopfs
    	Row tablehead = new Row();
    	
    	tablehead.addColumn(new Column("Bezeichnung"));
    	tablehead.addColumn(new Column("Menge"));
    	tablehead.addColumn(new Column("Einheit"));
    	tablehead.addColumn(new Column("Haendler"));
    	tablehead.addColumn(new Column("Erstellungsdatum"));
    	result.addRow(tablehead);
    	
    	//Fuer jedes Listitem wird eine Reihe mit Spalten erstellt
    	for(Listitem l : relevantListitems) {
    		Row r1 = new Row();
    		r1.addColumn(new Column(this.listitemMapper.getProductnameOf(l.getId())));       		
    		r1.addColumn(new Column(String.valueOf(l.getAmount())));
    		r1.addColumn(new Column(this.listitemUnitMapper.getUnitOf(l).getName()));
    		r1.addColumn(new Column(this.retailerMapper.getRetailerOf(l).getName()));
    		r1.addColumn(new Column(l.getCreationDateConvertToString()));
    		result.addRow(r1);
    	}

        return result;

    }
    
    /**
     * Methode zum erstellen eines AllListitemsOfGroupReport
     * 
     * @param g Gruppe, fuer welche der Report erstellt werden soll
     * @return AllListitemsOfGroupReport der vollstaendige Report
     * @throws IllegalArgumentException
     */
    public AllListitemsOfGroupReport createAllListitemsOfGroupReport(Group g, Date startdate, Date enddate) throws IllegalArgumentException {
    
    	//Anlegen eines leeren Reports
    	AllListitemsOfGroupReport result = new AllListitemsOfGroupReport();
    	
    	//Setzen des Titels
    	result.setTitle("Report der Gruppe: " + g.getName());
    	
    	//Zeitpunkt der Erstellung speichern
    	result.setCreationDate(new Date());	
	
    	//Ausgeben aller Einkauslisten der Gruppe
		ArrayList<Shoppinglist> shoppinglists = this.shoppinglistMapper.getShoppinglistsOf(g);
		
		//Liste mit allen Eintraegen der Gruppe
		ArrayList<Listitem> listitems = new ArrayList<Listitem>();
		
		//Liste mit allen relevanten Eintraegen der Gruppe
		ArrayList<Listitem> relevantListitems = new ArrayList<Listitem>();
		
		//Erstellen einer Liste mit allen Eintraegen aus allen Listen
		if(!shoppinglists.isEmpty()) {
			for (Shoppinglist s: shoppinglists)	{
    			listitems.addAll(this.listitemMapper.getListitemsOf(s));
    		}
			for (Listitem l : listitems) {
				if(l.getCreationDate().compareTo(startdate) > 0) {
					if(l.getCreationDate().compareTo(enddate) < 0) {
							relevantListitems.add(l);
					
    				}
				}
			}      			
		}
    	
    	//Erstellen eines Tabellenkopfs
    	Row tablehead = new Row();
    	
    	tablehead.addColumn(new Column("Bezeichnung"));
    	tablehead.addColumn(new Column("Menge"));
    	tablehead.addColumn(new Column("Einheit"));
    	tablehead.addColumn(new Column("Haendler"));
    	tablehead.addColumn(new Column("Erstellungsdatum"));
    	result.addRow(tablehead);
    	
    	//Fuer jedes Listitem wird eine Reihe mit Spalten erstellt
    	for(Listitem l : relevantListitems) {
    		Row r2 = new Row();
    		r2.addColumn(new Column(this.listitemMapper.getProductnameOf(l.getId())));       		
    		r2.addColumn(new Column(String.valueOf(l.getAmount())));
    		r2.addColumn(new Column(this.listitemUnitMapper.getUnitOf(l).getName()));
    		r2.addColumn(new Column(this.retailerMapper.getRetailerOf(l).getName()));
    		r2.addColumn(new Column(l.getCreationDateConvertToString()));
    		result.addRow(r2);
    	}	
        	
        return result;

    }
    
    /**
     * 
     * @param g
     * @param startdate
     * @param enddate
     * @param r
     * @return
     * @throws IllegalArgumentException
     */
    public AllListitemsOfGroupReport createAllListitemsOfGroupReport(Group g, Date startdate, Date enddate, Retailer r) throws IllegalArgumentException {

    	//Anlegen eines leeren Reports
    	AllListitemsOfGroupReport result = new AllListitemsOfGroupReport();
    	
    	//Setzen des Titels
    	result.setTitle("Report der Gruppe: " + g.getName());
    	
    	//Zeitpunkt der Erstellung speichern
    	result.setCreationDate(new Date());	
    		
		//Ausgeben aller Einkauslisten der Gruppe
		ArrayList<Shoppinglist> shoppinglists = this.shoppinglistMapper.getShoppinglistsOf(g);
		
		//Liste mit allen Eintraegen der Gruppe
		ArrayList<Listitem> listitems = new ArrayList<Listitem>();
		
		//Liste mit allen relevanten Eintraegen der Gruppe
		ArrayList<Listitem> relevantListitems = new ArrayList<Listitem>();
		
		//Erstellen einer Liste mit allen Eintraegen aus allen Listen
		if(!shoppinglists.isEmpty()) {
			for (Shoppinglist s: shoppinglists)	{
    			listitems.addAll(this.listitemMapper.getListitemsOf(s));
    		}
			for (Listitem l : listitems) {
				if(l.getCreationDate().compareTo(startdate) > 0) {
					if(l.getCreationDate().compareTo(enddate) < 0) {
						if(l.getRetailerID() == r.getId()) {
							relevantListitems.add(l);
						}
    				}
				}
			}    			
		}
		
    	//Erstellen eines Tabellenkopfs
    	Row tablehead = new Row();
    	
    	tablehead.addColumn(new Column("Bezeichnung"));
    	tablehead.addColumn(new Column("Menge"));
    	tablehead.addColumn(new Column("Einheit"));
    	tablehead.addColumn(new Column("Haendler"));
    	tablehead.addColumn(new Column("Erstellungsdatum"));
    	result.addRow(tablehead);
    	
    	//Fuer jedes Listitem wird eine Reihe mit Spalten erstellt
    	for(Listitem l : relevantListitems) {
    		Row r3 = new Row();
    		r3.addColumn(new Column(this.listitemMapper.getProductnameOf(l.getId())));       		
    		r3.addColumn(new Column(String.valueOf(l.getAmount())));
    		r3.addColumn(new Column(this.listitemUnitMapper.getUnitOf(l).getName()));
    		r3.addColumn(new Column(this.retailerMapper.getRetailerOf(l).getName()));
    		r3.addColumn(new Column(l.getCreationDateConvertToString()));
    		result.addRow(r3);
    	}
        	
    	return result;

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
		
		return this.shoppinglistAdministration.getListitemsOf(s);
	}

	@Override
	public String getProductnameOf(Listitem l) throws IllegalArgumentException {
		
		return this.shoppinglistAdministration.getProductnameOf(l);
	}

	@Override
	public NamedBusinessObject getListitemUnitOf(Listitem l) throws IllegalArgumentException {
		
		return this.listitemUnitMapper.getUnitOf(l);
	}

	@Override
	public ArrayList<Retailer> getAllRetailers() throws IllegalArgumentException {

		return this.retailerMapper.findAll();
	}
	
}

