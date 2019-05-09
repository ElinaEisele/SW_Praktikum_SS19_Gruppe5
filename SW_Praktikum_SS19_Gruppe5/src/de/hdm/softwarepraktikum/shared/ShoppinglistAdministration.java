package de.hdm.softwarepraktikum.shared;


import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Product;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Unit;
import de.hdm.softwarepraktikum.shared.bo.User;
import de.hdm.softwarepraktikum.shared.dummydata.GroupDD;
import de.hdm.softwarepraktikum.shared.dummydata.ShoppinglistDD;
import de.hdm.softwarepraktikum.shared.dummydata.UserDD;


/**
 * <p>
 * Synchrone Schnittstelle f�r eine RPC-f�hige Klasse zur Verwaltung von Shoppinglists.
 * </p>
 * <p>
 * <code>@RemoteServiceRelativePath("shoppinglistadministration")</code> ist bei der
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
	
	//Methode zum Testen der Dummydata fuer die Registrierung
	public void saveUser(User u) throws IllegalArgumentException;; 
	
	ArrayList<Group> getAllGroups();

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
	public GroupDD createGroup(String name) throws IllegalArgumentException;
	
	/**
	 * Eine Shoppinglist anlegen
	 * @param group Gruppe, welcher eine Shoppinglist hinzugef�gt werden soll
	 * @param name Name der Shoppinglist
	 * @return fertiges Shoppinglist-Objekt
	 * @throws IllegalArgumentException
	 */
	public ShoppinglistDD createShoppinglist(GroupDD group, String name) throws IllegalArgumentException;
	
	/**
	 * Ein Listitem anlegen
	 * @param shoppinglist Einkaufsliste, in welcher ein Eintrag erstellt werden soll
	 * @param productname Bezeichneung des zu beschaffenden Artikels
	 * @param amount Mengenangabe des Artikels bezogen auf die Mengeneinheit
	 * @param unit Mengeneinheit 
	 * @param retailer Einzelh�ndler, bei welchem der Artikel zu beschaffen ist. Hier kann auch die M�glichkeit "Noch nicht bekannt" ausgew�hlt werden.
	 * @return fertiges Listitem-Objekt
	 * @throws IllegalArgumentException
	 */
	public Listitem createListitem(ShoppinglistDD shoppinglist, String productname, float amount, Unit unit, Retailer retailer) throws IllegalArgumentException;
	
	/**
	 * Ein Standardeintrag anlegen ohne Unit, Retailer und Menge
	 * @param product zu beschaffender Artikel
	 * @param group Gruppe, in welcher der Standardartikel hinzugefueft werden soll
	 * @return fertiges Listitem-Objekt
	 * @throws IllegalArgumentException
	 */
	public Listitem standardListitem(Product product, GroupDD group) throws IllegalArgumentException;
	
	/**
	 * Einen Retailer anlegen
	 * @param name Name des Einzelh�ndlers
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
	public void save(ShoppinglistDD shoppinglist) throws IllegalArgumentException;
	
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
	 * L�schen des �bergebenen User-Objekts
	 * @param user User-Objekt, welches in der Datenbank gel�scht werden soll
	 * @throws IllegalArgumentException
	 */
	public void delete(User user) throws IllegalArgumentException;
	
	/**
	 * L�schen des �bergebenen Group-Objekts
	 * @param group Group-Objekt, welches in der Datenbank gel�scht werden soll
	 * @throws IllegalArgumentException
	 */
	public void delete(GroupDD group) throws IllegalArgumentException;
	
	/**
	 * L�schen des �bergebenen Shoppinglist-Objekts
	 * @param shoppinglist Shoppinglist-Objekt, welches in der Datenbank gel�scht werden soll
	 * @throws IllegalArgumentException
	 */
	public void delete(ShoppinglistDD shoppinglist) throws IllegalArgumentException;
	
	/**
	 * L�schen des �bergebenen Listitem-Objekts
	 * @param listitem Listitem-Objekt, welches in der Datenbank gel�scht werden soll
	 * @throws IllegalArgumentException
	 */
	public void delete(Listitem listitem) throws IllegalArgumentException;
	
	/**
	 * S�mtliche Gruppen eines Users-Objekts ausgeben
	 * @param user Nutzer, dessen Gruppen angezeigt werden sollen
	 * @return ArrayList s�mtlicher Gruppen eines Users
	 * @throws IllegalArgumentException
	 */
	public ArrayList<GroupDD> getGroupsOf(User user) throws IllegalArgumentException;
	
	/**
	 * S�mtliche Gruppen eines Users mit Hilfe der UserID ausgeben
	 * @param userId ID eines Nutzers, dessen Gruppen angezeigt werden sollen
	 * @return ArrayList s�mtlicher Gruppen eines Users
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Group> getGroupsOf(int userId) throws IllegalArgumentException;
	
	/**
	 * S�mtliche Gruppen eines Users mit Hilfe des Usernames ausgeben
	 * @param username eines Nutzers Nutzer, dessen Gruppen angezeigt werden sollen
	 * @return ArrayList s�mtlicher Gruppen eines Users
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Group> getGroupsOf(String username) throws IllegalArgumentException;
	
	/**
	 * R�ckgabe eines bestimmten Group-Objekts
	 * @param id ID der gesuchten Gruppe
	 * @return Das erste Group-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	public GroupDD getGroupById(int id) throws IllegalArgumentException;
	
	/**
	 * S�mliche Mitglieder einer Gruppe ausgeben mit Hilfe der �bergabe eines Gruppen-Objekts
	 * @param group Gruppe, deren Mitglieder ausgegeben werden sollen
	 * @return ArrayList s�mtlicher Mitglieder einer Gruppe
	 * @throws IllegalArgumentException
	 */
	public ArrayList<User> getUsersOf(GroupDD group) throws IllegalArgumentException;
	
	/**
	 * S�mliche Mitglieder einer Gruppe ausgeben mit Hilfe der �bergabe der ID eines Gruppen-Obejekts
	 * @param groupId GruppenID von einer Gruppe, deren Mitglieder ausgegeben werden sollen
	 * @return ArrayList s�mtlicher Mitglieder einer Gruppe
	 * @throws IllegalArgumentException
	 */
	public ArrayList<User> getUsersOf(int groupId) throws IllegalArgumentException;
	
	/**
	 * R�ckgabe eines User-Objekts mit einer bestimmten ID
	 * @param userId ID des gesuchten User-Objekts
	 * @return Das erste User-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	public User getUserById(int userId) throws IllegalArgumentException;
	
	/**
	 * S�mtliche User-Objekte mit einem bestimmten Namen werden ausgegeben
	 * @param name Username
	 * @return ArrayList s�mtlicher User-Objekte, welche einen bestimmten Namen besitzen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<User> getUsersByName(String name) throws IllegalArgumentException;
	
	/**
	 * User-Objekt mit einer bestimmten E-Mail-Adresse wird ausgegeben
	 * @param mail ist die EMail des gesuchten Users
	 * @return User, welcher �bergebene EMail-Adresse besitzt
	 * @throws IllegalArgumentException
	 */
	public User getUserByMail(String mail) throws IllegalArgumentException;
	
	/**
	 * S�mtliche Shoppinglist-Objekte aus einer Gruppe werden ausgegeben
	 * @param group Gruppe, deren Shoppinglist-Objekte ausgegeben werden sollen
	 * @return Das erste User-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Shoppinglist> getShoppinglistsOf(GroupDD group) throws IllegalArgumentException;
	
	/**
	 * S�mtliche Shoppinglist-Objekt mit einem bestimmten Namen werden ausgegeben
	 * @param name ist die Bezeichnung der gesuchten Shoppinglists
	 * @return ArrayList mit Shoppinglist-Objekten, welche einen bestimmten Namen besitzen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Shoppinglist> getShoppinglistsByName(String name) throws IllegalArgumentException;
	
	/**
	 * Das Shoppinglist-Objekt mit der �bergebenen ID wird ausgegeben
	 * @param shoppinglistId ist die ID der gesuchten Shoppinglist
	 * @return Das erste Shoppinglist-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	public Shoppinglist getShoppinglistById(int shoppinglistId) throws IllegalArgumentException;
	
	/**
	 * S�mtliche Listitem-Objekte mit einer bestimmten Produktbezeichnung in einer bestimmen Einkaufsliste werden zur�ckgegeben
	 * @param shoppinglist ist die Einkaufsliste, in welcher nach einer bestimmten Produktbezeichnung gesucht werden soll
	 * @param productname ist die Produktbezeichung nach welcher gesucht werden soll
	 * @return ArrayList mit Listitem-Objekten, welche eine bestimmte Prosuktbezeichung enthalten
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Listitem> getListitemsOf(ShoppinglistDD shoppinglist, String productname) throws IllegalArgumentException;
	
	/**
	 * S�mtliche Listitem-Objekte auch einer bestimmten Shoppinglist werden ausgegeben
	 * @param shoppinglist ist die Einkaufsliste, aus welcher alle Listitem-Objekte ausgegeben werden sollen
	 * @return ArrayList mit allen Listitem-Objekten aus einer bestimmten Einkaufsliste
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Listitem> getAllListitemsOf(ShoppinglistDD shoppinglist) throws IllegalArgumentException;
	
	/**
	 * S�mtliche Retailer-Objetke werden ausgegeben
	 * @return ArrayList mit allen Retailer-Objekten
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Retailer> getAllRetailers() throws IllegalArgumentException;
	
	/**
	 * S�mtliche Retailer-Objekte mit einem bestimmten Namen werden ausgegeben
	 * @param name ist die Bezeichnung der gesuchten Retailer-Objekte
	 * @return ArrayList mit allen Ratailer-Objekten, welche einen bestimmten Namen besitzen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Retailer> getRetailersByName(String name) throws IllegalArgumentException;
	
	/**
	 * Ein Retailer-Objekt mit einer bestimmten ID wird ausgegeben
	 * @param retailerId ist die ID des gesuchten Einzelh�ndlers
	 * @return Das erste Retailer-Objekt, welches den Suchkriterien entspricht wird ausgegeben
	 * @throws IllegalArgumentException
	 */
	public Retailer getRetailerById(int retailerId) throws IllegalArgumentException; 
	
	/**
	 * S�mtliche Retailer-Objekte in einer Shoppinglist werden ausgegeben
	 * @param shoppinglist ist die Einkaufsliste, in welcher nach allen Retailer-Objekten gesucht wird
	 * @return ArrayList mit allen Retailer-Objekten innerhalb einer bestimmten Shoppinglist
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Retailer> getRetailersOf(ShoppinglistDD shoppinglist) throws IllegalArgumentException;
	
	/**
	 * S�mtliche Retailer-Objekte einer Shoppinglist, welche einem bestimmten User zugeordnet sind werden ausgegeben
	 * @param shoppinglist shoppinglist ist die Einkaufsliste, in welcher nach allen Retailer-Objekten gesucht wird
	 * @param user ist der Nutzer, nach wessen zugewiesenen Einzelh�ndlern gesucht wird
	 * @return ArrayList mit allen Retailer-Objekten innerhalb einer bestimmten Shoppinglist, welche einem besimmten Nutzer zugeordnet sind
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Retailer> getRetailersOf(ShoppinglistDD shoppinglist, User user) throws IllegalArgumentException;
	
	/**
	 * Ein Retailer-Objekt wird einem Listitem als Beschaffungsort zugewiesen
	 * @param retailer ist der Einzel�ndler, welcher als Beschaffungsort eines Eintrags gilt
	 * @param listitem ist der Eintrag, welchem der Retailer zugeordnet wird
	 * @throws IllegalArgumentException
	 */
	public void assignRetailer(Retailer retailer, Listitem listitem) throws IllegalArgumentException;
	
	/**
	 * Ein Nutzer wird einem Eintrag als Verantwortlicher zugeordnet
	 * @param user ist der Nutzer, welcher einem Eintrag als Verantwortlicher zugeordnet wird
	 * @param listitem ist er Eintrag, welcher einen Nutzer als Verantwortlichen erh�lt
	 * @throws IllegalArgumentException
	 */
	public void assignUser (User user, Listitem listitem) throws IllegalArgumentException;
	
	/**
	 * Einem Eintrag wird Produkt zugeordnet
	 * @param product ist das Produkt, welches einem Eintrag zugeordnet wird
	 * @param listitem ist der Eintrag, welchem ein Produkt zugeordnet wird
	 * @throws IllegalArgumentException
	 */
	public void setProduct (Product product, Listitem listitem) throws IllegalArgumentException;
	
	/**
	 * Setzen eines Listitem-Objekts inkl. User-Objekt
	 * @param product ist das Produkt, welches sich in dem Listitem-Objekt befindet
	 * @param amount ist die Menge des zu besorgenden Produkts bezogen auf die angegebene Mengenienheit
	 * @param unit ist die Mengeneinheit, in welcher die Menge angegeben wird
	 * @param retailer ist der Einzelh�ndler, bei welchem das Produkt zu besorgen ist
	 * @param user ist der Nutzer, welcher f�r das Besorgen des Produkts verantwortlich ist
	 */
	public void setListitem(Product product, float amount, Unit unit, Retailer retailer, User user) throws IllegalArgumentException;
	
	/**
	 * Setzen eines Listitem-Objekts ohne User-Objekt
	 * @param product ist das Produkt, welches sich in dem Listitem-Objekt befindet
	 * @param amount ist die Menge des zu besorgenden Produkts bezogen auf die angegebene Mengenienheit
	 * @param unit ist die Mengeneinheit, in welcher die Menge angegeben wird
	 * @param retailer ist der Einzelh�ndler, bei welchem das Produkt zu besorgen ist
	 */
	public void setListitem(Product product, float amount, Unit unit, Retailer retailer) throws IllegalArgumentException;
	
	/**
	 * Setzen eines Standard-Eintrags innerhalb einer Gruppe
	 * @param listitem ist der Eintrag, welcher als Standard gesetzt wird
	 * @param group ist die Gruppe, in welcher der Standardeintrag gesetzt wird
	 * @throws IllegalArgumentException
	 */
	public void setStandardListitem(Listitem listitem, GroupDD group) throws IllegalArgumentException; 
	
	/**
	 * Ausgeben des Product-Objekts aus einem Listitem-Objekt
	 * @param listitem ist der Eintrag, aus welchem das Produkt ausgegeben werden soll
	 * @throws IllegalArgumentException
	 */
	public void getProductOf(Listitem listitem) throws IllegalArgumentException;
	
	/**
	 * Ausgeben von allen Listitems aus einer Gruppe
	 * @param group ist die Gruppe, aus welcher die StandardListitems ausgegeben werden sollen
	 * @return ArrayList mit Listitem-Objekte, welche innerhalb einer Gruppe als StandardListitems markiert wurden
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Listitem> getStandardListitemsOf(GroupDD group) throws IllegalArgumentException;
	
	/**
	 * Filtern einer Einkaufsliste nach Verantwortungsbereich eines Nutzers
	 * @param shoppinglist ist die Einkaufsliste, in welcher nach Verantwortungsbereich eines bestimmten Nutzers gefiltert werden soll
	 * @param user ist der Nutzer, nach wessen Verantwortungsbereich gefiltert werden soll
	 * @return ArrayList mit Listitem-Objekten, welche im Verantwortungberiehc eines Nutzers liegen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Listitem> filterShoppinglistsByUsername(ShoppinglistDD shoppinglist, User user) throws IllegalArgumentException;
	
	/**
	 * Filtern einer Einkaufsliste nach Listitem-Objekten, welche einem bestimmten Einzelh�ndler zugeordnet sind
	 * @param shoppinglist ist die Einkaufslsite, in welcher nach Einzelh�ndler gefiltert werden soll
	 * @param retailer ist der Einzelh�ndler, nach welchem die Einkaufsliste gefiltert werden soll
	 * @return ArrayList mit Listitem-Objekten, welche einem bestimmten Einzelh�ndler zugeordnet sind
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Listitem> filterShoppinglistsByRetailer(ShoppinglistDD shoppinglist, Retailer retailer) throws IllegalArgumentException;
	
	/**
	 * Ein User-Objekt einer Gruppe hinzufuegen
	 * @param user ist ein Nutzer, welcher einer Gruppe hinzugefuegt wird
	 * @param group ist eine Gruppe, welcher ein User-Objekt hinzugf�gt wird
	 * @throws IllegalArgumentException
	 */
	public void addUserToGroup(User user, GroupDD group) throws IllegalArgumentException;
	
	/**
	 * Ein User-Objekt soll von einer Gruppe entfernt werden
	 * @param user ist ein Nutzer, welcher von einer Gruppe entfernt wird
	 * @param group ist eine Gruppe, von welcher ein User-Objekt entfernt werden soll
	 * @throws IllegalArgumentException
	 */
	public void removeUserFromGroup(User user, GroupDD group) throws IllegalArgumentException;
	
	/**
	 * Setzen einer Bezeichnung f�r ein Produkt
	 * @param name ist die Bezeichnung des Produkts
	 * @param product ist das Produkt, welches die Bezcihung erh�lt
	 * @throws IllegalArgumentException
	 */
	public void setProductName(String name, Product product) throws IllegalArgumentException;
	
	/**
	 * Setzen einer Menge f�r einen Eintrag
	 * @param amount ist die Menge
	 * @param listitem ist der Eintrag, f�r welchen die Menge gesetzt wird
	 * @throws IllegalArgumentException
	 */
	public void setAmount(float amount, Listitem listitem) throws IllegalArgumentException;
	
	/**
	 * Setzen einer Mengeneinheit f�r ein Listitem-Objekt
	 * @param unit ist die Mengeneinheit, welche einem Listitem-Objekt hinzugef�gt wird
	 * @param listitem ist der Eintrag, welchem eine Menge hinzugef�gt wird
	 * @throws IllegalArgumentException
	 */
	public void setUnit(Unit unit, Listitem listitem) throws IllegalArgumentException;
	
	/**
	 * Ausgeben der Mengeneinheit eines Eintrags
	 * @param listitem ist der Eintrag, dessen Mengeneinheit zur�ckgegeben wird
	 * @return Unit 
	 * @throws IllegalArgumentException
	 */
	public Unit getUnit(Listitem listitem) throws IllegalArgumentException;
	
	/**
	 * Ausgeben der Menge eines Eintrags
	 * @param listitem ist der Eintrag, dessen Menge ausgegeben wird
	 * @return float
	 * @throws IllegalArgumentException
	 */
	public float getAmount(Listitem listitem) throws IllegalArgumentException;
}

