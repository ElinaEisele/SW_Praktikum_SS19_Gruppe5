package de.hdm.softwarepraktikum.shared;


import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.softwarepraktikum.server.bo.Group;
import de.hdm.softwarepraktikum.server.bo.Listitem;
import de.hdm.softwarepraktikum.server.bo.Product;
import de.hdm.softwarepraktikum.server.bo.Retailer;
import de.hdm.softwarepraktikum.server.bo.Shoppinglist;
import de.hdm.softwarepraktikum.server.bo.Unit;
import de.hdm.softwarepraktikum.server.bo.User;



/**
 * <p>
 * Synchrone Schnittstelle f�r eine RPC-f�hige Klasse zur Verwaltung von Shoppinglists.
 * </p>
 * <p>
 * <code>@RemoteServiceRelativePath("bankadministration")</code> ist bei der
 * Adressierung des aus der zugeh�rigen Impl-Klasse entstehenden
 * Servlet-Kompilats behilflich. Es gibt im Wesentlichen einen Teil der URL des
 * Servlets an.
 * </p>
 * 
 * @author TimBeutelspacher
 */

@RemoteServiceRelativePath("administration")
public interface ShoppinglistAdministration extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	
	  /**
	   * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von GWT
	   * RPC zus�tzlich zum No Argument Constructor der implementierenden Klasse
	   * {@link ShoppinglistAdministrationImpl} notwendig. Bitte diese Methode direkt nach der
	   * Instantiierung aufrufen.
	   * 
	   * @throws IllegalArgumentException
	   */
	public void init() throws IllegalArgumentException;
	
	/**
	 * Einen User anlegen
	 * @return User-Objekt
	 */
	public User createUser(String mail) throws IllegalArgumentException;
	
	/**
	 * Eine Gruppe anlegen
	 * 
	 */
	public Group createGroup(String name) throws IllegalArgumentException;
	
	/**
	 * Eine Shoppinglist anlegen
	 */
	public Shoppinglist createShoppinglist(Group group, String name) throws IllegalArgumentException;
	
	/**
	 * Ein Listitem anlegen
	 */
	public Listitem createListitem(Shoppinglist shoppinglist, String productname, float amount, Unit unit, Retailer retailer) throws IllegalArgumentException;
	
	/*
	 * Ein Standardeintrag anlegen
	 */
	public Listitem standardListitem(Product product, Group group) throws IllegalArgumentException;
	
	/*
	 * Einen Retailer anlegen
	 */
	public Retailer createRetailer(String name) throws IllegalArgumentException;
	
	/*
	 * Speichern eines User-Objekts in der Datenbank
	 */
	public void save(User user) throws IllegalArgumentException;
	
	/*
	 * Speichern eines Group-Objekts in der Datenbank
	 */
	public void save(Group group) throws IllegalArgumentException;
	
	/*
	 * Speichern eines Shoppinglist-Objekts in der Datenbank
	 */
	public void save(Shoppinglist shoppinglist) throws IllegalArgumentException;
	
	/*
	 * Speichern eines Listitem-Objekt in der Datenbank
	 */
	public void save(Listitem listitem) throws IllegalArgumentException;
	
	/*
	 * Speichern eines Retailer-Objekts in der Datenbank
	 */
	public void save(Retailer retailer) throws IllegalArgumentException;
	
	/*
	 * L�schen des �bergebenen User-Objekts
	 */
	public void delete(User user) throws IllegalArgumentException;
	
	/*
	 * L�schen des �bergebenen Group-Objekts
	 */
	public void delete(Group group) throws IllegalArgumentException;
	
	/*
	 * L�schen des �bergebenen Shoppinglist-Objekts
	 */
	public void delete(Shoppinglist shoppinglist) throws IllegalArgumentException;
	
	/*
	 * L�schen des �bergebenen Listitem-Objekts
	 */
	public void delete(Listitem listitem) throws IllegalArgumentException;
	
	/*
	 * S�mtliche Gruppen eines Users-Objekts ausgeben
	 */
	public ArrayList<Group> getGroupsOf(User user) throws IllegalArgumentException;
	
	/*
	 * S�mtliche Gruppen eines Users mit Hilfe der UserID ausgeben
	 */
	public ArrayList<Group> getGroupsOf(int userId) throws IllegalArgumentException;
	
	/*
	 * S�mtliche Gruppen eines Users mit Hilfe des Usernames ausgeben
	 */
	public ArrayList<Group> getGroupsOf(String username) throws IllegalArgumentException;
	
	
	
	
}

