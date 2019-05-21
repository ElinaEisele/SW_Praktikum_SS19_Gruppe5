package de.hdm.softwarepraktikum.shared;

/**
 * <p>
 * Die Klasse <code>FieldVerifier</code> überprüft den eigegebenen Namen des Users auf dessen Gültigkeit.
 * </p>
 * <p>
 * Diese Klasse befindet sich im <code>shared</code> -package, da es sowohl auf Client- als auch 
 * auf ServerSeite genutzt wird. Auf der Client-seite wird der Name überprüft, bevor dieser in einem 
 * RPC-Request gesendet wird. Somit muss der Nutzer nicht warten bis der Server antwortet.
 * Auf der Server-Seite wird überprüft, ob der Name gültig ist um Fehlern vorzubeugen.
 * </p>
 * @author TimBeutelspacher
 */

public class FieldVerifier {

	/**
	 * Prüft den übergebenen Namen auf dessen Gültigkeit.
	 * Hierbei sind die Bedinungen, dass der Übergebene String nicht null ist UND 
	 * eine Länge von mindestens 4 Zeichen besitzt.
	 * 
	 * @param name ist der zuüberprüfende Name
	 * @return true oder false, je nach dem ob der String den Bedingungen entspricht.
	 */
	public static boolean isValidName(String name) {
		if (name == null) {
			return false;
		}
		return name.length() > 3;
	}
}
