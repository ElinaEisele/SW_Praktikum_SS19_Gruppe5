package de.hdm.softwarepraktikum.server;

import java.util.logging.Logger;

/**
 * <p>
 * Klasse mit Eigenschaften und Diensten, die f�r alle serverseitigen Klassen
 * relevant sind.
 * </p>
 * <p>
 * In ihrem aktuellen Entwicklungsstand bietet die Klasse eine rudimentaere
 * Unterstuetzung der Logging-Funkionalitaet unter Java. Es wird ein
 * applikationszentraler Logger realisiert, der mittels
 * <code>ServerSideSettings.getLogger()</code> genutzt werden kann.
 * </p>
 * 
 * @author CarlaHofmann
 * 
 */

public class ServersideSettings {
	
	private static final String LOGGER_NAME = "SharedShoppinglist Server";
	private static final Logger log = Logger.getLogger(LOGGER_NAME);
	
	/**
     * Auslesen des applikationsweiten zentralen Loggers.
     * @return serverseitige Logger-Instanz 
     */
	
	public static Logger getLogger() {
	    return log;
	}

}
