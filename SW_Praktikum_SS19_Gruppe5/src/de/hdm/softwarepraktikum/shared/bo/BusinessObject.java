package de.hdm.softwarepraktikum.shared.bo;

import java.io.Serializable;
import java.sql.Date;

/**
 * <p>
 * Die Klasse <code>BusinessObject</code> stellt die Basisklasse aller in diesem
 * Projekt f�r die Umsetzung des Fachkonzepts relevanten Klassen dar. Hierbei hat 
 * jede Sub-Klasse eine ID.
 * </p>
 * <p>
 * Zentrales Merkmal ist, dass jedes <code>BusinessObject</code> eine Nummer
 * besitzt, die man in einer relationalen Datenbank auch als Prim�rschl�ssel
 * bezeichnen w�rde. Fernen ist jedes <code>BusinessObject</code> als
 * gekennzeichnet. Durch diese Eigenschaft kann jedes <code>BusinessObject</code> 
 * automatisch in eine textuelle Form �berf�hrt und z.B. zwischen Client und Server
 * transportiert werden.
 * </p>
 * 
 * @author TimBeutelspacher
 */

public abstract class BusinessObject implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Date creationDate;
	
	public BusinessObject() {
		java.util.Date java = new java.util.Date();
		java.sql.Date sql = new java.sql.Date(java.getTime());
	    this.setCreationDate(sql);
		/*
		 * Setzen einer vorl�ufigen ID. Im Insert-Aufruf wird die Id gesetzt, welche mit der Datenbank
		 * konsisent ist.
		 */
		this.setId(1);
//		this.setCreationDate(new Date());
	}
	
	/**
	 * Die eindeutige Identifikationsnummer einer Instanz dieser Klasse.
	 */
	private int id = 0;
	
	/**
	 * Auslesen der ID.
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Setzen der ID
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Erzeugen einer einfachen textuellen Darstellung der jeweiligen
	 * Instanz. Kann in Sub-Klassen �berschrieben werden
	 */ 
	public String toString() {
		/*
		 * Ausgabe des Klassennamens und der ID
		 */
		return this.getClass().getName() +" #" +this.getId();
	}
	
	/**
	 * Gleichheit zweier BusinessObjects �berpr�fen. Hierbei wird sich auf
	 * die ID's bezogen.
	 */
	public boolean equals(Object o) {
	    /*
	     * Abfragen, ob ein Objekt ungleich NULL ist und ob ein Objekt gecastet
	     * werden kann.
	     */
	    if (o != null && o instanceof BusinessObject) {
	      BusinessObject bo = (BusinessObject) o;
	      try {
	        if (bo.getId() == this.id)
	          return true;
	      }
	      catch (IllegalArgumentException e) {
	        /*
	         * Falls etwas schief geht, wird false zur�ckgegeben.
	         */
	        return false;
	      }
	    }
	    /*
	     * Wenn bislang keine Gleichheit bestimmt werden konnte, dann wird
	     * false zur�ckgeben.
	     */
	    return false;
	  }
	
	/**
	   * <p>
	   * Erzeugen einer ganzen Zahl, die f�r das <code>BusinessObject</code> charakteristisch ist.
	   * </p>
	   * <p>
	   * Zusammen mit <code>equals</code> sollte diese Methode immer definiert werden.
	   */
	public int hashCode() {
		return this.id;
	}

	/**
	 * Ausgeben des Erstellungsdatum.
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Setzen des Erstellungsdatums.
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	/**
	 * Ausgeben des Erstellungsdatums als String.
	 */
	public String getCreationDateConvertToString() {
		return this.getCreationDate().toString();
	}
	
	/*
	 * Ausgeben der SerialVersionUID
	 */
	public long getSerialVersionUID() {
		return this.serialVersionUID;
	}
	
}
