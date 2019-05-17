package de.hdm.softwarepraktikum.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.softwarepraktikum.server.db.*;
import de.hdm.softwarepraktikum.shared.*;
import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Die Klasse <code>ShoppinglistAdministrationImpl</code> implementiert das Interface
 * ShoppinglistAdministation. In der Klasse ist neben der ReportGeneratorImpl sämtliche
 * Applikationslogik vorhanden.
 * 
 * @author TimBeutelspacher, FelixRapp, CarlaHofmann
 * 
 */

@SuppressWarnings("serial")
public class ShoppinglistAdministrationImpl extends RemoteServiceServlet implements ShoppinglistAdministration {
	
	/**
	 * Referenz auf den UserMapper, welcher User-Objekte mit der Datenbank
	 * abgleicht.
	 */
	private UserMapper userMapper = null;
	
	/**
	 * Referenz auf den ProductMapper, welcher Produkt-Objekte mit der Datenbank
	 * abgleicht.
	 */
	private ProductMapper productMapper = null;
	
	/**
	 * Referenz auf den ListitemMapper, welcher Listitem-Objekte mit der Datenbank
	 * abgleicht.
	 */
	private ListitemMapper listitemMapper = null;
	
	/**
	 * Referenz auf den ShoppinglistMapper, welcher Shoppinglist-Objekte mit der Datenbank
	 * abgleicht.
	 */
	private ShoppinglistMapper shoppinglistMapper = null;
	
	/**
	 * Referenz auf den RetailerMapper, welcher Retailer-Objekte mit der Datenbank
	 * abgleicht.
	 */
	private RetailerMapper retailerMapper = null;
	
	/**
	 * Referenz auf den GroupMapper, welcher Group-Objekte mit der Datenbank
	 * abgleicht.
	 */
	private GroupMapper groupMapper = null;
	
	/**
	 * Referenz auf den ListitemUnitMapper, welcher ListitemUnit-Objekte mit der Datenbank
	 * abgleicht.
	 */
	private ListitemUnitMapper listitemUnitMapper = null;
	
	/**
	 * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
	 * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu ist
	 * ein solcher No-Argument-Konstruktor anzulegen.
	 * 
	 * Es bietet sich also an, eine separate Instanzenmethode zu erstellen, die
	 * Client-seitig direkt nach <code>GWT.create(Klassenname.class)</code>
	 * aufgerufen wird, um eine Initialisierung der Instanz vorzunehmen.
	 * 
	 * @see init()
	 * @throws IllegalArgumentException
	 */
	
	public ShoppinglistAdministrationImpl() throws IllegalArgumentException {
	}

	
	/**
	 * Initialisiert alle Mapper in der Klasse.
	 */
	public void init() throws IllegalArgumentException {

		/**
		 * Um mit der Datenbank kommunizieren zu können benäftigt die Klasse
		 * ContactadministrationImpl einen vollständigen Satz von Mappern.
		 */

		this.groupMapper = GroupMapper.groupMapper();
		this.listitemMapper = ListitemMapper.listitemMapper();
		this.productMapper = ProductMapper.productMapper();
		this.retailerMapper = RetailerMapper.retailerMapper();
		this.shoppinglistMapper = ShoppinglistMapper.shoppinglistMapper();
		this.userMapper = UserMapper.userMapper();
	}
	

/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: Methoden für Group-Objekte
 * 
 * **********************************************************************************
 **/
	
