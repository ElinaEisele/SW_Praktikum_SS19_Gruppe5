package de.hdm.softwarepraktikum.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.NamedBusinessObject;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;
import de.hdm.softwarepraktikum.shared.report.AllListitemsOfGroupReport;

@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService {
	
    /**
     * Initialsierungsmethode.
     */
    public void init() throws IllegalArgumentException;
    
    /**
     * Methode zum erstellen eines AllListitemsOfGroupReport
     * 
     * @param g Gruppe, für welche der Report erstellt werden soll
     * @return AllListitemsOfGroupReport der vollständige Report
     * @throws IllegalArgumentException
     */
    public AllListitemsOfGroupReport createAllListitemsOfGroupReport(Group g, Date startdate, Date enddate) throws IllegalArgumentException;
    
    /**
     * 
     * @param g
     * @param r
     * @return
     * @throws IllegalArgumentException
     */
    public AllListitemsOfGroupReport createAllListitemsOfGroupReport(Group g, Retailer r) throws IllegalArgumentException;
    
    /**
     * 
     * @param g
     * @param startdate
     * @param enddate
     * @param r
     * @return
     * @throws IllegalArgumentException
     */
    
    public AllListitemsOfGroupReport createAllListitemsOfGroupReport(Group g, Date startdate, Date enddate, Retailer r) throws IllegalArgumentException;
    
    /**
     * 
     * @param u
     * @return ArrayList<Group>
     * @throws IllegalArgumentException
     */
    public ArrayList<Group> getAllGroupsOf(User u) throws IllegalArgumentException;

	/**
	 * 
	 * @param g
	 * @return ArrayList<Shoppinglist>
	 * @throws IllegalArgumentException
	 */
    public ArrayList<Shoppinglist> getShoppinglistsOf(Group g) throws IllegalArgumentException;

	/**
	 * 
	 * @param s
	 * @return ArrayList<Listitem>
	 * @throws IllegalArgumentException
	 */
    public ArrayList<Listitem> getListitemsOf(Shoppinglist s) throws IllegalArgumentException;

	/**
	 * 
	 * @param l
	 * @return String
	 */
    public String getProductnameOf(Listitem l) throws IllegalArgumentException;

	/**
	 * 
	 * @param l
	 * @return NamedBusinessObject
	 */
    public NamedBusinessObject getListitemUnitOf(Listitem l) throws IllegalArgumentException;
    
    /**
     * 
     * @param r
     * @return Arraylist <Retailer>
     * @throws IllegalArgumentException
     */
    public ArrayList<Retailer> getAllRetailers() throws IllegalArgumentException;

}
