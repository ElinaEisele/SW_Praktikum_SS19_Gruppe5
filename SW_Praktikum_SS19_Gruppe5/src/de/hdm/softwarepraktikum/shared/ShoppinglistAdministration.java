package de.hdm.softwarepraktikum.shared;


import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Product;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.ListitemUnit;
import de.hdm.softwarepraktikum.shared.bo.User;


/**
 * <p>
 * Synchrone Schnittstelle fuer eine RPC-faehige Klasse zur Verwaltung von Shoppinglists.
 * </p>
 * <p>
 * <code>@RemoteServiceRelativePath("shoppinglistadministration")</code> ist bei der
 * Adressierung des aus der zugehï¿½rigen Impl-Klasse entstehenden
 * Servlet-Kompilats behilflich. Es gibt im Wesentlichen einen Teil der URL des
 * Servlets an.
 * </p>
 * 
 * @author TimBeutelspacher
 */

@RemoteServiceRelativePath("administration")
public interface ShoppinglistAdministration extends RemoteService {
		
	/**
	 * Alle Gruppen werden ausgegeben.
	 * @return ArrayList mit Group-Objekten
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Group> getAllGroups() throws IllegalArgumentException;

	  /**
	   * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von GWT
	   * RPC zusaetzlich zum No Argument Constructor der implementierenden Klasse
	   * notwendig. Bitte diese Methode direkt nach der Instantiierung aufrufen.
	   * 
	   * @throws IllegalArgumentException
	   */
	public void init() throws IllegalArgumentException;
	
	/**
	 * Einen User anlegen
	 * @param mail GoogleMail des Users
	 * @param name Name des Users
	 * @return fertiges User-Objekt
	 * @throws IllegalArgumentException
	 */
	public User createUser(String name, String mail) throws IllegalArgumentException;
	
	/**
	 * Eine Gruppe anlegen
	 * @param user Nutzer der eine Gruppe erstellt
	 * @param name Gruppenname
	 * @return fertiges Group-Objekt
	 * @throws IllegalArgumentException
	 */
	public Group createGroupFor(User user, String name) throws IllegalArgumentException;
	
	/**
	 * Eine Shoppinglist anlegen
	 * @param group Gruppe, welcher eine Shoppinglist hinzugefuegt werden soll
	 * @param name Name der Shoppinglist
	 * @return fertiges Shoppinglist-Objekt
	 * @throws IllegalArgumentException
	 */
	public Shoppinglist createShoppinglistFor(Group group, String name) throws IllegalArgumentException;
	
	/**
	 * Ein Listitem anlegen mit Retailer
	 * @param shoppinglist Einkaufsliste, in welcher ein Eintrag erstellt werden soll
	 * @param productname Bezeichneung des zu beschaffenden Artikels
	 * @param amount Mengenangabe des Artikels bezogen auf die Mengeneinheit
	 * @param listitemUnit Mengeneinheit 
	 * @param retailer Einzelhaendler, bei welchem der Artikel zu beschaffen ist. Hier kann auch die Moeglichkeit "Noch nicht bekannt" ausgewaehlt werden.
	 * @return fertiges Listitem-Objekt
	 * @throws IllegalArgumentException
	 */
	public Listitem createListitem(Shoppinglist shoppinglist, String productname, float amount, ListitemUnit listitemUnit, Retailer retailer) throws IllegalArgumentException;
	
	/**
	 * Einen Retailer anlegen
	 * @param name Name des Einzelhaendlers
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
	 * Loeschen des uebergebenen User-Objekts
	 * @param user User-Objekt, welches in der Datenbank geloescht werden soll
	 * @throws IllegalArgumentException
	 */
	public void delete(User user) throws IllegalArgumentException;
	
	/**
	 * Loeschen des uebergebenen Group-Objekts
	 * @param group Group-Objekt, welches in der Datenbank geloescht werden soll
	 * @throws IllegalArgumentException
	 */
	public void delete(Group group) throws IllegalArgumentException;
	
	/**
	 * Loeschen des uebergebenen Shoppinglist-Objekts
	 * @param shoppinglist Shoppinglist-Objekt, welches in der Datenbank geloescht werden soll
	 * @throws IllegalArgumentException
	 */
	public void delete(Shoppinglist shoppinglist) throws IllegalArgumentException;
	
