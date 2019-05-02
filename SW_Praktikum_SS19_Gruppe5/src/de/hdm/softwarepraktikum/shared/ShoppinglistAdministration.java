package de.hdm.softwarepraktikum.shared;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.softwarepraktikum.server.bo.Group;
import de.hdm.softwarepraktikum.server.bo.Listitem;
import de.hdm.softwarepraktikum.server.bo.Product;
import de.hdm.softwarepraktikum.server.bo.Retailer;
import de.hdm.softwarepraktikum.server.bo.Shoppinglist;
import de.hdm.softwarepraktikum.server.bo.Unit;
import de.hdm.softwarepraktikum.server.bo.User;



/*
 * <p>
 * Synchrone Schnittstelle für eine RPC-fähige Klasse zur Verwaltung von Shoppinglists.
 * </p>
 * <p>
 * <code>@RemoteServiceRelativePath("bankadministration")</code> ist bei der
 * Adressierung des aus der zugehörigen Impl-Klasse entstehenden
 * Servlet-Kompilats behilflich. Es gibt im Wesentlichen einen Teil der URL des
 * Servlets an.
 * </p>
 * 
 * @author TimBeutelspacher
 */

@RemoteServiceRelativePath("administration")
public interface ShoppinglistAdministration extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	
	  /*
	   * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von GWT
	   * RPC zusätzlich zum No Argument Constructor der implementierenden Klasse
	   * {@link ShoppinglistAdministrationImpl} notwendig. Bitte diese Methode direkt nach der
	   * Instantiierung aufrufen.
	   * 
	   * @throws IllegalArgumentException
	   */
	public void init() throws IllegalArgumentException;
	
	/*
	 * Einen User anlegen
	 */
	public User createUser(String mail) throws IllegalArgumentException;
	
	/*
	 * Eine Gruppe anlegen
	 */
	public Group createGroup(String name) throws IllegalArgumentException;
	
	/*
	 * Eine Shoppinglist anlegen
	 */
	public Shoppinglist createShoppinglist(Group group, String name) throws IllegalArgumentException;
	
	/*
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
	
	
	
}