	/**
	 * Alle Gruppen werden ausgegeben.
	 * @return ArrayList mit Group-Objekten
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<Group> getAllGroups() throws IllegalArgumentException {
		return this.groupMapper.findAll();
	}
	
	/**
	 * Eine Gruppe anlegen.
	 * @param name Gruppenname
	 * @return fertiges Group-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Group createGroupFor(User user, String name) throws IllegalArgumentException {
		Group group = new Group(name);
		this.groupMapper.insert(group);
		this.groupMapper.addUserToGroup(user, group);
		return group;
	}
	
	/**
	 * Speichern eines Group-Objekts in der Datenbank
	 * @param group Group-Objekt, welches in der Datenbank gespeichert werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void save(Group group) throws IllegalArgumentException {
		this.groupMapper.update(group);
	}
	
	
	/**
	 * Loeschen des uebergebenen Group-Objekts
	 * @param group Group-Objekt, welches in der Datenbank geloescht werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void delete(Group group) throws IllegalArgumentException {
		ArrayList<Shoppinglist> shoppinglists = this.getShoppinglistsOf(group);
		
		//Bevor eine Gruppe geloescht wird, werden alle Einkauslisten der Gruppe
		//geloescht.
		if (shoppinglists != null) {
			for (Shoppinglist s : shoppinglists) {
				this.delete(s);
			}
		}
		this.groupMapper.delete(group);
	}
	
	/**
	 * Saemtliche Gruppen eines Users-Objekts ausgeben
	 * @param user Nutzer, dessen Gruppen angezeigt werden sollen
	 * @return ArrayList saemtlicher Gruppen eines Users
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Group> getGroupsOf(User user) throws IllegalArgumentException {
		return this.groupMapper.getGroupsOf(user);
	}
	
	/**
	 * Saemtliche Gruppen eines Users mit Hilfe der UserID ausgeben
	 * @param userId ID eines Nutzers, dessen Gruppen angezeigt werden sollen
	 * @return ArrayList saemtlicher Gruppen eines Users
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Group> getGroupsOf(int userId) throws IllegalArgumentException {
		return this.getGroupsOf(this.getUserById(userId));
	}
	
	/**
	 * Rueckgabe eines bestimmten Group-Objekts
	 * @param id ID der gesuchten Gruppe
	 * @return Das erste Group-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	@Override
	public Group getGroupById(int groupId) throws IllegalArgumentException {
		return this.groupMapper.findById(groupId);
	}
	
	
/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: Methoden für Listitem-Objekte
 * 
 * **********************************************************************************
 **/
	
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
	@Override
	public Listitem createListitem(Shoppinglist shoppinglist, String productname, float amount, ListitemUnit listitemUnit) throws IllegalArgumentException {
		
		Listitem li = new Listitem(amount, listitemUnit);
		// Fremdschluessel zum Retailer wird auf default-Wert 0 gesetzt.
		li.setRetailerID(0);
		
		/**
		 * Nach dem createProduct()-Aufruf erhält das Produkt die ID welche mit der Datenbank konsistent ist.
		 * Somit kann die Fremdschluesselbeziehung vom Listitem zum Product gesetzt werden.
		 */
		Product p = this.createProduct(productname);
		
		this.setProduct(p, li);

		//In der Insert-Methode erhält das Listitem-Objekt die finale ID, welche mit der Datenbank konsistent ist.
		return this.listitemMapper.insert(li);
	}
	
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
	@Override
	public Listitem createListitem(Shoppinglist shoppinglist, String productname, float amount, ListitemUnit listitemUnit,
			Retailer retailer) throws IllegalArgumentException {
		
		//Listitem mit den übergebenen Parametern wird erstellt.
		Listitem li = new Listitem(amount, listitemUnit, retailer);
		//Fremdschluessel zum Retailer-Objekt wird gesetzt.
		this.assignRetailer(retailer, li);
		
		//Enthaltenes Product-Objekt wird erstellt und erhält ID, welche mit der Datenbank konsistent ist.
		Product p = this.createProduct(productname);
		
		//Fremdschluessel vom Listitem zum Product wird gesetzt.
		this.setProduct(p, li);
		
		return this.listitemMapper.insert(li);
	}
	