	/**
	 * Loeschen des uebergebenen Listitem-Objekts
	 * @param listitem Listitem-Objekt, welches in der Datenbank geloescht werden soll
	 * @throws IllegalArgumentException
	 */
	public void delete(Listitem listitem) throws IllegalArgumentException;
	
	/**
	 * Saemtliche Gruppen eines Users-Objekts ausgeben
	 * @param user Nutzer, dessen Gruppen angezeigt werden sollen
	 * @return ArrayList saemtlicher Gruppen eines Users
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Group> getGroupsOf(User user) throws IllegalArgumentException;
	
	/**
	 * Saemtliche Gruppen eines Users mit Hilfe der UserID ausgeben
	 * @param userId ID eines Nutzers, dessen Gruppen angezeigt werden sollen
	 * @return ArrayList saemtlicher Gruppen eines Users
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Group> getGroupsOf(int userId) throws IllegalArgumentException;
	
	/**
	 * Rueckgabe eines bestimmten Group-Objekts
	 * @param id ID der gesuchten Gruppe
	 * @return Das erste Group-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	public Group getGroupById(int id) throws IllegalArgumentException;
	
	/**
	 * Saemliche Mitglieder einer Gruppe ausgeben mit Hilfe der Uebergabe eines Gruppen-Objekts
	 * @param group Gruppe, deren Mitglieder ausgegeben werden sollen
	 * @return ArrayList saemtlicher Mitglieder einer Gruppe
	 * @throws IllegalArgumentException
	 */
	public ArrayList<User> getUsersOf(Group group) throws IllegalArgumentException;
	
	/**
	 * Saemliche Mitglieder einer Gruppe ausgeben mit Hilfe der uebergabe der ID eines Gruppen-Obejekts
	 * @param groupId GruppenID von einer Gruppe, deren Mitglieder ausgegeben werden sollen
	 * @return ArrayList saemtlicher Mitglieder einer Gruppe
	 * @throws IllegalArgumentException
	 */
	public ArrayList<User> getUsersOf(int groupId) throws IllegalArgumentException;
	
	/**
	 * Rueckgabe eines User-Objekts mit einer bestimmten ID
	 * @param userId ID des gesuchten User-Objekts
	 * @return Das erste User-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	public User getUserById(int userId) throws IllegalArgumentException;
	
	/**
	 * Saemtliche User-Objekte mit einem bestimmten Namen werden ausgegeben
	 * @param name Username
	 * @return ArrayList saemtlicher User-Objekte, welche einen bestimmten Namen besitzen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<User> getUsersByName(String name) throws IllegalArgumentException;
	
	/**
	 * User-Objekt mit einer bestimmten E-Mail-Adresse wird ausgegeben
	 * @param mail ist die EMail des gesuchten Users
	 * @return User, welcher uebergebene EMail-Adresse besitzt
	 * @throws IllegalArgumentException
	 */
	public User getUserByMail(String mail) throws IllegalArgumentException;
	
