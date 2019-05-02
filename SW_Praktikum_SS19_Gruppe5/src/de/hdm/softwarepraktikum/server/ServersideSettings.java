package de.hdm.softwarepraktikum.server;

import java.util.logging.Logger;

import de.hdm.softwarepraktikum.shared.CommonSettings;

/**
 * <p>
 * Klasse mit Eigenschaften und Diensten, die f�r alle Server-seitigen Klassen
 * relevant sind.
 * </p>
 * <p>
 * In ihrem aktuellen Entwicklungsstand bietet die Klasse eine rudiment�re
 * Unterst�tzung der Logging-Funkionalit�t unter Java. Es wird ein
 * applikationszentraler Logger realisiert, der mittels
 * <code>ServerSideSettings.getLogger()</code> genutzt werden kann.
 * </p>
 * 
 */

public class ServersideSettings extends CommonSettings {
	
	private static final String LOGGER_NAME = "SharedShoppinglist Server";
	private static final Logger log = Logger.getLogger(LOGGER_NAME);
	
	/**
     * Auslesen des applikationsweiten zentralen Loggers.
     * @return Server-Seitige Logger-Instanz 
     */
	
	public static Logger getLogger() {
	    return log;
	}

}
