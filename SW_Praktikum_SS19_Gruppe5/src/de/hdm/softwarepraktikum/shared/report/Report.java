package de.hdm.softwarepraktikum.shared.report;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * <p>
 * Diese Klasse ist die Basis aller Reports. Alle Reports sind Serializable,
 * sodass diese vom Server an den Client gesendet werden k�nnen. Der Zugriff
 * auf die Reports erfolgt lokal �ber den Client.
 * </p>
 * <p>
 * Jeder Report besitzt einige Standardelemente, welche in dieser Klasse
 * mit Hilfe von Attributen dargestellt und dokumentiert.
 * </p>
 * 
 * @author TimBeutelspacher
 *
 */
public class Report  implements IsSerializable {

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
		return imprint;
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
		return headerData;
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
	    return title;
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
		return creationDate;
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
	
	/**
     * Hier wird das Erstellungsdatum in einen String gespeichert und gek�rzt.
     *
     * @return Das zum Anzeigen formatierte Creationdate wird zur�ckgegeben.
     */
    public String getCreationDateString() {
    	
    	String creationDate = this.creationDate.toString().split("\\.")[0];
    	return creationDate;
	}
	
	
}