	/**
	 * Saemtliche Shoppinglist-Objekte aus einer Gruppe werden ausgegeben
	 * @param group Gruppe, deren Shoppinglist-Objekte ausgegeben werden sollen
	 * @return Das erste User-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Shoppinglist> getShoppinglistsOf(Group group) throws IllegalArgumentException;
	
	/**
	 * Saemtliche Shoppinglist-Objekt mit einem bestimmten Namen werden ausgegeben
	 * @param name ist die Bezeichnung der gesuchten Shoppinglists
	 * @return ArrayList mit Shoppinglist-Objekten, welche einen bestimmten Namen besitzen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Shoppinglist> getShoppinglistsByName(String name) throws IllegalArgumentException;
	
	/**
	 * Das Shoppinglist-Objekt mit der uebergebenen ID wird ausgegeben
	 * @param shoppinglistId ist die ID der gesuchten Shoppinglist
	 * @return Das erste Shoppinglist-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	public Shoppinglist getShoppinglistById(int shoppinglistId) throws IllegalArgumentException;
	
	/**
	 * Saemtliche Listitem-Objekte mit einer bestimmten Produktbezeichnung in einer bestimmen Einkaufsliste werden zurueckgegeben
	 * @param shoppinglist ist die Einkaufsliste, in welcher nach einer bestimmten Produktbezeichnung gesucht werden soll
	 * @param productname ist die Produktbezeichung nach welcher gesucht werden soll
	 * @return ArrayList mit Listitem-Objekten, welche eine bestimmte Prosuktbezeichung enthalten
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Listitem> getListitemsByNameOf(Shoppinglist shoppinglist, String productname) throws IllegalArgumentException;
	
	/**
	 * Saemtliche Listitem-Objekte auch einer bestimmten Shoppinglist werden ausgegeben
	 * @param shoppinglist ist die Einkaufsliste, aus welcher alle Listitem-Objekte ausgegeben werden sollen
	 * @return ArrayList mit allen Listitem-Objekten aus einer bestimmten Einkaufsliste
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Listitem> getListitemsOf(Shoppinglist shoppinglist) throws IllegalArgumentException;
	
	/**
	 * Saemtliche Retailer-Objetke werden ausgegeben
	 * @return ArrayList mit allen Retailer-Objekten
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Retailer> getAllRetailers() throws IllegalArgumentException;
	
	/**
	 * Saemtliche Retailer-Objekte mit einem bestimmten Namen werden ausgegeben
	 * @param name ist die Bezeichnung der gesuchten Retailer-Objekte
	 * @return ArrayList mit allen Ratailer-Objekten, welche einen bestimmten Namen besitzen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Retailer> getRetailersByName(String name) throws IllegalArgumentException;
	
	/**
	 * Ein Retailer-Objekt mit einer bestimmten ID wird ausgegeben
	 * @param retailerId ist die ID des gesuchten Einzelhï¿½ndlers
	 * @return Das erste Retailer-Objekt, welches den Suchkriterien entspricht wird ausgegeben
	 * @throws IllegalArgumentException
	 */
	public Retailer getRetailerById(int retailerId) throws IllegalArgumentException; 
	
	/**
	 * Saemtliche Retailer-Objekte in einer Shoppinglist werden ausgegeben
	 * @param shoppinglist ist die Einkaufsliste, in welcher nach allen Retailer-Objekten gesucht wird
	 * @return ArrayList mit allen Retailer-Objekten innerhalb einer bestimmten Shoppinglist
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Retailer> getRetailersOf(Shoppinglist shoppinglist) throws IllegalArgumentException;
	
	/**
	 * Saemtliche Retailer-Objekte einer Shoppinglist, welche einem bestimmten User zugeordnet sind werden ausgegeben
	 * @param shoppinglist shoppinglist ist die Einkaufsliste, in welcher nach allen Retailer-Objekten gesucht wird
	 * @param user ist der Nutzer, nach wessen zugewiesenen Einzelhaendlern gesucht wird
	 * @return ArrayList mit allen Retailer-Objekten innerhalb einer bestimmten Shoppinglist, welche einem besimmten Nutzer zugeordnet sind
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Retailer> getRetailersOf(Shoppinglist shoppinglist, User user) throws IllegalArgumentException;
	
	/**
	 * Ein Retailer-Objekt wird einem Listitem als Beschaffungsort zugewiesen
	 * @param retailer ist der Einzelhaendler, welcher als Beschaffungsort eines Eintrags gilt
	 * @param listitem ist der Eintrag, welchem der Retailer zugeordnet wird
	 * @throws IllegalArgumentException
	 */
	public void assignRetailer(Retailer retailer, Listitem listitem) throws IllegalArgumentException;
	
	/**
	 * Ein Nutzer wird einem Eintrag als Verantwortlicher zugeordnet
	 * @param user ist der Nutzer, welcher einem Eintrag als Verantwortlicher zugeordnet wird
	 * @param listitem ist er Eintrag, welcher einen Nutzer als Verantwortlichen erhaelt
	 * @throws IllegalArgumentException
	 */
	public void assignUser (User user, Retailer retailer, Shoppinglist shoppinglist) throws IllegalArgumentException;
	
