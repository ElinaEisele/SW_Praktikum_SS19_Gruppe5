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
 * ABSCHNITT, Beginn: Methoden für BO-Objekte
 * 
 * **********************************************************************************
 **/
	
//	public void setBOid(int boId) throws IllegalArgumentException {
//		
//	}
//	
//	public int getBOid() throws IllegalArgumentException{
//		int id = this.getBOid();
//		return id;
//	}
//	
//	public void setCreationDate(Date date) throws IllegalArgumentException{
//		
//	}
//	
//	public Date getCreationDate() throws IllegalArgumentException{
//		Date creationDate = this.getCreationDate();
//		return creationDate;
//	}
	
/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: Methoden für Named BO-Objekte
 * 
 * **********************************************************************************
 **/
	
//	public void setName(String name) throws IllegalArgumentException{
//		
//	}
//	
//	public String getName() throws IllegalArgumentException {
//		String name = this.getName();
//		
//		return name;
//	}
	
/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: Methoden für Group-Objekte
 * 
 * **********************************************************************************
 **/
	
//	public Group getGroup() throws IllegalArgumentException {
//		Group group = this.getGroup();
//		return group;
//	}
	
//	public ArrayList<Group> getGroups() throws IllegalArgumentException {
//		ArrayList<Group> groups = new ArrayList<Group>();
//		
//		return groups;
//	}
//	
//	
//	public void leaveGroup(Group group) throws IllegalArgumentException {
//		
//	}
//	
//	public void addMember(User user) throws IllegalArgumentException {
//		
//	}
//	
//	public ArrayList<User> getMembers() throws IllegalArgumentException {
//		ArrayList<User> members = new ArrayList<User>();
//		return members;
//	}
//	
//	public ArrayList<Shoppinglist> getShoppinglists() throws IllegalArgumentException {
//		ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();
//		return shoppinglists;
//	}
	
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
		group.getUsers().add(this);
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
	 * @return ArrayList sï¿½mtlicher Gruppen eines Users
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
	
	@Override
	public Listitem createListitem(Shoppinglist shoppinglist, String productname, float amount, Unit unit,
			Retailer retailer) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void save(Listitem listitem) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void delete(Listitem listitem) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public ArrayList<Listitem> getListitemsOf(Shoppinglist shoppinglist, String productname)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Listitem> getAllListitemsOf(Shoppinglist shoppinglist) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setListitem(Product product, float amount, Unit unit, Retailer retailer, User user)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListitem(Product product, float amount, Unit unit, Retailer retailer)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
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
	
	@Override
	public void setAmount(float amount, Listitem listitem) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUnit(Unit unit, Listitem listitem) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

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
	public Shoppinglist createShoppinglist(Group group, String name) throws IllegalArgumentException {
		Shoppinglist sl = new Shoppinglist(name);
		sl.setGroupId(group.getId());
		
		//Standardeinträge hinzufuegen
		sl.getListitems().addAll(getStandardListitemsOf(group));
		
		// Objekt in der Datenbank speichern.
		return this.shoppinglistMapper.insert(sl);
	}
	
	@Override
	public void save(Shoppinglist shoppinglist) throws IllegalArgumentException {
		shoppinglistMapper.update(shoppinglist);
		
	}
	
	@Override
	public void delete(Shoppinglist shoppinglist) throws IllegalArgumentException {
		shoppinglistMapper.delete(shoppinglist);
		
	}
	
	@Override
	public ArrayList<Shoppinglist>  getShoppinglistsOf(Group group) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Shoppinglist> getShoppinglistsByName(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shoppinglist getShoppinglistById(int shoppinglistId) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: Methoden für User-Objekte
 * 
 * **********************************************************************************
 **/
	
	@Override
	public User createUser(String mail) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void save(User user) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void delete(User user) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public ArrayList<User> getUsersOf(Group group) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<User> getUsersOf(int groupId) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserById(int userId) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<User> getUsersByName(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByMail(String mail) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void assignUser(User user, Listitem listitem) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addUserToGroup(User user, Group group) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUserFromGroup(User user, Group group) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
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
