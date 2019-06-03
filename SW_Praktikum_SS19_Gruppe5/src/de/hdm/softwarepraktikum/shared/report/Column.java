package de.hdm.softwarepraktikum.shared.report;

import java.io.Serializable;

/**
 * Diese Klasse stellt die Unterteilung eines <code>Row</code> -Objektes dar.
 * <code>Column</code> implemtentiert das Interface <code>Serializable</code>,
 * da Objekte der Klasse vom Server zum Client gesendet werden sollen.
 * 
 * @author TimBeutelspacher
 */

public class Column implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * Dieser String-Wert stellt den Wert innerhalb einer Spalte dar. Ingesamt gesehen
	 * repräsentiert dieser Wert den Inhalt einer Zelle des Reports.
	 */
	private String value = "";

	/**
	 * Da diese Klasse mittels GWT-RPC transportiert wird, muss die Klasse einen
	 * No-Argument-Konstruktor besitzen. 
	 * Es gibt keine expliziten Konstruktoren, da diese dafuer sorgen wuerden, dass
	 * der No-Argument-Konstruktor nicht beachtet wird. 
	 */
	public Column() {
		
	}
	
	/**
	 * Konstruktor zum erstellen eines Coulmn-Objekts mit dem setzen eines Wertes
	 * @param value ist der Wert welcher in der Zelle stehen soll
	 */
	public Column(String value) {
		this.setValue(value);
	}
	
	/**
	 * Ausgeben des Wertes einer Spalte.
	 * @return String-Objekt mit dem Inhalt der Spalte.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Setzen eines Spaltenwerts.
	 * @param value ist der zu setzende Wert
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Umwandeln des <code>Column</code> -Objekts in einen String.
	 * @return String mit dem Wert der Spalte.
	 */
	@Override
	public String toString() {
		return value;
		
	}
	
}
