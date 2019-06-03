package de.hdm.softwarepraktikum.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;

import de.hdm.softwarepraktikum.shared.CommonSettings;
import de.hdm.softwarepraktikum.shared.LoginService;
import de.hdm.softwarepraktikum.shared.LoginServiceAsync;
import de.hdm.softwarepraktikum.shared.ReportGenerator;
import de.hdm.softwarepraktikum.shared.ReportGeneratorAsync;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministration;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;

/**
 * Klasse mit Eigenschaften und Diensten, die fuer alle Client-seitigen
 * Klassen relevant sind.
 * 
 * @author ElinaEisele
 *
 */

public class ClientsideSettings extends CommonSettings{
	
	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitenen
	 * Dienst namens <code>ShoppinglistAdministration</code>.
	 */

	private static ShoppinglistAdministrationAsync shoppinglistAdministration = null;
	private static LoginServiceAsync loginService = null;
	private static ReportGeneratorAsync reportGenerator = null;
	
	/**
	 * Name des Client-seitigen Loggers.
	 */
	private static final String LOGGER_NAME = "SW_Praktikum_SS19_Gruppe5 Web Client";
	
	/**
	 * Instanz des Client-seitigen Loggers.
	 */
	private static final Logger log = Logger.getLogger(LOGGER_NAME);
	
	/**
	 * @return Logger-Instanz fuer die Server-Seite
	 */
	public static Logger getLogger() {
		return log;
	}
	
	/**
	   * <p>
	   * Anlegen und Auslesen der applikationsweit eindeutigen ShoppinglistAdministration. Diese
	   * Methode erstellt die ShoppinglistAdministration, sofern sie noch nicht existiert. Bei
	   * wiederholtem Aufruf dieser Methode wird stets das bereits zuvor angelegte
	   * Objekt zur�ckgegeben.
	   * </p>
	   * 
	   * <p>
	   * Der Aufruf dieser Methode erfolgt im Client z.B. durch
	   * <code>ShoppinglistAdministrationAsync shoppinglistAdministration = ClientSideSettings.getshoppinglistAdministration()</code>
	   * .
	   * </p>
	   * 
	   * @return eindeutige Instanz des Typs <code>ShoppinglistAdministrationAsync</code>
	   * @author ElinaEisele, TimBeutelspacher
	   */
	public static ShoppinglistAdministrationAsync getShoppinglistAdministration() {
		if (shoppinglistAdministration == null) {
			shoppinglistAdministration = GWT.create(ShoppinglistAdministration.class);
		}
		return shoppinglistAdministration;

	}
	
	public static LoginServiceAsync getLoginService() {
    	if(loginService == null){
    		loginService = GWT.create(LoginService.class);
		}
		return loginService;
	}
	
//	public static ReportGeneratorAsync getReportGenerator() {
//		if(reportGenerator == null) {
//			reportGenerator = GWT.create(ReportGenerator.class);
//		}
//		return reportGenerator;
//	}

}
