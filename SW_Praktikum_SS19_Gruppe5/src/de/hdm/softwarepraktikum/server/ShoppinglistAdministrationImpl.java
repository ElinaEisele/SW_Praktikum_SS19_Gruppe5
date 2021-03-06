package de.hdm.softwarepraktikum.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.softwarepraktikum.client.gui.ListitemForm;
import de.hdm.softwarepraktikum.server.db.*;
import de.hdm.softwarepraktikum.shared.*;
import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Die Klasse <code>ShoppinglistAdministrationImpl</code> implementiert das Interface
 * ShoppinglistAdministation. In der Klasse ist neben der ReportGeneratorImpl saemtliche
 * Applikationslogik vorhanden.
 * 
 * @author TimBeutelspacher, FelixRapp, CarlaHofmann
 * 
 */


public class ShoppinglistAdministrationImpl extends RemoteServiceServlet implements ShoppinglistAdministration {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		 * Um mit der Datenbank kommunizieren zu koennen benfoetigt die Klasse
		 * ShoppinglistAdministrationImpl einen vollstaendigen Satz von Mappern.
		 */

		this.groupMapper = GroupMapper.groupMapper();
		this.listitemMapper = ListitemMapper.listitemMapper();
		this.productMapper = ProductMapper.productMapper();
		this.retailerMapper = RetailerMapper.retailerMapper();
		this.shoppinglistMapper = ShoppinglistMapper.shoppinglistMapper();
		this.userMapper = UserMapper.userMapper();
		this.listitemUnitMapper = ListitemUnitMapper.listitemUnitMapper();
	}
	

