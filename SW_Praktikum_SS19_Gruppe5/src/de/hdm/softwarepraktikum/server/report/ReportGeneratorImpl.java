package de.hdm.softwarepraktikum.server.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import de.hdm.softwarepraktikum.server.db.ProductMapper;
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
	
	private ProductMapper productMapper = null;
	
	private Float amount = null;
	
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
      this.productMapper = ProductMapper.productMapper();
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
    	
      AllListitemsOfGroupReport result = new AllListitemsOfGroupReport();

    	result.setTitle("Report der Gruppe: " + g.getName());
    	result.setCreationDate(new Date());
		
    	ArrayList<Shoppinglist> shoppinglists = this.shoppinglistMapper.getShoppinglistsOf(g);
  		ArrayList<Listitem> listitems = new ArrayList<Listitem>();
	
	  	if(!shoppinglists.isEmpty()) {
		
			for (Shoppinglist s: shoppinglists)	{
				ArrayList<Listitem>  listit = this.listitemMapper.getArchivedListitemsOf(s);
				for(Listitem rw : listit) {
					listitems.add(rw);
				}
			}
		}

		Map<String, Float> map = new HashMap<String, Float>();
		for (Listitem l : listitems) {
			if(l.getRetailerID() == r.getId()) {
				
				String key = l.getRetailerID() + ";" +l.getListitemUnitID() + ";" +this.productMapper.findById(l.getProductID()).getName();
				
				if(map.containsKey(key)) {
					float tmp = map.get(key);
					map.put(key, l.getAmount() +tmp);
				}
				else {
					map.put(key, l.getAmount());
				}
			}    			
		}

		Row tablehead = new Row();
    	
    	tablehead.addColumn(new Column("Bezeichnung"));
    	tablehead.addColumn(new Column("Menge"));
    	tablehead.addColumn(new Column("Einheit"));
    	tablehead.addColumn(new Column("Haendler"));
    	result.addRow(tablehead);
    	
		map.forEach((k, v) -> {
			String[] tmp = k.split(";");
			int retailId = Integer.parseInt(tmp[0].toString());
			int unitId = Integer.parseInt(tmp[1].toString());
			String name = tmp[2];
	   
    		Row r3 = new Row();
    		r3.addColumn(new Column(name.toString()));       		
    		r3.addColumn(new Column(v.toString()));
    		r3.addColumn(new Column(this.listitemUnitMapper.findById(unitId).getName()));
    		r3.addColumn(new Column(this.retailerMapper.findById(retailId).getName()));
    		result.addRow(r3);
		});

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
    	
    	AllListitemsOfGroupReport result = new AllListitemsOfGroupReport();

    	result.setTitle("Report der Gruppe: " + g.getName());
    	result.setCreationDate(new Date());
		
    	ArrayList<Shoppinglist> shoppinglists = this.shoppinglistMapper.getShoppinglistsOf(g);
		ArrayList<Listitem> listitems = new ArrayList<Listitem>();
		
		if(!shoppinglists.isEmpty()) {
			for (Shoppinglist s: shoppinglists)	{
				ArrayList<Listitem>  listit = this.listitemMapper.getArchivedListitemsOf(s);
				for(Listitem rw : listit) {
					listitems.add(rw);
				}
    		}
		}
    	
		Map<String, Float> map = new HashMap<String, Float>();
		for (Listitem l : listitems) {
			if(l.getCreationDate().compareTo(startdate) > 0) {
				if(l.getCreationDate().compareTo(enddate) < 0) {
						
					String key =l.getListitemUnitID() + ";" + this.productMapper.findById(l.getProductID()).getName();
					
					if(map.containsKey(key)) {
						float tmp = map.get(key);
						map.put(key, l.getAmount() +tmp);
					}
					else {
						map.put(key, l.getAmount());
					}
				}
			}    			
		}

		Row tablehead = new Row();
    	
    	tablehead.addColumn(new Column("Bezeichnung"));
    	tablehead.addColumn(new Column("Menge"));
    	tablehead.addColumn(new Column("Einheit"));
    	result.addRow(tablehead);
    	
		map.forEach((k, v) -> {
			String[] tmp = k.split(";");
			int unitId = Integer.parseInt(tmp[0].toString());
			String name = tmp[1];
	   
    		Row r3 = new Row();
    		r3.addColumn(new Column(name.toString()));       		
    		r3.addColumn(new Column(v.toString()));
    		r3.addColumn(new Column(this.listitemUnitMapper.findById(unitId).getName()));
    		result.addRow(r3);
		});

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

    	AllListitemsOfGroupReport result = new AllListitemsOfGroupReport();

    	result.setTitle("Report der Gruppe: " + g.getName());
    	result.setCreationDate(new Date());
		
    	ArrayList<Shoppinglist> shoppinglists = this.shoppinglistMapper.getShoppinglistsOf(g);
		ArrayList<Listitem> listitems = new ArrayList<Listitem>();
	
		if(!shoppinglists.isEmpty()) {
			for (Shoppinglist s: shoppinglists)	{
				ArrayList<Listitem>  listit = this.listitemMapper.getArchivedListitemsOf(s);
				for(Listitem rw : listit) {
					listitems.add(rw);
				}
			}
		}

		Map<String, Float> map = new HashMap<String, Float>();
		for (Listitem l : listitems) {
			if(l.getCreationDate().compareTo(startdate) > 0) {
				if(l.getCreationDate().compareTo(enddate) < 0) {
					if(l.getRetailerID() == r.getId()) {
						
						String key = l.getRetailerID() + ";" +l.getListitemUnitID() + ";" +this.productMapper.findById(l.getProductID()).getName();
						
						if(map.containsKey(key)) {
							float tmp = map.get(key);
							map.put(key, l.getAmount() +tmp);
						}
						else {
							map.put(key, l.getAmount());
						}
					}
				}
			}    			
		}

		Row tablehead = new Row();
    	
    	tablehead.addColumn(new Column("Bezeichnung"));
    	tablehead.addColumn(new Column("Menge"));
    	tablehead.addColumn(new Column("Einheit"));
    	tablehead.addColumn(new Column("Haendler"));
    	result.addRow(tablehead);
    	
		map.forEach((k, v) -> {
			String[] tmp = k.split(";");
			int retailId = Integer.parseInt(tmp[0].toString());
			int unitId = Integer.parseInt(tmp[1].toString());
			String name = tmp[2];
	   
    		Row r3 = new Row();
    		r3.addColumn(new Column(name.toString()));       		
    		r3.addColumn(new Column(v.toString()));
    		r3.addColumn(new Column(this.listitemUnitMapper.findById(unitId).getName()));
    		r3.addColumn(new Column(this.retailerMapper.findById(retailId).getName()));
    		result.addRow(r3);
		});

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