	/**
	 * Speichern eines Listitem-Objekt in der Datenbank
	 * @param listitem, Listitem-Objekt, welches in der Datenbank gepseichert werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void save(Listitem listitem) throws IllegalArgumentException {
		this.listitemMapper.update(listitem);
		
	}
	
	/**
	 * Loeschen des uebergebenen Listitem-Objekts
	 * @param listitem Listitem-Objekt, welches in der Datenbank geloescht werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void delete(Listitem listitem) throws IllegalArgumentException {
		this.listitemMapper.delete(listitem);
		//Beim Löschen eines Listitem-Objekts wird ebenfalls das enthaltene Product-Objekt gelöscht.
		this.productMapper.delete(this.productMapper.findById(listitem.getProductID()));
		
	}	
	
	/**
	 * Saemtliche Listitem-Objekte mit einer bestimmten Produktbezeichnung in einer bestimmen Einkaufsliste werden zurueckgegeben
	 * @param shoppinglist ist die Einkaufsliste, in welcher nach einer bestimmten Produktbezeichnung gesucht werden soll
	 * @param productname ist die Produktbezeichung nach welcher gesucht werden soll
	 * @return ArrayList mit Listitem-Objekten, welche eine bestimmte Prosuktbezeichung enthalten
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<Listitem> getListitemsByNameOf(Shoppinglist shoppinglist, String productname) throws IllegalArgumentException {
		return this.listitemMapper.getListitemsByNameOf(shoppinglist, productname);
	}

	/**
	 * Saemtliche Listitem-Objekte auch einer bestimmten Shoppinglist werden ausgegeben
	 * @param shoppinglist ist die Einkaufsliste, aus welcher alle Listitem-Objekte ausgegeben werden sollen
	 * @return ArrayList mit allen Listitem-Objekten aus einer bestimmten Einkaufsliste
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<Listitem> getListitemsOf(Shoppinglist shoppinglist) throws IllegalArgumentException {
		return this.listitemMapper.getListitemsOf(shoppinglist);
	}
	

	
	@Override
	public void setStandardListitem(Listitem listitem, Group group) throws IllegalArgumentException {
		return this.listitemMapper.setStandardListitemIn(group, listitem);
		
	}
	
	/**
	 * Ausgeben von allen Standard-Listitems aus einer Gruppe
	 * @param group ist die Gruppe, aus welcher die StandardListitems ausgegeben werden sollen
	 * @return ArrayList mit Listitem-Objekte, welche innerhalb einer Gruppe als StandardListitems markiert wurden
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<Listitem> getStandardListitemsOf(Group group) throws IllegalArgumentException {
		return this.groupMapper.getStandardListitemsOf(group);
	}

	/**
	 * Filtern einer Einkaufsliste nach Verantwortungsbereich eines Nutzers
	 * @param shoppinglist ist die Einkaufsliste, in welcher nach Verantwortungsbereich eines bestimmten Nutzers gefiltert werden soll
	 * @param user ist der Nutzer, nach wessen Verantwortungsbereich gefiltert werden soll
	 * @return ArrayList mit Listitem-Objekten, welche im Verantwortungbereichc eines Nutzers liegen
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<Listitem> filterShoppinglistsByUsername(Shoppinglist shoppinglist, User user)
			throws IllegalArgumentException {
		return this.listitemMapper.filterShoppinglistByUsername(shoppinglist, user.getName());
	}

	/**
	 * Filtern einer Einkaufsliste nach Listitem-Objekten, welche einem bestimmten Einzelhaendler zugeordnet sind
	 * @param shoppinglist ist die Einkaufslsite, in welcher nach Einzelhaendler gefiltert werden soll
	 * @param retailer ist der Einzelhaendler, nach welchem die Einkaufsliste gefiltert werden soll
	 * @return ArrayList mit Listitem-Objekten, welche einem bestimmten Einzelhaendler zugeordnet sind
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<Listitem> filterShoppinglistsByRetailer(Shoppinglist shoppinglist, Retailer retailer)
			throws IllegalArgumentException {
		return this.listitemMapper.filterShoppinglistByRetailer(shoppinglist, retailer.getName());
	}
	
	/**
	 * Ausgeben der Mengeneinheit eines Eintrags
	 * @param listitem ist der Eintrag, dessen Mengeneinheit zurueckgegeben wird
	 * @return ListitemUnit 
	 * @throws IllegalArgumentException
	 */
	@Override
	public ListitemUnit getListitemUnitOf(Listitem listitem) throws IllegalArgumentException {
		return this.listitemMapper.getListitemUnitOf(listitem);
	}

	/**
	 * Ausgeben der Menge eines Eintrags
	 * @param listitem ist der Eintrag, dessen Menge ausgegeben wird
	 * @return float
	 * @throws IllegalArgumentException
	 */
	@Override
	public float getAmountOf(Listitem listitem) throws IllegalArgumentException {
		return this.listitemMapper.getAmountOf(listitem);
	}

