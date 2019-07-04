package de.hdm.softwarepraktikum.shared.bo;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * <p>
 * Die Klasse <code>BusinessObject</code> stellt die Basisklasse aller in diesem
 * Projekt fuer die Umsetzung des Fachkonzepts relevanten Klassen dar. Hierbei hat 
 * jede Sub-Klasse eine ID.
 * </p>
 * <p>
 * Zentrales Merkmal ist, dass jedes <code>BusinessObject</code> eine Nummer
 * besitzt, die man in einer relationalen Datenbank auch als Primaerschluessel
 * bezeichnen wuerde. Fernen ist jedes <code>BusinessObject</code> als
 * gekennzeichnet. Durch diese Eigenschaft kann jedes <code>BusinessObject</code> 
 * automatisch in eine textuelle Form ueberfuehrt und z.B. zwischen Client und Server
 * transportiert werden.
 * </p>
 * 
 * @author TimBeutelspacher
 */

public abstract class BusinessObject implements IsSerializable{

	private static final long serialVersionUID = 1L;
	
	private Date creationDate;
	
	public BusinessObject() {	
	    this.setCreationDate(new Date());
		/*
		 * Setzen einer voraeufigen ID. Im Insert-Aufruf wird die Id gesetzt, welche mit der Datenbank
		 * konsisent ist.
		 */
		this.setId(1);	}
	
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
	 * Instanz. Kann in Sub-Klassen ueberschrieben werden
	 */ 
	public String toString() {
		/*
		 * Ausgabe des Klassennamens und der ID
		 */
		return this.getClass().getName() +" #" +this.getId();
	}
	
	/**
	 * Gleichheit zweier BusinessObjects ueberpruefen. Hierbei wird sich auf
	 * die ID's bezogen.
	 */
	@Override
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
	         * Falls etwas schief geht, wird false zurueckgegeben.
	         */
	        return false;
	      }
	    }
	    /*
	     * Wenn bislang keine Gleichheit bestimmt werden konnte, dann wird
	     * false zurueckgeben.
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
	@Override
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
	
	public String getCreationDateConvertToStringWithStyle() {
		String year = this.creationDate.toString().split("-")[0];
		String month = this.creationDate.toString().split("-")[1];
		String day = this.creationDate.toString().split("-")[2];
		
		String creationDate = day + "." + month + "." + year ;
		return creationDate;
	}
	
	/*
	 * Ausgeben der SerialVersionUID
	 */
	public long getSerialVersionUID() {
		return this.serialVersionUID;
	}
	
}
