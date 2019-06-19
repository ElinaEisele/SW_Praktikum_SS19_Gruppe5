package de.hdm.softwarepraktikum.shared.report;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Diese Klasse ist die Basis aller Reports. Alle Reports sind Serializable,
 * sodass diese vom Server an den Client gesendet werden können. Der Zugriff
 * auf die Reports erfolgt lokal über den Client.
 * </p>
 * <p>
 * Jeder Report besitzt einige Standardelemente, welche in dieser Klasse
 * mit Hilfe von Attributen dargestellt und dokumentiert.
 * </p>
 * 
 * @author TimBeutelspacher
 *
 */
public class Report  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default Konstruktor
	 */
	public Report() {
		
	}
	
	/**
	 * Impressum der MaulTasche GmbH. Hierbei werden Informationen wie Firmenname,
	 * Adresse, Logo, etc. 
	 */
	private Paragraph imprint = null;
	
	/**
	 * Kopfdaten den Berichts.
	 */
	private Paragraph headerData = null;
	
	/**
	 * Jeder Bericht kann einen individuellen Titel besitzen.
	 */
	private String title = "Report";

	/**
	 * Erstellungdatum des Berichts.
	 */
	private Date creationDate = new Date();

	/**
	 * Auslesen des Impressums.
	 * @return Text des Impressums
	 */
	public Paragraph getImprint() {
		return this.imprint;
	}

	/**
	 * Setzen des Impressums.
	 * @param imprint ist ein Paragraph-Objekt, welches das Impressum beinhaltet.
	 */
	public void setImprint(Paragraph imprint) {
		this.imprint = imprint;
	}

	/**
	 * Ausgeben der Kopfdaten des Berichts.
	 * @return Paragraph-Objekt, welches die Kopfdaten wiedergibt.
	 */
	public Paragraph getHeaderData() {
		return this.headerData;
	}

	/**
	 * Setzen der Kopfdaten des Berichts.
	 * @param headerData ist ein Paragraph-Objekt, welches die Kopfdaten enthaelt.
	 */
	public void setHeaderData(Paragraph headerData) {
		this.headerData = headerData;
	}
	
	  /**
	   * Ausgeben des Titels.
	   * @return Titeltext
	   */
	  public String getTitle() {
	    return this.title;
	  }

	  /**
	   * Setzen des Titels.
	   * @param title Titeltext
	   */
	  public void setTitle(String title) {
	    this.title = title;
	  }

	/**
	 * Ausgeben des Erstellungsdatums.
	 * @return Date-Objekt, welches das Erstellungsdatum wiederspiegelt.
	 */
	public Date getCreationDate() {
		return this.creationDate;
	}

	/**
	 * Setzten des Erstellungsdatums
	 * @param creationDate ist das Erstellungsdatum, welches gesetzt werden soll.
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Ausgeben der SerialVersionUID.
	 * @return SerialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