	/**
	 * Methode, welche den Namen des zugeordneten Produktes zurueckgibt.
	 * @param listitem Eintrag von welchem der Produktname aufgerufen werden soll.
	 * @return String Name des Produktes
	 * @throws IllegalArgumentException
	 */
	@Override
	public String getProductnameOf(Listitem listitem) throws IllegalArgumentException {
		return this.listitemMapper.getProductnameOf(listitem);
	}
	
/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: Methoden fuer Product-Objekte
 * 
 * **********************************************************************************
 **/
	/**
	 * Zum erstellen eines Produkts, welches einem Eintrag zugeordnet wird.
	 * @param name Bezeichung des Produkts
	 * @return Product-Objekt, mit einem bestimmten Name, welches einem Listitemn-Objekt zugeordnet ist
	 * @throws IllegalArgumentException
	 */
	@Override
	public Product createProduct(String name) throws IllegalArgumentException {
		Product product = new Product(name);
		return this.productMapper.insert(product);
	}
	
	/**
	 * Einem Eintrag wird Produkt zugeordnet
	 * @param product ist das Produkt, welches einem Eintrag zugeordnet wird
	 * @param listitem ist der Eintrag, welchem ein Produkt zugeordnet wird
	 * @throws IllegalArgumentException
	 */
	@Override
	public void setProduct(Product product, Listitem listitem) throws IllegalArgumentException {
		listitem.setProductID(product.getId());
		
	}
	
	/**
	 * Ausgeben des Product-Objekts aus einem Listitem-Objekt
	 * @param listitem ist der Eintrag, aus welchem das Produkt ausgegeben werden soll
	 * @return Product-Objekt, welches in bestimmtem Listitem enthalten ist
	 * @throws IllegalArgumentException
	 */
	@Override
	public Product getProductOf(Listitem listitem) throws IllegalArgumentException {
		return this.productMapper.getProductOf(listitem);
		
	}
	
	
	
/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: Methoden für Retailer-Objekte
 * 
 * **********************************************************************************
 **/
	
	/**
	 * Einen Retailer anlegen
	 * @param name Name des Einzelhaendlers
	 * @return fertiges Listitem-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Retailer createRetailer(String name) throws IllegalArgumentException {
		Retailer retailer = new Retailer(name);
		return this.retailerMapper.insert(retailer);
	}
	
	/**
	 * Speichern eines Retailer-Objekts in der Datenbank
	 * @param retailer Retailer-Objekt, welches in der Datenbank gepseichert werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void save(Retailer retailer) throws IllegalArgumentException {
		this.retailerMapper.update(retailer);
		
	}
	
	/**
	 * Saemtliche Retailer-Objetke werden ausgegeben
	 * @return ArrayList mit allen Retailer-Objekten
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<Retailer> getAllRetailers() throws IllegalArgumentException {
		return this.retailerMapper.findAll();
		
	}

	/**
	 * Saemtliche Retailer-Objekte mit einem bestimmten Namen werden ausgegeben
	 * @param name ist die Bezeichnung der gesuchten Retailer-Objekte
	 * @return ArrayList mit allen Ratailer-Objekten, welche einen bestimmten Namen besitzen
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<Retailer> getRetailersByName(String name) throws IllegalArgumentException {
		return this.retailerMapper.findByName(name);
		
	}

	/**
	 * Ein Retailer-Objekt mit einer bestimmten ID wird ausgegeben
	 * @param retailerId ist die ID des gesuchten Einzelhï¿½ndlers
	 * @return Das erste Retailer-Objekt, welches den Suchkriterien entspricht wird ausgegeben
	 * @throws IllegalArgumentException
	 */
	@Override
	public Retailer getRetailerById(int retailerId) throws IllegalArgumentException {
		return this.retailerMapper.findById(retailerId);
	}