	/**
	 * Einem Eintrag wird Produkt zugeordnet
	 * @param product ist das Produkt, welches einem Eintrag zugeordnet wird
	 * @param listitem ist der Eintrag, welchem ein Produkt zugeordnet wird
	 * @throws IllegalArgumentException
	 */
	public void setProduct (Product product, Listitem listitem) throws IllegalArgumentException;
	
	/**
	 * Setzen eines Standard-Eintrags innerhalb einer Gruppe
	 * @param listitem ist der Eintrag, welcher als Standard gesetzt wird
	 * @param group ist die Gruppe, in welcher der Standardeintrag gesetzt wird
	 * @throws IllegalArgumentException
	 */
	public void setStandardListitem(Listitem listitem, Group group) throws IllegalArgumentException; 
	
	/**
	 * Ausgeben des Product-Objekts aus einem Listitem-Objekt
	 * @param listitem ist der Eintrag, aus welchem das Produkt ausgegeben werden soll
	 * @return Product-Objekt, welches in bestimmtem Listitem enthalten ist
	 * @throws IllegalArgumentException
	 */
	public Product getProductOf(Listitem listitem) throws IllegalArgumentException;
	
	/**
	 * Ausgeben von allen Standard-Listitems aus einer Gruppe
	 * @param group ist die Gruppe, aus welcher die StandardListitems ausgegeben werden sollen
	 * @return ArrayList mit Listitem-Objekte, welche innerhalb einer Gruppe als StandardListitems markiert wurden
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Listitem> getStandardListitemsOf(Group group) throws IllegalArgumentException;
	
	/**
	 * Filtern einer Einkaufsliste nach Verantwortungsbereich eines Nutzers
	 * @param shoppinglist ist die Einkaufsliste, in welcher nach Verantwortungsbereich eines bestimmten Nutzers gefiltert werden soll
	 * @param user ist der Nutzer, nach wessen Verantwortungsbereich gefiltert werden soll
	 * @return ArrayList mit Listitem-Objekten, welche im Verantwortungbereichc eines Nutzers liegen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Listitem> filterShoppinglistsByUsername(Shoppinglist shoppinglist, User user) throws IllegalArgumentException;
	
	/**
	 * Filtern einer Einkaufsliste nach Listitem-Objekten, welche einem bestimmten Einzelhaendler zugeordnet sind
	 * @param shoppinglist ist die Einkaufslsite, in welcher nach Einzelhaendler gefiltert werden soll
	 * @param retailer ist der Einzelhaendler, nach welchem die Einkaufsliste gefiltert werden soll
	 * @return ArrayList mit Listitem-Objekten, welche einem bestimmten Einzelhaendler zugeordnet sind
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Listitem> filterShoppinglistsByRetailer(Shoppinglist shoppinglist, Retailer retailer) throws IllegalArgumentException;
	
	/**
	 * Ein User-Objekt einer Gruppe hinzufuegen
	 * @param user ist ein Nutzer, welcher einer Gruppe hinzugefuegt wird
	 * @param group ist eine Gruppe, welcher ein User-Objekt hinzugfuegt wird
	 * @throws IllegalArgumentException
	 */
	public void addUserToGroup(User user, Group group) throws IllegalArgumentException;
	
	/**
	 * Ein User-Objekt soll von einer Gruppe entfernt werden
	 * @param user ist ein Nutzer, welcher von einer Gruppe entfernt wird
	 * @param group ist eine Gruppe, von welcher ein User-Objekt entfernt werden soll
	 * @throws IllegalArgumentException
	 */
	public void removeUserFromGroup(User user, Group group) throws IllegalArgumentException;
	
	/**
	 * Ausgeben der Mengeneinheit eines Eintrags
	 * @param listitem ist der Eintrag, dessen Mengeneinheit zurueckgegeben wird
	 * @return listitemUnit
	 * @throws IllegalArgumentException
	 */
	public ListitemUnit getListitemUnitOf(Listitem listitem) throws IllegalArgumentException;
	
	/**
	 * Ausgeben der Menge eines Eintrags
	 * @param listitem ist der Eintrag, dessen Menge ausgegeben wird
	 * @return float
	 * @throws IllegalArgumentException
	 */
	public float getAmountOf(Listitem listitem) throws IllegalArgumentException;

