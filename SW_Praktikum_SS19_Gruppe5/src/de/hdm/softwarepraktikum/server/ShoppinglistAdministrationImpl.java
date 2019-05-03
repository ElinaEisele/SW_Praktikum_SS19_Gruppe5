package de.hdm.softwarepraktikum.server;

import java.sql.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.softwarepraktikum.server.db.GroupMapper;
import de.hdm.softwarepraktikum.server.db.ListitemMapper;
import de.hdm.softwarepraktikum.server.db.ProductMapper;
import de.hdm.softwarepraktikum.server.db.RetailerMapper;
import de.hdm.softwarepraktikum.server.db.ShoppinglistMapper;
import de.hdm.softwarepraktikum.server.db.UserMapper;
import de.hdm.softwarepraktikum.shared.FieldVerifier;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministration;

/**
 * Die Klasse <code>ShoppinglistAdministrationImpl</code> implementiert das Interface
 * ShoppinglistAdministation. In der Klasse ist neben der ReportGeneratorImpl sämtliche
 * Applikationslogik vorhanden.
 * 
 * @author CarlaHofmann
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
	 * *************************
	 * ABSCHNITT, Beginn: Methoden für BO-Objekte
	 * 
	 * *************************
	 **/
	
	public void setBOid (int boId) {
		
	}
	
	public int getBOid () {
		
	}
	
	public void setCreationDate (Date date) {
		
	}
	
	public Date getCreationDate () {
		
	}
	
	
	
	
	
	
	
	
	
	
	

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