	/**
	 * Saemtliche Retailer-Objekte in einer Shoppinglist werden ausgegeben
	 * @param shoppinglist ist die Einkaufsliste, in welcher nach allen Retailer-Objekten gesucht wird
	 * @return ArrayList mit allen Retailer-Objekten innerhalb einer bestimmten Shoppinglist
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<Retailer> getRetailersOf(Shoppinglist shoppinglist) throws IllegalArgumentException {
		return this.retailerMapper.getRetailersOf(shoppinglist);
	}

	/**
	 * Saemtliche Retailer-Objekte einer Shoppinglist, welche einem bestimmten User zugeordnet sind werden ausgegeben
	 * @param shoppinglist shoppinglist ist die Einkaufsliste, in welcher nach allen Retailer-Objekten gesucht wird
	 * @param user ist der Nutzer, nach wessen zugewiesenen Einzelhaendlern gesucht wird
	 * @return ArrayList mit allen Retailer-Objekten innerhalb einer bestimmten Shoppinglist, welche einem besimmten Nutzer zugeordnet sind
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<Retailer> getRetailersOf(Shoppinglist shoppinglist, User user) throws IllegalArgumentException {
		return this.retailerMapper.getAssignedRetailersOf(shoppinglist, user);
	}

	/**
	 * Ein Retailer-Objekt wird einem Listitem als Beschaffungsort zugewiesen
	 * @param retailer ist der Einzelhaendler, welcher als Beschaffungsort eines Eintrags gilt
	 * @param listitem ist der Eintrag, welchem der Retailer zugeordnet wird
	 * @throws IllegalArgumentException
	 */
	@Override
	public void assignRetailer(Retailer retailer, Listitem listitem) throws IllegalArgumentException {
		listitem.setRetailerID(retailer.getId());
		this.save(listitem);
	}
	
/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: Methoden für Shoppinglist-Objekte
 * 
 * **********************************************************************************
 **/
	
	/**
	 * Eine Shoppinglist anlegen
	 * @param group Gruppe, welcher eine Shoppinglist hinzugefuegt werden soll
	 * @param name Name der Shoppinglist
	 * @return fertiges Shoppinglist-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Shoppinglist createShoppinglistFor(Group group, String name) throws IllegalArgumentException {
		Shoppinglist sl = new Shoppinglist(name);
		sl.setGroupId(group.getId());
		
		//Standardeinträge hinzufuegen
		sl.getListitems().addAll(getStandardListitemsOf(group));
		
		// Objekt in der Datenbank speichern.
		return this.shoppinglistMapper.insert(sl);
	}
	
	/**
	 * Speichern eines Shoppinglist-Objekts in der Datenbank
	 * @param shoppinglist, Shoppinglist-Objekt, welches in der Datenbank gespeichert weden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void save(Shoppinglist shoppinglist) throws IllegalArgumentException {
		shoppinglistMapper.update(shoppinglist);
		
	}
	
	/**
	 * Loeschen des uebergebenen Shoppinglist-Objekts
	 * @param shoppinglist Shoppinglist-Objekt, welches in der Datenbank geloescht werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void delete(Shoppinglist shoppinglist) throws IllegalArgumentException {
		ArrayList<Listitem> listitems = this.getAllListitemsOf(shoppinglist);
		
		// Beim Löschen einer Shoppinglist, müssen auch alle enthaltenen Listitems geloescht werden
		if(listitems != null) {
			for(Listitem l : listitems) {
				this.delete(l);
			}
		}
		// Sobald alle enthaltenen Listitems gelöscht wurden, kann die Shoppinglist gelöscht werden
		this.shoppinglistMapper.delete(shoppinglist);
		
	}
	
	/**
	 * Saemtliche Shoppinglist-Objekte aus einer Gruppe werden ausgegeben
	 * @param group Gruppe, deren Shoppinglist-Objekte ausgegeben werden sollen
	 * @return Das erste User-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<Shoppinglist> getShoppinglistsOf(Group group) throws IllegalArgumentException {		
		return this.shoppinglistMapper.getShoppinglistsOf(group);
	}

	/**
	 * Saemtliche Shoppinglist-Objekt mit einem bestimmten Namen werden ausgegeben
	 * @param name ist die Bezeichnung der gesuchten Shoppinglists
	 * @return ArrayList mit Shoppinglist-Objekten, welche einen bestimmten Namen besitzen
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<Shoppinglist> getShoppinglistsByName(String name) throws IllegalArgumentException {
		return this.shoppinglistMapper.findByName(name);
	}

	/**
	 * Das Shoppinglist-Objekt mit der uebergebenen ID wird ausgegeben
	 * @param shoppinglistId ist die ID der gesuchten Shoppinglist
	 * @return Das erste Shoppinglist-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	@Override
	public Shoppinglist getShoppinglistById(int shoppinglistId) throws IllegalArgumentException {
		return this.shoppinglistMapper.findById(shoppinglistId);
	}

	
	
/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: Methoden fuer User-Objekte
 * 
 * **********************************************************************************
 **/
	