/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: Methoden fuer Group-Objekte
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
		//Durch den insert-Aufruf wird die ID gesetzt, welche mit der Datenbank konsistent ist.
		Group g = this.groupMapper.insert(group);
		//Nachdem die korrekte ID vorhanden ist, wird das Membership gesetzt.
		this.groupMapper.insertMembership(user.getId(), g.getId());
		
		return g;
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
		// ArrayList mit allen Shoppinglists innerhalb der Gruppe.
		ArrayList<Shoppinglist> shoppinglists = this.getShoppinglistsOf(group);
		
		//Bevor eine Gruppe geloescht wird, werden alle Einkauslisten der Gruppe
		//geloescht.
		if (shoppinglists != null) {
			for (Shoppinglist s : shoppinglists) {
				this.delete(s);
			}
		}
		//Alle Memberships im Zusammenhang mit dieser Gruppe werden geloescht.
		this.groupMapper.deleteMemberships(group.getId());
		//Als letztes wird die Gruppe an sich geloescht.
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
	 * Ausgabe der zugewiesenen Gruppe einer Shoppinglist.
	 * @param shoppinglist ist die Shoppinglist, deren zugewiesene Gruppe gesucht wird.
	 * @return Group-Objekt, welches der Shoppinglist zugewiesen ist.
	 * @throws IllegalArgumentException
	 */
	@Override
	public Group getGroupOf(Shoppinglist shoppinglist) throws IllegalArgumentException {
		return this.getGroupById(shoppinglist.getGroupId());
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
	 * Aendern des Namens einer Gruppe.
	 * @param group ist das Group-Objekt, dessen Name geaendert werden soll.
	 * @param name ist der neue Name der Gruppe.
	 * @return neues Group-Objekt mit neuem Name
	 * @throws IllegalArgumentException
	 */
	@Override
	public Group changeNameOf(Group group, String name) throws IllegalArgumentException {
		group.setName(name);
		this.save(group);
		return group;
	}
	
/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: Methoden fuer Listitem-Objekte
 * 
 * **********************************************************************************
 **/
	
	/**
	 * Ein Listitem anlegen ohne Retailer
	 * @param shoppinglist Einkaufsliste, in welcher ein Eintrag erstellt werden soll
	 * @param productname Bezeichneung des zu beschaffenden Artikels
	 * @param amount Mengenangabe des Artikels bezogen auf die Mengeneinheit
	 * @param listitemUnit Mengeneinheit 
	 * @return fertiges Listitem-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Listitem createListitem(Group group, Shoppinglist shoppinglist, String productname, float amount, ListitemUnit listitemUnit) throws IllegalArgumentException {
		
		Listitem li = new Listitem(amount, listitemUnit);
		// Fremdschluessel zum Retailer wird auf default-Wert 1 gesetzt.
		li.setRetailerID(1);
		
		// Fremdschluessel zur Shoppinglist wird gesetzt.
		li.setShoppinglistID(shoppinglist.getId());
		
		// Fremdschluessel zur Gruppe wird gesetzt.
		li.setGroupID(group.getId());
		
		// Fremdschluessel zur ListitemUnit wird gesetzt
		li.setListitemUnitID(listitemUnit.getId());
		
		/**
		 * Nach dem createProduct()-Aufruf erhaelt das Produkt die ID welche mit der Datenbank konsistent ist.
		 * Somit kann die Fremdschluesselbeziehung vom Listitem zum Product gesetzt werden.
		 */
		Product p = this.createProduct(productname);
		
		li.setProductID(p.getId());;

		//In der Insert-Methode erhaelt das Listitem-Objekt die finale ID, welche mit der Datenbank konsistent ist.
		Listitem koLi = this.listitemMapper.insert(li);
		
		//Das neue Listitem wird als letztes geaendertes Listitem gekennzeichnet.
		this.setLatestEdit(shoppinglist, koLi);
		
		return koLi;
	}
	
	/**
	 * Ein Listitem anlegen ohne Retailer mit standard-Attribut
	 * @param shoppinglist Einkaufsliste, in welcher ein Eintrag erstellt werden soll
	 * @param productname Bezeichneung des zu beschaffenden Artikels
	 * @param amount Mengenangabe des Artikels bezogen auf die Mengeneinheit
 	 * @param standard ist der boolean-Wert, welcher festlegt, ob ein Listitem ein Standard-Listitem ist.
	 * @param listitemUnit Mengeneinheit 
	 * @return fertiges Listitem-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Listitem createListitem(Group group, Shoppinglist shoppinglist, String productname, float amount, ListitemUnit listitemUnit, boolean standard) throws IllegalArgumentException {
		
		Listitem li = new Listitem(amount, listitemUnit);
		// Fremdschluessel zum Retailer wird auf default-Wert 1 gesetzt.
		li.setRetailerID(1);
		
		// Fremdschluessel zur Shoppinglist wird gesetzt.
		li.setShoppinglistID(shoppinglist.getId());
		
		// Fremdschluessel zur Gruppe wird gesetzt.
		li.setGroupID(group.getId());
		
		// Fremdschluessel zur ListitemUnit wird gesetzt
		li.setListitemUnitID(listitemUnit.getId());
		
		//Boolean-Wert fuer Standard-Attribut wird gesetzt.
		li.setStandard(standard);
		
		/**
		 * Nach dem createProduct()-Aufruf erhaelt das Produkt die ID welche mit der Datenbank konsistent ist.
		 * Somit kann die Fremdschluesselbeziehung vom Listitem zum Product gesetzt werden.
		 */
		Product p = this.createProduct(productname);
		
		li.setProductID(p.getId());;

		//In der Insert-Methode erhaelt das Listitem-Objekt die finale ID, welche mit der Datenbank konsistent ist.
		Listitem koLi = this.listitemMapper.insert(li);
		
		//Das neue Listitem wird als letztes geaendertes Listitem gekennzeichnet.
		this.setLatestEdit(shoppinglist, koLi);
		
		return koLi;
	}
	
	
	/**
	 * Ein Listitem anlegen mit Retailer
	 * @param shoppinglist Einkaufsliste, in welcher ein Eintrag erstellt werden soll
	 * @param productname Bezeichneung des zu beschaffenden Artikels
	 * @param amount Mengenangabe des Artikels bezogen auf die Mengeneinheit
	 * @param listitemUnit Mengeneinheit 
	 * @param retailer Einzelhaendler, bei welchem der Artikel zu beschaffen ist.
	 * @return fertiges Listitem-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Listitem createListitem(Group group, Shoppinglist shoppinglist, String productname, float amount, ListitemUnit listitemUnit,
			Retailer retailer) throws IllegalArgumentException {
		
		//Listitem mit den uebergebenen Parametern wird erstellt.
		Listitem li = new Listitem(amount, listitemUnit);
		
		// Fremdschluessel zur Shoppinglist wird gesetzt.
		li.setShoppinglistID(shoppinglist.getId());
				
		// Fremdschluessel zur Gruppe wird gesetzt.
		li.setGroupID(group.getId());
		
		// Fremdschluessel zur ListitemUnit wird gesetzt
		li.setListitemUnitID(listitemUnit.getId());	
		
		//Fremdschluessel zum Retailer-Objekt wird gesetzt.
		this.assignRetailer(retailer, li);
		
		//Enthaltenes Product-Objekt wird erstellt und erhaelt ID, welche mit der Datenbank konsistent ist.
		Product p = this.createProduct(productname);
		
		//Fremdschluessel vom Listitem zum Product wird gesetzt.
		li.setProductID(p.getId());
		
		//In der Insert-Methode erhaelt das Listitem-Objekt die finale ID, welche mit der Datenbank konsistent ist.
		Listitem koLi = this.listitemMapper.insert(li);
		
		//Das neue Listitem wird als letztes geaendertes Listitem gekennzeichnet.
		this.setLatestEdit(shoppinglist, koLi);
		
		return koLi;
	}
	
	/**
	 * Ein Listitem anlegen mit Retailer mit Standard-Attribut
	 * @param shoppinglist Einkaufsliste, in welcher ein Eintrag erstellt werden soll
	 * @param productname Bezeichneung des zu beschaffenden Artikels
	 * @param amount Mengenangabe des Artikels bezogen auf die Mengeneinheit
	 * @param listitemUnit Mengeneinheit 
	 * @param retailer Einzelhaendler, bei welchem der Artikel zu beschaffen ist.
	 * @param standard ist der boolean-Wert, welcher festlegt, ob ein Listitem ein Standard-Listitem ist.
	 * @return fertiges Listitem-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Listitem createListitem(Group group, Shoppinglist shoppinglist, String productname, float amount, ListitemUnit listitemUnit,
			Retailer retailer, boolean standard) throws IllegalArgumentException {
		
		//Listitem mit den uebergebenen Parametern wird erstellt.
		Listitem li = new Listitem(amount, listitemUnit);
		
		// Fremdschluessel zur Shoppinglist wird gesetzt.
		li.setShoppinglistID(shoppinglist.getId());
				
		// Fremdschluessel zur Gruppe wird gesetzt.
		li.setGroupID(group.getId());
		
		// Fremdschluessel zur ListitemUnit wird gesetzt
		li.setListitemUnitID(listitemUnit.getId());	
		
		//Fremdschluessel zum Retailer-Objekt wird gesetzt.
		this.assignRetailer(retailer, li);
		
		//Boolean-Wert fuer Standard-Attribut wird gesetzt.
		li.setStandard(standard);
		
		//Enthaltenes Product-Objekt wird erstellt und erhaelt ID, welche mit der Datenbank konsistent ist.
		Product p = this.createProduct(productname);
		
		//Fremdschluessel vom Listitem zum Product wird gesetzt.
		li.setProductID(p.getId());
		
		//In der Insert-Methode erhaelt das Listitem-Objekt die finale ID, welche mit der Datenbank konsistent ist.
		Listitem koLi = this.listitemMapper.insert(li);
		
		//Das neue Listitem wird als letztes geaendertes Listitem gekennzeichnet.
		this.setLatestEdit(shoppinglist, koLi);
		
		return koLi;
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
	 * Speichern eines Standard-Listitems in der Datenbank, wobei dieses StandardListitem in allen Shoppinglists der Gruppe angepasst wird.
	 * @param oldListitem ist das Listitem vor der Bearbeitung
	 * @param newListitem ist das Listitem nach der Bearbeitung
	 * @throws IllegalArgumentException
	 */
	public void saveStandardListitem(Listitem oldListitem, Listitem newListitem, String oldProductname) throws IllegalArgumentException{
		this.save(newListitem);
		
				
		//Zwischenspeichern aller Standard-Listitems der Gruppe.
		ArrayList<Listitem> standardListitems = new ArrayList<Listitem>();
		standardListitems = this.getStandardListitemsOf(this.getGroupById(newListitem.getGroupID()));
		
		if(!standardListitems.isEmpty()) {
			for(Listitem l : standardListitems) {
				
//				System.out.println();
//				System.out.println("ueberpruefen:");
//				
//				System.out.println("Menge:   l: " +l.getAmount() +"| oldListitem: " +oldListitem.getAmount());
//				if(l.getAmount() == oldListitem.getAmount()) {
//					System.out.println(true);
//				}
//				else {
//					System.out.println(false);
//				}
//				
//				System.out.println("RetailerID:   l: " +l.getRetailerID() +"| oldListitem: " +oldListitem.getRetailerID());
//				if(l.getRetailerID() == oldListitem.getRetailerID()) {
//					System.out.println(true);
//				}
//				else {
//					System.out.println(false);
//				}
//				
//				System.out.println("ListitemUnitID:   l: " +l.getListitemUnitID() +"| oldListitem: " +oldListitem.getListitemUnitID());
//				if(l.getListitemUnitID() == oldListitem.getListitemUnitID()) {
//					System.out.println(true);
//				}
//				else {
//					System.out.println(false);
//				}
//				
//				System.out.println("Bezeichnung:   l: " +this.getProductnameOf(l) +"| oldListitem: " +oldProductname);
//				if(this.getProductnameOf(l).equals(oldProductname)) {
//					System.out.println(true);
//				}
//				else {
//					System.out.println(false);
//				}
//				
//				System.out.println();
				
				//Pruefung ob ein Listitem die selben Werte wie das eigentliche Listitem hat.
				if(l.getAmount() == oldListitem.getAmount() && l.getRetailerID() == oldListitem.getRetailerID() 
						&& l.getListitemUnitID() == oldListitem.getListitemUnitID() 
						&& this.getProductnameOf(l).equals(oldProductname) && !l.isArchived()
						) {
					
					Product p = this.getProductOf(l);
					p.setName(this.getProductnameOf(newListitem));
					this.save(p);
					
					l.setAmount(newListitem.getAmount());
					l.setListitemUnitID(newListitem.getListitemUnitID());
					l.setRetailerID(newListitem.getRetailerID());
					l.setProductID(newListitem.getProductID());
					this.save(l);
					
					
				}
			}
		}
	}
	
	/**
	 * Loeschen des uebergebenen Listitem-Objekts
	 * @param listitem Listitem-Objekt, welches in der Datenbank geloescht werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void delete(Listitem listitem) throws IllegalArgumentException {
			
		//Bevor das Product-Objekt geloescht wird kann das Listitem-Objekt geloescht werden.
		this.listitemMapper.delete(listitem);
		
		//Das dazugeh�rige Product-Objekt wird gel�scht.
		this.delete(this.getProductOf(listitem));
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
	 * Saemtliche nicht archivierte Listitem-Objekte auch einer bestimmten Shoppinglist werden ausgegeben.
	 * @param shoppinglist ist die Einkaufsliste, aus welcher alle nicht archivierten Listitem-Objekte ausgegeben werden sollen.
	 * @return ArrayList mit allen nicht archivierten Listitem-Objekten aus einer bestimmten Einkaufsliste.
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<Listitem> getListitemsOf(Shoppinglist shoppinglist) throws IllegalArgumentException {
		ArrayList<Listitem> list = new ArrayList<Listitem>();
		if(shoppinglist == null) {
			return list;
		}
		
		//Alle Listitems werden zwischengespeichert.
		ArrayList<Listitem> allListitems = this.listitemMapper.findAll();
		
		//In dieser ArrayList werden nur die nicht archivierten Listitems der bestimmten Shoppinglist zwischengespeichert.
		ArrayList<Listitem> slListitems = new ArrayList<Listitem>();
		for(Listitem l : allListitems) {
			//Fremdschluessenbeziehung ueberpruefen.
			if(l.getShoppinglistID() == shoppinglist.getId() && l.isArchived() == false) {
				slListitems.add(l);
			}
		}
		return slListitems;
	}
	
	/**
	 * Saemtliche Listitem-Objekte auch einer bestimmten Shoppinglist werden ausgegeben.
	 * @param shoppinglist ist die Einkaufsliste, aus welcher alle Listitem-Objekte ausgegeben werden sollen.
	 * @return ArrayList mit allen Listitem-Objekten aus einer bestimmten Einkaufsliste.
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<Listitem> getAllListitemsOf(Shoppinglist shoppinglist) throws IllegalArgumentException {
		ArrayList<Listitem> list = new ArrayList<Listitem>();
		if(shoppinglist == null) {
			return list;
		}
		
		//Alle Listitems werden zwischengespeichert.
		ArrayList<Listitem> allListitems = this.listitemMapper.findAll();
		
		//In dieser ArrayList werden nur die Listitems der bestimmten Shoppinglist zwischengespeichert.
		ArrayList<Listitem> slListitems = new ArrayList<Listitem>();
		for(Listitem l : allListitems) {
			//Fremdschluessenbeziehung ueberpruefen.
			if(l.getShoppinglistID() == shoppinglist.getId()) {
				slListitems.add(l);
			}
		}
		return slListitems;
	}
	
	/**
	 * Setzen bzw. entfernen eines Standard-Eintrags innerhalb einer Gruppe
	 * @param listitem ist der Eintrag, welcher als Standard gesetzt wird
	 * @param group ist die Gruppe, in welcher der Standardeintrag gesetzt wird
	 * @throws IllegalArgumentException
	 */
	@Override
	public void setStandardListitem(Listitem listitem, Group group, boolean value) throws IllegalArgumentException {
		
		
		//der zustand muss nur aktualisiert werden, wenn der Wert ein anderer als der vorherige ist.
		if(listitem.isStandard() != value) {
			
			//Zwischenspeichern aller Shoppinglists einer Gruppe
			ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();
			shoppinglists = this.getShoppinglistsOf(group);
			
			//Falls eine Gruppe keine Shoppinglists hat, muss nichts geaendert werden
			if(!shoppinglists.isEmpty()) {
				
				for(Shoppinglist s : shoppinglists) {
					
					//Zwischenspeichern aller Listitems der aktuellen Shoppinglist.
					ArrayList<Listitem> listitems = new ArrayList<Listitem>();
					listitems = this.getListitemsOf(s);
					
					//Falls in einer Shoppingliste keine Listitems vorhanden sind, muss nichts geändert werden
					if(!listitems.isEmpty()) {
						
						//Jedes Listitem soll geprueft werden
						for(Listitem l : listitems) {
							
							//Pruefung ob ein Listitem die selben Werte wie das eigentliche Listitem hat.
							if(this.getAmountOf(l) == this.getAmountOf(listitem) && this.getRetailerOf(l).getId() == this.getRetailerOf(listitem).getId() 
									&& l.getListitemUnitID() == listitem.getListitemUnitID() && this.getProductnameOf(l).equals(this.getProductnameOf(listitem))) {
								l.setStandard(value);
								this.listitemMapper.update(l);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Ausgeben von allen Standard-Listitems aus einer Gruppe
	 * @param group ist die Gruppe, aus welcher die StandardListitems ausgegeben werden sollen
	 * @return ArrayList mit Listitem-Objekte, welche innerhalb einer Gruppe als StandardListitems markiert wurden
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<Listitem> getStandardListitemsOf(Group group) throws IllegalArgumentException {
		return this.listitemMapper.getStandardListitemsOf(group);
	}

	/**
	 * Filtern einer Einkaufsliste nach Verantwortungsbereich eines Nutzers.
	 * @param shoppinglist ist die Einkaufsliste, in welcher nach Verantwortungsbereich eines bestimmten Nutzers gefiltert werden soll.
	 * @param user ist der Nutzer, nach wessen Verantwortungsbereich gefiltert werden soll.
	 * @return ArrayList mit Listitem-Objekten, welche im Verantwortungbereichc eines Nutzers liegen.
	 * @throws IllegalArgumentException
	 */
	@Override
	public Map<Listitem, ArrayList<String>> filterShoppinglistsByUser(Shoppinglist shoppinglist, User user)
			throws IllegalArgumentException {
		return this.listitemMapper.filterShoppinglistByUser(shoppinglist.getId(), user.getId());
	}

	/**
	 * Filtern einer Einkaufsliste nach Listitem-Objekten, welche einem bestimmten Einzelhaendler zugeordnet sind.
	 * @param shoppinglist ist die Einkaufslsite, in welcher nach Einzelhaendler gefiltert werden soll.
	 * @param retailer ist der Einzelhaendler, nach welchem die Einkaufsliste gefiltert werden soll.
	 * @return ArrayList mit Listitem-Objekten, welche einem bestimmten Einzelhaendler zugeordnet sind.
	 * @throws IllegalArgumentException
	 */
	@Override
	public Map<Listitem, ArrayList<String>> filterShoppinglistsByRetailer(Shoppinglist shoppinglist, Retailer retailer)
			throws IllegalArgumentException {
		return this.listitemMapper.filterShoppinglistByRetailer(shoppinglist.getId(), retailer.getId());
	}
	
	/**
	 * Ausgeben der Mengeneinheit eines Eintrags.
	 * @param listitem ist der Eintrag, dessen Mengeneinheit zurueckgegeben wird.
	 * @return ListitemUnit eines bestimmten Listitems.
	 * @throws IllegalArgumentException
	 */
	@Override
	public ListitemUnit getListitemUnitOf(Listitem listitem) throws IllegalArgumentException {
		return this.listitemUnitMapper.getUnitOf(listitem);
	}

	/**
	 * Ausgeben der Menge eines Eintrags.
	 * @param listitem ist der Eintrag, dessen Menge ausgegeben wird
	 * @return float ist die Menge, welche von dem Produkt besorgt werden soll.
	 * @throws IllegalArgumentException
	 */
	@Override
	public float getAmountOf(Listitem listitem) throws IllegalArgumentException {
		return this.listitemMapper.findById(listitem.getId()).getAmount();
	}

	/**
	 * Methode, welche den Namen des zugeordneten Produktes zurueckgibt.
	 * @param listitem Eintrag von welchem der Produktname aufgerufen werden soll.
	 * @return String Name des Produktes.
	 * @throws IllegalArgumentException
	 */
	@Override
	public String getProductnameOf(Listitem listitem) throws IllegalArgumentException {
		return this.getProductOf(listitem).getName();
	}
	
	/**
	 * Archivieren einer Menge von Listitem-Objekten.
	 * @param listitems sind die Listitems, welche archiviert werden sollen.
	 * @throws IllegalArgumentException
	 */
	@Override
	public void archiveListitems(ArrayList<Listitem> listitems) throws IllegalArgumentException {
		
		for(Listitem l : listitems) {
			l.setArchived(true);
			this.save(l);
		}
	}

	/**
	 * Saemtliche archivierte Listitem-Objekte einer bestimmten Shoppinglist werden ausgegeben.
	  @param shoppinglist ist die Einkaufsliste, aus welcher alle Listitem-Objekte ausgegeben werden sollen.
	  @return ArrayList mit allen Listitem-Objekten aus einer bestimmten Einkaufsliste.
	  @throws IllegalArgumentException
	 */
	 
	@Override
	public ArrayList<Listitem> getArchivedListitemsOf(Shoppinglist shoppinglist) throws IllegalArgumentException {
		ArrayList<Listitem> list = new ArrayList<Listitem>();
		if(shoppinglist == null) {
			return list;
		}
		
		//Alle Listitems werden zwischengespeichert.
		ArrayList<Listitem> shoppinglistListitems = this.getListitemsOf(shoppinglist);
		
		//In dieser ArrayList werden nur die Listitems der bestimmten Shoppinglist zwischengespeichert, welceh archiviert wurden.
		ArrayList<Listitem> archivedListitems = new ArrayList<Listitem>();
		for(Listitem l : shoppinglistListitems) {
			//Fremdschluessenbeziehung ueberpruefen.
			if(l.isArchived()) {
				archivedListitems.add(l);
			}
		}
		
		return archivedListitems;
	}
	
	@Override
	public ArrayList<Listitem> getArchivedListitemsOf(Group group) throws IllegalArgumentException {
		ArrayList<Listitem> list = new ArrayList<Listitem>();
		if(group == null) {
			return list;
		}
		
		//Ausgeben aller Einkauslisten der Gruppe
		ArrayList<Shoppinglist> shoppinglists = this.shoppinglistMapper.getShoppinglistsOf(group);
		
		//Liste mit allen Eintraegen der Gruppe
		ArrayList<Listitem> listitems = new ArrayList<Listitem>();
		
		//Erstellen einer Liste mit allen Eintraegen aus allen Listen
		if(!shoppinglists.isEmpty()) {
			for (Shoppinglist s: shoppinglists)	{
    			listitems.addAll(this.listitemMapper.getArchivedListitemsOf(s));
    		}    			
		}	
		return listitems;
	}
	
	/**
	 * Ausgabe aller Listitem Eigenschaften als String zur Bef�llung des <code>ShoppinglistCellTable</code>.
	 * @param Shoppinglist object
	 * @return Map mit Listitem-Objekten sowie den zugeh�rigen Daten als String
	 * @throws IllegalArgumentException
	 */
	public Map<Listitem, ArrayList<String>> getListitemData(Shoppinglist shoppinglist) throws IllegalArgumentException{
		return this.listitemMapper.getListitemData(shoppinglist);
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
	 * Ausgeben des Product-Objekts aus einem Listitem-Objekt
	 * @param listitem ist der Eintrag, aus welchem das Produkt ausgegeben werden soll
	 * @return Product-Objekt, welches in bestimmtem Listitem enthalten ist
	 * @throws IllegalArgumentException
	 */
	@Override
	public Product getProductOf(Listitem listitem) throws IllegalArgumentException {
		return this.productMapper.findById(listitem.getProductID());
	}
	
	/**
	 * Speichern eines Product-Objekts in der Datenbank
	 * @param product ist das Product-Objekt, welches in der Datenbank gespeichert werden soll.
	 * @throws IllegalArgumentException
	 */
	@Override
	public void save(Product product) throws IllegalArgumentException {
		this.productMapper.update(product);
	}
	
	/**
	 * Loeschen des uebergebenen Product-Objekts
	 * @param product Product-Objekt, welches in der Datenbank geloescht werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void delete(Product product) throws IllegalArgumentException {
		
		//Loeschen des Product-Objekts.
		this.productMapper.delete(product);
	}	
	
/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: Methoden fuer Retailer-Objekte
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
	 * @param retailerId ist die ID des gesuchten Einzelhaendlers
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
	 * Ausgabe des zugewiesenen Retailers eines Listitems.
	 * @param listitem ist das Listitem, dessen zugewiesenes Retailer-Objekt zurückgegeben werden soll.
	 * @return Retailer-Objekt, welches dem Listitem zugewiesen ist.
	 * @throws IllegalArgumentException
	 */
	@Override
	public Retailer getRetailerOf(Listitem listitem) throws IllegalArgumentException {
		return this.retailerMapper.findById(listitem.getRetailerID());
	}
	
	/**
	 * Ausgabe aller schon zugewiesenen Retailer.
	 */
	public ArrayList<Retailer> getAssignedRetailersOf(Shoppinglist shoppinglist) throws IllegalArgumentException{
		return this.retailerMapper.getAssignedRetailersOf(shoppinglist);
	}
	
	/**
	 * Ausgabe aller Händler- und Nutzernamen Zuweisungen in einer <code>Shoppinglist</code>
	 */
	public Map<String, String> getUserRetailerAllocation(Shoppinglist shoppinglist) throws IllegalArgumentException{
		return this.retailerMapper.getAllocationsOf(shoppinglist);
	}
	
	/**
	 * Ausgabe des zugewiesenen Nutzers.
	 */
	public User getAssigndUserOf(Shoppinglist shoppinglist, Retailer retailer) {
		return this.retailerMapper.getAssigndUserOf(shoppinglist, retailer);
	}
	
	public ArrayList<User> getAssigndUserOf(Shoppinglist shoppinglist) {
		return this.retailerMapper.getAssigndUsersOf(shoppinglist);
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
	 * Eine Zuweisung wird gel�scht.
	 * @param retailer ist der Einzelhaendler, welcher als Beschaffungsort eines Eintrags gilt
	 * @param user ist der User, dessen Zuweisung gel�scht werden soll.
	 * @param shoppinglist ist die Shoppinglist, innerhalb 
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteAssignment(Retailer retailer, Shoppinglist shoppinglist) throws IllegalArgumentException{
		retailerMapper.deleteResponsibility(retailer, shoppinglist);
	}
/**
 * **********************************************************************************
 * ABSCHNITT, Beginn: Methoden fuer Shoppinglist-Objekte
 * 
 * **********************************************************************************
 **/
	
	
	/**
	 * Eine Shoppinglist anlegen und alle Standardeintraege hinzufuegen.
	 * @param group Gruppe, welcher eine Shoppinglist hinzugefuegt werden soll
	 * @param name Name der Shoppinglist
	 * @return fertiges Shoppinglist-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Shoppinglist createShoppinglistFor(Group group, String name) throws IllegalArgumentException {
		//Erst wird ein neues Shoppinglist-Objekt erstellt.
		Shoppinglist sl = new Shoppinglist(name);
		
		//Fremdschluessel zur Gruppe setzen
		sl.setGroupId(group.getId());
		
		//Um die korrekte (mit der Datenbank konsistente) Id zu erhalten, muss erst die insert-Methode aufgerufen werden.
		this.shoppinglistMapper.insert(sl);
		
		//Alle Standardeintraege der Gruppe werden zwischengespeichert.
		ArrayList<Listitem> standard = this.listitemMapper.getStandardListitemsOf(group);
		
		
		//Neue Listitem-Objekte mit der Fremdschluesselbeziehung zur neuen Shoppinglist werden erstellt.
		for(Listitem l : standard) {
			
			//Bei jeder Iteration werden alle Listitems der neuen Shoppinglist hinzugefuegt.
			ArrayList<Listitem> alreadyInSl = this.getListitemsOf(sl);
			
			//Nachdem etwas in der Shoppingliste vorhanden ist muss geprueft werden ob bereits ein gleiches Listitem vorhanden ist.
			if(!alreadyInSl.isEmpty()) {
				boolean isThere = true;
				//Abfragen, ob bereits ein Listitem mit den selben Werten in der neuen Shoppinglist existiert
				for(Listitem li : alreadyInSl) {
					if(this.getAmountOf(l) == this.getAmountOf(li) && this.getRetailerOf(l).getId() == this.getRetailerOf(li).getId() 
							&& l.getListitemUnitID() == li.getListitemUnitID() && this.getProductnameOf(l).equals(this.getProductnameOf(li))) {
						isThere = false;
					}
				}
				// Falls noch kein solches Listitem existiert wird eins erstellt.
				if(isThere) {
					this.createListitem(group, sl, this.getProductnameOf(l), this.getAmountOf(l), this.getListitemUnitOf(l), this.getRetailerOf(l), true);
				}
			}
			// Das erste Listitem wird direkt gesetzt.
			else {
				this.createListitem(group, sl, this.getProductnameOf(l), this.getAmountOf(l), this.getListitemUnitOf(l), this.getRetailerOf(l), true);
			}
		}
		return sl;
	}
	
	/**
	 * Speichern eines Shoppinglist-Objekts in der Datenbank
	 * @param shoppinglist, Shoppinglist-Objekt, welches in der Datenbank gespeichert weden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void save(Shoppinglist shoppinglist) throws IllegalArgumentException {
		this.shoppinglistMapper.update(shoppinglist);
		
	}
	
	/**
	 * Loeschen des uebergebenen Shoppinglist-Objekts
	 * @param shoppinglist Shoppinglist-Objekt, welches in der Datenbank geloescht werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void delete(Shoppinglist shoppinglist) throws IllegalArgumentException {
		ArrayList<Listitem> listitems = this.getListitemsOf(shoppinglist);
		
		// Beim Loeschen einer Shoppinglist, muessen auch alle enthaltenen Listitems geloescht werden
		if(listitems != null) {
			for(Listitem l : listitems) {
				this.delete(l);
			}
		}
		//Alle Zustaendigkeiten fuer die Einkaufsliste werden geloescht.
		this.shoppinglistMapper.deleteResponsibilities(shoppinglist.getId());
		// Sobald alle enthaltenen Listitems geloescht wurden, kann die Shoppinglist geloescht werden.
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
	 * Setzen der Fremdschluesselbeziehung zu dem zuletzt geaenderten Lisitem-Objekt einer Shoppingliste.
	 * @param shoppinglist ist die Einkaufsliste, in welcher das zuletzt geaenderte Listitem gesetzt werden soll. 
	 * @param listitem ist das zuletzt geaenderte Listitem.
	 * @throws IllegalArgumentException
	 */
	@Override
	public void setLatestEdit(Shoppinglist shoppinglist, Listitem listitem) throws IllegalArgumentException {
		shoppinglist.setLastestEdit(listitem.getId());
		this.save(shoppinglist);
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
		
		// Alle Responsibilities in Verbindung mit dem User loeschen
		this.userMapper.deleteResponsibilities(user.getId());
		// User aus der Gruppe loeschen
		this.userMapper.deleteMemberships(user.getId());
		// Als letztes wird der User an sich geloescht
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
		return this.userMapper.getUsersOf(group);
	}
	
	/**
	 * Saemliche Mitglieder einer Gruppe ausgeben mit Hilfe der Uebergabe eines Gruppen-Objekts
	 * @param groupId Gruppen Id, deren Mitglieder ausgegeben werden sollen
	 * @return ArrayList saemtlicher Mitglieder einer Gruppe
	 * @throws IllegalArgumentException
	 */
	@Override
	public ArrayList<User> getUsersOf(int groupId) throws IllegalArgumentException {
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
	 * @param mail ist die E-Mail des gesuchten Users
	 * @return User, welcher uebergebene EMail-Adresse besitzt
	 * @throws IllegalArgumentException
	 */
	@Override
	public User getUserByMail(String mail) throws IllegalArgumentException {
		return this.userMapper.findByGMail(mail);
	}
	
	public ArrayList<User> getAllUsers() throws IllegalArgumentException {
		return this.userMapper.findAll();
	}
	
	/**
	 * Ein Nutzer wird einem Einzelhaendler als Verantwortlicher zugeordnet.
	 * @param user ist der Nutzer, welcher einem Eintrag als Verantwortlicher zugeordnet wird
	 * @param listitem ist er Eintrag, welcher einen Nutzer als Verantwortlichen erhaelt
	 * @throws IllegalArgumentException
	 */
	@Override
	public void assignUser(User user, Retailer retailer, Shoppinglist shoppinglist) throws IllegalArgumentException {
		this.retailerMapper.insertResponsibility(retailer.getId(), user.getId(), shoppinglist.getId());
	}
	
	
	
	
	
	/**
	 * Ein User-Objekt einer Gruppe hinzufuegen
	 * @param user ist ein Nutzer, welcher einer Gruppe hinzugefuegt wird
	 * @param group ist eine Gruppe, welcher ein User-Objekt hinzugfuegt wird
	 * @throws IllegalArgumentException
	 */
	@Override
	public void addUserToGroup(User user, Group group) throws IllegalArgumentException {
		this.groupMapper.insertMembership(user.getId(), group.getId());
	}
	
	/**
	 * Ein User-Objekt soll von einer Gruppe entfernt werden
	 * @param user ist ein Nutzer, welcher von einer Gruppe entfernt wird
	 * @param group ist eine Gruppe, von welcher ein User-Objekt entfernt werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void removeUserFromGroup(User user, Group group) throws IllegalArgumentException {
		this.groupMapper.deleteMembership(user.getId(), group.getId());
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
	 * Ausgabe einer bestimmten Mengeneinheit anhand der Ã¼bergebenen ID.
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
	 * Suche eines Listitem-Objekts anhand eines Suchbegriffs.
	 * @param searchString ist der String, nach welchem gestucht wird.
	 * @param shoppinglist ist die Einkaufsliste, in welcher gesucht wird.
	 * @return Map, in welcher sich die Shoppinglist sowie die darin enthaltenen Listitems befinden.
	 * @throws IllegalArgumentException
	 */
	@Override
	public Map<Shoppinglist, ArrayList<Listitem>> getListitemMapBy(String searchString, Shoppinglist shoppinglist)
			throws IllegalArgumentException {
		
		if(searchString != null && shoppinglist != null) {
			
			// Alle Listitems der übergebenen Shoppinglist werden abgerufen.
			ArrayList<Listitem> listitems = this.getListitemsOf(shoppinglist);
			
			HashMap<Shoppinglist, ArrayList<Listitem>> result = new HashMap<Shoppinglist, ArrayList<Listitem>>();
			
			for(Listitem l : listitems) {
				
				//Zwischenspeichern der Listitems, welche den Suchkriterien entsprechen
				ArrayList<Listitem> resultListitems = new ArrayList<Listitem>();
				
				// Prüfen, ob der Name des aktuellen Listitems dem SearchString entspricht.
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

	/**
	 * Alle Listitems einer Shoppinglist werden in einer Map mit dem Produktnamen verknüpft.
	 * @param shoppinglist ist die aktuell selektierte Shoppingliste.
	 * @return Map, welche Listitems mit dem dazugehörigen Produktname ausgibt.
	 * @throws IllegalArgumentException
	 */
	@Override
	public Map<Listitem, String> getListitemsNameMapBy(Shoppinglist shoppinglist) throws IllegalArgumentException {
		
		if(shoppinglist != null) {
			HashMap<Listitem, String> listitemNameMap = new LinkedHashMap<Listitem, String>();
			
			// Alle Listitems aus der uebergebenen Shoppinglist werden zwischengespeichert.
			ArrayList<Listitem> listitems = this.getListitemsOf(shoppinglist);
			
			for(Listitem l : listitems) {
				
				listitemNameMap.put(l, this.getProductnameOf(l));
			}
			return listitemNameMap;
		}
		return null;
	}
	
	public Map<Listitem, ArrayList<String>> getListitemsNameMapBy(Shoppinglist shoppinglist, String productName) throws IllegalArgumentException {
		if (shoppinglist != null){
			HashMap<Listitem, ArrayList<String>> listitemNameMap = new LinkedHashMap<Listitem, ArrayList<String>>();
			ArrayList<Listitem> listitems = this.getListitemsByNameOf(shoppinglist, productName);
			for (Listitem l : listitems) {
				ArrayList<String> data = this.listitemMapper.getListitemDataOf(shoppinglist, l);
				listitemNameMap.put(l, data);
			}
			return listitemNameMap;
		}
		return null;
	}


	


}
