package de.hdm.softwarepraktikum.shared;

/**
 * <p>
 * Die Klasse <code>FieldVerifier</code> �berpr�ft den eigegebenen Namen des Users auf dessen G�ltigkeit.
 * </p>
 * <p>
 * Diese Klasse befindet sich im <code>shared</code> -package, da es sowohl auf Client- als auch 
 * auf ServerSeite genutzt wird. Auf der Client-seite wird der Name �berpr�ft, bevor dieser in einem 
 * RPC-Request gesendet wird. Somit muss der Nutzer nicht warten bis der Server antwortet.
 * Auf der Server-Seite wird �berpr�ft, ob der Name g�ltig ist um Fehlern vorzubeugen.
 * </p>
 * @author TimBeutelspacher
 */

public class FieldVerifier {

	/**
	 * Pr�ft den �bergebenen Namen auf dessen G�ltigkeit.
	 * Hierbei sind die Bedinungen, dass der �bergebene String nicht null ist UND 
	 * eine L�nge von mindestens 4 Zeichen besitzt.
	 * 
	 * @param name ist der zu�berpr�fende Name
	 * @return true oder false, je nach dem ob der String den Bedingungen entspricht.
	 */
	public static boolean isValidName(String name) {
		if (name == null) {
			return false;
		}
		return name.length() > 3;
	}
}