	/**
	 * Einen User anlegen
	 * @param mail GoogleMail des Users
	 * @param name Name des Users
	 * @return fertiges User-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public User createUser(String name, String mail) throws IllegalArgumentException {
		User user = new User(name, mail);
		return this.userMapper.insert(user);
	}
	
	/**
	 * Speichern eines User-Objekts in der Datenbank
	 * @param user User-Objekt, welches in der Datenbank gespeichert werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void save(User user) throws IllegalArgumentException {
		this.userMapper.update(user);
	}
	
	/**
	 * Loeschen des uebergebenen User-Objekts
	 * @param user User-Objekt, welches in der Datenbank geloescht werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void delete(User user) throws IllegalArgumentException {
		ArrayList<Group> groups = this.groupMapper.getGroupsOf(user);
		for (int i=0; groups.size()>i; i++) {
			Group g = groups.get(i);
			ArrayList<Shoppinglist> shoppinglists = this.shoppinglistMapper.getShoppinglistsOf(g);
			for (int u=0; shoppinglists.size()>i; i++) {
				ArrayList<Listitem> listitems = this.listitemMapper.getListitemsOf(shoppinglists.get(u));
				
			}
			//Die Eintraege, welche dem User zugeteilt wurden muessen hier noch geloescht werden.
			//Die Zuweisung von Haendlern zu Usern wurde jedoch noch nicht realisiert.
			//Ausserdem koennte hier noch abgefragt werden, ob die Gruppen nach loeschen eines Users
			//noch Mitglieder haben oder nicht. Sollen Gruppen ohne Mitglieder gelöscht werden?
			
		}
		this.userMapper.delete(user);
	}
	
	/**
	 * Saemliche Mitglieder einer Gruppe ausgeben mit Hilfe der Uebergabe eines Gruppen-Objekts
	 * @param group Gruppe, deren Mitglieder ausgegeben werden sollen
	 * @return ArrayList saemtlicher Mitglieder einer Gruppe
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<User> getUsersOf(Group group) throws IllegalArgumentException {
		return this.groupMapper.getUsersOf(group);
	}
	
	/**
	 * Saemliche Mitglieder einer Gruppe ausgeben mit Hilfe der Uebergabe eines Gruppen-Objekts
	 * @param groupId Gruppen Id, deren Mitglieder ausgegeben werden sollen
	 * @return ArrayList saemtlicher Mitglieder einer Gruppe
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<User> getUsersOf(int groupId) throws IllegalArgumentException {
		//Fehler, da Methode in Mapper noch nicht realisiert.
		return this.getUsersOf(this.groupMapper.findById(groupId));
	}

	/**
	 * Rueckgabe eines User-Objekts mit einer bestimmten ID
	 * @param userId ID des gesuchten User-Objekts
	 * @return Das erste User-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	@Override
	public User getUserById(int userId) throws IllegalArgumentException {
		return this.userMapper.findById(userId);
	}
	
	/**
	 * Saemtliche User-Objekte mit einem bestimmten Namen werden ausgegeben
	 * @param name Username
	 * @return ArrayList saemtlicher User-Objekte, welche einen bestimmten Namen besitzen
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<User> getUsersByName(String name) throws IllegalArgumentException {
		return this.userMapper.findByName(name);
	}

	/**
	 * User-Objekt mit einer bestimmten E-Mail-Adresse wird ausgegeben
	 * @param mail ist die EMail des gesuchten Users
	 * @return User, welcher uebergebene EMail-Adresse besitzt
	 * @throws IllegalArgumentException
	 */
	@Override
	public User getUserByMail(String mail) throws IllegalArgumentException {
		return this.userMapper.findByGMail(mail);
	}
	
	/**
	 * Ein Nutzer wird einem Eintrag als Verantwortlicher zugeordnet
	 * @param user ist der Nutzer, welcher einem Eintrag als Verantwortlicher zugeordnet wird
	 * @param listitem ist er Eintrag, welcher einen Nutzer als Verantwortlichen erhaelt
	 * @throws IllegalArgumentException
	 */
	@Override
	public void assignUser(User user, Listitem listitem) throws IllegalArgumentException {
		//Methode nicht implementiert
	}
	
