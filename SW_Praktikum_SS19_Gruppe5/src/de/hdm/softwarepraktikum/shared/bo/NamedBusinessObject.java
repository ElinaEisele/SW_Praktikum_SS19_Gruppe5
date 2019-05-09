package de.hdm.softwarepraktikum.shared.bo;

/**
 *  <p>
 * Die Klasse <code>NamedBusinessObject</code> stellt die Basisklasse aller in diesem
 * Projekt für die Umsetzung des Fachkonzepts relevanten Klassen, welche die Attribute ID und Name besitzen dar.
 * </p>
 * <p>
 * Zentrales Merkmal ist, dass jedes <code>NamedBusinessObject</code> eine Nummer
 * besitzt, die man in einer relationalen Datenbank auch als Primärschlüssel
 * bezeichnen würde. Diese Klasse erweitert die Klasse <code>BusinessObject</code> um das Attribut name
 * des Datentyps String.
 * </p>
 * @author TimBeutelspacher
 *
 */
public class NamedBusinessObject extends BusinessObject{

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attribut, um welches die Klasse BusinessObject erweitert wird
	 */
	private String name;
	
	/*
	 * Default-Konstruktor, um diesen in Subklassen aufrufen zu können.
	 */
	public NamedBusinessObject() {
		
	}
	
	/**
	 * Konstruktor, welcher den Namen setzt.
	 * @param name
	 */
	public NamedBusinessObject(String name) {
		this.setName(name);
	}
	
	/*
	 * Ausgeben des Namen
	 */
	public String getName() {
		return name;
	}

	/*
	 * Setzen des Namen
	 */
	public void setName(String name) {
		this.name = name;
	}
}