	/**
	 * Zum erstellen eines Produkts, welches einem Eintrag zugeordnet wird.
	 * @param name Bezeichung des Produkts
	 * @return Product-Objekt, mit einem bestimmten Name, welches einem Listitemn-Objekt zugeordnet ist
	 * @throws IllegalArgumentException
	 */
	public Product createProduct(String name) throws IllegalArgumentException;


	/**
	 * Ein Listitem anlegen ohne Retailer
	 * @param shoppinglist Einkaufsliste, in welcher ein Eintrag erstellt werden soll
	 * @param productname Bezeichneung des zu beschaffenden Artikels
	 * @param amount Mengenangabe des Artikels bezogen auf die Mengeneinheit
	 * @param listitemUnit Mengeneinheit 
	 * @param retailer Einzelhaendler, bei welchem der Artikel zu beschaffen ist. Hier kann auch die Moeglichkeit "Noch nicht bekannt" ausgewaehlt werden.
	 * @return fertiges Listitem-Objekt
	 * @throws IllegalArgumentException
	 */
	public Listitem createListitem(Shoppinglist shoppinglist, String productname, float amount, ListitemUnit listitemUnit) throws IllegalArgumentException;

	
	/**
	 * Methode, welche den Namen des zugeordneten Produktes zurueckgibt.
	 * @param listitem Eintrag von welchem der Produktname aufgerufen werden soll.
	 * @return String Name des Produktes
	 * @throws IllegalArgumentException
	 */
	public String getProductnameOf(Listitem listitem) throws IllegalArgumentException;
	
	/**
	 * Ausgabe aller vorhandenen Mengeneinheiten.
	 * @return ArrayList mit allen vorhandenen ListitemUnit-Objekten.
	 * @throws IllegalArgumentException
	 */
	public ArrayList<ListitemUnit> getAllListitemUnits() throws IllegalArgumentException;

	/**
	 * Ausgabe einer bestimmten Mengeneinheit anhand der übergebenen ID.
	 * @param id ist die ID der gesuchten Mengeneinheit.
	 * @return ListitemUnit, welches eine bestimmte ID enthaelt
	 * @throws IllegalArgumentException
	 */
	public ListitemUnit getListitemUnitById(int id) throws IllegalArgumentException;

	/**
	 * Gibt einen Boolean Wert zurueck ob sich in den Gruppen des Nutzers etwas veraendert hat
	 * @param groups Gruppen des Nutzers
	 * @param u Objekt des Nutzers 
	 * @return Boolean
	 * @throws IllegalArgumentException
	 */
	public Boolean refreshData(ArrayList<Group> groups, User u) throws IllegalArgumentException;
	
	/**
	 * Suche eines Listite-Objekts anhand eines Suchbegriffs.
	 * @param searchString ist der String, nach welchem gestucht wird.
	 * @param shoppinglist ist die Einkaufsliste, in welcher gesucht wird.
	 * @return Map, in welcher sich die Shoppinglist sowie die darin enthaltenen Listitems befinden.
	 * @throws IllegalArgumentException
	 */
	public Map<Shoppinglist, ArrayList<Listitem>> getListitemMapBy(String searchString, Shoppinglist shoppinglist) throws IllegalArgumentException;
	
	/**
	 * Alle Listitems einer Shoppinglist werden in einer Map mit dem Produktnamen verkn�pft.
	 * @param shoppinglist ist die aktuell selektierte Shoppingliste.
	 * @return Map, welche Listitems mit dem dazugeh�rigen Produktname ausgibt.
	 * @throws IllegalArgumentException
	 */
	public Map<Listitem, String> getListitemsNameMapBy(Shoppinglist shoppinglist) throws IllegalArgumentException;
	
	/**
	 * Ausgabe des zugewiesenen Retailers eines Listitems.
	 * @param listitem ist das Listitem, dessen zugewiesenes Retailer-Objekt zur�ckgegeben werden soll.
	 * @return Retailer-Objekt, welches dem Listitem zugewiesen ist.
	 * @throws IllegalArgumentException
	 */
	public Retailer getRetailerOf(Listitem listitem) throws IllegalArgumentException;
}