	/**
	 * Ein User-Objekt einer Gruppe hinzufuegen
	 * @param user ist ein Nutzer, welcher einer Gruppe hinzugefuegt wird
	 * @param group ist eine Gruppe, welcher ein User-Objekt hinzugfuegt wird
	 * @throws IllegalArgumentException
	 */
	@Override
	public void addUserToGroup(User user, Group group) throws IllegalArgumentException {
		this.groupMapper.addUserToGroup(user, group);
	}
	
	/**
	 * Ein User-Objekt soll von einer Gruppe entfernt werden
	 * @param user ist ein Nutzer, welcher von einer Gruppe entfernt wird
	 * @param group ist eine Gruppe, von welcher ein User-Objekt entfernt werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void removeUserFromGroup(User user, Group group) throws IllegalArgumentException {
		this.groupMapper.removeUserFromGroup(user.getId(), group.getId());
		
		//Einfacher direkt im Mapper?
		/*
		 * Methode im GroupMapper: removeUserFromGroup(int userId, int goupId)
		 * passendes STATEMENT:
		 * DELETE * FROM Membership WHERE user_id = userId AND group_id = groupId
		 * 
		 * DANN Code in dieser Methode:
		 * return this.groupMapper.removeUserFromGroup(user.getId(), group.getId());
		 */
	}
  

/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: Methoden fuer ListitemUnit-Objekte
 * 
 * **********************************************************************************
 **/	
	
	/**
	 * Ausgabe aller vorhandenen Mengeneinheiten.
	 * @return ArrayList mit allen vorhandenen ListitemUnit-Objekten.
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<ListitemUnit> getAllListitemUnits() throws IllegalArgumentException {
		return this.listitemUnitMapper.findAll();
	}
	
	/**
	 * Ausgabe einer bestimmten Mengeneinheit anhand der übergebenen ID.
	 * @param id ist die ID der gesuchten Mengeneinheit.
	 * @return ListitemUnit, welches eine bestimmte ID enthaelt
	 * @throws IllegalArgumentException
	 */
	@Override
	public ListitemUnit getListitemUnitById(int id) throws IllegalArgumentException {
		return this.listitemUnitMapper.findById(id);
	}	
	

/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: sonstige Methoden
 * 
 * **********************************************************************************
 **/	

	
	/**
	 * Gibt einen Boolean Wert zurueck ob sich in den Gruppen des Nutzers etwas veraendert hat
	 * @param groups Gruppen des Nutzers
	 * @param u Objekt des Nutzers 
	 * @return Boolean
	 * @throws IllegalArgumentException
	 */
	@Override
	public Boolean refreshData(ArrayList<Group> g, User u) throws IllegalArgumentException {

		ArrayList<Group> groups = this.getGroupsOf(u);

		if (groups != null && g != null) {

			if (!g.equals(groups)) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * Suche eines Listite-Objekts anhand eines Suchbegriffs.
	 * @param searchString ist der String, nach welchem gestucht wird.
	 * @param shoppinglist ist die Einkaufsliste, in welcher gesucht wird.
	 * @return Map, in welcher sich die Shoppinglist sowie die darin enthaltenen Listitems befinden.
	 * @throws IllegalArgumentException
	 */
	@Override
	public Map<Shoppinglist, ArrayList<Listitem>> getListitemMapBy(String searchString, Shoppinglist shoppinglist)
			throws IllegalArgumentException {
		
		if(searchString != null && shoppinglist != null) {
			
			// Alle Listitems der �bergebenen Shoppinglist werden abgerufen.
			ArrayList<Listitem> listitems = this.getListitemsOf(shoppinglist);
			
			HashMap<Shoppinglist, ArrayList<Listitem>> result = new HashMap<Shoppinglist, ArrayList<Listitem>>();
			
			for(Listitem l : listitems) {
				
				//Zwischenspeichern der Listitems, welche den Suchkriterien entsprechen
				ArrayList<Listitem> resultListitems = new ArrayList<Listitem>();
				
				// Pr�fen, ob der Name des aktuellen Listitems dem SearchString entspricht.
				if(this.productMapper.findById(l.getProductID()).getName().equals(searchString)) {
					
					//Listitem in der ArrayList zwischenspeichern
					resultListitems.add(l);
					
					//aktualisieren der Map
					result.put(shoppinglist, resultListitems);
				}
			}
			return result;
		}
		return null;
	}
	
	
}
