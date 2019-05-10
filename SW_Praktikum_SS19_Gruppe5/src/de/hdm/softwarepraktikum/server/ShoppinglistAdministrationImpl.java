package de.hdm.softwarepraktikum.server;

import java.sql.Date;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.softwarepraktikum.server.db.GroupMapper;
import de.hdm.softwarepraktikum.server.db.ListitemMapper;
import de.hdm.softwarepraktikum.server.db.ProductMapper;
import de.hdm.softwarepraktikum.server.db.RetailerMapper;
import de.hdm.softwarepraktikum.server.db.ShoppinglistMapper;
import de.hdm.softwarepraktikum.server.db.UserMapper;
import de.hdm.softwarepraktikum.shared.FieldVerifier;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministration;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Product;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.Unit;
import de.hdm.softwarepraktikum.shared.bo.User;
import de.hdm.softwarepraktikum.shared.dummydata.GroupDD;
import de.hdm.softwarepraktikum.shared.dummydata.ShoppinglistDD;

/**
 * Die Klasse <code>ShoppinglistAdministrationImpl</code> implementiert das Interface
 * ShoppinglistAdministation. In der Klasse ist neben der ReportGeneratorImpl sämtliche
 * Applikationslogik vorhanden.
 * 
 * @author CarlaHofmann & TimBeutelspacher
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
		return this.groupMapper.getGroupsOf(user);;
	}
	
	/**
	 * Saemtliche Gruppen eines Users mit Hilfe der UserID ausgeben
	 * @param userId ID eines Nutzers, dessen Gruppen angezeigt werden sollen
	 * @return ArrayList saemtlicher Gruppen eines Users
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Group> getGroupsOf(int userId) throws IllegalArgumentException {
		return this.groupMapper.getGroupsOf(userId);
	}
	
	/**
	 * Saemtliche Gruppen eines Users mit Hilfe des Usernames ausgeben
	 * @param username eines Nutzers Nutzer, dessen Gruppen angezeigt werden sollen
	 * @return ArrayList saemtlicher Gruppen eines Users
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<Group> getGroupsOf(String username) throws IllegalArgumentException {
		return this.groupMapper.getGroupsOf(username);
	}
	
	/**
	 * Rueckgabe eines bestimmten Group-Objekts
	 * @param id ID der gesuchten Gruppe
	 * @return Das erste Group-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	@Override
	public Group getGroupById(int id) throws IllegalArgumentException {
		return this.groupMapper.getGroupsOf(id);
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
	 * @param unit Mengeneinheit 
	 * @param retailer Einzelhaendler, bei welchem der Artikel zu beschaffen ist. Hier kann auch die Moeglichkeit "Noch nicht bekannt" ausgewaehlt werden.
	 * @return fertiges Listitem-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Listitem createListitem(Shoppinglist shoppinglist, String productname, float amount, Unit unit) throws IllegalArgumentException {
		
		Listitem li = new Listitem(amount, unit);
		// Fremdschluessel zum Retailer wird auf default-Wert 0 gesetzt.
		li.setRetailerID(0);
		
		/**
		 * Nach dem createProduct()-Aufruf erhält das Produkt die ID welche mit der Datenbank konsistent ist.
		 * Somit kann die Fremdschluesselbeziehung vom Listitem zum Product gesetzt werden.
		 */
		Product p = this.createProductFor(li, productname);
		li.setProductID(p.getId());
		
		/*
		 * Problem: Product hat ein Attribut "listitemId", welches jedoch erst gesetzt werden kann nach dem 
		 * Aufruf der insert(Listiitem)-Methode.
		 * Lösung: In der Insert-Methode des Listitem-Objekts muss die Fremdschluesselbeziehung vom enthaltenen Produkt mit der
		 * korrekten und konsistenten ID des Listitems überschrieben werden.
		 */

		//In der Insert-Methode erhält das Listitem-Objekt die finale ID, welche mit der Datenbank konsistent ist.
		return this.listitemMapper.insert(li);
	}
	
	/**
	 * Ein Listitem anlegen mit Retailer
	 * @param shoppinglist Einkaufsliste, in welcher ein Eintrag erstellt werden soll
	 * @param productname Bezeichneung des zu beschaffenden Artikels
	 * @param amount Mengenangabe des Artikels bezogen auf die Mengeneinheit
	 * @param unit Mengeneinheit 
	 * @param retailer Einzelhaendler, bei welchem der Artikel zu beschaffen ist. Hier kann auch die Moeglichkeit "Noch nicht bekannt" ausgewaehlt werden.
	 * @return fertiges Listitem-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Listitem createListitem(Shoppinglist shoppinglist, String productname, float amount, Unit unit,
			Retailer retailer) throws IllegalArgumentException {
		
		//Listitem mit den übergebenen Parametern wird erstellt.
		Listitem li = new Listitem(amount, unit, retailer);
		//Fremdschluessel zum Retailer-Objekt wird gesetzt.
		li.setRetailerID(retailer.getId());
		
		//Enthaltenes Product-Objekt wird erstellt und erhält ID, welche mit der Datenbank konsistent ist.
		Product p = this.createProductFor(li, productname);
		//Fremdschluessel vom Listitem zum Product wird gesetzt.
		li.setProductID(p.getId());
		
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
	public ArrayList<Listitem> getAllListitemsOf(Shoppinglist shoppinglist) throws IllegalArgumentException {
		return this.listitemMapper.getListitemsOf(shoppinglist);
	}
	

	@Override
	public void setStandardListitem(Listitem listitem, Group group) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
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


	@Override
	public ArrayList<Listitem> filterShoppinglistsByUsername(Shoppinglist shoppinglist, User user)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Listitem> filterShoppinglistsByRetailer(Shoppinglist shoppinglist, Retailer retailer)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@Override
//	public void setAmount(float amount, Listitem listitem) throws IllegalArgumentException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setUnit(Unit unit, Listitem listitem) throws IllegalArgumentException {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public Unit getUnit(Listitem listitem) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getAmount(Listitem listitem) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}

	
/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: Methoden für Product-Objekte
 * 
 * **********************************************************************************
 **/
	
	@Override
	public Product createProductFor(Listitem listitem, String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setProduct(Product product, Listitem listitem) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void getProductOf(Listitem listitem) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setProductName(String name, Product product) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	
/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: Methoden für Retailer-Objekte
 * 
 * **********************************************************************************
 **/
	
	@Override
	public Retailer createRetailer(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void save(Retailer retailer) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public ArrayList<Retailer> getAllRetailers() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Retailer> getRetailersByName(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Retailer getRetailerById(int retailerId) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Retailer> getRetailersOf(Shoppinglist shoppinglist) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Retailer> getRetailersOf(Shoppinglist shoppinglist, User user) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assignRetailer(Retailer retailer, Listitem listitem) throws IllegalArgumentException {
		// TODO Auto-generated method stub	
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
		return this.groupMapper.getAllShoppinglists();
	}

	/**
	 * Saemtliche Shoppinglist-Objekt mit einem bestimmten Namen werden ausgegeben
	 * @param name ist die Bezeichnung der gesuchten Shoppinglists
	 * @return ArrayList mit Shoppinglist-Objekten, welche einen bestimmten Namen besitzen
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<Shoppinglist> getShoppinglistsByName(String name) throws IllegalArgumentException {
		return this.groupMapper.getShoppinglistsByName();
	}

	/**
	 * Das Shoppinglist-Objekt mit der uebergebenen ID wird ausgegeben
	 * @param shoppinglistId ist die ID der gesuchten Shoppinglist
	 * @return Das erste Shoppinglist-Objekt, welches den Suchkriterien entspricht
	 * @throws IllegalArgumentException
	 */
	@Override
	public Shoppinglist getShoppinglistById(int shoppinglistId) throws IllegalArgumentException {
		return this.groupMapper.getShoppinglistById();
	}

	
	
/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: Methoden für User-Objekte
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
			//Die Einträge, welche dem User zugeteilt wurden müssen hier noch gelöscht werden.
			//Die Zuweisung von Händlern zu Usern wurde jedoch noch nicht realisiert.
			
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
		return this.getUsersOf(group.getId());
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
		return this.groupMapper.getUsersOf(groupId);
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
//		Group g = this.groupMapper.findById(group.getId());
//		g.getUsers();
		
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
 * ABSCHNITT, Beginn: vorgefertigte Methoden
 * 
 * **********************************************************************************
 **/
	
	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client .
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>"
				+ userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}


	

}
