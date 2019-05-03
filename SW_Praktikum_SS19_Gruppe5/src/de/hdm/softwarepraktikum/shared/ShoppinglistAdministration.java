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
	
	  /**
	   * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von GWT
	   * RPC zusätzlich zum No Argument Constructor der implementierenden Klasse
	   * {@link ShoppinglistAdministrationImpl} notwendig. Bitte diese Methode direkt nach der
	   * Instantiierung aufrufen.
	   * 
	   * @throws IllegalArgumentException
	   */
	public void init() throws IllegalArgumentException;
	
	/**
	 * Einen User anlegen
	 * @param mail GoogleMail des Users
	 * @return fertiges User-Objekt
	 * @throws IllegalArgumentException
	 */
	public User createUser(String mail) throws IllegalArgumentException;
	
	/**
	 * Eine Gruppe anlegen
	 * @param name Gruppenname
	 * @return fertiges Group-Objekt
	 * @throws IllegalArgumentException
	 */
	public Group createGroup(String name) throws IllegalArgumentException;
	
	/**
	 * Eine Shoppinglist anlegen
	 * @param group Gruppe, welcher eine Shoppinglist hinzugefügt werden soll
	 * @param name Name der Shoppinglist
	 * @return fertiges Shoppinglist-Objekt
	 * @throws IllegalArgumentException
	 */
	public Shoppinglist createShoppinglist(Group group, String name) throws IllegalArgumentException;
	
	/**
	 * Ein Listitem anlegen
	 * @param shoppinglist Einkaufsliste, in welcher ein Eintrag erstellt werden soll
	 * @param productname Bezeichneung des zu beschaffenden Artikels
	 * @param amount Mengenangabe des Artikels bezogen auf die Mengeneinheit
	 * @param unit Mengeneinheit 
	 * @param retailer Einzelhändler, bei welchem der Artikel zu beschaffen ist. Hier kann auch die Möglichkeit "Noch nicht bekannt" ausgewählt werden.
	 * @return fertiges Listitem-Objekt
	 * @throws IllegalArgumentException
	 */
	public Listitem createListitem(Shoppinglist shoppinglist, String productname, float amount, Unit unit, Retailer retailer) throws IllegalArgumentException;
	
	/**
	 * Ein Standardeintrag anlegen ohne Unit, Retailer und Menge
	 * @param product zu beschaffender Artikel
	 * @param group Gruppe, in welcher der Standardartikel hinzugefueft werden soll
	 * @return fertiges Listitem-Objekt
	 * @throws IllegalArgumentException
	 */
	public Listitem standardListitem(Product product, Group group) throws IllegalArgumentException;
	
	/**
	 * Einen Retailer anlegen
	 * @param name Name des Einzelhändlers
	 * @return fertiges Listitem-Objekt
	 * @throws IllegalArgumentException
	 */
	public Retailer createRetailer(String name) throws IllegalArgumentException;
	
	/**
	 * Speichern eines User-Objekts in der Datenbank
	 * @param user User-Objekt, welches in der Datenbank gespeichert werden soll
	 * @throws IllegalArgumentException
	 */
	public void save(User user) throws IllegalArgumentException;
	
	/**
	 * Speichern eines Group-Objekts in der Datenbank
	 * @param group Group-Objekt, welches in der Datenbank gespeichert werden soll
	 * @throws IllegalArgumentException
	 */
	public void save(Group group) throws IllegalArgumentException;
	
	/**
	 * Speichern eines Shoppinglist-Objekts in der Datenbank
	 * @param shoppinglist, Shoppinglist-Objekt, welches in der Datenbank gespeichert weden soll
	 * @throws IllegalArgumentException
	 */
	public void save(Shoppinglist shoppinglist) throws IllegalArgumentException;
	
	/**
	 * Speichern eines Listitem-Objekt in der Datenbank
	 * @param listitem, Listitem-Objekt, welches in der Datenbank gepseichert werden soll
	 * @throws IllegalArgumentException
	 */
	public void save(Listitem listitem) throws IllegalArgumentException;
	
	/**
	 * Speichern eines Retailer-Objekts in der Datenbank
	 * @param retailer Retailer-Objekt, welches in der Datenbank gepseichert werden soll
	 * @throws IllegalArgumentException
	 */
	public void save(Retailer retailer) throws IllegalArgumentException;
	
	/**
	 * Löschen des übergebenen User-Objekts
	 * @param user User-Objekt, welches in der Datenbank gelöscht werden soll
	 * @throws IllegalArgumentException
	 */
	public void delete(User user) throws IllegalArgumentException;
	
	/**
	 * Löschen des übergebenen Group-Objekts
	 * @param group Group-Objekt, welches in der Datenbank gelöscht werden soll
	 * @throws IllegalArgumentException
	 */
	public void delete(Group group) throws IllegalArgumentException;
	
	/**
	 * Löschen des übergebenen Shoppinglist-Objekts
	 * @param shoppinglist Shoppinglist-Objekt, welches in der Datenbank gelöscht werden soll
	 * @throws IllegalArgumentException
	 */
	public void delete(Shoppinglist shoppinglist) throws IllegalArgumentException;
	
	/**
	 * Löschen des übergebenen Listitem-Objekts
	 * @param listitem Listitem-Objekt, welches in der Datenbank gelöscht werden soll
	 * @throws IllegalArgumentException
	 */
	public void delete(Listitem listitem) throws IllegalArgumentException;
	
	/**
	 * Sämtliche Gruppen eines Users-Objekts ausgeben
	 * @param user Nutzer, dessen Gruppen angezeigt werden sollen
	 * @return ArrayList sämtlicher Gruppen eines Users
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Group> getGroupsOf(User user) throws IllegalArgumentException;
	
	/**
	 * Sämtliche Gruppen eines Users mit Hilfe der UserID ausgeben
	 * @param userId ID eines Nutzers, dessen Gruppen angezeigt werden sollen
	 * @return ArrayList sämtlicher Gruppen eines Users
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Group> getGroupsOf(int userId) throws IllegalArgumentException;
	
	/**
	 * Sämtliche Gruppen eines Users mit Hilfe des Usernames ausgeben
	 * @param username eines Nutzers Nutzer, dessen Gruppen angezeigt werden sollen
	 * @return ArrayList sämtlicher Gruppen eines Users
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Group> getGroupsOf(String username) throws IllegalArgumentException;
	
	/**
	 * Rückgabe eines bestimmten Group-Objekts
	 * @param id ID der gesuchten Gruppe
	 * @return Das erste Group-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	public Group getGroupById(int id) throws IllegalArgumentException;
	
	/**
	 * Sämliche Mitglieder einer Gruppe ausgeben mit Hilfe der Übergabe eines Gruppen-Objekts
	 * @param group Gruppe, deren Mitglieder ausgegeben werden sollen
	 * @return ArrayList sämtlicher Mitglieder einer Gruppe
	 * @throws IllegalArgumentException
	 */
	public ArrayList<User> getUsersOf(Group group) throws IllegalArgumentException;
	
	/**
	 * Sämliche Mitglieder einer Gruppe ausgeben mit Hilfe der Übergabe der ID eines Gruppen-Obejekts
	 * @param groupId GruppenID von einer Gruppe, deren Mitglieder ausgegeben werden sollen
	 * @return ArrayList sämtlicher Mitglieder einer Gruppe
	 * @throws IllegalArgumentException
	 */
	public ArrayList<User> getUsersOf(int groupId) throws IllegalArgumentException;
	
	/**
	 * Rückgabe eines User-Objekts mit einer bestimmten ID
	 * @param userId ID des gesuchten User-Objekts
	 * @return Das erste User-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	public User getUserById(int userId) throws IllegalArgumentException;
	
	/**
	 * Sämtliche User-Objekte mit einem bestimmten Namen werden ausgegeben
	 * @param name Username
	 * @return ArrayList sämtlicher User-Objekte, welche einen bestimmten Namen besitzen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<User> getUsersByName(String name) throws IllegalArgumentException;
	
	/**
	 * User-Objekt mit einer bestimmten E-Mail-Adresse wird ausgegeben
	 * @param mail ist die EMail des gesuchten Users
	 * @return User, welcher übergebene EMail-Adresse besitzt
	 * @throws IllegalArgumentException
	 */
	public User getUserByMail(String mail) throws IllegalArgumentException;
	
	/**
	 * Sämtliche Shoppinglist-Objekte aus einer Gruppe werden ausgegeben
	 * @param group Gruppe, deren Shoppinglist-Objekte ausgegeben werden sollen
	 * @return Das erste User-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Shoppinglist> getShoppinglistsOf(Group group) throws IllegalArgumentException;
	
	/**
	 * Sämtliche Shoppinglist-Objekt mit einem bestimmten Namen werden ausgegeben
	 * @param name ist die Bezeichnung der gesuchten Shoppinglists
	 * @return ArrayList mit Shoppinglist-Objekten, welche einen bestimmten Namen besitzen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Shoppinglist> getShoppinglistsByName(String name) throws IllegalArgumentException;
	
	/**
	 * Das Shoppinglist-Objekt mit der übergebenen ID wird ausgegeben
	 * @param shoppinglistId ist die ID der gesuchten Shoppinglist
	 * @return Das erste Shoppinglist-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	public Shoppinglist getShoppinglistById(int shoppinglistId) throws IllegalArgumentException;
	
	/**
	 * Sämtliche Listitem-Objekte mit einer bestimmten Produktbezeichnung in einer bestimmen Einkaufsliste werden zurückgegeben
	 * @param shoppinglist ist die Einkaufsliste, in welcher nach einer bestimmten Produktbezeichnung gesucht werden soll
	 * @param productname ist die Produktbezeichung nach welcher gesucht werden soll
	 * @return ArrayList mit Listitem-Objekten, welche eine bestimmte Prosuktbezeichung enthalten
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Listitem> getListitemsOf(Shoppinglist shoppinglist, String productname) throws IllegalArgumentException;
	
	/**
	 * Sämtliche Listitem-Objekte auch einer bestimmten Shoppinglist werden ausgegeben
	 * @param shoppinglist ist die Einkaufsliste, aus welcher alle Listitem-Objekte ausgegeben werden sollen
	 * @return ArrayList mit allen Listitem-Objekten aus einer bestimmten Einkaufsliste
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Listitem> getAllListitemsOf(Shoppinglist shoppinglist) throws IllegalArgumentException;
	
	
	
	
	
	
	
}

